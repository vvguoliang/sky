package com.sky.beautiful;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by vvguoliang on 2017/12/18.
 * <p>
 * Application 基类启动
 */

public class SykApplication extends MultiDexApplication {


    public static boolean isDebug = true;
    public static String APP_NAME;

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        //获取Context
        context = getApplicationContext();
        //初始化handler
        mHandler = new Handler();
        // 设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取Context
        context = getApplicationContext();
        //初始化handler
        mHandler = new Handler();
        // 设置该CrashHandler为程序的默认处理器
        UnCeHandler catchExcep = new UnCeHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(catchExcep);
    }

    //返回
    public static Context getContextObject() {
        return context;
    }

    /**
     * 在主线程中刷新UI的方法
     *
     * @param r
     */
    public static void runOnUIThread(Runnable r) {
        SykApplication.getMainHandler().post(r);
    }

    //qcl用来在主线程中刷新ui
    private static Handler mHandler;

    public static Handler getMainHandler() {
        return mHandler;
    }
}
