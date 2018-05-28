package com.hivee2.mvp.ui.base;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;


public class BaseActivity extends FragmentActivity {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();//用于Activity或Frament生命周期结束后销毁页面所有正在执行的请求
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

}
