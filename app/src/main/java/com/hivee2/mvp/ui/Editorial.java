package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.UserdetialBean;
import com.hivee2.utils.HiveUtil;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/23.
 */
public class Editorial extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    public String userid="";
    public String token="";
    public String sign="";
    private EditText CustomerName;
    private TextView Parent;
    private EditText ContactPerson;
    private EditText MobilePhone;
    private EditText Address;
    private TextView edit;
    private TextView title;
    private RelativeLayout Back;
    String message;
    String customer;
    String parent;
    String contactperson;
    String mobilephone;
    String address;
    public String check1;
    public String check2;
    public String check3;
    public String user="";
    public String passName="";
    public String display="";
    private ProgressDialog progressDialog;
    private SharedPreferences sp=null;// 存放用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editorial);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        check1= sp.getString("check1", "");
        check2=sp.getString("check2", "");
        check3=sp.getString("check3","");
        user=sp.getString("usename","");
        passName=sp.getString("password","");
        display=sp.getString(Constant.sp_display,"");
        init();
        initlisten();
        RequestParams params = new RequestParams(Editorial.this);
        params.addFormDataPart("UserID", userid);
        params.addFormDataPart("TokenString", token);
        //  地址  参数  回调函数
        HttpRequest.post(Api.GET_USER_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("8888888-------->", jsonObject.toString());
                UserdetialBean userdetialBean = JSONObject.parseObject(jsonObject.toString(), UserdetialBean.class);
                UserdetialBean.ResponseCustomerBean responseCustomerBean = userdetialBean.getResponse_Customer();
//                UserdetialBean.ResponseCustomerBean responseCustomerBean = JSONObject.parseObject(jsonObject.toString(), UserdetialBean.ResponseCustomerBean.class);
                customer = responseCustomerBean.getCustomerName();
                Log.e("555--->", customer + "4");
                if (userdetialBean.getParentID() != null) {
                    RequestParams params1 = new RequestParams(Editorial.this);
                    Log.e("9999--->", userdetialBean.getParentID().toString() + "");
                    params1.addFormDataPart("UserID", userdetialBean.getParentID().toString());
                    params1.addFormDataPart("TokenString", token);
                    HttpRequest.post(Api.GET_USER_INFO, params1, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
                            UserdetialBean userdetialBean1 = JSONObject.parseObject(jsonObject.toString(), UserdetialBean.class);
                            parent = userdetialBean1.getUserName();
                            Parent.setText(parent);
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

                } else {
                    Parent.setText("-");
                }
                contactperson = responseCustomerBean.getContactPerson();
                mobilephone = responseCustomerBean.getMobilePhone();
                address = responseCustomerBean.getAddress();
                initshow();
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
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    public void init()
    {
        CustomerName=(EditText)findViewById(R.id.editText12);
        Parent=(TextView)findViewById(R.id.textView45);
        ContactPerson=(EditText)findViewById(R.id.editText7);
        MobilePhone=(EditText)findViewById(R.id.editText8);
        Address=(EditText)findViewById(R.id.editText9);
        edit=(TextView)findViewById(R.id.title_select);
        Back=(RelativeLayout)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title_name);
    }
    public void initlisten()
    {
        Intent intent=getIntent();
        userid=intent.getStringExtra("childid");
        token=intent.getStringExtra("token");
        sign=intent.getStringExtra("sign");
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
                    RequestParams params = new RequestParams(Editorial.this);
                    message="{"+'"'+"UserID"+'"'+":"+'"'+ userid+'"'
                            +","+'"' +"CustomerName"+'"'+":"+'"'+CustomerName.getText().toString()+ '"'
                            +","+ '"'+"ContactPerson"+'"'+":"+'"'+ ContactPerson.getText().toString()+'"'
                            +","+ '"'+"MobilePhone"+'"'+":"+'"'+MobilePhone.getText().toString()+'"'
                            +","+ '"'+"Address"+'"'+":"+'"'+Address.getText().toString()+'"'
                            +"}";
                    params.addFormDataPart("Param", message);
                    params.addFormDataPart("TokenString", token);

                    HttpRequest.post(Api.MODIFY_USER_INFO, params, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
                            Log.e("----99", "ok");
//                            UserdetialBean userdetialBean1 = JSONObject.parseObject(jsonObject.toString(), UserdetialBean.class);
//                            UserdetialBean.ResponseCustomerBean responseCustomerBean = userdetialBean1.getResponse_Customer();
//                            responseCustomerBean.setCustomerName( CustomerName.getText().toString());
//                            responseCustomerBean.setContactPerson(ContactPerson.getText().toString());
//                            responseCustomerBean.setMobilePhone(MobilePhone.getText().toString());
//                            responseCustomerBean.setAddress(Address.getText().toString());

                            customer=CustomerName.getText().toString();
                            Log.e("SHUSHUSHU",sign+customer);
                            if(sign.equals("father"))
                            {

                                String ID1=sp.getString(Constant.sp_userId, "");
                                String ID2=sp.getString(Constant.sp_parentId, "");
                                String token1=sp.getString(Constant.sp_token, "");
                                String token2=sp.getString(Constant.login_token,"");
                                String message=sp.getString("message","");
                                check1= sp.getString("check1", "");
                                check2=sp.getString("check2", "");
                                check3=sp.getString("check3","");
                                user=sp.getString("usename","");
                                passName=sp.getString("password","");
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("check1", check1);
                                editor.putString("usename",user);
                                editor.putString("password", passName);
                                editor.putString("check2",check2);
                                editor.putString("check3",check3);
                                editor.putString(Constant.sp_queryString,"");
                                editor.putInt(Constant.sp_page, 1);
                                editor.putString(Constant.sp_userId, ID1);
                                editor.putString(Constant.sp_parentId,ID2);
                                editor.putString(Constant.sp_token,token1);
                                editor.putString(Constant.login_token,token2);
                                editor.putString(Constant.sp_display,display);
                                editor.putString(Constant.sp_customer,customer);
                                editor.putString("message",message);
                                editor.commit();
                            }
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
                            Editorial.this.finish();
                            Toast.makeText(getApplicationContext(), "编辑成功！",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                }

            }
        });
    }
    public void initshow()
    {
        CustomerName.setText(customer);
        ContactPerson.setText(contactperson);
        MobilePhone.setText(mobilephone);
        Address.setText(address);
        title.setText("编辑资料");
        edit.setText("确认");
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
