package com.hivee2.mvp.model.biz;

import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;

/**
 * Created by gewubin on 2016/7/14
 * email: gewubin95@qq.com
 */
public interface IDeviceListBiz {
    public void getDeviceListByUserId(RequestParams params,JsonHttpRequestCallback jsonHttpRequestCallback);
}
