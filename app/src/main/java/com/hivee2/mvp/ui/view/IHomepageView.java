package com.hivee2.mvp.ui.view;

import android.content.SharedPreferences;

import com.baidu.mapapi.map.BaiduMap;
import com.hivee2.mvp.basemvp.BaseView;
import com.hivee2.mvp.model.bean.DeviceBean;

/**
 * Created by gewubin on 2016/7/14
 * email: gewubin95@qq.com
 */
public interface IHomepageView extends BaseView{

    SharedPreferences getSharedPreferences();
    BaiduMap getBaiduMap();
    void showInfowindow(DeviceBean.CarListBean carListBean);
}
