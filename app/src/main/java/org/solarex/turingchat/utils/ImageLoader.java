package org.solarex.turingchat.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.util.LruCache;
import android.widget.ImageView;

import org.solarex.turingchat.R;
import org.solarex.turingchat.impl.HttpFetchResult;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;

public class ImageLoader {
    private static final String TAG = "ImageLoader";
    public static final int MSG_POST_RESULT = 1;

    private static final long DISK_CACHE_SIZE = 10*1024*1024;
    private static final int IO_BUFFER_SIZE = 8*1024;
    private static final int DISK_CACHE_INDEX = 0;
    private boolean mIsDiskLruCacheCreated = false;

    private static final int IMAGE_TAG = R.id.item_cook_iv_icon & R.id.item_news_iv_icon;

    private static ImageLoader sInstance = null;

    private Handler mMainHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MSG_POST_RESULT:
                    LoaderResult rlt = (LoaderResult)msg.obj;
                    ImageView imageView = rlt.imageView;
                    String uri = rlt.uri;
                    String tag = (String)imageView.getTag();
                    if (uri.equals(tag)){
                        imageView.setImageBitmap(rlt.bitmap);
                    } else {
                        Logs.d(TAG, "handlerMessage | item recycled");
                    }
            }
        }
    };


    private Context mContext;
    private ImageResizer mImageResizer = new ImageResizer();

    private LruCache<String, Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private ImageLoader(Context context){
        mContext = context.getApplicationContext();
        int maxMemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };

        File diskCacheDir = getDiskCacheDir(mContext, "bitmap_cache");
        if (!diskCacheDir.exists()){
            diskCacheDir.mkdirs();
        }

        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE){
            try{
                mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1, DISK_CACHE_SIZE);
                mIsDiskLruCacheCreated = true;
            } catch (Exception e){
                Logs.d(TAG, "ImageLoader | create disk cache exception = " + e);
            }
        }
    }

    private long getUsableSpace(File diskCacheDir) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            return diskCacheDir.getUsableSpace();
        }
        final StatFs stats = new StatFs(diskCacheDir.getPath());
        return (long)stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }

    private File getDiskCacheDir(Context mContext, String uniqueName) {
        boolean externalStroageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStroageAvailable){
            cachePath = mContext.getExternalCacheDir().getPath();
        } else {
            cachePath = mContext.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    public static ImageLoader getInstance(Context context){
        if (sInstance == null){
            synchronized (ImageLoader.class){
                if (sInstance == null){
                    sInstance = new ImageLoader(context);
                }
            }
        }
        return sInstance;
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap){
        if (getBitmapFromMemoryCache(key) == null){
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key){
        return mMemoryCache.get(key);
    }

    public void bindBitmap(final String uri, final ImageView imageView){
        bindBitmap(uri, imageView, 0, 0);
    }

    public void bindBitmap(String uri, ImageView imageView, final int reqWidth, final int reqHeight) {
        imageView.setTag(IMAGE_TAG, uri);
        Bitmap bitmap = loadBitmapFromMemCache(uri);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            return;
        }

        HttpFetchResult.sExecutor.execute(new LoadBitmapTask(uri, imageView, reqWidth, reqHeight));
    }

    public class LoadBitmapTask implements Runnable{
        private static final String TAG = "LoadBitmapTask";
        private String mUri = "";
        private WeakReference<ImageView> mImageView = null;
        private int mWidth, mHeight;
        public LoadBitmapTask(String uri, ImageView imageView, int reqWidth, int reqHeight){
            mUri = uri;
            mImageView = new WeakReference<ImageView>(imageView);
            mWidth = reqWidth;
            mHeight = reqHeight;
        }


        @Override
        public void run() {
            Bitmap bitmap = loadBitmap(mUri, mWidth, mHeight);
            if (bitmap != null){
                if (mImageView != null && mImageView.get() != null){
                    LoaderResult rlt = new LoaderResult(mImageView.get(), mUri, bitmap);
                    mMainHandler.obtainMessage(MSG_POST_RESULT, rlt).sendToTarget();
                }
            }
        }
    }

    private Bitmap loadBitmap(String mUri, int mWidth, int mHeight) {
        Bitmap bitmap = loadBitmapFromMemCache(mUri);
        if (bitmap != null){
            Logs.d(TAG, "loadBitmap | load bitmap from memory cache, uri = " + mUri);
            return bitmap;
        }

        try {
            bitmap = loadBitmapFromDiskCache(mUri, mWidth, mHeight);
            if (bitmap != null){
                Logs.d(TAG, "loadBitmap | load bitmap from disk cache, uri = " + mUri);
                return bitmap;
            }
            bitmap = loadBitmapFromNetwork(mUri, mWidth, mHeight);
            Logs.d(TAG, "loadBitmap | load bitmap from network, uri = " + mUri);
        } catch (Exception e){
            Logs.d(TAG, "loadBitmap | exception = " + e);
        }

        if (bitmap == null && !mIsDiskLruCacheCreated){
            Logs.d(TAG, "loadBitmap | encounter error, DiskLruCache has not been created");
            bitmap = downloadBitmapFromNetwork(mUri);
        }
        return bitmap;
    }


    private Bitmap loadBitmapFromDiskCache(String mUri, int mWidth, int mHeight) throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()){
            Logs.d(TAG, "loadBitmapFromDiskCache | time consuming work should not operated on main thread");
        }
        if (mDiskLruCache == null){
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFromUri(mUri);
        DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
        if (snapshot != null){
            FileInputStream fileInputStream = (FileInputStream)snapshot.getInputStream(DISK_CACHE_INDEX);
            FileDescriptor fd = fileInputStream.getFD();
            bitmap = mImageResizer.decodeSampledBitmapFromFileDescriptor(fd, mWidth, mHeight);
            if (bitmap != null){
                addBitmapToMemoryCache(key, bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromNetwork(String mUri, int mWidth, int mHeight) throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()){
            Logs.d(TAG, "loadBitmapFromNetwork | time consuming work should not operated on main thread");
        }
        if (mDiskLruCache == null){
            return null;
        }
        String key = hashKeyFromUri(mUri);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (dowloadUriToOutputStream(mUri, outputStream)){
                editor.commit();
            } else {
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return loadBitmapFromDiskCache(mUri, mWidth, mHeight);
    }

    private boolean dowloadUriToOutputStream(String mUri, OutputStream outputStream) {
        HttpURLConnection conn = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            conn = (HttpURLConnection) new URL(mUri).openConnection();
            bis = new BufferedInputStream(conn.getInputStream(), IO_BUFFER_SIZE);
            bos = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int bytesRead = -1;
            while ((bytesRead = bis.read()) != -1){
                bos.write(bytesRead);
            }
            return true;
        } catch (Exception e){
            Logs.d(TAG, "dowloadUriToOutputStream | exception = " + e);
        } finally {
            if (conn != null){
                conn.disconnect();
            }
            AppUtils.close(bos);
            AppUtils.close(bis);
        }
        return false;
    }

    private Bitmap downloadBitmapFromNetwork(String mUri) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        BufferedInputStream bis = null;
        try {
            conn = (HttpURLConnection)new URL(mUri).openConnection();
            bis = new BufferedInputStream(conn.getInputStream(), IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(bis);
        } catch (Exception ex){
            Logs.d(TAG, "downloadBitmapFromNetwork | exception = " + ex);
        } finally {
            if (conn != null){
                conn.disconnect();
            }
            AppUtils.close(bis);
        }
        return bitmap;
    }

    private Bitmap loadBitmapFromMemCache(String uri) {
        final String key = hashKeyFromUri(uri);
        Bitmap bitmap = getBitmapFromMemoryCache(key);
        return bitmap;
    }

    private String hashKeyFromUri(String uri) {
        String cacheKey;
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(uri.getBytes());
            cacheKey = convertBytesToHexString(md.digest());
        } catch (Exception ex){
            Logs.d(TAG, "hashKeyFromUri | exception = " + ex);
            cacheKey = String.valueOf(uri.hashCode());
        }
        return cacheKey;
    }

    private String convertBytesToHexString(byte[] digest) {
        StringBuilder sb = new StringBuilder();
        String hex = "";
        for (int i = 0; i < digest.length; i++){
            hex = Integer.toHexString(0xff & digest[i]);
            if (hex.length() == 1){
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap){
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }

    }
}
