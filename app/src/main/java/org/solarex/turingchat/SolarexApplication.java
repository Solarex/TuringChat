package org.solarex.turingchat;

import android.app.Application;

import org.solarex.turingchat.utils.SolarexExceptionHandler;


public class SolarexApplication extends Application {
    private static SolarexApplication sInstance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Thread.currentThread().setDefaultUncaughtExceptionHandler(new SolarexExceptionHandler());
    }

    public static SolarexApplication getsInstance(){
        return sInstance;
    }
}
