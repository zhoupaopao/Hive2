package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Lenovo on 2018/3/13.
 */

public class OrderDetailActivity extends Activity {
    private String orderid;
    private String createtimee;
    private TextView orderidd;
    private TextView createtime;
    private ImageView imageView2;
    private TextView title_name1;
    private SharedPreferences sp=null;
    private TextView statusname;
    private TextView pledger_name;
    private TextView pledger_idcard;
    private TextView car_vin;
    private TextView car_num;
    private TextView car_brand;
    private TextView car_name;
    private TextView pledger_phone;
    private TextView username;
    private TextView install_cont_per;
    private TextView install_cont_pho;
    private TextView install_date;
    private TextView install_address;
    private ProgressDialog progressDialog1;
    private String userid;
    private TextView car_color;
    private TextView num_yx;
    private TextView num_wx;
    private TextView device_from;
    private TextView servicelife;
//    private TextView createtime;
//    private TextView createtime;
//    private TextView createtime;
//    private TextView createtime;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderid=getIntent().getStringExtra("orderid");
        createtimee=getIntent().getStringExtra("createtime");
        sp=this.getSharedPreferences("hive2",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("detail","1");
        editor.commit();
        progressDialog1=new ProgressDialog(this);
        Log.i("orderid", orderid);
        setContentView(R.layout.activity_order_detail);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        orderidd= (TextView) findViewById(R.id.orderid);
        createtime= (TextView) findViewById(R.id.create_time);
        imageView2= (ImageView) findViewById(R.id.imageView2);
        title_name1= (TextView) findViewById(R.id.title_name1);
        statusname= (TextView) findViewById(R.id.statusname);
        pledger_name= (TextView) findViewById(R.id.pledger_name);
        pledger_idcard= (TextView) findViewById(R.id.pledger_idcard);
        car_vin= (TextView) findViewById(R.id.car_vin);
        car_num= (TextView) findViewById(R.id.car_num);
        car_brand= (TextView) findViewById(R.id.car_brand);
        car_name= (TextView) findViewById(R.id.car_name);
        pledger_phone= (TextView) findViewById(R.id.pledger_phone);
        username= (TextView) findViewById(R.id.username);
        install_cont_per= (TextView) findViewById(R.id.install_cont_per);
        install_cont_pho= (TextView) findViewById(R.id.install_cont_pho);
        install_date= (TextView) findViewById(R.id.install_date);
        install_address= (TextView) findViewById(R.id.install_address);
        car_color= (TextView) findViewById(R.id.car_color);
        num_yx= (TextView) findViewById(R.id.num_yx);
        num_wx= (TextView) findViewById(R.id.num_wx);
        device_from= (TextView) findViewById(R.id.device_from);
        servicelife= (TextView) findViewById(R.id.servicelife);
    }

    private void initData() {
        userid= sp.getString(Constant.sp_userId, "");
        RequestParams params=new RequestParams();
        params.addFormDataPart("worksheet_id",orderid);
        params.addFormDataPart("userid",userid);
        Log.i("params", params.toString());
        Log.i("params", Api.APP_SELECT_DETAIL+"?worksheet_id="+orderid+"&userid="+userid);
        HttpRequest.get(Api.APP_SELECT_DETAIL,params,new JsonHttpRequestCallback(){
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
//                Log.i("jsonObject3", jsonObject.toString());
                if(jsonObject.getBoolean("success")){
                    JSONObject jsonObject1= jsonObject.getJSONObject("object");
                    createtime.setText(createtimee);
                    statusname.setText(jsonObject1.getString("statusname"));
                    pledger_name.setText(jsonObject1.getString("pledgername"));
                    pledger_idcard.setText(jsonObject1.getString("pledgeridcard"));
                    car_vin.setText(jsonObject1.getString("car_vin"));
                    car_num.setText(jsonObject1.getString("car_num"));
                    car_brand.setText(jsonObject1.getString("car_brand"));
                    car_name.setText(jsonObject1.getString("car_model"));
                    pledger_phone.setText(jsonObject1.getString("pledgerphone"));
                    username.setText(jsonObject1.getString("installarea"));
                    install_cont_per.setText(jsonObject1.getString("installcontper"));
                    install_cont_pho.setText(jsonObject1.getString("installcontpho"));
                    install_date.setText(jsonObject1.getString("installtime"));
                    install_address.setText(jsonObject1.getString("installaddress"));
                    car_color.setText(jsonObject1.getString("car_color"));
                    device_from.setText(jsonObject1.getString("devsourcename"));
                    JSONArray jsonArray=jsonObject1.getJSONArray("deviceg");
                    servicelife.setText(jsonObject1.getString("servicelife"));
                    if(jsonArray==null){
                        num_yx.setText("0");
                        num_wx.setText("0");
                    }else{
                        for(int i=0;i<jsonArray.size();i++){
                            JSONObject jsonObject2=jsonArray.getJSONObject(i);
                            if(jsonObject2.getString("type").equals("有线")){
                                //是有线设备
                                Log.i("有线", "1 ");
                                num_yx.setText(jsonObject2.getInteger("count")+"");
                            }else{
                                Log.i("无线", "1 ");
                                num_wx.setText(jsonObject2.getInteger("count")+"");
                            }
                        }
                    }
                }else{
                    Toast.makeText(OrderDetailActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog1.setMessage("正在获取信息");
                progressDialog1.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog1.isShowing() && progressDialog1 != null) {
                    progressDialog1.dismiss();

                }
            }
        });
    }

    private void initListener() {
        orderidd.setText(orderid);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title_name1.setText("查看详情");
    }
}
