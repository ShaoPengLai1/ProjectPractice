package com.bawei.shaopenglai.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;

public class App extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mContext=getApplicationContext();
        //backPage();
    }
    public static Context getApplication(){
        return mContext;
    }
    private long exitTime = 0;


}
