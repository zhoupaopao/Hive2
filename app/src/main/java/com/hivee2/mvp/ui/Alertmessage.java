package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.AlertMsgAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.AlertBean;
import com.hivee2.utils.HiveUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/17.
 */
public class Alertmessage extends Activity  implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private RelativeLayout back;
    private TextView title;
    private ListView listView;
    private TextView select;
    private AlertMsgAdapter adapter;
    public String end_time;
    public String start_time;
    public String token="";
    public String userid="";
    public String type="";
    public Boolean showchild=true;
    private SharedPreferences sp=null;// 存放用户信息
    private ProgressDialog progressDialog;
    List<AlertBean.AlertListBean> alertList = new ArrayList<AlertBean.AlertListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertmessage);
        init();
        initlisten();

        getifomation();

    }
    public void getifomation()
    {
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");
        RequestParams params = new RequestParams(Alertmessage.this);
        if(type==null){
            type="";
        }
        String ppparam="{"+'"'+"tokenstring"+'"'+":'"+token+"',"+'"'+"userid"+'"'+":'"+userid+"','querystring"+"':''"+","+'"'+"starttime"+'"'+":'"+start_time+"',"+'"'+"endtime"+'"'+":'"+end_time+"',"+'"'+"page"+'"'+":'"+"1"+"',"+'"'+"pagesize"+'"'+":'"+"10000"+"',"+'"'+"isshowchild"+'"'+":'"+showchild+"',"+'"'+"alarmtype"+'"'+":'"+type+"'}";
        Log.i("ppparam", ppparam);
//        params.addFormDataPart("UserID", userid);
//        params.addFormDataPart("queryString","");
//        params.addFormDataPart("StartTime",start_time);
//        params.addFormDataPart("EndTime",end_time);
//        params.addFormDataPart("IsChecked","");
//        params.addFormDataPart("page",1);
//        params.addFormDataPart("pagSize",1000);
//        params.addFormDataPart("sortName","");
//        params.addFormDataPart("asc","");
//        params.addFormDataPart("alarmType",type);
//        params.addFormDataPart("showChild",showchild);
//        params.addFormDataPart("TokenString",token);
        Log.e("CESHI",Api.GET_ALARMS_BY_USER+userid+"----"+start_time+"----"+end_time+"----"+type+"---"+showchild+"---"+token+"====="+type);
        params.addFormDataPart("param",ppparam);
        //  地址  参数  回调函数
        HttpRequest.post(Api.GET_ALARMS_BY_USER, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--------->", jsonObject.toString());
                if(jsonObject.getInteger("Result")==3){
                    Toast.makeText(Alertmessage.this,jsonObject.getString("ErrorMsg"),Toast.LENGTH_LONG).show();
                }
                AlertBean alertBean = JSONObject.parseObject(jsonObject.toString(), AlertBean.class);
//                Log.e("login1", alertBean.getUserID());
                alertList = alertBean.getDataList();

//                System.out.println("------->" + alertList.size());

//                alertList.add(alertBean);
                adapter = new AlertMsgAdapter(Alertmessage.this, alertList);
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
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    public void init()
    {
        Calendar calendar = new GregorianCalendar();
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        calendar.setTime(date);
        end_time=format.format(date);
        calendar.add(calendar.DATE, -3);// 整数往后推,负数往前移动
        start_time = format.format(calendar.getTime());
        Log.e("weixian", ""+start_time+"------"+end_time);
        back=(RelativeLayout)findViewById(R.id.back);
        listView=(ListView)findViewById(R.id.listView3);
        title=(TextView)findViewById(R.id.title_name);
        select=(TextView)findViewById(R.id.title_select);
        listView.setAdapter(adapter);
        title.setText("预警列表");
        progressDialog=new ProgressDialog(this);
    }
    public void initlisten()
    {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Alertmessage.this, SelectAlert.class);
                startActivityForResult(intent, 1);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                System.out.println("position" + position);
                // TODAuto-generated method stub
                Intent intent = new Intent(Alertmessage.this, DetailWaring.class);
                intent.putExtra("alert1",alertList.get(position).getAddress());
                intent.putExtra("alert2",alertList.get(position).getAlarmType());
                intent.putExtra("alert3",alertList.get(position).getCarNumber());
                intent.putExtra("alert4",alertList.get(position).getPledgerName());
                intent.putExtra("alert5",alertList.get(position).getDeviceID());
                intent.putExtra("alert6",alertList.get(position).getStarttime());
                intent.putExtra("alert7",alertList.get(position).getBLat());
                intent.putExtra("alert8",alertList.get(position).getBLng());

                Log.e("login---------->", alertList.get(position).getAddress()+"---"+
                        alertList.get(position).getCarNumber()+"---"+alertList.get(position).getPledgerName()
                        +"---"+alertList.get(position).getStarttime()+"---"+alertList.get(position).getAlarmType()
                        +"---"+alertList.get(position).getBLat());
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 1:
              start_time=data.getStringExtra("StartTime");
                end_time=data.getStringExtra("EndTime");
                type=data.getStringExtra("type");
                showchild=Boolean.valueOf(data.getStringExtra("showChild"));
                getifomation();
                break;
        }
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
