package com.hivee2.mvp.ui.view;

import android.content.SharedPreferences;

import com.hivee2.mvp.basemvp.BaseView;
import com.hivee2.mvp.model.bean.LoginBean;


public interface IUserLoginView extends BaseView{
    String getDisplay();
    String getUserName();
   ;Boolean getcheck1();
    Boolean getcheck2();
    String getPassword();
    String getMessage();

    void toMainActivity(LoginBean user);

    void showFailedError();

    SharedPreferences getSharedPreferences();


}
