package com.hivee2.mvp.model.biz;

import com.hivee2.content.Api;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;


public class UserBiz implements IUserBiz {
    @Override
    public void login(RequestParams params, final JsonHttpRequestCallback jsonHttpRequestCallback) {

        HttpRequest.post(Api.LOGIN, params, jsonHttpRequestCallback);
    }
}