package com.example.balagnese.testapp;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext(); // Grab the Context you want.
    }

    public static Context getContext() {
        return context;
    }
}
