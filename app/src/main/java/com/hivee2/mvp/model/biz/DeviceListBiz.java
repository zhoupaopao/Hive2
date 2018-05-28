package com.hivee2.mvp.model.biz;

import android.util.Log;

import com.hivee2.content.Api;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by gewubin on 2016/7/14
 * email: gewubin95@qq.com
 */
public class DeviceListBiz implements IDeviceListBiz{
    @Override
    public void getDeviceListByUserId(RequestParams params, JsonHttpRequestCallback jsonHttpRequestCallback) {
        HttpRequest.post(Api.GET_DEVICE_BY_USERID, params, jsonHttpRequestCallback);
        Log.e("GET_DEVICE_BY_USERID",Api.GET_DEVICE_BY_USERID);
    }
}
