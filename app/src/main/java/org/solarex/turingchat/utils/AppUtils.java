package org.solarex.turingchat.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import java.io.Closeable;


public class AppUtils {
    private static final String TAG = "AppUtils";

    public static void close(Closeable closeable){
        if (closeable == null){
            return;
        }
        try {
            closeable.close();
        }catch (Exception ex){
            Logs.d(TAG, "close | exception = " + ex);
        } finally {
            try {
                closeable.close();
            }catch (Exception e){
                //ignore
            }
        }
    }

    public static void openUrl(Context context, String url){
        if (context == null || TextUtils.isEmpty(url)){
            Logs.d(TAG, "openUrl | context " + context + ",url = " + url);
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intent.resolveActivity(context.getPackageManager()) != null){
            context.startActivity(intent);
        }
        return;
    }

    public static float dp2px(Context context, float dp){
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        float px = dp * ((float)dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
