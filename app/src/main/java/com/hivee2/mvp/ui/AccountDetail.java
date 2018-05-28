package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.AccountAdapter;
import com.hivee2.content.Api;
import com.hivee2.mvp.model.bean.DeviceInforBean;
import com.hivee2.mvp.model.bean.UserdetialBean;
import com.hivee2.utils.HiveUtil;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/22.
 */
public class AccountDetail extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    public String userid="";
    public String sign="";
    public String token="";
    private TextView CustomerName;
    private TextView UserName;
    private TextView QTY;
    private TextView Parent;
    private TextView ContactPerson;
    private TextView MobilePhone;
    private TextView Address;
    private TextView title;
    private TextView edit;
    private ListView listView;
    private RelativeLayout Back;
    String customer;
    String username;
    int temqty;
    int aleaqty;
    String parent;
    String contactperson;
    String mobilephone;
    String address;
    private AccountAdapter adapter;
    private ProgressDialog progressDialog;
    List<DeviceInforBean.DataListBean> accountList = new ArrayList<DeviceInforBean.DataListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_account);
        init();
        initlisten();
        RequestParams params = new RequestParams(AccountDetail.this);
        params.addFormDataPart("UserID",userid);
        params.addFormDataPart("TokenString",token);
//        params.addFormDataPart("TokenString",token);
        Log.i("UserIDTokenString", userid+"||"+token);
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
                username = userdetialBean.getUserName();
                temqty = userdetialBean.getTempQty();
                aleaqty = userdetialBean.getAleaQty();

                if (userdetialBean.getParentID() != null) {
                    RequestParams params1 = new RequestParams(AccountDetail.this);
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

                } else {
                    Parent.setText("无");
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
                progressDialog.setMessage("正在获取信息");
                progressDialog.show();
                //show  progressdialog
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(progressDialog.isShowing()&&progressDialog!=null){
                    progressDialog.dismiss();

                }
                //hide progressdialog
            }
        });
        RequestParams params2 = new RequestParams(AccountDetail.this);
        params2.addFormDataPart("queryString","");
        params2.addFormDataPart("UserID",userid);
        params2.addFormDataPart("page",1);
        params2.addFormDataPart("pageSize",10000);
        params2.addFormDataPart("sortName","IsOnline");
        params2.addFormDataPart("asc","asc");
        params2.addFormDataPart("showChild",true);
        params2.addFormDataPart("TokenString",token);
        HttpRequest.post(Api.GET_DEVICE_INFOBYUSER, params2, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--11111111------->", jsonObject.toString());
                DeviceInforBean deviceInforBean = JSONObject.parseObject(jsonObject.toString(),DeviceInforBean.class);
                accountList = deviceInforBean.getDataList();
                adapter = new AccountAdapter(AccountDetail.this,accountList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
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
        CustomerName=(TextView)findViewById(R.id.textView39);
        UserName=(TextView)findViewById(R.id.textView41);
        QTY=(TextView)findViewById(R.id.textView42);
        Parent=(TextView)findViewById(R.id.textView1);
        ContactPerson=(TextView)findViewById(R.id.textView3);
        MobilePhone=(TextView)findViewById(R.id.textView5);
        Address=(TextView)findViewById(R.id.textView12);
        Back=(RelativeLayout)findViewById(R.id.back);
        edit=(TextView)findViewById(R.id.title_select);
        title=(TextView)findViewById(R.id.title_name);
        listView=(ListView)findViewById(R.id.listView4);
        listView.setAdapter(adapter);
        progressDialog=new ProgressDialog(this);
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
                Intent intent = new Intent(AccountDetail.this, Editorial.class);
                intent.putExtra("childid", userid);
                intent.putExtra("token", token);
                intent.putExtra("sign",sign);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       if(requestCode==1&&resultCode==2)
       {
           RequestParams params = new RequestParams(AccountDetail.this);
           params.addFormDataPart("UserID",userid);
           params.addFormDataPart("TokenString",token);
//        params.addFormDataPart("TokenString",token);
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
                   username = userdetialBean.getUserName();
                   temqty = userdetialBean.getTempQty();
                   aleaqty = userdetialBean.getAleaQty();

                   if (userdetialBean.getParentID() != null)
                   {
                       RequestParams params1 = new RequestParams(AccountDetail.this);
                       Log.e("9999--->",userdetialBean.getParentID().toString()+"");
                       params1.addFormDataPart("UserID",userdetialBean.getParentID().toString());
                       params1.addFormDataPart("TokenString",token);
                       HttpRequest.post(Api.GET_USER_INFO, params1, new JsonHttpRequestCallback() {
                           @Override
                           protected void onSuccess(Headers headers, JSONObject jsonObject) {
                               super.onSuccess(headers, jsonObject);
                               UserdetialBean userdetialBean1 = JSONObject.parseObject(jsonObject.toString(), UserdetialBean.class);
                               parent=userdetialBean1.getUserName();
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

                   }
                   else {
                       Parent.setText("无");
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
    }

    public void initshow()
    {
        CustomerName.setText(customer);
        UserName.setText(username);
        QTY.setText(aleaqty+"/"+temqty);
        ContactPerson.setText(contactperson);
        MobilePhone.setText(mobilephone);
        Address.setText(address);
        title.setText("账户详情");
        edit.setText("编辑资料");
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
