package com.bawei.shaopenglai.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

public class App extends Application {

    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=getApplicationContext();
        //backPage();
    }
    public static Context getApplication(){
        return mContext;
    }
    private long exitTime = 0;
    /*private void backPage(){
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
                    //   scroll.setVisibility(View.VISIBLE);
                    //  byName.setVisibility(View.GONE);
                    if(System.currentTimeMillis()-exitTime>2000){
                        exitTime=System.currentTimeMillis();
                    }else {
                        //启动一个意图,回到桌面
                        Intent backHome = new Intent(Intent.ACTION_MAIN);
                        backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        backHome.addCategory(Intent.CATEGORY_HOME);
                        startActivity(backHome);
                    }
                    return true;
                }else {
                    return false;
                }
            }
        });
    }*/

}
