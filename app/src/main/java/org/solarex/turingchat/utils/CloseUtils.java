package org.solarex.turingchat.utils;

import java.io.Closeable;


public class CloseUtils {
    private static final String TAG = "CloseUtils";

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
}
