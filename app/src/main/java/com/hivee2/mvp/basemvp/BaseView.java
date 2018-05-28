package com.hivee2.mvp.basemvp;

import cn.finalteam.okhttpfinal.HttpCycleContext;

/**
 * Created by gewubin on 2016/7/13
 * email: gewubin95@qq.com
 */
public interface BaseView extends HttpCycleContext {
    void showLoading();
    void hideLoading();
    void showToast(String toastStr);
}
