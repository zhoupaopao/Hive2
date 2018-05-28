package com.hivee2.mvp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.hivee2.R;
import com.hivee2.utils.HiveUtil;

/**
 * Created by 13126 on 2017/7/4.
 */

public class ChoiseActivity extends Activity {
    private Button sbbj;
    private Button sdbj;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise);
        initView();
        initListener();
    }

    private void initListener() {
        sbbj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView() {
        sbbj= (Button) findViewById(R.id.sbbj);
        sdbj= (Button) findViewById(R.id.sdbj);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
