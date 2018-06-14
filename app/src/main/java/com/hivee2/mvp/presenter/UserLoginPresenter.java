package com.hivee2.mvp.presenter;

import android.content.SharedPreferences;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.content.Constant;
import com.hivee2.mvp.basemvp.BasePresenter;
import com.hivee2.mvp.model.bean.LoginBean;
import com.hivee2.mvp.model.biz.IUserBiz;
import com.hivee2.mvp.model.biz.UserBiz;
import com.hivee2.mvp.ui.view.IUserLoginView;

import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;


public class UserLoginPresenter extends BasePresenter<IUserLoginView>{
    private IUserBiz userBiz;
    private LoginBean userBean;

    public LoginBean getUserBean() {
        return userBean;
    }

    public void setUserBean(LoginBean userBean) {
        this.userBean = userBean;
    }

    public UserLoginPresenter()
    {
        this.userBiz = new UserBiz();
    }

    public void login(){
        if(!isAttached()){
            return;
        }
        mView.showLoading();

        RequestParams params = new RequestParams(mView);
        params.addFormDataPart("username", mView.getUserName());
        params.addFormDataPart("pwd", mView.getPassword());
        userBiz.login(params,new JsonHttpRequestCallback(){
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.i("shuju",jsonObject.toString());
               // System.out.println(jsonObject.toString());
                //解析json数据
                userBean= JSONObject.parseObject(jsonObject.toString(),LoginBean.class);
                if(userBean.getResult()!=0){//登录失败
                    mView.showToast(String.valueOf(userBean.getErrorMsg()));
                }else{
                    Constant.BaseUrl=userBean.getServer().getSystemServer();
                    Log.e("dizhi",Constant.BaseUrl);
                    initSharedPreferences();// sharedPreferences中初始化一些数据
                    mView.toMainActivity(userBean);


                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                if(!isAttached()){
                    return;
                }
                mView.showToast(errorCode+"网络不稳定请稍后再试");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(!isAttached()){
                return;
            }
            mView.hideLoading();
        }
        });
    }

    void initSharedPreferences(){
        if(!isAttached()){
            return;
        }
        SharedPreferences.Editor editor=mView.getSharedPreferences().edit();
        if (mView.getcheck1())
        {
            editor.putString("check1", "true");
            editor.putString("usename",mView.getUserName());
            editor.putString("password", mView.getPassword());

        }
        else {
            editor.putString("check1","false");
            editor.putString("usename","");
            editor.putString("password", "");
        }
        if(mView.getcheck2())
        {
            editor.putString("check2","true");
        }
        else {
            editor.putString("check2","false");
        }
        editor.putString("check3","false");
        editor.putString(Constant.sp_queryString,"");
        editor.putInt(Constant.sp_page, 1);
        editor.putString(Constant.sp_userId, userBean.getLinkUser());
        editor.putString(Constant.sp_login_userId, userBean.getUserID());

        editor.putString(Constant.sp_parentId, userBean.getUserID());
        editor.putString(Constant.sp_token,userBean.getToken());
        editor.putString(Constant.login_token,userBean.getToken());
        editor.putString(Constant.sp_customer,userBean.getResponse_Customer().getCustomerName());
        editor.putString(Constant.sp_display,mView.getDisplay());
        editor.putString("message",mView.getMessage());
        editor.commit();
    }

    @Override
    public void onResume() {

    }
}
