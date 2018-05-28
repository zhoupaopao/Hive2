package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.model.bean.OpenBean;
import com.hivee2.mvp.model.bean.TrackBean;
import com.hivee2.utils.AMapUtil;
import com.hivee2.utils.CoordinateUtil;
import com.hivee2.utils.HiveUtil;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

;import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/14.
 */
public class TraceActivity extends Activity implements OnGetRoutePlanResultListener,OnGetPoiSearchResultListener,HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    LocationClient mLocClient;
    private BaiduMap mBaiduMap;
    private ImageView back;
    private ImageView change;
    private TextView cancel;
    private TextView save1;
    private ImageView mNavigationView;
    private LinearLayout zhui;
    private PopupWindow popupWindow;
    private Context mContext = null;
    private TextView baidu;
    private TextView gaode;
    private InfoWindow mInfoWindow;// 点击覆盖物显示的窗口
    private TextView title;
    private TextView mTimeView;
    private TextView mTrackView;
    private TextView mTrackView2;
    private TextView showstatuss;
    private TextView show;
//    private TextView follow;
    private double longitude1=120;//人经度
    private double latitude1=30;//人纬度
    private double longitude2=120;//车经度
    private double latitude2=30;//车纬度
    private double longitude3=120;//车经度
    private double latitude3=30;//车纬度
    private int nowintere = 0;
    private int totalintrer = 0;
    private int numb=0;
    private int HighFrequency;
    private String ProblemTypeName;
    private String interestPoint = "停车场,加油站,维修厂";
    private String token;
    private String fathertoken;
    private LatLng nowtarger;
    private PoiSearch mPoiSearch = null;
    // 浏览路线节点相关
    boolean isFirstLoc = true; // 是否首次定位
    boolean isFirstruote=true;
    private Boolean nav_map_statu = false;
    List<OverlayOptions> oolist = new ArrayList<OverlayOptions>();
    private ImageView visual; //切换视角
    private int judge=1;//判断
    private boolean isTraced=false;//是否开启追踪
    private boolean isfastTraced=false;//是否开启追踪
    private int flag;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    PoiOverlay poiOverlay;
    MyOverlayManager moM;
    private boolean isopen = false;// 是否开启追踪模式
    MapView mMapView = null;    // 地图View
    RouteLine route = null;
    private String cardeviceid;
    String[] intraPoint;
    private ImageView tg_nav_load;
    public MyLocationListenner myListener = new MyLocationListenner();
    OverlayManager routeOverlay = null;
    DrivingRouteResult nowResultd  = null;
    private SharedPreferences sp = null; // 存放用户信息
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private int runtime = 10000;
    private TimeCount time2;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private ImageView starMap;
    private int changmap=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trace);
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        init();
        initListener();
       // getIntent();
        searchRoute();

    }

    public void startTimer() {
        Log.i("starttimer","1111");
        if (time2 == null) {
            time2 = new TimeCount(runtime, 1000);
            time2.start();
        } else {
            time2.start();
        }
    }
    public void stopTimer() {
        if(time2!=null){
            time2.cancel();
            time2=null;
        }
    }
    //倒计时显示在textview中
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture,
                         long countDownInterval) {
            super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔

        }
        @Override
        public void onFinish() {//计时完毕时触发
            mTimeView.setText("距离下一次刷新还有0秒");


            RequestParams params = new RequestParams(TraceActivity.this);
//            mBaiduMap.clear();
            params.addFormDataPart("DeviceID",cardeviceid);
            params.addFormDataPart("TokenString", fathertoken);
            HttpRequest.post(Api.GET_TRACK, params, new JsonHttpRequestCallback() {
                @Override
                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                    super.onSuccess(headers, jsonObject);
                    Log.e("--565656------->", jsonObject.toString());
                    TrackBean trackBean = JSONObject.parseObject(jsonObject.toString(), TrackBean.class);
                    latitude2=Double.valueOf(String.valueOf(trackBean.getBLat()));
                    longitude2=Double.valueOf(String.valueOf(trackBean.getBLng()));
                    latitude3=Double.valueOf(String.valueOf(trackBean.getLat()));
                    longitude3=Double.valueOf(String.valueOf(trackBean.getLng()));
                    Log.e("TIANJIA",trackBean.getIsFastTrack()+"");
//                    if(open==1)
//                    {
//                        mTrackView.setVisibility(View.GONE);
//                        searchRoute();
//                    }
                    Log.e("PPPPPP", trackBean.getBLat() + "");

//                    longitude2 = Double.valueOf(trackBean.getBLng());
                    if(numb==4){
                        Log.i("numb", "jll");
                        mBaiduMap.clear();
                        searchRoute();
                        numb=0;
                    }else{
                        Log.i("numb", "myjin");
                        startTimer();
                        numb++;
                    }
//                    searchRoute();
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

//            flag= Constant.TimerGetTrackInfo;
//            startThread();
        }
        @Override
        public void onTick(long millisUntilFinished){//计时过程显示

            mTimeView.setText("距离下一次刷新还有"+millisUntilFinished /1000+"秒");
            Log.i("millisUntilFinished",millisUntilFinished+"");
        }

    }
    void showDialog(final String messageStr ,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
               TraceActivity.this);

        builder.setMessage(messageStr);
        builder.setNegativeButton("知道了",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    void Dialog(final String titleString,String messageStr, final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                TraceActivity.this);
        builder.setTitle(titleString);
        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("否",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    void showOpenDialog(String titleString,String messageStr, final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                TraceActivity.this);
        builder.setTitle(titleString);
        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(dialogFlag==0){
//                    View popView = LayoutInflater.from(mContext).inflate(
//                            R.layout.trace_window, null);
//                    View rootView = findViewById(R.id.root_main4); // 當前頁面的根佈局
//                    popupWindow = new PopupWindow(popView,
//                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    // 设置弹出动画
////                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
//                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                    popupWindow.setFocusable(true);
//                    popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    // 顯示在根佈局的底部
////                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
////                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
//                    cancel = (TextView) popView.findViewById(R.id.textView5);
//                    save1 = (TextView) popView.findViewById(R.id.textView6);
//                    radioButton3=(RadioButton)popView.findViewById(R.id.radioButton3);
//                    radioButton4=(RadioButton)popView.findViewById(R.id.radioButton4);

//
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(), "未开启追踪",
//                                    Toast.LENGTH_SHORT).show();
//                            popupWindow.dismiss();
//                        }
//                    });
//                    save1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int time;
//                            if(radioButton3.isChecked())
//                            {
//                                time=0;
//                                Log.e("OKMA","OK");
//                            }
//                            else {
//                                time=1;
//                            }
                            RequestParams params = new RequestParams(TraceActivity.this);
                            params.addFormDataPart("DeviceID", cardeviceid);
                            params.addFormDataPart("fastTrack", 0);
                            params.addFormDataPart("TokenString",fathertoken);
                            Log.e("5555555------5555555", Api.DEVICE_OPEN + "----"+cardeviceid+fathertoken);
                            HttpRequest.post(Api.DEVICE_OPEN2, params, new JsonHttpRequestCallback() {
                                @Override
                                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                    super.onSuccess(headers, jsonObject);
                                    OpenBean openBean = JSONObject.parseObject(jsonObject.toString(), OpenBean.class);
                                    Log.e("5555555------5555555", jsonObject.toString());
                                    Log.e("5555555------5555555", HighFrequency + "");

                                    if (openBean.getResult() == 0) {
                                        mTrackView.setText("停止追踪");
                                        isTraced = true;
                                        startTimer();
                                        mTimeView.setVisibility(View.VISIBLE);
                                        mTrackView2.setVisibility(View.VISIBLE);
                                        mTrackView2.setText("快速追踪(1分钟/次)");
//                                        popupWindow.dismiss();
                                        if (HighFrequency == 1) {
//                                            follow.setVisibility(View.VISIBLE);
                                        } else if (HighFrequency == 0) {
//                                            follow.setVisibility(View.VISIBLE);
                                            String time = openBean.getEstimateTime().substring(6, 19);
                                            String opentime = sdf.format(new Date(Long.valueOf(time)));
//                                            follow.setText("设备预计在" + opentime + "变更到追踪模式，请注意电量消耗");
                                            Log.e("5555555------5555555", "" + openBean.getEstimateTime());
                                        }
                                    }
                                    else if(openBean.getResult()==3)
                                    {
//                                        popupWindow.dismiss();
                                        showDialog(openBean.getErrorMsg(), 0);
//                                        Toast.makeText(getApplicationContext(), openBean.getErrorMsg(),
//                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
//                        1470289074248)

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
//
//
//                        }
//                    });



        //dialogFlag 表示是否不要询问 就可以开启   为false则要再次询问
//                    flag = Constant.DeviceopenTrack;
//                    startThread();// 开启线程 开启追踪
//                    Constant.carInfoStatusChange=true;//车辆状态改变 通知主页要刷新
                }else if(dialogFlag==1){
                    showOpenDialog("是否开启追踪","您真的确定开启追踪吗？是否确认？",0);

                }else if(dialogFlag==2){
                    showOpenDialog("是否开启追踪","乱开剁手，开启请及时关闭！是否确认？",1);

                }

            }
        });
        builder.setNegativeButton("否",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    void showCloseDialog(String titleString,String messageStr,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
               TraceActivity.this);
        builder.setTitle(titleString);
        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialogFlag==0){
                    RequestParams params = new RequestParams(TraceActivity.this);
//            params2.addFormDataPart("UserID",userid);
                    params.addFormDataPart("DeviceID",cardeviceid);
                    params.addFormDataPart("TokenString", fathertoken);
                    HttpRequest.post(Api.DEVICE_CLOSE, params, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
                            BaseApiResponse closebean = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
                            if (closebean.getResult() == 0) {
                                mTrackView.setText("开启追踪");
                                isTraced = false;
                                isfastTraced=false;
                                stopTimer();
                                mTimeView.setVisibility(View.INVISIBLE);
                                mTrackView2.setVisibility(View.GONE);
                            }
                            else if(closebean.getResult()==3)
                            {
                                showDialog(closebean.getErrorMsg(), 0);
//                                Toast.makeText(getApplicationContext(), closebean.getErrorMsg(),
//                                        Toast.LENGTH_SHORT).show();

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
//                    flag = Constant.DeviceCloseTrack;
//                    Constant.carInfoStatusChange=true;//车辆状态改变 通知主页要刷新
//                    startThread();
                }else if(dialogFlag==1){
                    showCloseDialog("是否关闭追踪",
                            "请再次确认关闭追踪？", 0);
                }

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopTimer();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    void showOpenDialog2(String titleString,String messageStr, final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                TraceActivity.this);
        builder.setTitle(titleString);
        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(dialogFlag==0){
//                    View popView = LayoutInflater.from(mContext).inflate(
//                            R.layout.trace_window, null);
//                    View rootView = findViewById(R.id.root_main4); // 當前頁面的根佈局
//                    popupWindow = new PopupWindow(popView,
//                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//                    // 设置弹出动画
////                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
//                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                    popupWindow.setFocusable(true);
//                    popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    // 顯示在根佈局的底部
////                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
////                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                    popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
//                    cancel = (TextView) popView.findViewById(R.id.textView5);
//                    save1 = (TextView) popView.findViewById(R.id.textView6);
//                    radioButton3=(RadioButton)popView.findViewById(R.id.radioButton3);
//                    radioButton4=(RadioButton)popView.findViewById(R.id.radioButton4);

//
//                    cancel.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(getApplicationContext(), "未开启追踪",
//                                    Toast.LENGTH_SHORT).show();
//                            popupWindow.dismiss();
//                        }
//                    });
//                    save1.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            int time;
//                            if(radioButton3.isChecked())
//                            {
//                                time=0;
//                                Log.e("OKMA","OK");
//                            }
//                            else {
//                                time=1;
//                            }
                    RequestParams params = new RequestParams(TraceActivity.this);
                    params.addFormDataPart("DeviceID", cardeviceid);
                    params.addFormDataPart("fastTrack", 1);
                    params.addFormDataPart("TokenString",fathertoken);
                    Log.e("5555555------5555555", Api.DEVICE_OPEN + "----"+cardeviceid+fathertoken);
                    HttpRequest.post(Api.DEVICE_OPEN2, params, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
                            OpenBean openBean = JSONObject.parseObject(jsonObject.toString(), OpenBean.class);
                            Log.e("5555555------5555555", jsonObject.toString());
                            Log.e("5555555------5555555", HighFrequency + "");

                            if (openBean.getResult() == 0) {
                                mTrackView.setText("停止追踪");
                                isTraced = true;
                                isfastTraced=true;
                                startTimer();
                                mTimeView.setVisibility(View.VISIBLE);
                                mTrackView2.setVisibility(View.VISIBLE);
                                mTrackView2.setText("普通追踪(5分钟/次)");
//                                        popupWindow.dismiss();
                                if (HighFrequency == 1) {
//                                    follow.setVisibility(View.VISIBLE);
                                } else if (HighFrequency == 0) {
//                                    follow.setVisibility(View.VISIBLE);
                                    String time = openBean.getEstimateTime().substring(6, 19);
                                    String opentime = sdf.format(new Date(Long.valueOf(time)));
//                                    follow.setText("设备预计在" + opentime + "变更到追踪模式，请注意电量消耗");
                                    Log.e("5555555------5555555", "" + openBean.getEstimateTime());
                                }
                            }
                            else if(openBean.getResult()==3)
                            {
//                                        popupWindow.dismiss();
                                showDialog(openBean.getErrorMsg(), 0);
//                                        Toast.makeText(getApplicationContext(), openBean.getErrorMsg(),
//                                                Toast.LENGTH_SHORT).show();

                            }
                        }
//                        1470289074248)

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
//
//
//                        }
//                    });



                    //dialogFlag 表示是否不要询问 就可以开启   为false则要再次询问
//                    flag = Constant.DeviceopenTrack;
//                    startThread();// 开启线程 开启追踪
//                    Constant.carInfoStatusChange=true;//车辆状态改变 通知主页要刷新
                }else if(dialogFlag==1){
                    showOpenDialog2("是否切换到快速追踪模式","您真的确定切换到快速追踪模式吗？是否确认？",0);

                }else if(dialogFlag==2){
                    showOpenDialog2("是否切换到快速追踪模式","切换后记得及时关闭！是否确认？",1);

                }

            }
        });
        builder.setNegativeButton("否",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    void showCloseDialog2(String titleString,String messageStr,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                TraceActivity.this);
        builder.setTitle(titleString);
        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialogFlag==0){
                    RequestParams params = new RequestParams(TraceActivity.this);
                    params.addFormDataPart("DeviceID", cardeviceid);
                    params.addFormDataPart("fastTrack", 0);
                    params.addFormDataPart("TokenString",fathertoken);
                    Log.e("5555555------5555555", Api.DEVICE_OPEN + "----"+cardeviceid+fathertoken);
                    HttpRequest.post(Api.DEVICE_OPEN2, params, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
                            OpenBean openBean = JSONObject.parseObject(jsonObject.toString(), OpenBean.class);
                            Log.e("5555555------5555555", jsonObject.toString());
                            Log.e("5555555------5555555", HighFrequency + "");

                            if (openBean.getResult() == 0) {
                                mTrackView.setText("停止追踪");
                                isTraced = true;
                                isfastTraced=false;
                                startTimer();
                                mTimeView.setVisibility(View.VISIBLE);
                                mTrackView2.setVisibility(View.VISIBLE);
                                mTrackView2.setText("快速追踪(1分钟/次)");
//                                        popupWindow.dismiss();
                                if (HighFrequency == 1) {
//                                    follow.setVisibility(View.VISIBLE);
                                } else if (HighFrequency == 0) {
//                                    follow.setVisibility(View.VISIBLE);
                                    String time = openBean.getEstimateTime().substring(6, 19);
                                    String opentime = sdf.format(new Date(Long.valueOf(time)));
//                                    follow.setText("设备预计在" + opentime + "变更到追踪模式，请注意电量消耗");
                                    Log.e("5555555------5555555", "" + openBean.getEstimateTime());
                                }
                            }
                            else if(openBean.getResult()==3)
                            {
//                                        popupWindow.dismiss();
                                showDialog(openBean.getErrorMsg(), 0);
//                                        Toast.makeText(getApplicationContext(), openBean.getErrorMsg(),
//                                                Toast.LENGTH_SHORT).show();

                            }
                        }
//                        1470289074248)

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
                }else if(dialogFlag==1){
                    showCloseDialog2("是否切换到普通模式",
                            "请确认是否切换？", 0);
                }

            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopTimer();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            latitude1=location.getLatitude();
            longitude1=location.getLongitude();

//            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
//                            // 此处设置开发者获取到的方向信息，顺时针0-360
//                    .direction(100).latitude(location.getLatitude())
//                    .longitude(location.getLongitude()).build();
//            mBaiduMap.setMyLocationData(locData);
//            if (isFirstLoc) {
//                searchRoute();
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    void init(){
        mContext = this;
        starMap=(ImageView)findViewById(R.id.imageView6);
        show=(TextView)findViewById(R.id.textView27);
        back = (ImageView) findViewById(R.id.imageView2);
        showstatuss= (TextView) findViewById(R.id.showstatuss);
        title=(TextView)findViewById(R.id.title_name1);
        mMapView = (MapView) findViewById(R.id.bmapView);
        visual=(ImageView)findViewById(R.id.imageView3);
        mTimeView=(TextView)findViewById(R.id.textView26);
        mNavigationView=(ImageView)findViewById(R.id.imageView4);
        mTrackView=(TextView)findViewById(R.id.textView25);
        zhui=(LinearLayout)findViewById(R.id.zhui);
        mTrackView2=(TextView)findViewById(R.id.textView222);
        mTrackView2.setVisibility(View.GONE);
        change=(ImageView)findViewById(R.id.imageView3);
        tg_nav_load = (ImageView)findViewById(R.id.imageView5);
//        follow=(TextView)findViewById(R.id.textView48);
//        mMapView.setZoomControlsPosition(new Point(150,50));
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        poiOverlay = new MyPoiOverlay(mBaiduMap);
        mTimeView.setVisibility(View.INVISIBLE);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 初始化搜索模块，注册事件监听
        mSearch = RoutePlanSearch.newInstance();
        mSearch.setOnGetRoutePlanResultListener(this);
        Intent intent = getIntent();
        show.setText(intent.getStringExtra("Address"));
        longitude2=Double.valueOf(intent.getStringExtra("longitude2"));
        latitude2=Double.valueOf(intent.getStringExtra("latitude2"));
        longitude3=Double.valueOf(intent.getStringExtra("longitude2"));
        latitude3=Double.valueOf(intent.getStringExtra("latitude2"));
        longitude1=Double.valueOf(intent.getStringExtra("longitude1"));
        latitude1=Double.valueOf(intent.getStringExtra("latitude1"));
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        fathertoken=sp.getString(Constant.login_token, "");
        token=intent.getStringExtra("token");
        cardeviceid=intent.getStringExtra("cardeviceid");
        ProblemTypeName=intent.getStringExtra("ProblemTypeName");
        HighFrequency=intent.getIntExtra("HighFrequency", 0);
        isopen=intent.getBooleanExtra("isopen", true);
        isopen=intent.getBooleanExtra("isopen", true);
        Log.e("shifuo", isopen + "" + HighFrequency);
        Log.e("LONGTITUDE", "" + cardeviceid + "--------" + token);
        if(ProblemTypeName.equals("")){
            showstatuss.setVisibility(View.INVISIBLE);
        }else if(ProblemTypeName.equals("拆除")){
            showstatuss.setVisibility(View.VISIBLE);
            showstatuss.setText("此设备可能被拆除");
        }else if(ProblemTypeName.equals("屏蔽")){
            showstatuss.setVisibility(View.VISIBLE);
            showstatuss.setText("此设备可能被屏蔽");
        }else if(ProblemTypeName.equals("拆除且屏蔽")){
            showstatuss.setVisibility(View.VISIBLE);
            showstatuss.setText("此设备可能被拆除且屏蔽");
        }else{
            showstatuss.setVisibility(View.INVISIBLE);
            showstatuss.setText("");
        }
        if(HighFrequency==0)
        {
            if(isopen)
            {
                mTrackView2.setVisibility(View.VISIBLE);
                RequestParams params = new RequestParams(TraceActivity.this);
                Log.e("00PP00",cardeviceid+"----"+fathertoken);
                params.addFormDataPart("DeviceID",cardeviceid);
                params.addFormDataPart("TokenString", fathertoken);
                HttpRequest.post(Api.GET_TRACK, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("--565656------->", jsonObject.toString());
                        TrackBean trackBean = JSONObject.parseObject(jsonObject.toString(), TrackBean.class);
                        Log.e("TIANJIA4",trackBean.getIsFastTrack()+"");
                        if(trackBean.getIsFastTrack())
                        {
                            isfastTraced=true;
                            mTrackView.setText("关闭追踪");
                            mTrackView2.setText("普通追踪(5分钟/次)");
                        }
                        else {
                            isfastTraced=false;
                            mTrackView.setText("关闭追踪");
                            mTrackView2.setText("快速追踪(1分钟/次)");
                        }

                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        Log.e("alertMsg failure", errorCode + msg);
                    }
                    @Override
                    public void onStart() {
                        super.onStart();//show  progressdialog
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();//hide progressdialog
                    }
                });
            }

        }
        else {
        }


    }
    //                setVisibility
    void initListener(){
        starMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changmap==1)
                {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    changmap=0;
                }
                else if(changmap==0)
                {
                    mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    changmap=1;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data1 = new Intent();

                TraceActivity.this.setResult(RESULT_OK, data1);
                finish();
            }
        });
        title.setText("车辆追踪");
