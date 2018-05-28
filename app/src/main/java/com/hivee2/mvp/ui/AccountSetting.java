package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.AccountsetAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.ChildBean;
import com.hivee2.utils.HiveUtil;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;


public class AccountSetting extends Activity  implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private RelativeLayout delete;
    private TextView title;
    private TextView create;
    private ListView listView;
    private LinearLayout User;
    private TextView UserText;
    private AccountsetAdapter adapter;
    public String token="";
    public String userid="";
    public String customer="";
    private SharedPreferences sp=null;// 存放用户信息
    private ProgressDialog progressDialog;
//    "c0a0cfb7b2504c649fcc116a77b54c09"
//    "P+/3yIWL5j1p6abpPrClz+SOxnKAFZ0AMXju0Ap91nYQ0igvprnBjRIfa20gYpmaQ7d8BclnZMfXejJtRzIAtX6IdYbVUKg19dLUR4ZWvhNUr9BHwDkIXcnUTWRJIsmj2FZn7tTEhyFjeNAflg5bMXUcX5r+YUzTC3DsNUoTEqY=";
    List<ChildBean.DataListBean> childList = new ArrayList<ChildBean.DataListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsetting);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");
        customer=sp.getString(Constant.sp_customer,"");

        init();
        initlisten();
        RequestParams params = new RequestParams(AccountSetting.this);
        params.addFormDataPart("UserID",userid);
        params.addFormDataPart("TokenString",token);
              //  地址  参数  回调函数
        HttpRequest.post(Api.GET_CHILD_USER_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                ChildBean childBean = JSONObject.parseObject(jsonObject.toString(), ChildBean.class);
                childList = childBean.getDataList();
                adapter = new AccountsetAdapter(AccountSetting.this, childList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
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
    public void init2()
    {
        RequestParams params = new RequestParams(AccountSetting.this);
        params.addFormDataPart("UserID",userid);
        params.addFormDataPart("TokenString",token);
        //  地址  参数  回调函数
        HttpRequest.post(Api.GET_CHILD_USER_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                ChildBean childBean = JSONObject.parseObject(jsonObject.toString(), ChildBean.class);
                childList = childBean.getDataList();
                adapter = new AccountsetAdapter(AccountSetting.this, childList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
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
                progressDialog.setMessage("正在获取信息");
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //hide progressdialog
                if(progressDialog.isShowing()&&progressDialog!=null){
                    progressDialog.dismiss();

                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1&&resultCode==2) {
            RequestParams params = new RequestParams(AccountSetting.this);
            params.addFormDataPart("UserID",userid);
            params.addFormDataPart("TokenString",token);
            //  地址  参数  回调函数
            HttpRequest.post(Api.GET_CHILD_USER_INFO, params, new JsonHttpRequestCallback() {
                @Override
                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                    super.onSuccess(headers, jsonObject);
                    Log.e("-1222-------->", jsonObject.toString());
                    ChildBean childBean = JSONObject.parseObject(jsonObject.toString(), ChildBean.class);
                    childList = childBean.getDataList();
                    adapter = new AccountsetAdapter(AccountSetting.this, childList);
                    adapter.notifyDataSetChanged();
                    listView.setAdapter(adapter);
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
                    progressDialog.setMessage("正在获取信息");
                    progressDialog.show();
                }
                @Override
                public void onFinish() {
                    super.onFinish();
                    //hide progressdialog
                    if(progressDialog.isShowing()&&progressDialog!=null){
                        progressDialog.dismiss();
                    }
                }
            });

        }
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    public void init()
    {
        delete=(RelativeLayout)findViewById(R.id.back);
        create=(TextView)findViewById(R.id.title_select);
        title=(TextView)findViewById(R.id.title_name);
        listView=(ListView)findViewById(R.id.listView);
        User=(LinearLayout)findViewById(R.id.user);
        UserText=(TextView)findViewById(R.id.textView36);
        listView.setAdapter(adapter);
        title.setText("账户管理");
        create.setText("新增账户");
        Log.e("2525---->", customer + "");
        UserText.setText(customer);
        progressDialog=new ProgressDialog(this);
    }
    public void initlisten()
    {
        delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AccountSetting.this.finish();
        }
    });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                System.out.println("position" + position);
                // TODAuto-generated method stub
                Intent intent = new Intent(AccountSetting.this, AccountDetail.class);
                intent.putExtra("childid", childList.get(position).getUserID());
                intent.putExtra("token", token);
                intent.putExtra("sign","child");
                startActivity(intent);
            }
        });
        User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSetting.this, AccountDetail.class);
                intent.putExtra("childid", userid);
                intent.putExtra("token", token);
                intent.putExtra("sign","father");
                startActivity(intent);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(AccountSetting.this, NewAccount.class);
                intent.putExtra("childid", userid);
                intent.putExtra("token", token);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        HiveUtil ut=new HiveUtil();
        ut.onResumePage(this,this.getClass().getCanonicalName());
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid = sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");
        customer=sp.getString(Constant.sp_customer,"");
        UserText.setText(customer);
        Log.e("SHUSHUSHU",customer);
        init2();
    }

    @Override
    protected void onPause() {
        super.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());
    }
}
