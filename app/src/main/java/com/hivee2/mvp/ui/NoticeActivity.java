package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.mvp.model.bean.JpushBean;
import com.hivee2.utils.HiveUtil;

/**
 * Created by 狄飞 on 2016/9/20.
 */
public class NoticeActivity extends Activity {
    private ImageView back;
    private TextView title;
    private LinearLayout check;
    private String message="";
    private TextView PledgerName;
    private TextView CarNumber;
    private TextView InternalNum;
    private TextView AlarmType;
    private TextView PositionTime;
    private TextView detail;
    private String userid="";
    private SharedPreferences sp=null;// 存放用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice);
        init();

    }
    private void init()
    {
        back=(ImageView)findViewById(R.id.imageView2);
        title=(TextView)findViewById(R.id.title_name1);
        check=(LinearLayout)findViewById(R.id.check);
        PledgerName=(TextView)findViewById(R.id.textView);
        CarNumber=(TextView)findViewById(R.id.textView2);
        InternalNum=(TextView)findViewById(R.id.textView1);
        AlarmType=(TextView)findViewById(R.id.textView4);
        PositionTime=(TextView)findViewById(R.id.textView5);
//        detail=(TextView)findViewById(R.id.textView6);

        title.setText("通知");
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        message=sp.getString("message", "");
        Log.e("UUUU", message);
        if(message.equals(""))
        {}
        else {
            final JpushBean jpushBean = JSONObject.parseObject(message, JpushBean.class);
            PledgerName.setText("借款人：  "+jpushBean.getPledgerName());
            CarNumber.setText("车牌号： "+jpushBean.getCarNumber());
            InternalNum.setText("设备号：  "+jpushBean.getInternalNum());
            AlarmType.setText("报警类型： "+jpushBean.getAlarmType());
            PositionTime.setText("报警时间： " + jpushBean.getPositionTime());

            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(jpushBean.getLat()==null||jpushBean.getLng()==null){
                        Toast.makeText(NoticeActivity.this,"没有报警数据",Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(NoticeActivity.this, AlertMap.class);
                        intent.putExtra("Lat", jpushBean.getLat());
                        intent.putExtra("Lng", jpushBean.getLng());
                        startActivity(intent);
                    }

                }
            });
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        detail.setVisibility(View.GONE);

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
