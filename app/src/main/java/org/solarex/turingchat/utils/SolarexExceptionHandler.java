package org.solarex.turingchat.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SolarexExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "SolarexExceptionHandler";
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED){
            Logs.d(TAG, "uncaughtException | sdcar not ready");
            return;
        }
        String log_dir = Environment.getExternalStorageDirectory().getPath() + "/Solarex";
        File dir = new File(log_dir);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File log = new File(log_dir + "/crash.log");
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(log)));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = sdf.format(new Date(System.currentTimeMillis()));
            pw.write(time);
            pw.println();
            ex.printStackTrace(pw);
        } catch (Exception e){
            Logs.d(TAG, "uncaughtException | exception = " + e);
        } finally {
            AppUtils.close(pw);
        }
        return;
    }
}
