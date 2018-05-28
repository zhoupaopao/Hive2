package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.UnbindAdapter2;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.CarDeviceBean;
import com.hivee2.mvp.model.bean.UnbindBean;
import com.hivee2.utils.HiveUtil;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/10/12.
 */
public class Bend_List extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private TextView save;
    private TextView title_name;
    private RelativeLayout back;
    private ListView listView;
    private UnbindAdapter2 adapter2;
    private ProgressDialog progressDialog;
    private Context mContext = null;
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";
    private String message="";
    private String carID="";
    private String queryString="";
    private EditText eSearch;
    List<UnbindBean.DataListBean> unbindList  = new ArrayList<UnbindBean.DataListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bendlist);
        init();
        listen();
        set_eSearch_TextChanged();
    }
    private void set_eSearch_TextChanged()
    {
        eSearch = (EditText) findViewById(R.id.childac_search1);

        eSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
                //这是文本框改变之前会执行的动作
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                /**这是文本框改变之后 会执行的动作
                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
                 * 所以这里我们就需要加上数据的修改的动作了。
                 */
                if (s.length() == 0) {
                    queryString = eSearch.getText().toString();
                    listen();
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                    myhandler.post(eChanged);

                    queryString = eSearch.getText().toString();
                    listen();
                }
            }
        });

    }
    public void init()
    {
        save=(TextView)findViewById(R.id.title_select);
        title_name=(TextView)findViewById(R.id.title_name);
        back=(RelativeLayout)findViewById(R.id.back);
        listView=(ListView)findViewById(R.id.listView);
        progressDialog=new ProgressDialog(this);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");
        save.setText("保存");
        Intent intent=getIntent();
        carID=intent.getStringExtra("carID");
        title_name.setText("绑定设备列表");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message="{"+'"'+"DataList"+'"'+":[";

                for(int i=0;i<unbindList.size();i++)
                {
//                    {"DataList":[{"CarID":"123ac98753d649d69e4abf2001325faa", "DeviceID":"14410009119"},
// {"CarID":"123ac98753d649d69e4abf2001325faa","DeviceID":"14410009120"},]}
                    if(adapter2.getmessage().substring(i,i+1).equals("1"))
                    {
                        message=message+"{"+'"'+"CarID"+'"'+":"+'"'+carID+'"'+","+ '"'+"DeviceID"+ '"'+":"+ '"'+unbindList.get(i).getIMEI()+'"'+"},";
                    }
                }
                message=message+"]}";
                RequestParams params = new RequestParams(Bend_List.this);
                params.addFormDataPart("param", message);
                params.addFormDataPart("tokenString", token);
                Log.e("TTTTTT", "" + message + "----" + token);
                HttpRequest.post(Api.CAR_BIND_DEVICE, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        CarDeviceBean carDeviceBean = JSONObject.parseObject(jsonObject.toString(), CarDeviceBean.class);

                        if (carDeviceBean.getResult() == 0) {
                            setResult(RESULT_OK);
                            finish();
                            Toast.makeText(Bend_List.this, "绑定成功！", Toast.LENGTH_SHORT).show();
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
                        progressDialog.setMessage("正在刷新");
                        progressDialog.show();
                        //show  progressdialog
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (progressDialog.isShowing() && progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        //hide progressdialog
                    }
                });



            }
        });

    }
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    public void listen()
    {
        RequestParams params = new RequestParams(Bend_List.this);
        params.addFormDataPart("userID", userid);
        params.addFormDataPart("onLineType","0");
        params.addFormDataPart("isBindCar","2");
        params.addFormDataPart("queryString",queryString);
        params.addFormDataPart("page", 1);
        params.addFormDataPart("pageSize", 10000);
        params.addFormDataPart("sortName", "");
        params.addFormDataPart("asc","asc");
        params.addFormDataPart("TokenString", token);
        HttpRequest.post(Api.GET_DEVICE_BY_USERID, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--ttt------->", jsonObject.toString());
                UnbindBean unbindBean = JSONObject.parseObject(jsonObject.toString(), UnbindBean.class);
                unbindBean.getDataList();
                unbindList = unbindBean.getDataList();
                adapter2 = new UnbindAdapter2(Bend_List.this, unbindList);

                Log.e("TIAOYUE",adapter2.getmessage());
////                        adapter2.notifyDataSetChanged();
                listView.setAdapter(adapter2);
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
