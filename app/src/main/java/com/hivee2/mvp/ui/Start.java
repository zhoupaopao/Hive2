package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import com.hivee2.R;
import com.hivee2.mvp.ui.mvpactivity.LoginActivity;
import com.hivee2.utils.HiveUtil;

public class Start extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Intent intent=new Intent(Start.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HiveUtil ut=new HiveUtil();
        ut.onResumePage(this,this.getClass().getCanonicalName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());
    }
}
