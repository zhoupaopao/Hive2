package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hivee2.R;
import com.hivee2.content.Constant;
import com.hivee2.utils.HiveUtil;

/**
 * Created by 狄飞 on 2016/10/10.
 */
public class DisplayActivity extends Activity {
    private RelativeLayout back;
    private TextView title;
    private  TextView save;
    private CheckBox checkBox;
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    public String customer="";
    public String check1;
    public String check2;
    public String check3;
    public String user="";
    public String passName="";
    public String display="";
    public String display1="";
    public String ID1="";
    public String ID2="";
    public String token1="";
    public String token2="";
    private String message="";
    private ProgressDialog progressDialog;
    private SharedPreferences sp=null;// 存放用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);
        init();
        listen();
    }
    public  void  init()
    {
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        check1= sp.getString("check1", "");
        check2=sp.getString("check2", "");
        check3=sp.getString("check3", "");
        user=sp.getString("usename", "");
        passName=sp.getString("password", "");
        display=sp.getString(Constant.sp_display,"");
        ID1=sp.getString(Constant.sp_userId, "");
        ID2=sp.getString(Constant.sp_parentId, "");
        token1=sp.getString(Constant.sp_token, "");
        token2=sp.getString(Constant.login_token,"");
        customer=sp.getString(Constant.sp_customer, "");
        message=sp.getString("message","");




        back=(RelativeLayout)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title_name);
        title.setText("车辆显示设置");
        save=(TextView)findViewById(R.id.title_select);
        save.setText("保存");
        checkBox=(CheckBox)findViewById(R.id.checkBox);
        checkBox1=(CheckBox)findViewById(R.id.checkBox1);
        checkBox2=(CheckBox)findViewById(R.id.checkBox2);
        checkBox3=(CheckBox)findViewById(R.id.checkBox3);
        checkBox4=(CheckBox)findViewById(R.id.checkBox4);
        checkBox5=(CheckBox)findViewById(R.id.checkBox5);
        checkBox6=(CheckBox)findViewById(R.id.checkBox6);
        checkBox7=(CheckBox)findViewById(R.id.checkBox7);
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DisplayActivity.this.finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                display="";
                if(checkBox.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox1.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox2.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox3.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox4.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox5.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox6.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                if(checkBox7.isChecked())
                {
                    display1="1";
                }else
                {
                    display1="0";
                }
                display=display+display1;
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("check1",check1);
                editor.putString("usename",user);
                editor.putString("password", passName);
                editor.putString("check2",check2);
                editor.putString("check3",check3);
                editor.putString(Constant.sp_queryString, "");
                editor.putInt(Constant.sp_page, 1);
                editor.putString(Constant.sp_userId, ID1);
                editor.putString(Constant.sp_parentId,ID2);
                editor.putString(Constant.sp_token,token1);
                editor.putString(Constant.login_token,token2);
                editor.putString(Constant.sp_customer, customer);
                editor.putString(Constant.sp_display,display);
                editor.putString("message",message);
                editor.commit();
                Constant.atychildaccoumt=true;
                Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();// 显示时间较短
            }
        });
    }
    public void listen()
    {
        if(display.substring(0,1).equals("1"))
        {
            checkBox.setChecked(true);
        }
        else {
            checkBox.setChecked(false);
        }
        if(display.substring(1,2).equals("1"))
        {
            checkBox1.setChecked(true);
        }
        else {
            checkBox1.setChecked(false);
        }
        if(display.substring(2,3).equals("1"))
        {
            checkBox2.setChecked(true);
        }
        else {
            checkBox2.setChecked(false);
        }
        if(display.substring(3,4).equals("1"))
        {
            checkBox3.setChecked(true);
        }
        else {
            checkBox3.setChecked(false);
        }
        if(display.substring(4,5).equals("1"))
        {
            checkBox4.setChecked(true);
        }
        else {
            checkBox4.setChecked(false);
        }
        if(display.substring(5,6).equals("1"))
        {
            checkBox5.setChecked(true);
        }
        else {
            checkBox5.setChecked(false);
        }
        if(display.substring(6,7).equals("1"))
        {
            checkBox6.setChecked(true);
        }
        else {
            checkBox6.setChecked(false);
        }
        if(display.substring(7,8).equals("1"))
        {
            checkBox7.setChecked(true);
        }
        else {
            checkBox7.setChecked(false);
        }
        Log.e("ZIFUDUAN",display.substring(0,1));
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