//        follow.setVisibility(View.GONE);
        if(HighFrequency==0)
        {
            if(isopen)
            {
                isTraced=true;
                startTimer();
                mTrackView.setText("停止追踪");
                mTimeView.setVisibility(View.VISIBLE);
            }
        }
            else
        {
            mTrackView.setVisibility(View.GONE);
            zhui.setVisibility(View.GONE);
            isTraced=true;
            startTimer();
            mTimeView.setVisibility(View.VISIBLE);

        }




        mTrackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTraced) {//当前已经开启追踪 再次点击即为关闭追踪 文字上显示的是关闭追踪
                    showCloseDialog("是否关闭追踪",
                            "关闭追踪能节约设备耗电，但一旦关闭了之后需要等数小时才能重新开启追踪，是否确认关闭?", 1);
//               mTrackView.setText("开启追踪");
//               isTraced=false;
//                 stopTimer();
//               mTimeView.setVisibility(View.INVISIBLE);
                } else {
                    showOpenDialog("是否开启追踪", "开启后电量消耗较快，请谨慎操作！是否确认", 2);
//               mTrackView.setText("停止追踪");
//               isTraced=true;
//               startTimer();
//               mTimeView.setVisibility(View.VISIBLE);
                }
            }
        });
        mTrackView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isfastTraced) {//当前已经开启追踪 再次点击即为关闭追踪 文字上显示的是关闭追踪
                    showCloseDialog2("是否切换到普通模式追踪",
                            "切换到普通模式追踪能节约设备耗电，是否确认关闭?", 1);
//               mTrackView.setText("开启追踪");
//               isTraced=false;
//                 stopTimer();
//               mTimeView.setVisibility(View.INVISIBLE);
                } else {
                    showOpenDialog2("是否切换到快速模式追踪", "快速模式追踪电量消耗更快，请谨慎操作！是否确认", 2);
//               mTrackView.setText("停止追踪");
//               isTraced=true;
//               startTimer();
//               mTimeView.setVisibility(View.VISIBLE);
                }
            }
        });
