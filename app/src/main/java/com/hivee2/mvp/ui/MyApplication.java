package com.hivee2.mvp.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.hivee2.utils.SharePreferenceUtil;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;


public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    @Override
    public void onCreate() {
        super.onCreate();
        OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
//        builder.setDispatcher(new Dispatcher(Executors.newFixedThreadPool(5)));
        OkHttpFinal.getInstance().init(builder.build());
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JAnalyticsInterface.setDebugMode( true);
        JAnalyticsInterface.init( this);
        SDKInitializer.initialize(getApplicationContext());
        SharePreferenceUtil.init(getApplicationContext());
    }


    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        Intent intent = new Intent(this, getTopActivity());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
    public Class getTopActivity() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        String className = manager.getRunningTasks(1).get(0).topActivity.getClassName();
        Class cls = null;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }
}
