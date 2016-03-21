package org.solarex.turingchat.impl;

import org.solarex.turingchat.bean.Msg;
import org.solarex.turingchat.utils.AppUtils;
import org.solarex.turingchat.utils.JsonUtils;
import org.solarex.turingchat.utils.Logs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public class HttpFetchResult {

    static interface FetchCacllback{
        void onFetchSuccess(Msg message);
        void onFetchFailure(Msg message);
    }
    private static final String TAG = "HttpFetchResult";
    private static final String API_URL = "http://www.tuling123.com/openapi/api";
    private static final String API_KEY = "67b139f8ecc4c48d2387a12ad66c8c21";
    private static final String API_USERID = UUID.randomUUID().toString();
    private static final int CPU_COUNT = 4;
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = 2*CPU_COUNT + 1;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private AtomicInteger atomicInteger = new AtomicInteger();
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "SOLAREX-THREAD-" + atomicInteger.getAndIncrement());
        }
    };
    public static ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), sThreadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());

    public FetchCacllback mFetchCallback;
    public HttpFetchResult(FetchCacllback cacllback){
        mFetchCallback = cacllback;
    }

    public void doFetch(String userInput){
        sExecutor.execute(new FetchRunnable(userInput, mFetchCallback));
    }

    public void quitAllRequests() {
        sExecutor.shutdown();
    }

    private static class FetchRunnable implements Runnable{
        private static final Msg DEFAULT_MSG = Msg.createFrom(Msg.TYPE_ERROR, "default_message");
        private String input = null;
        private FetchCacllback mCallback = null;
        public FetchRunnable(String userInput, FetchCacllback callback){
            this.input = userInput;
            this.mCallback = callback;
        }
        @Override
        public void run() {
            String url = buildURL(input);
            HttpURLConnection conn = null;
            InputStream is = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setReadTimeout(5 * 1000);
                conn.setConnectTimeout(5 * 1000);
                conn.setRequestMethod("GET");
                conn.connect();

                int sc = conn.getResponseCode();
                Logs.d(TAG, "run | status code = " + sc);
                if (sc == 200){
                    StringBuilder sb = new StringBuilder();
                    is = conn.getInputStream();
                    int len;
                    byte[] buffer = new byte[1024];
                    while ((len = is.read(buffer)) != -1){
                        sb.append(new String(buffer, 0, len));
                    }
                    Logs.d(TAG, "run | json = " + sb.toString());
                    Msg msg = JsonUtils.parse(sb.toString());
                    if (msg != null /**/){
                        mCallback.onFetchSuccess(msg);
                    } else {
                        mCallback.onFetchFailure(DEFAULT_MSG);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logs.d(TAG, "run | exception = " + e);
                mCallback.onFetchFailure(DEFAULT_MSG);
            } finally {
                AppUtils.close(is);
                if (conn != null){
                    try {
                        conn.disconnect();
                    }catch (Exception ex){
                        Logs.d(TAG, "run | exception = " + ex);
                    }
                }
            }
        }
    }

    private static String buildURL(String input) {
        StringBuilder sb = new StringBuilder(API_URL);
        sb.append("?key=" + API_KEY + "&userid="+API_USERID);
        try {
            sb.append("&info=" + URLEncoder.encode(input, "utf-8"));
        }catch (UnsupportedEncodingException ex){
            Logs.d(TAG, "buildURL | ex = " + ex);
        }
        Logs.d(TAG, "buildURL | url = " + sb.toString());
        return sb.toString();
    }
}
