package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.ListViewAddDelAdapter1;
import com.hivee2.adapter.ListViewAddDelAdapter2;
import com.hivee2.adapter.UnbindAdapter2;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.model.bean.CarDeviceBean;
import com.hivee2.mvp.model.bean.CarMessage_Bean;
import com.hivee2.mvp.model.bean.PledgerBean;
import com.hivee2.mvp.model.bean.UnbindBean;
import com.hivee2.utils.HiveUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/11.
 */
public class BorrowDetail extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private RelativeLayout people1;
    private LinearLayout carmessage;
    private LinearLayout IMEImessage;
    private TextView edit;
    private RelativeLayout back;
    private TextView new1;
    private TextView title;
    private TextView delete1;
    private TextView delete2;
    private TextView PledgerNameView;
    private TextView CarNumberView;
    private TextView CarBrandView;
    private TextView CarValueView;
    private TextView PledgeTimeView;
    private TextView RepayTimeView;
    private TextView InternalNumView;
    private TextView IMEIView;
    private TextView ModelView;
    private TextView BSView;
    private TextView BLView;
    private TextView AddressView;
    private TextView carmessage2;
    private TextView BindDevice;
    private String PledgerName;
    private String carID;
    private String CarDeviceID;
    private String CarNumber;
    private String CarBrand;
    private String CarValue;
    private String PledgeTime;
    private String RepayTime;
    private String InternalNum;
    private String IMEI;
    private String Model;
    private String BS;
    private String BL;
    private String Address;
    private String PledgementID;
    private String CarVersion;
    private String VIN;
    private String PledgeCarID;
    private String Source;
    private String Status;
    private ProgressDialog progressDialog;
    private PopupWindow popupWindow;
    private Context mContext = null;
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";
    private TextView cancel;
    private TextView save2;
    private EditText eSearch;
    private ListView listView;
    private String message="";
    private UnbindAdapter2 adapter2;
    private String queryString="";
    private ListView lv_bend;
    private RelativeLayout st_clear_Layout2;
    private ListViewAddDelAdapter2 listViewAddDelAdapter2;
    List<UnbindBean.DataListBean> unbindList  = new ArrayList<UnbindBean.DataListBean>();
    private boolean canedit=true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailborrow);
        intview();
        getinfomation();
        achievePermission();
        run();
    }

    private void achievePermission() {
        RequestParams params = new RequestParams(BorrowDetail.this);
        String param="{'userid':'"+userid+"','tokenstring':'"+token+"'}";
        params.addFormDataPart("param", param);
        Log.i("achievePermission: ", Api.Json_GetCustomerInfoByChildID+"?param="+param);
        HttpRequest.get(Api.Json_GetCustomerInfoByChildID, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--ttt------->", jsonObject.toString());
                if(jsonObject.getInteger("Result")==0){
                    //成功
                    if(jsonObject.getString("userid").equals("90f4ea676cda45a1808f532efc149b73")){
                        canedit=false;
                    }
                }else{
                    Toast.makeText(BorrowDetail.this,"请求异常",Toast.LENGTH_SHORT).show();
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
                progressDialog.setMessage("正在加载");
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void intview() {
        people1=(RelativeLayout)findViewById(R.id.st_clear_Layout1);
        edit=(TextView)findViewById(R.id.textView4);
        back=(RelativeLayout)findViewById(R.id.back);
        new1=(TextView)findViewById(R.id.title_select);
        title=(TextView)findViewById(R.id.title_name);
        delete1=(TextView)findViewById(R.id.textView3);
//        delete2=(TextView)findViewById(R.id.textView23);
        BindDevice=(TextView)findViewById(R.id.textView);
        lv_bend= (ListView) findViewById(R.id.lv_bend);
        PledgerNameView=(TextView)findViewById(R.id.textView22);
        CarNumberView=(TextView)findViewById(R.id.textView13);
        CarBrandView=(TextView)findViewById(R.id.textView14);
        CarValueView=(TextView)findViewById(R.id.textView15);
        PledgeTimeView=(TextView)findViewById(R.id.textView16);
        RepayTimeView=(TextView)findViewById(R.id.textView12);
        InternalNumView=(TextView)findViewById(R.id.textView21);
        IMEIView=(TextView)findViewById(R.id.textView20);
        ModelView=(TextView)findViewById(R.id.textView19);
        BSView=(TextView)findViewById(R.id.textView18);
        BLView=(TextView)findViewById(R.id.textView17);
        AddressView=(TextView)findViewById(R.id.textView30);
        carmessage=(LinearLayout)findViewById(R.id.carmessage);
        st_clear_Layout2= (RelativeLayout) findViewById(R.id.st_clear_Layout2);
        carmessage.setVisibility(View.GONE);
        st_clear_Layout2.setVisibility(View.GONE);
        IMEImessage=(LinearLayout)findViewById(R.id.linearlayout);
        IMEImessage.setVisibility(View.GONE);
        carmessage2=(TextView)findViewById(R.id.textView64);
        progressDialog=new ProgressDialog(this);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");
        mContext = this;
    }
    private void getinfomation() {
        lv_bend= (ListView) findViewById(R.id.lv_bend);
        listViewAddDelAdapter2=new ListViewAddDelAdapter2(BorrowDetail.this);
        lv_bend.setAdapter(listViewAddDelAdapter2);
        Intent intent = getIntent();
        PledgerName = intent.getStringExtra("PledgerName");
        Log.e("PledgerName123",PledgerName+"");
        carID=intent.getStringExtra("carID");
        CarDeviceID=intent.getStringExtra("CarDeviceID");
        CarNumber = intent.getStringExtra("CarNumber");
        CarBrand = intent.getStringExtra("CarBrand");
        CarValue = intent.getStringExtra("CarValue");
        Source=intent.getStringExtra("Source");
        Status=intent.getStringExtra("Status");
        Log.e("Source",Source+"");
        PledgeTime = intent.getStringExtra("PledgeTime");
        RepayTime = intent.getStringExtra("RepayTime");
        InternalNum = intent.getStringExtra("InternalNum");
        IMEI = intent.getStringExtra("IMEI");
        Model = intent.getStringExtra("Model");
        BS = intent.getStringExtra("BS");
        BL = intent.getStringExtra("BL");
        Address = intent.getStringExtra("Address");
        PledgementID=intent.getStringExtra("PledgementID");
        VIN=intent.getStringExtra("VIN");
        CarVersion=intent.getStringExtra("CarVersion");
        PledgeCarID=intent.getStringExtra("PledgeCarID");

        Log.e("YYY",PledgementID);
        if (PledgerName != null) {
            PledgerNameView.setText("借款人：" + PledgerName);
        }
        if(CarNumber!=null)
        {
            carmessage2.setVisibility(View.GONE);
            carmessage.setVisibility(View.VISIBLE);
            st_clear_Layout2.setVisibility(View.VISIBLE);
        CarNumberView.setText("车牌号：" + CarNumber);
    }

        if(CarBrand.equals("null"))
            {}
        else {
            CarBrandView.setText("车辆品牌："+CarBrand);
        }
        if(CarValue.equals("null"))
        {}
        else {
            CarValueView.setText("借款金额：￥"+CarValue);
        }
        Log.e("SHIJIAN",PledgeTime);
        if(PledgeTime.equals(""))
        {}
        else {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            int a =PledgeTime.indexOf("(", 0);
            int b=PledgeTime.indexOf(")", 0);
            PledgeTimeView.setText("开始日期："+format.format(Long.valueOf(PledgeTime.substring(a +1,b)) ));
        }
        Log.e("SHIJIAN", RepayTime);
     if(RepayTime.equals("null"))
     {}
        else {
         DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
         int a =RepayTime.indexOf("(", 0);
         int b=RepayTime.indexOf(")", 0);
         RepayTimeView.setText("结束日期："+format.format(Long.valueOf(RepayTime.substring(a +1,b)) ));
     }
        if(InternalNum.equals("null"))
        {}
        else {
            String[]inn=InternalNum.split("/");
            String[]inM=Model.split("/");
            String[]inc=CarDeviceID.split("/");
            Log.i("getinfomation", CarDeviceID);
            for(int i=0;i<inn.length;i++){
                String dat=inn[i]+","+inM[i]+","+inc[i];
                listViewAddDelAdapter2.addData(dat);
            }
            listViewAddDelAdapter2.notifyDataSetChanged();
            IMEImessage.setVisibility(View.VISIBLE);
            InternalNumView.setText("设备名称："+InternalNum);
        }
    if(IMEI.equals("null"))
    {}
        else {
        IMEIView.setText("设备编号："+IMEI);
    }
        if(Model.equals("null")){}
        else {
            ModelView.setText("设备类型："+Model);
        }
        if(BS.equals("null")){}
        else {
            BSView.setText("插拔状态："+BS);
        }
                if(BL!=null){}
        else {  BLView.setText("设备电量："+BL+"%");}
        if(Address.equals("null"))
        {} else {
            AddressView.setText("最后位置："+Address);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
// TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            setResult(RESULT_OK);
            finish();
        }
    }
    public void del(int pos) {
        listViewAddDelAdapter2.delData(pos);
        listViewAddDelAdapter2.notifyDataSetChanged();
        // 判断是否>0

    }
    private void run()
    {
        people1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(Source.equals("2")){
//                    Toast.makeText(BorrowDetail.this,"该信息来自于工单不可编辑",Toast.LENGTH_SHORT).show();
//                }else {
                if(canedit) {
                    Intent intent = new Intent(BorrowDetail.this, EditBorrowMen.class);
                    intent.putExtra("PledgementID", PledgementID);
                    intent.putExtra("Source", Source);
                    startActivity(intent);
                }else{
                    Toast.makeText(BorrowDetail.this, "您暂无此权限", Toast.LENGTH_SHORT).show();
                }
//                }
            }
        });
       edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //先验证是否有编辑权限
//               if(Source.equals("2")){
//                Toast.makeText(BorrowDetail.this,"该信息来自于工单不可编辑",Toast.LENGTH_SHORT).show();
//               }else{
               if(canedit){
                   Intent intent = new Intent(BorrowDetail.this, EditcarActivity.class);
                   intent.putExtra("PledgeCarID",PledgeCarID);
                   intent.putExtra("pledgerID", PledgementID);
                   intent.putExtra("PledgerName", PledgerName);
                   intent.putExtra("Source", Source);
                   Log.i("PledgerName3", PledgerName);
                   startActivity(intent);
               }else{
                   Toast.makeText(BorrowDetail.this, "您暂无此权限", Toast.LENGTH_SHORT).show();

               }

//               }

           }
       });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowDetail.this, AddcarActivity.class);
                intent.putExtra("pledgerID", PledgementID);
                intent.putExtra("pledgername",PledgerName);
                Log.i("pledgername", PledgerName);
                startActivityForResult(intent, 1);
            }
        });
        BindDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Status", Status+"");
                if(Status.equals("0")){
                    View popView = LayoutInflater.from(mContext).inflate(
                            R.layout.binddevice_window, null);
                    View rootView = findViewById(R.id.root_main5); // 當前頁面的根佈局
                    popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出动画
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setFocusable(true);
                    popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// 顯示在根佈局的底部
                    popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
                    cancel = (TextView) popView.findViewById(R.id.btn_cancle);
                    save2=(TextView) popView.findViewById(R.id.btn_cancle2);
                    listView=(ListView)popView.findViewById(R.id.listView8);
                    eSearch = (EditText) popView.findViewById(R.id.childac_search1);
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
                                RequestParams params = new RequestParams(BorrowDetail.this);
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
                                        adapter2 = new UnbindAdapter2(BorrowDetail.this, unbindList);
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
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                            } else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                    myhandler.post(eChanged);

                                queryString = eSearch.getText().toString();
                                RequestParams params = new RequestParams(BorrowDetail.this);
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
                                        adapter2 = new UnbindAdapter2(BorrowDetail.this, unbindList);
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
                        }
                    });
                    RequestParams params = new RequestParams(BorrowDetail.this);
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
                            adapter2 = new UnbindAdapter2(BorrowDetail.this, unbindList);
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

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    save2.setOnClickListener(new View.OnClickListener() {
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
                            RequestParams params = new RequestParams(BorrowDetail.this);
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
                                        Toast.makeText(BorrowDetail.this, "绑定成功！", Toast.LENGTH_SHORT).show();
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
//         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
//             @Override
//             public void onItemClick(AdapterView<?> parent, View view, int position,
//                                     long id) {
//                 System.out.println("position" + position);
//                 unbindList.get(position).getCarDeviceID();
//                 RequestParams params = new RequestParams(BorrowDetail.this);
////                 {"DataList":[,{"CarID":"123ac98753d649d69e4abf2001325faa","DeviceID":"14410009119"},{"CarID":"123ac98753d649d69e4abf2001325faa","DeviceID":"14410009120"},]}
////14410009116/20    QNND8eMD4edb/CHQbqoQaY05G3tAlr8RMSdDsKbXW9Dh26xsTWWHMBC4UQ5DoagOhdikjtAOQasoWGcxKw1F5dPPIjnVQFNre+qpmXJr7x8EE1jfgmIV9WMMmp2/1qXoQmPSBcPZaVbuCU5fiBgqmtCcm+6yWLaeoe8lP8wGjBs=
//                 String message="{"+'"'+"DataList"+'"'+":[{"+'"'+"CarID"+'"'+":"+'"'+carID+'"'+","+ '"'+"DeviceID"+ '"'+":"+
//                         '"'+unbindList.get(position).getIMEI()+'"'+"}]}";
//                 params.addFormDataPart("param", message);
//                 params.addFormDataPart("tokenString", token);
//                 Log.e("TTTTTT", "" + message+"----"+token);
//                 HttpRequest.post(Api.CAR_BIND_DEVICE, params, new JsonHttpRequestCallback() {
//                     @Override
//                     protected void onSuccess(Headers headers, JSONObject jsonObject) {
//                         super.onSuccess(headers, jsonObject);
//                         CarDeviceBean carDeviceBean=JSONObject.parseObject(jsonObject.toString(), CarDeviceBean.class);
//
//                         if (carDeviceBean.getResult() == 0) {
//                             finish();
//                             Toast.makeText(BorrowDetail.this, "绑定成功！", Toast.LENGTH_SHORT).show();
//                         }
//                     }
//
//                     @Override
//                     public void onFailure(int errorCode, String msg) {
//                         super.onFailure(errorCode, msg);
//                         Log.e("alertMsg failure", errorCode + msg);
//
//                     }
//
//                     @Override
//                     public void onStart() {
//                         super.onStart();
//                         progressDialog.setMessage("正在刷新");
//                         progressDialog.show();
//                         //show  progressdialog
//                     }
//
//                     @Override
//                     public void onFinish() {
//                         super.onFinish();
//                         if (progressDialog.isShowing() && progressDialog != null) {
//                             progressDialog.dismiss();
//                         }
//                         //hide progressdialog
//                     }
//                 });
//
//             }
//         });
                }else if(Status.equals("1")){
                    Toast.makeText(BorrowDetail.this,"此业务来源于工单创建，且尚未激活，不能编辑！",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BorrowDetail.this,"状态不明",Toast.LENGTH_SHORT).show();
                }

            }
        });
        new1.setText("新增车辆");
        title.setText("借款详情");
        delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹出框
                AlertDialog.Builder builder = new AlertDialog.Builder(BorrowDetail.this);

                builder.setTitle("提示");
                builder.setMessage("你是否确定要删除该辆车？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (InternalNum.equals("null")) {

                            RequestParams params = new RequestParams(BorrowDetail.this);
                            params.addFormDataPart("carID", carID);
                            params.addFormDataPart("tokenString", token);
                            params.addFormDataPart("jsoncallback", "");
                            Log.e("YYY", CarDeviceID + "  " + token);
                            HttpRequest.post(Api.DELCAR, params, new JsonHttpRequestCallback() {
                                @Override
                                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                    super.onSuccess(headers, jsonObject);
                                    Log.i("onSuccess: ", jsonObject.toString());
                                    BaseApiResponse cardevice = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
                                    if (cardevice.getResult() == 0) {
                                        finish();
                                        Toast.makeText(BorrowDetail.this, "解绑成功！", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(BorrowDetail.this, "解绑失败！", Toast.LENGTH_SHORT).show();
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
                                    if (progressDialog.isShowing() && progressDialog != null)
                                    {
                                        progressDialog.dismiss();
                                    }
                                    //hide progressdialog
                                }
                            });
                        }
                        else{
                            Toast.makeText(BorrowDetail.this, "对不起，该车辆绑定了设备，请解绑后再删除！", Toast.LENGTH_SHORT).show();
                        }
//                        Toast.makeText(BorrowDetail.this, "你选择了确定按钮！", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(BorrowDetail.this, "对不起，该辆车绑定了设备，请解绑设备后再删除", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
//        delete2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(BorrowDetail.this);
//                builder.setTitle("提示");
//                builder.setMessage("你是否确定要解绑该设备？");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        RequestParams params = new RequestParams(BorrowDetail.this);
//                        params.addFormDataPart("carDeviceID", CarDeviceID);
//                        params.addFormDataPart("tokenString", token);
//                        Log.e("YYY", CarDeviceID + "  " + token);
//                        HttpRequest.post(Api.SETCAR_UNBIND_DEVICE, params, new JsonHttpRequestCallback() {
//                            @Override
//                            protected void onSuccess(Headers headers, JSONObject jsonObject) {
//                                super.onSuccess(headers, jsonObject);
//                                BaseApiResponse cardevice = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
//                                if (cardevice.getResult() == 0) {
//                                    finish();
//                                    Toast.makeText(BorrowDetail.this, "删除成功！", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(int errorCode, String msg) {
//                                super.onFailure(errorCode, msg);
//                                Log.e("alertMsg failure", errorCode + msg);
//
//                            }
//
//                            @Override
//                            public void onStart() {
//                                super.onStart();
//                                progressDialog.setMessage("正在刷新");
//                                progressDialog.show();
//                                //show  progressdialog
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                super.onFinish();
//                                if (progressDialog.isShowing() && progressDialog != null) {
//                                    progressDialog.dismiss();
//                                }
//                                //hide progressdialog
//                            }
//                        });
//
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        Toast.makeText(BorrowDetail.this, "对不起，该辆车绑定了设备，请解绑设备后再删除", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                builder.show();
//            }
//        });
    }
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    @Override
    protected void onResume() {
        super.onResume();
        HiveUtil ut=new HiveUtil();
        ut.onResumePage(this,this.getClass().getCanonicalName());
        RequestParams params = new RequestParams(BorrowDetail.this);
        params.addFormDataPart("pledgerID",PledgementID);
        params.addFormDataPart("tokenString", token);
        Log.e("YYY",PledgementID+"  "+token+"    "+userid);
        HttpRequest.post(Api.GET_PLEDGER_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                PledgerBean pledgerBean = JSONObject.parseObject(jsonObject.toString(), PledgerBean.class);
                Log.e("PLED", jsonObject.toString() + pledgerBean.getDataList().size());
                if (pledgerBean.getDataList().toString().equals("[]")) {
                } else {
                    PledgerNameView.setText("借款人：" + pledgerBean.getDataList().get(0).getPledgerName());
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

        RequestParams params1 = new RequestParams(BorrowDetail.this);
        params1.addFormDataPart("carID",PledgeCarID);
        params1.addFormDataPart("tokenString", token);
        Log.e("--AAAAA------->",PledgeCarID + "----" + token);
        HttpRequest.post(Api.GET_CAR_INFO, params1, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--AAAAA------->", jsonObject.toString());
                JSONArray sdasd1= jsonObject.getJSONArray("DataList");
                Log.e("--AAAAA------->", sdasd1.size()+"");
                if(sdasd1.size()==0){

                }else{
                    CarMessage_Bean carMessage = JSONObject.parseObject(jsonObject.toString(), CarMessage_Bean.class);
                    CarNumberView.setText("车牌号：" +carMessage.getDataList().get(0).getCarInfo().getCarNumber());
                    CarBrandView.setText("车辆品牌："+carMessage.getDataList().get(0).getCarInfo().getCarBrand());
                    CarValueView.setText("借款金额：￥"+ carMessage.getDataList().get(0).getCarInfo().getCarValue());
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    carMessage.getDataList().get(0).getPledgeTime();
                    int a = carMessage.getDataList().get(0).getPledgeTime().indexOf("(", 0);
                    int b = carMessage.getDataList().get(0).getPledgeTime().indexOf(")", 0);
                    PledgeTimeView.setText("开始日期："+format.format(Long.valueOf(carMessage.getDataList().get(0).getPledgeTime().substring(a + 1, b)) ));
                    DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                    carMessage.getDataList().get(0).getRepayTime();
                    int a1 = carMessage.getDataList().get(0).getRepayTime().indexOf("(", 0);
                    int b1 = carMessage.getDataList().get(0).getRepayTime().indexOf(")", 0);
                    RepayTimeView.setText("结束日期："+format1.format(Long.valueOf(carMessage.getDataList().get(0).getRepayTime().substring(a1 + 1, b1))));
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
    @Override
    protected void onPause() {
        super.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());
    }
}
