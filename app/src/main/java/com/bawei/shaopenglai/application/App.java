package com.bawei.shaopenglai.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
/**
 *
 * @author Peng
 * @time 2019/1/16 14:00
 *      图片加载是Android开发中最最基础的功能，同时图片加载OOM也一直困扰着很多开发者，
 *      因此为了降低开发周期和难度，我们经常会选用一些图片加载的开源库。
 *      老牌的有ImageLoader，UIL,Volley，主流的有，Picasso，Glide，Fresco等等，选择一款好的图片加载裤就成了我们的首要问题
 * -
 */

        /**
         * Java Heap是对于Java 虚拟机而说的，一般的大小上限是 16M 24M 48M 76M 具体视手机而定。
         * Native Heap是对于C/C++直接操纵的系统堆内存，所以它的上限一般是具体RAM的2/3左右。
         * 所以对于2G的手机而言，Java Heap 大概76M，而Native Heap是760M左右，相差10倍
         *
         *
         * 所以Fresco也是存在一定风险的，因为native heap数据实在是太恐怖了。
         */
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
