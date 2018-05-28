package com.hivee2.mvp.model.biz;

import android.util.Log;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class MyStringCallBack extends StringCallback {

    public MyStringCallBack(){
        Log.i("MyStringCallBack: ", "1");
    }

    @Override
    public void onError(Call call, Exception e, int id) {
        Log.i("MyStringCallBack: ", "2");
    }

    @Override
    public void onResponse(String response, int id) {
        Log.i("MyStringCallBack: ", "3");
    }

}
