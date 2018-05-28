package com.hivee2.mvp.basemvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import cn.finalteam.okhttpfinal.HttpTaskHandler;

/**
 * Created by gewubin on 2016/7/13
 * email: gewubin95@qq.com
 * mvp模式activity的父类
 */
public abstract class  BaseMvpActivity<V,T extends BasePresenter<V>> extends Activity {
    public T mPresenter;
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();//用于Activity或Frament生命周期结束后销毁页面所有正在执行的请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mPresenter=initPresenter();//初始化 实例化
        mPresenter.attach((V)this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onDestroy() {
        HttpTaskHandler.getInstance().removeTask(HTTP_TASK_KEY);//Activity或Frament生命周期结束后销毁页面所有正在执行的请求
        mPresenter.dettach();//view层销毁的时候 presenter解除绑定view  避免一直持有view对象导致内存泄漏

        super.onDestroy();
    }

    public abstract T initPresenter();


}
