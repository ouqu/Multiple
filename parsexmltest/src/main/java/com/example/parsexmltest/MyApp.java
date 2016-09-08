package com.example.parsexmltest;

import android.app.Application;
import android.content.Context;

/**
 * Created by hahaha on 9/1/16.
 */
public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
