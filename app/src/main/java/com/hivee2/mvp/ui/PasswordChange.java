package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.ui.mvpactivity.LoginActivity;
import com.hivee2.utils.HiveUtil;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/26.
 */
public class PasswordChange extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private EditText old;
    private EditText new1;
    private  EditText newagain;
    private TextView exange;
    private  TextView title;
    private ImageView back;
    public int a=1 ;
    public String token="";
    private SharedPreferences sp=null;// 存放用户信息

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.login_token, "");
        SysApplication.getInstance().addActivity(this);
        init();
        initlisten();
    }
    private void init()
    {
          old=(EditText)findViewById(R.id.editText13);
          new1=(EditText)findViewById(R.id.editText1211);
          newagain=(EditText)findViewById(R.id.editText111);
         exange=(TextView)findViewById(R.id.textView46);
         back=(ImageView)findViewById(R.id.imageView2);
         title=(TextView)findViewById(R.id.title_name1);
         title.setText("修改密码");
    }
    private void initlisten()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(a==0)
                {Intent intent = new Intent(PasswordChange.this,LoginActivity.class);
                    intent.putExtra("autologinflag", true);
                    startActivity(intent);
                    finish();
                }
                else {
                    finish();
                }
            }
        });
        exange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("888--->77","88"+new1.getText().toString().length());
            if(old.getText().toString().equals(""))
            {
                Toast.makeText(getApplicationContext(), "必须输入老密码！",
                        Toast.LENGTH_SHORT).show();
            }
                else if(new1.getText().toString().length()<6)
            {
                Toast.makeText(getApplicationContext(), "新密码不能少于6个字符!",
                        Toast.LENGTH_SHORT).show();
            }
                else if(new1.getText().toString().equals(newagain.getText().toString()))
            {

                RequestParams params = new RequestParams(PasswordChange.this);

                params.addFormDataPart("oldPwd",old.getText().toString());
                params.addFormDataPart("newPwd",new1.getText().toString());
                params.addFormDataPart("TokenString", token);
                //  地址  参数  回调函数
                HttpRequest.post(Api.SET_PWD, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("-------->", jsonObject.toString());
                        BaseApiResponse baseApiResponse = JSONObject.parseObject(jsonObject.toString(),BaseApiResponse.class);
                        a=baseApiResponse.getResult();
                        if(a==0)
                        {
                            Toast.makeText(getApplicationContext(), "修改密码成功，请重新登录！",
                                    Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(getApplicationContext(), "原始密码不正确！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        Log.e("alertMsg failure", errorCode + msg);

                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                        //show  progressdialog
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        //hide progressdialog
                    }
                });


            }
                else {
                Toast.makeText(getApplicationContext(), "两次密码不一致，请确认！",
                        Toast.LENGTH_SHORT).show();
            }
            }
        });
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
