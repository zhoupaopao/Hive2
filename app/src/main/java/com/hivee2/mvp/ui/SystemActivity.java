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

import com.hivee2.R;
import com.hivee2.mvp.ui.mvpactivity.LoginActivity;

/**
 * Created by 狄飞 on 2016/7/26.
 */
public class SystemActivity extends Activity {
     private LinearLayout EditName;
     private LinearLayout CarShow;
    private LinearLayout About;
    private LinearLayout Editlogin;
    private ImageView Back;
    private TextView title;
    private TextView about;
    public String check1;
    public String check2;
    public String user="";
    public String passName="";
    private String message="";
    private SharedPreferences sp=null;// 存放用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        SysApplication.getInstance().addActivity(this);
        init();
        initlisten();
    }
    public void  init()
    {
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        check1= sp.getString("check1", "");
        check2=sp.getString("check2", "");
        user=sp.getString("usename","");
        passName=sp.getString("password","");
        message=sp.getString("message","");
        SharedPreferences.Editor editor=sp.edit();

        EditName=(LinearLayout)findViewById(R.id.editname);
        CarShow=(LinearLayout)findViewById(R.id.car_show);
        About=(LinearLayout)findViewById(R.id.about);
        Editlogin=(LinearLayout)findViewById(R.id.editlogin);
        Back=(ImageView)findViewById(R.id.imageView2);
        title=(TextView)findViewById(R.id.title_name1);
        about=(TextView)findViewById(R.id.textView2);
        title.setText("系统设置");
    }
    public void initlisten()
    {
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Editlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SystemActivity.this,LoginActivity.class);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("usename",user);
                editor.putString("password",passName);
                editor.putString("check1", check1);
                editor.putString("message",message);
                Log.e("456-->",check2);
                editor.putString("check3", "true");
                editor.commit();
                intent.putExtra("autologinflag", true);
                startActivity(intent);
                finish();
            }
        });
        CarShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemActivity.this,DisplayActivity.class);
                startActivity(intent);
            }
        });
        EditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemActivity.this,PasswordChange.class);
                startActivity(intent);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemActivity.this, About.class);
                startActivity(intent);
            }
        });
    }
}
