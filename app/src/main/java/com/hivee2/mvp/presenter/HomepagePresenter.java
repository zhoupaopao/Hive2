package com.hivee2.mvp.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hivee2.R;
import com.hivee2.content.Constant;
import com.hivee2.mvp.basemvp.BasePresenter;
import com.hivee2.mvp.model.bean.DeviceBean;
import com.hivee2.mvp.model.bean.LoginBean;
import com.hivee2.mvp.model.biz.DeviceListBiz;
import com.hivee2.mvp.model.biz.IDeviceListBiz;
import com.hivee2.mvp.ui.view.IHomepageView;

import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by gewubin on 2016/7/14
 * email: gewubin95@qq.com
 * 对viewz操作之前要调用isAttached判断是否返回为true(特别是耗时操作后)  true才可操作  true表示view!=null
 */
public class HomepagePresenter extends BasePresenter<IHomepageView> {
    private IDeviceListBiz deviceListBiz;
    private DeviceBean deviceBean;
    private LoginBean userBean;
    private int currentDeviceIndex=-1;//初始化 当前车辆索引
    private boolean hasCarInMap=false;//status==0的车才会显示在地图上。  此参数判断地图上是否有车

    public int getCurrentDeviceIndex() {
        return currentDeviceIndex;
    }

    public void setCurrentDeviceIndex(int currentDeviceIndex) {
        this.currentDeviceIndex = currentDeviceIndex;
    }
   public  HomepagePresenter(){
        deviceListBiz=new DeviceListBiz();


    }
    @Override
    public void onResume() {

    }
    //
    public void getDeviceListByUserId(){
        System.out.println("getDeviceListByUserId");


        SharedPreferences sp=mView.getSharedPreferences();//得到sharePreferences对象

        RequestParams params = new RequestParams(mView);

        params.addFormDataPart("userID", sp.getString(Constant.sp_userId,""));
        Log.e("feixing1",sp.getString(Constant.sp_userId,""));
        params.addFormDataPart("onLineType", 0);
        params.addFormDataPart("isBindCar", 0);
        params.addFormDataPart("queryString", sp.getString(Constant.sp_queryString,""));
        Log.e("feixing2",sp.getString(Constant.sp_queryString,""));
        params.addFormDataPart("page", 1);
        params.addFormDataPart("pageSize", ( Constant.pageSize*sp.getInt(Constant.sp_page,1) )  );
        Log.e("feixing0",Constant.pageSize*sp.getInt(Constant.sp_page,1)+"");
        params.addFormDataPart("sortName", "");
        params.addFormDataPart("asc", "");
        params.addFormDataPart("showChild", true);
        params.addFormDataPart("tokenString", sp.getString(Constant.sp_token, ""));
        Log.e("feixing3", sp.getString(Constant.sp_token, ""));
        deviceListBiz.getDeviceListByUserId(params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                if (isAttached()) {
                    System.out.println(jsonObject.toString());
                    Log.e("FSADF",jsonObject.toString());
                    deviceBean = JSONObject.parseObject(jsonObject.toString(), DeviceBean.class);
                    if (deviceBean.getResult() == 0) {
                        //成功
                        baidumapAddMarker();
                    } else {
                        mView.showToast(deviceBean.getErrorMsg());
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Log.e("okhttp", errorCode + "" + msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (isAttached()) {
                    mView.hideLoading();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                if (isAttached()) {
                    mView.showLoading();
                }
            }
        });
    }

    //根据deviceBean.getDataList()在地图上画出车辆图标  并将其在list中的索引存入 marker的title中
    void baidumapAddMarker(){
        if(!isAttached()){
            return;
        }
        BaiduMap baiduMap=mView.getBaiduMap();
        baiduMap.clear();
        hasCarInMap=false;
        //LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(int i=0;i<deviceBean.getDataList().size();i++){

            DeviceBean.CarListBean carListBean=deviceBean.getDataList().get(i);
            if(carListBean.getStatus()==0){// 只有status==0的才显示在地图上
                hasCarInMap=true;

                System.out.println("lat-->" + carListBean.getBLat());
                LatLng markerll = new LatLng(carListBean.getBLat(), carListBean.getBLng());
                BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.car_static1);
                if (carListBean.isIsOnline()) {// true为在线
                    bd = BitmapDescriptorFactory.fromResource(R.drawable.car_online1);

                } else {
                    bd = BitmapDescriptorFactory.fromResource(R.drawable.car_static1);
                }
                OverlayOptions oo = new MarkerOptions().icon(bd).position(markerll);// 图标
                Marker marker = (Marker) (baiduMap.addOverlay(oo));
                marker.setTitle(i+"");

            }
        }

        //地图上加载好车辆后 将地图移动到第一辆车处
        currentCar();
    }

    public void currentCar(){
        if(!isAttached()){
            return;
        }
        if(currentDeviceIndex>=deviceBean.getDataList().size()){
                //当前车辆索引 数组越界 则初始化这个参数
            currentDeviceIndex=-1;
        }
        rightNextCar();

    }

    public void leftNextCar(){
        if (!hasCarInMap) {
            //地图中没有车
            mView.showToast("地图中暂无车辆");
            return;
        } else {
            currentDeviceIndex = currentDeviceIndex - 1;
            if (currentDeviceIndex <0 ) {
                currentDeviceIndex = deviceBean.getDataList().size()-1;//到第一个就移回最后一个
            }
            // 只有status==0的才显示在地图上
            if (deviceBean.getDataList().get(currentDeviceIndex).getStatus() == 0) {
                //在地图上显示
                showInfowindow(currentDeviceIndex);
            } else {
                leftNextCar();
            }
        }


    }

    public void rightNextCar() {
        if (!hasCarInMap) {
            //地图中没有车
            mView.showToast("地图中暂无车辆");
            return;
        } else {
            currentDeviceIndex = currentDeviceIndex + 1;
            if (currentDeviceIndex >= deviceBean.getDataList().size()) {
                currentDeviceIndex = 0;//到最后就移回第一个
            }
            // 只有status==0的才显示在地图上
            if (deviceBean.getDataList().get(currentDeviceIndex).getStatus() == 0) {
                //在地图上显示
                showInfowindow(currentDeviceIndex);
            } else {
                rightNextCar();
            }
        }
    }

   public void showInfowindow(int index){
        mView.showInfowindow(deviceBean.getDataList().get(index));
    }

    public DeviceBean getDeviceBean() {
        return deviceBean;
    }

    public void setDeviceBean(DeviceBean deviceBean) {
        this.deviceBean = deviceBean;
    }

    public LoginBean getUserBean() {
        return userBean;
    }

    public void setUserBean(LoginBean userBean) {
        this.userBean = userBean;
    }


}