//        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
//
//            @Override
//            public void onMapStatusChangeStart(MapStatus arg0) {
//
//            }
//
//            @Override
//            public void onMapStatusChangeFinish(MapStatus arg0) {
//                nowintere = 0;
//                poiOverlay.removeFromMap();
//                oolist.clear();
//                if (moM != null) {
//                    moM.removeFromMap();
//                }
//                nowtarger = arg0.target;// 获取屏幕中心
//                searchInterest();
//            }
//
//            @Override
//            public void onMapStatusChange(MapStatus arg0) {
//
//            }
//        });
        mNavigationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startNavi();
//                LatLng p1 = new LatLng(latitude1, longitude1);
//                LatLng p2 = new LatLng(latitude2, longitude2);
//                NaviParaOption para = new NaviParaOption();
//                para.startPoint(p1);
//                para.startName("当前位置");
//                para.endPoint(p2);
//                para.endName("目的地");
//                try {
//                    BaiduMapNavigation.openWebBaiduMapNavi(para,
//                            TraceActivity.this);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//				try {
//					// 调用百度客户端导航
//					BaiduMapNavigation.setSupportWebNavi(true);
//					BaiduMapNavigation.openBaiduMapNavi(para,
//							Tab_GetTrackInfo.this);
//				} catch (BaiduMapAppNotSupportNaviException e) {
//					// 若未安装则调用web端导航
//					e.printStackTrace();
//					BaiduMapNavigation.openWebBaiduMapNavi(para,
//							Tab_GetTrackInfo.this);
//				}

            }
        });
        // 路况
        tg_nav_load.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nav_map_statu) {// 默认为false关闭
                    mBaiduMap.setTrafficEnabled(false);
                    nav_map_statu = !nav_map_statu;
                    tg_nav_load.setImageDrawable(getResources().getDrawable(
                            R.mipmap.nav_map_load_normal));
                    Toast.makeText(getApplicationContext(), "实时路况关闭",
                            Toast.LENGTH_SHORT).show();
                } else {// 本来为关闭 点击一下后打开
                    mBaiduMap.setTrafficEnabled(true);
                    nav_map_statu = !nav_map_statu;
                    tg_nav_load.setImageDrawable(getResources().getDrawable(
                            R.mipmap.nav_map_load_pressed));
                    Toast.makeText(getApplicationContext(), "实时路况开启",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        visual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (judge == 1) {
                    LatLng ll = new LatLng(latitude1, longitude1);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    judge = 0;
                    change.setImageDrawable(getResources().getDrawable(R.mipmap.location2_normal));
                } else if (judge == 0) {
                    LatLng ll = new LatLng(latitude2, longitude2);
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    judge = 1;
                    change.setImageDrawable(getResources().getDrawable(R.mipmap.location3_normal));
                }
            }
        });

    }
    public void startNavi() {
        LatLng pt1 = new LatLng(latitude1, longitude1);
        LatLng pt2 = new LatLng(latitude2, longitude2);

        // 构建 导航参数
        NaviParaOption para = new NaviParaOption()
                .startPoint(pt1).endPoint(pt2)
                .startName("当前位置").endName("目的地");

        View popView = LayoutInflater.from(mContext).inflate(
                R.layout.map_window, null);
        View rootView = findViewById(R.id.root_main4); // 當前頁面的根佈局
        popupWindow = new PopupWindow(popView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置弹出动画
//                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 顯示在根佈局的底部
//                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
        baidu = (TextView) popView.findViewById(R.id.textView79);
        gaode = (TextView) popView.findViewById(R.id.textView78);
        baidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng pt1 = new LatLng(latitude1, longitude1);
                LatLng pt2 = new LatLng(latitude2, longitude2);
                // 构建 导航参数
                NaviParaOption para = new NaviParaOption()
                        .startPoint(pt1).endPoint(pt2)
                        .startName("当前位置").endName("目的地");
                Toast.makeText(getApplicationContext(), "请先打开百度地图",
                        Toast.LENGTH_SHORT).show();
                        try {
            BaiduMapNavigation.openBaiduMapNavi(para,TraceActivity.this);
        } catch (BaiduMapAppNotSupportNaviException e) {

            e.printStackTrace();
//            showDialog();
        }
//                Toast.makeText(getApplicationContext(), "未保存修改",
//                        Toast.LENGTH_SHORT).show();
//                popupWindow.dismiss();
            }
        });
        gaode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("DOUBLE",latitude3+"");
                double latitude4;
                double longitude4;
                double pi = 3.14159265358979324;
                double ee = 0.00669342162296594323;
                double a = 6378245.0;  //地球半径
                latitude4= CoordinateUtil.transformLat(longitude3 - 105.0, latitude3 - 35.0);
                longitude4= CoordinateUtil.transformLon(longitude3 - 105.0, latitude3 - 35.0);
                double radLat = latitude3 / 180.0 * pi;
                double magic = Math.sin(radLat);
                magic = 1 - ee * magic * magic;
                double sqrtMagic = Math.sqrt(magic);
                latitude4 = (latitude4 * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
                longitude4 = (longitude4 * 180.0) / (a / sqrtMagic * Math.cos(radLat) * pi);
                latitude3 = latitude3 + latitude4;
                longitude3 = longitude3 + longitude4 ;  //精确到小数点后7位
                Log.e("DOUBLE",latitude4+"");
                        if (AMapUtil.isInstallByRead("com.autonavi.minimap")){
            AMapUtil.goToNaviActivity(TraceActivity.this,"test",null,String.valueOf(latitude3),String.valueOf(longitude3),"1","2");
        }
//                Toast.makeText(getApplicationContext(), "未保存修改",
//                        Toast.LENGTH_SHORT).show();
//                popupWindow.dismiss();
            }
        });

