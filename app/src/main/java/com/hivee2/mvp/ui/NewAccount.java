package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.utils.HiveUtil;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/23.
 */
public class NewAccount extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private TextView Parent;
    private EditText CustomerName;
    private EditText UserName;
    private EditText Password1;
    private EditText Password2;
    private EditText ContactPerson;
    private EditText MobilePhone;
    private EditText Address;
    private EditText Rmark;
    private TextView edit;
    private TextView title;
    private RelativeLayout Back;
    public String userid = "";
    public String token = "";
    String message = "";
    String customer = "";
    String parent = "";
    String contactperson = "";
    String mobilephone = "";
    String address = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_new);
        init();
        initlisten();
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    public void init() {
        Parent = (TextView) findViewById(R.id.textView451);
        CustomerName = (EditText) findViewById(R.id.editText121);
        UserName = (EditText) findViewById(R.id.editText55);
        ContactPerson = (EditText) findViewById(R.id.editText71);
        MobilePhone = (EditText) findViewById(R.id.editText81);
        Address = (EditText) findViewById(R.id.editText91);
        Password1 = (EditText) findViewById(R.id.editText851);
        Password2 = (EditText) findViewById(R.id.editText581);
        Rmark=(EditText)findViewById(R.id.editText911);
        edit = (TextView) findViewById(R.id.title_select);
        Back = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title_name);
        title.setText("新增账户");
        edit.setText("确认");
    }

    public void initlisten() {

        Intent intent = getIntent();
        userid = intent.getStringExtra("childid");
        token = intent.getStringExtra("token");
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("7777-->", CustomerName.getText().toString());
                if(CustomerName.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "必须输入账户名称！",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if(UserName.getText().toString().equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "必须输入登录账户！",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if(Password1.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "必须输入密码！",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            if(Password1.getText().length()<6)
                            {
                                Toast.makeText(getApplicationContext(), "密码不能少于6个字符！",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if(Password1.getText().toString().equals(Password2.getText().toString()))
                                {
                                    RequestParams params = new RequestParams(NewAccount.this);
                                    message="{"+'"'+"ParentID"+'"'+":"+'"'+ userid+'"'
                                            +","+'"' +"CustomerName"+'"'+":"+'"'+CustomerName.getText().toString()+ '"'
                                            +","+'"' +"UserName"+'"'+":"+'"'+UserName.getText().toString()+ '"'
                                            +","+'"' +"Password"+'"'+":"+'"'+Password1.getText().toString()+ '"'
                                            +","+'"' +"CustomerType"+'"'+":"+false
                                            +","+ '"'+"ContactPerson"+'"'+":"+'"'+ ContactPerson.getText().toString()+'"'
                                            +","+ '"'+"MobilePhone"+'"'+":"+'"'+MobilePhone.getText().toString()+'"'
                                            +","+ '"'+"Address"+'"'+":"+'"'+Address.getText().toString()+'"'
                                            +","+ '"'+"Remark"+'"'+":"+'"'+Rmark.getText().toString()+'"'
                                            +","+ '"'+"BLng"+'"'+":"+'"'+'"'
                                            +","+ '"'+"BLat"+'"'+":"+'"'+'"'
                                            +"}";
                                    params.addFormDataPart("Param", message);
                                    params.addFormDataPart("TokenString", token);

                                    HttpRequest.post(Api.ADD_CHILD_USER, params, new JsonHttpRequestCallback() {
                                        @Override
                                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                            super.onSuccess(headers, jsonObject);
                                            Log.e("----99", "ok");
                                            setResult(2);

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
                                            NewAccount.this.finish();
                                            Toast.makeText(getApplicationContext(), "添加成功！",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "两次密码不一致，请确认！",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }

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