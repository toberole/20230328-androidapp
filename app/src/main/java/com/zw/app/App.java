package com.zw.app;


import android.app.Application;

public class App extends Application {
    static {
        System.loadLibrary("app");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