//        try {
//            BaiduMapNavigation.openBaiduMapNavi(para, this);
//        } catch (BaiduMapAppNotSupportNaviException e) {
//            e.printStackTrace();
////            showDialog();
//        }

    }
    private class MyPoiOverlay extends PoiOverlay {

        public MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            // if (poi.hasCaterDetails) {
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
                    .poiUid(poi.uid));
            // }
            return true;
        }
    }
    void searchRoute()
    {

        PlanNode stNode = PlanNode.withLocation(new LatLng(latitude1,longitude1));

//        System.out.println("searchroute-->" + lat + "," + lon);
        PlanNode enNode = PlanNode.withLocation(new LatLng(latitude2,longitude2));
        System.out.println("------------------lol"+latitude1+"---"+longitude1+"---"+latitude2+"----"+longitude2);
        mSearch.drivingSearch((new DrivingRoutePlanOption()).from(stNode).to(
                enNode));// 发起驾车路线规划

    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }
    // 定制RouteOverly
            private class MyDrivingRouteOverlay extends DrivingRouteOverlay {

        public MyDrivingRouteOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public BitmapDescriptor getStartMarker() {

                return BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);

        }

        @Override
        public BitmapDescriptor getTerminalMarker() {

                return BitmapDescriptorFactory.fromResource(R.drawable.car_online1);

        }
    }
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {
        if (drivingRouteResult == null || drivingRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(TraceActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // result.getSuggestAddrInfo()
            return;
        }
        if (drivingRouteResult.error == SearchResult.ERRORNO.NO_ERROR) {
             nodeIndex = -1;

             if ( drivingRouteResult.getRouteLines().size() >= 1 ) {
                 mBaiduMap.clear();
                route = drivingRouteResult.getRouteLines().get(0);
                DrivingRouteOverlay overlay = new MyDrivingRouteOverlay(mBaiduMap);
                routeOverlay = overlay;
                 mBaiduMap.setOnMarkerClickListener(overlay);
                overlay.setData(drivingRouteResult.getRouteLines().get(0));
                overlay.addToMap();
                 nowintere=0;
                searchInterest();
                 if(isFirstruote)
                 {
                     overlay.zoomToSpan();
                     isFirstruote=false;
                 }
                 if(isTraced) {  startTimer();}

//                mBtnPre.setVisibility(View.VISIBLE);
//                mBtnNext.setVisibility(View.VISIBLE);
            }

        }

    }
    @Override
    public void onGetPoiResult(PoiResult resultPoi) {

        if (resultPoi == null
                || resultPoi.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(TraceActivity.this, "未找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (resultPoi.error == SearchResult.ERRORNO.NO_ERROR) {
            // mBaidumap.clear();
            List<PoiInfo> poilist = new ArrayList<PoiInfo>();
            poilist.clear();
            poilist = resultPoi.getAllPoi();

            BitmapDescriptor bd = BitmapDescriptorFactory
                    .fromResource(R.mipmap.interestpoint_empty);
            if (intraPoint[nowintere].equals("厕所")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_toilet);
            } else if (intraPoint[nowintere].equals("停车场")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_park);
            } else if (intraPoint[nowintere].equals("加油站")) {
                bd = BitmapDescriptorFactory.fromResource(R.mipmap.ic_gas);
            } else if (intraPoint[nowintere].equals("银行")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_bank);
            } else if (intraPoint[nowintere].equals("景点")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_spot);
            } else if (intraPoint[nowintere].equals("餐饮")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_catering);
            } else if (intraPoint[nowintere].equals("酒店")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_hotel);
            } else if (intraPoint[nowintere].equals("服务区")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.interestpoint_service_area);
            } else if (intraPoint[nowintere].equals("维修厂")) {
                bd = BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_car_fix);
            }
            for (PoiInfo pi : poilist) {
                Bundle bundle = new Bundle();
                bundle.putString("poiaddress", pi.address);
                OverlayOptions oo = new MarkerOptions().icon(bd)
                        .position(pi.location).title(pi.name).extraInfo(bundle);

                oolist.add(oo);
            }
            System.out.println("---" + nowintere+"------"+totalintrer);
            nowintere++;
            if (nowintere < totalintrer) {

                searchInterest();
            } else if (nowintere == totalintrer) {
                moM = new MyOverlayManager(mBaiduMap, oolist);
                moM.addToMap();
            }
            // poiOverlay=new PoiOverlay(mBaidumap);
            //
            mBaiduMap.setOnMarkerClickListener(moM);
            // poiOverlay.setData(resultPoi);
            // poiOverlay.addToMap();

            // overlay.zoomToSpan();
            return;
        }

    }
    private class MyOverlayManager extends OverlayManager {

        private List<OverlayOptions> oolist;

        public MyOverlayManager(BaiduMap arg0, List<OverlayOptions> list) {
            super(arg0);
            oolist = list;
        }

        @Override
        public boolean onMarkerClick(Marker marker) {
            System.out.println("markerclick-->" + marker.getTitle());

            LayoutInflater inflater = LayoutInflater
                    .from(getApplicationContext());

            View linear = inflater.inflate(
                    R.layout.infowindow_tab_gettrackinfo, null);
            TextView name = (TextView) linear
                    .findViewById(R.id.infowindow_tgti_name);
            TextView address = (TextView) linear
                    .findViewById(R.id.infowindow_tgti_address);
            name.setText(marker.getTitle());
            address.setText(marker.getExtraInfo().getString("poiaddress"));
            LatLng ll = marker.getPosition();
			/*
			 * view - InfoWindow 展示的 view position - InfoWindow 显示的地理位置 yOffset
			 * - InfoWindow Y 轴偏移量 listener - InfoWindow 点击监听者
			 */
            mInfoWindow = new InfoWindow(linear, ll, -47);
            mBaiduMap.showInfoWindow(mInfoWindow);

            return true;
        }

        @Override
        public List<OverlayOptions> getOverlayOptions() {
            return oolist;
        }

        @Override
        public boolean onPolylineClick(Polyline arg0) {
            // TODO Auto-generated method stub
            return false;
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
    void searchInterest() {
        intraPoint = interestPoint.split(",");
        totalintrer = intraPoint.length;
        System.out.println(intraPoint.length + "-->" + nowintere);
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.radius(5000);//距离
        option.pageCapacity(5);
        option.location(new LatLng(latitude2,longitude2));
        option.keyword(intraPoint[nowintere]);
        mPoiSearch.searchNearby(option);
    }
    @Override
    protected void onResume() {

        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        HiveUtil ut=new HiveUtil();
        ut.onResumePage(this,this.getClass().getCanonicalName());

    }
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearch.destroy();
        mPoiSearch.destroy();
        stopTimer();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

    }


    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
}
