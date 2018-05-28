package com.hivee2.mvp.basemvp;

/**
 * Created by gewubin on 2016/7/13
 * email: gewubin95@qq.com
 * mvp模式presenter的父类
 */
public abstract class BasePresenter<T> {
    public T mView;

    //为presenter绑定view;
   public void attach(T mView){
        this.mView=mView;
    }
    //为presenter解除绑定的view;
    public void dettach(){
        mView=null;
    }
    public boolean isAttached(){
        if(mView==null){
            return false;
        }else{
            return true;
        }
    }
    public abstract void onResume();
}
