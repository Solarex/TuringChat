package org.solarex.turingchat.utils;

import android.text.TextUtils;
import android.util.Log;


public class Logs {
    private static final String DEFAULT_TAG = "Solarex";
    private static boolean isDebug = true;

    public static void d(String tag, String msg){
        if (isDebug){
            if (!TextUtils.isEmpty(tag)){
                Log.d(tag, msg == null ? "null" : msg);
            } else {
                Log.d(DEFAULT_TAG, msg == null ? "null" : msg);
            }
        }
    }
}
