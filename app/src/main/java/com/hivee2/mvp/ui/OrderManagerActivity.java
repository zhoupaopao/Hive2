package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.OrderListAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.model.bean.NewOrderListBean;
import com.hivee2.mvp.model.bean.OrderListBean;
import com.hivee2.widget.MyListener;
import com.hivee2.widget.PullToRefreshLayout;
import com.hivee2.widget.pullableview.PullableListView;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Lenovo on 2018/3/13.
 */

public class OrderManagerActivity extends Activity implements HttpCycleContext{
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private TextView title_select;
    private TextView title_name;
    private RelativeLayout back;
    private Spinner spinner;
    private ArrayList<String>data_list;
    private PullableListView orderlist;
    private SharedPreferences sp=null;
    private String userid="";
    private List<OrderListBean.OrderBean>listBean;
    private OrderListAdapter adapter;
    private ProgressDialog progressDialog;
    private ProgressDialog progressDialog1;
    private EditText childac_search1;
    private PullToRefreshLayout ptrl;
    private PullToRefreshLayout nowptf;
    private int nowpage=0;//当前页数
    private String queryy="";//输入条件
    private List<NewOrderListBean.NewOrderBean> newOrderBean;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordermanager);
        sp=this.getSharedPreferences("hive2",MODE_PRIVATE);
        initView();
        initData();
        initListener();
    }

    private void initView() {
        title_select= (TextView) findViewById(R.id.title_select);
        title_name= (TextView) findViewById(R.id.title_name);
        back= (RelativeLayout) findViewById(R.id.back);
        orderlist= (PullableListView) findViewById(R.id.orderlist);
        userid= sp.getString(Constant.sp_userId, "");
        progressDialog=new ProgressDialog(this);
        progressDialog1=new ProgressDialog(this);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
//        ptrl.setOnRefreshListener(new MyListener(this));
        childac_search1= (EditText) findViewById(R.id.childac_search1);
        if(sp.getBoolean("permit_code",true)){
            title_select.setVisibility(View.VISIBLE);
        }else{
            title_select.setVisibility(View.GONE);
        }
//        spinner= (Spinner) findViewById(R.id.type);
    }

    private void initData() {
        title_select.setText("新建工单");
        title_name.setText("工单管理");
        data_list=new ArrayList<>();
        data_list.add("全部");
        data_list.add("无保险");
        data_list.add("有保险");
        achieveList("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sp.getString("detail","").equals("1")){
            //从详情页返回不执行操作
            SharedPreferences.Editor editor=sp.edit();
            editor.remove("detail");
            editor.commit();
        }else{
            achieveList("");
        }

    }

    private void achieveList(String query) {
        Log.i("jsonObject", "213");
        queryy=query;
        //获取工单列表
        RequestParams params=new RequestParams();
        params.addFormDataPart("page",0);
        params.addFormDataPart("size",10);
        params.addFormDataPart("type",0);
        params.addFormDataPart("query",query);
        params.addFormDataPart("userid",userid);
        Log.i("APP_SELECT_LIST", Api.APP_SELECT_LIST+","+userid);
        HttpRequest.get(Api.APP_SELECT_LIST,params,new JsonHttpRequestCallback(){
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.i("jsonObject", jsonObject.toString());
                if(jsonObject.getBoolean("success")){
                    //请求成功
                    NewOrderListBean newOrderListBean=JSONObject.parseObject(jsonObject.toString(),NewOrderListBean.class);
                    newOrderBean=newOrderListBean.getList();
                    adapter=new OrderListAdapter(OrderManagerActivity.this,newOrderBean);
                    orderlist.setAdapter(adapter);
                }else{
                    Toast.makeText(OrderManagerActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode,msg );
                Log.i("jsonObject", msg);
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog.setMessage("正在获取信息");
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(progressDialog.isShowing()&&progressDialog!=null){
                    progressDialog.dismiss();

                }
            }
        });
    }
//下拉刷新，上拉加载需要的请求
    private void achieveListt(int page, final int typestyle) {
        Log.i("jsonObject", "213");
        //获取工单列表
        RequestParams params=new RequestParams();
        params.addFormDataPart("page",page);
        params.addFormDataPart("size",10);
        params.addFormDataPart("type",0);
        params.addFormDataPart("query",queryy);
        params.addFormDataPart("userid",userid);
        HttpRequest.get(Api.APP_SELECT_LIST,params,new JsonHttpRequestCallback(){
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.i("jsonObject", jsonObject.toString());
                if(jsonObject.getBoolean("success")){
                    //请求成功
                    NewOrderListBean newOrderListBean=JSONObject.parseObject(jsonObject.toString(),NewOrderListBean.class);
                    if(typestyle==1){
                        //刷新
                        newOrderBean.clear();
                        newOrderBean.addAll(newOrderListBean.getList());
                    }else{
                        //加载
                        newOrderBean.addAll(newOrderListBean.getList());
                    }

//                    newOrderBean=newOrderListBean.getList();
//                    adapter=new OrderListAdapter(OrderManagerActivity.this,newOrderBean);
//                    Log.i("jsonObject", newOrderBean.get(0).getCarvin());
                    adapter.notifyDataSetChanged();
//                    orderlist.setAdapter(adapter);
                }else{
                    Toast.makeText(OrderManagerActivity.this,jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode,msg );
                Log.i("jsonObject", msg);
            }

            @Override
            public void onStart() {
                super.onStart();
//                progressDialog.setMessage("正在获取信息");
//                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if(typestyle==1){
                    nowptf.refreshFinish(PullToRefreshLayout.SUCCEED);
                }else if(typestyle==2){
                    nowptf.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }

//                if(progressDialog.isShowing()&&progressDialog!=null){
//                    progressDialog.dismiss();
//
//                }
            }
        });
    }



    private void initListener() {
        ptrl.setOnRefreshListener(new MyListener(){
            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                //0代表0页,2代表加载
                nowptf=pullToRefreshLayout;
                nowpage=nowpage+1;
                achieveListt(nowpage,2);
            }

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
//                super.onRefresh(pullToRefreshLayout);
                //0代表0页,1代表刷新
                nowptf=pullToRefreshLayout;

                achieveListt(0,1);
            }
        });
//        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data_list);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i("onItemSelected", data_list.get(i));
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        orderlist
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        title_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(OrderManagerActivity.this,CreateOrderActivity.class);
                startActivity(intent);
            }
        });
        childac_search1.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) childac_search1.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(OrderManagerActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己要的操作...
                    nowpage=0;
                    achieveList(childac_search1.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
}
