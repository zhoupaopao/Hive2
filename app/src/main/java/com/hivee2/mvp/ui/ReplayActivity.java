package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.OverlayManager;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.route.DrivingRouteLine;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.hivee2.R;
import com.hivee2.adapter.Replay_Adapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.ReplayBean;
import com.hivee2.utils.HiveUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/8/4.
 */
public class ReplayActivity extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    LocationClient mLocClient;
    int msgCount = 0;
    DrivingRouteLine route = null;
    OverlayOptions oo;
    List<ReplayBean.DataListBean> points = new ArrayList<ReplayBean.DataListBean>();
    Polyline mPolyline;
    boolean highFrequency = true;// True:高频率定位。绘线
    OverlayManager routeOverlay = null;
    int nodeIndex = -1; // 节点索引,供浏览节点时使用
    PoiOverlay poiOverlay;
    MapView mMapView = null;    // 地图View
    ReplayBean replayBean;
    RoutePlanSearch mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private RelativeLayout Back;
    private GPSReplayTimerTask task = null;
    private RelativeLayout titleRela;
    private boolean play_statu = true;
    private TextView title;
    private TextView right;
    private ImageView select;
    private Timer timer = null;
    private ImageView isplay;
    private TextView gps_time;
    private ImageView speed;
    private LinearLayout bottom;
    private Button cancel;
    private SeekBar seekbar;
    private int progress = 0;
    private int runtime = 150;// 10 最快50非常快 150较快 300一般
    private int i = 0;
    private String cardeviceid;
    private String token;
    private InfoWindow mInfoWindow;// 点击覆盖物显示的窗口
    private BaiduMap mBaiduMap;
    private Marker marker;
    private Context mContext = null;
    private PopupWindow popupWindow;

    private String startDate = "";
    private String startTime = "";
    private String endDate = "";
    private String endTime = "";
    private int year_start;
    private int month_start;
    private int day_start;
    private int hour_start;
    private int min_start;
    private int year_end;
    private int month_end;
    private int day_end;
    private int hour_end;
    private int min_end;
    private int changenumber=1;
    private boolean isfreshDT = false;//true 确定 false 取消 用于区分点击的按钮
    private boolean isCustom = false;
    private ListView message1;
    private ImageView change;
    private String message = "";//接口返回信息
    private boolean isCheck = false;//接口是否在查询
    private Replay_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replay);
        init();
        initlisten();
//        addCustomElements();
    }

    public void init() {
        Back = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title_name);
        right = (TextView) findViewById(R.id.title_select);
        select = (ImageView) findViewById(R.id.imageView12);
        speed = (ImageView) findViewById(R.id.imageView13);
        gps_time = (TextView) findViewById(R.id.textView49);
        mMapView = (MapView) findViewById(R.id.bmapView);
        isplay = (ImageView) findViewById(R.id.imageView11);
        seekbar = (SeekBar) findViewById(R.id.seekBar);
        message1=(ListView)findViewById(R.id.listView99);
        change=(ImageView)findViewById(R.id.imageView6);
        message1.setVisibility(View.GONE);
        change.setVisibility(View.GONE);
//        titleRela = (RelativeLayout) findViewById(R.id.gps_title);
        bottom = (LinearLayout) findViewById(R.id.gps_bottom);
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
//        poiOverlay = new MyPoiOverlay(mBaiduMap);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
//        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        LatLng l = new LatLng(39.2222222, 120.22222222);// 随便定个第一个点
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);
        oo = new MarkerOptions().icon(bd).position(l);// 图标
        mLocClient.setLocOption(option);
        mLocClient.start();
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        cardeviceid = intent.getStringExtra("cardeviceid");

        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
        int year = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        int month = mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        int day = mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        int hour=mycalendar.get(Calendar.HOUR_OF_DAY);
        int minute=mycalendar.get(Calendar.MINUTE);
        year_start = year;
        month_start = month;
        day_start = day;
        year_end = year;
        month_end = month;
        day_end = day;
        hour_end=hour;
        min_end=minute;
    }

    // 地图上绘制折线
    void addline() {
        //                mBaiduMap.clear();// 先清空地图
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        msgCount = replayBean.getCount();
        Log.e("msgCount", msgCount+" " );
        if (replayBean.getResult() == 0 && replayBean.getCount() > 1) {
            List<LatLng> p_list = new ArrayList<LatLng>();
            for (ReplayBean.DataListBean point : points) {
                LatLng p = new LatLng(point.getBLat(), point.getBLng());
                p_list.add(p);

                builder.include(p);
            }
            OverlayOptions ooPolyline = new PolylineOptions().width(10).color(0xAA0000FF).points(p_list);
            mBaiduMap.addOverlay(ooPolyline);
            LatLngBounds bounds = builder.build();// 返回:创建出的地理范围对象
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);// 以动画方式更新地图状态，动画耗时 300 ms
            seekbar.setMax(msgCount - 1);
            marker = (Marker) (mBaiduMap.addOverlay(oo));
            marker.setTitle("车的位置");
//                    isFirstCli = false;// 第一次点击获取数据结束
            play_statu = true;// 设置为播放
            isplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
            setSeekBarListener();// 设置seekbar监听器
//            startTimer();// seekbar开启
        } else {
            Toast.makeText(ReplayActivity.this, "无行驶记录", Toast.LENGTH_SHORT).show();
            replayBean = null;
        }
    }

    public void addmarker() {
        adapter = new Replay_Adapter(ReplayActivity.this,points);
        adapter.notifyDataSetChanged();
        message1.setAdapter(adapter);
        mBaiduMap.clear();// 先清空地图
        BitmapDescriptor bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);// gps用红点
        // 基站用蓝点
        Log.i("addmarker", replayBean.getCount()+"");
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        final List<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < replayBean.getCount(); i++)
        {
            LatLng markerll = new LatLng(replayBean.getDataList().get(i).getBLat(),replayBean.getDataList().get(i).getBLng());
            BitmapDescriptor   bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);
            if (replayBean.getDataList().get(i).getType().equals("4000")) {// true为在线
                bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);

            } else if(replayBean.getDataList().get(i).getType().equals("5000")){
                bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);
            }else{
                bd = BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue);
            }
            OverlayOptions oo = new MarkerOptions().icon(bd).position(markerll);// 图标
            Marker marker = (Marker) (mBaiduMap.addOverlay(oo));
            marker.setTitle(i+"");
        }
//        for (int i = 0; i < replayBean.getCount(); i++) {
//            LatLng p = new LatLng(replayBean.getDataList().get(i).getLat(), replayBean.getDataList().get(i).getLng());
//            if (replayBean.getDataList().get(i).getType().equals("4000")) {// 4000=GPS定位 其他为基站定位
//                bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_red);
//            } else {
//                bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue);
//            }
//            OverlayOptions ooMarker = new MarkerOptions().icon(bdmarker).position(p);// 图标
//            Marker marker2 = (Marker) (mBaiduMap.addOverlay(ooMarker));
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("replayMarker", replayBean.getDataList().get(i));
//            marker2.setExtraInfo(bundle);
//            builder.include(p);
//        }
       message1.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                System.out.println("position" + position);
                LatLng ll = new LatLng(replayBean.getDataList().get(position).getBLat(),replayBean.getDataList().get(position).getBLng());
//                LatLng ll = new LatLng(points.get(position).latitude,points.get(position).longitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });

//        LatLngBounds bounds = builder.build();// 返回:创建出的地理范围对象
//        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
//        mBaiduMap.animateMapStatus(u);// 以动画方式更新地图状态，动画耗时 300 ms
//
//        System.out.println("msgCount-->" + msgCount);
//        seekbar.setMax(msgCount - 1);
////        isFirstCli = false;// 第一次点击获取数据结束
//        play_statu = true;// 设置为播放
//        isplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
//        setSeekBarListener();// 设置seekbar监听器
////        startTimer();// seekbar开启
    }

    public void addCustomElements() {
        RequestParams params = new RequestParams(ReplayActivity.this);
        params.addFormDataPart("DeviceID", cardeviceid);
        params.addFormDataPart("StartTime", startDate + " " + startTime);
        params.addFormDataPart("EndTime", endDate + " " + endTime);
        params.addFormDataPart("Page", 1);
        params.addFormDataPart("PageSize", 10000);
        params.addFormDataPart("TokenString", token);
        Log.e("TIANTIAN",cardeviceid+startDate + " " + startTime+ endDate + " " + endTime+"---"+token);
        HttpRequest.post(Api.GET_REPLAY_BY_DEVICEID, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                replayBean = JSONObject.parseObject(jsonObject.toString(), ReplayBean.class);
                Log.e("55555---->", "" + replayBean.getResult() + "----------" + replayBean.getCount());
                Log.e("555554---->", jsonObject.toString());
                points = replayBean.getDataList();
                highFrequency = replayBean.isHighFrequency();
                isCheck = false;
                if (highFrequency) {
                    Log.e("YOUXIAN","---------"+"OK");
                    addline();
                } else {
                    Log.e("WUXIAN", "---------"+"OK");
                    bottom.setVisibility(View.GONE);
                    message1.setVisibility(View.VISIBLE);
                    change.setVisibility(View.VISIBLE);
                    addmarker();
                    if(replayBean.getCount()==0){
                        Log.i("ErrorMsg", jsonObject.getString("ErrorMsg"));
                        Toast.makeText(ReplayActivity.this,jsonObject.getString("ErrorMsg"),Toast.LENGTH_LONG).show();
//                        message1.setVisibility(View.GONE);
//                        changenumber=0;
                    }
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                isCheck = false;
                message = msg;
                handler.sendEmptyMessage(Constant.NOK3);
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
                isCheck = false;
                //hide progressdialog
            }
        });
    }

    public void stopTimer() {
        if (timer != null) {
            task.cancel();
            timer.cancel();

            task = null;
            timer = null;
        }
    }

    public void startTimer() {
        if (timer == null) {
            timer = new Timer();
            task = new GPSReplayTimerTask(ReplayActivity.this) {
                @Override
                public void run() {
                    if (progress >= msgCount) {// 播放结束 线程中不可以改变ui
                        progress = msgCount - 1;
                        play_statu = !play_statu;
                        handler.sendEmptyMessage(99);
                    } else {
                        seekbar.setProgress(progress);
                        progress++;
                    }
                }
            };
            timer.schedule(task, runtime, runtime);
            /*
			 * 第一个参数"new MyTask(event.getServletContext())": 是 TimerTask
			 * 类，在包：import java.util.TimerTask .使用者要继承该类，并实现 public void run()
			 * 方法，因为 TimerTask 类实现了 Runnable 接口。 第二个参数"0"的意思是:(0就表示无延迟)
			 * 当你调用该方法后，该方法必然会调用 TimerTask 类 TimerTask 类 中的 run()
			 * 方法，这个参数就是这两者之间时间的差值，也就是说，用户调用 schedule() 方法后，要等待这么长的时间才可以第一次执行
			 * run() 方法。 第三个参数"60*60*1000"的意思就是: (单位是毫秒60*60*1000为一小时)
			 * (单位是毫秒3*60*1000为三分钟) 第一次调用之后，从第二次开始每隔多长的时间调用一次 run() 方法。
			 */
        }
    }

    public void initlisten() {
        Back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("轨迹回放");
        right.setVisibility(View.GONE);
        mContext = this;


        change.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
              if(changenumber==1)
              {
                  message1.setVisibility(View.GONE);
                  changenumber=0;
              }
              else if(changenumber==0)
                {
                    message1.setVisibility(View.VISIBLE);
                    changenumber=1;
                }
            }

    });

        speed.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                stopTimer();
                View popView = LayoutInflater.from(mContext).inflate(
                        R.layout.pop_window, null);
                View rootView = findViewById(R.id.root_main); // 當前頁面的根佈局
                popupWindow = new PopupWindow(popView,
                        LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                // 设置弹出动画
//                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                // 顯示在根佈局的底部
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
                        0);
                cancel = (Button) popView.findViewById(R.id.btn_cancle);
                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                    }
                });
                ((TextView)popView.findViewById(R.id.speed_general)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runtime = 300;
                        ((TextView) findViewById(R.id.textView51)).setText("一般");
                        popupWindow.dismiss();
                    }
                });
                ((TextView)popView.findViewById(R.id.speed_fast)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runtime = 150;
                        ((TextView) findViewById(R.id.textView51)).setText("较快");
                        popupWindow.dismiss();
                    }
                });
                ((TextView)popView.findViewById(R.id.speed_faster)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runtime = 50;
                        ((TextView) findViewById(R.id.textView51)).setText("非常快");
                        popupWindow.dismiss();
                    }
                });
                ((TextView)popView.findViewById(R.id.speed_fastest)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        runtime = 10;
                        ((TextView) findViewById(R.id.textView51)).setText("最快");
                        popupWindow.dismiss();
                    }
                });
            }
        });
        select.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                stopTimer();
                chooseTime();
            }
        });
        isplay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startDate.equals("") || startTime.equals("") || endDate.equals("") || endTime.equals("")){
                    chooseTime();
                    return;
                }
                if(replayBean == null){
                    if(isCheck) {
                        Toast.makeText(ReplayActivity.this, "查询中，请稍等", Toast.LENGTH_LONG).show();
                        return;
                    } else {
                        isCheck = true;
                        addCustomElements();
                        return;
                    }
                }
                if (replayBean.getCount() > 1 && play_statu == true) {
                    isplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pause));
                    play_statu = !play_statu;
                    startTimer();
                } else {
                    isplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
                    play_statu = !play_statu;
                    stopTimer();
                }
            }
        });
    }

    private DatePickerDialog.OnDateSetListener Datelistener_start = new DatePickerDialog.OnDateSetListener() {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //更新日期
            year_start = year;
            month_start = month;
            day_start = day;
            updateDate(year, month, day);
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate(int year, int month, int day) {
            String month_ = String.valueOf(month + 1);
            String day_ = String.valueOf(day);
            if (month + 1 < 10) {
                month_ = "0" + month_;
            }
            if (day < 10) {
                day_ = "0" + day_;
            }
            startDate = String.valueOf(year) + "-" + month_ + "-" + day_;
            isfreshDT = true;
        }
    };

    private DatePickerDialog.OnDateSetListener Datelistener_end = new DatePickerDialog.OnDateSetListener() {
        /**params：view：该事件关联的组件
         * params：myyear：当前选择的年
         * params：monthOfYear：当前选择的月
         * params：dayOfMonth：当前选择的日
         */
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            //更新日期
            year_end = year;
            month_end = month;
            day_end = day;
            updateDate(year, month, day);
        }
        //当DatePickerDialog关闭时，更新日期显示
        private void updateDate(int year, int month, int day) {
            String month_ = String.valueOf(month + 1);
            String day_ = String.valueOf(day);
            if (month + 1 < 10) {
                month_ = "0" + month_;
            }
            if (day < 10) {
                day_ = "0" + day_;
            }
            endDate = String.valueOf(year) + "-" + month_ + "-" + day_;
            isfreshDT = true;
        }
    };

    private TimePickerDialog.OnTimeSetListener Timelistener_start = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            hour_start = hour;
            min_start = minute;
            updateDate(hour, minute);
        }

        private void updateDate(int hour, int minute) {
            String hour_ = String.valueOf(hour);
            String minute_ = String.valueOf(minute);
            if (hour < 10) {
                hour_ = "0" + hour_;
            }
            if (minute < 10) {
                minute_ = "0" + minute_;
            }
            startTime = hour_ + ":" + minute_ + ":00";
            isfreshDT = true;
        }
    };

    private TimePickerDialog.OnTimeSetListener Timelistener_end = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            hour_end = hour;
            min_end = minute;
            updateDate(hour, minute);
        }

        private void updateDate(int hour, int minute) {
            String hour_ = String.valueOf(hour);
            String minute_ = String.valueOf(minute);
            if (hour < 10) {
                hour_ = "0" + hour_;
            }
            if (minute < 10) {
                minute_ = "0" + minute_;
            }
            endTime = hour_ + ":" + minute_ + ":59";
            isfreshDT = true;
        }
    };

    public void MyDateDialog_start(final TextView start_time){
        DatePickerDialog dpd = new DatePickerDialog(ReplayActivity.this, Datelistener_start, year_start, month_start, day_start);
        dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(isfreshDT) {
                    start_time.setText(startDate + " " + startTime);
                    isfreshDT = false;
                    MyTimeDialog_start(start_time);
                }
            }
        });
        if(!start_time.getText().toString().equals("")) {
            dpd.setButton3("时间", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyTimeDialog_start(start_time);
                }
            });
        }
        dpd.show();
    }

    public void MyDateDialog_end(final TextView end_time){
        DatePickerDialog dpd = new DatePickerDialog(ReplayActivity.this, Datelistener_end, year_end, month_end, day_end);
        dpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isfreshDT) {
                    end_time.setText(endDate + " " + endTime);
                    isfreshDT = false;
                    MyTimeDialog_end(end_time);
                }
            }
        });
        if(!end_time.getText().toString().equals("")) {
            dpd.setButton3("时间", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyTimeDialog_end(end_time);
                }
            });
        }
        dpd.show();
    }

    public void MyTimeDialog_start(final TextView start_time){
        TimePickerDialog tpd = new TimePickerDialog(ReplayActivity.this, Timelistener_start, hour_start, min_start, true);
        tpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(isfreshDT) {
                    start_time.setText(startDate + " " + startTime);
                    isfreshDT = false;
                }
            }
        });
        tpd.setButton3("日期", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDateDialog_start(start_time);
            }
        });
        tpd.show();
    }

    public void MyTimeDialog_end(final TextView end_time){
        TimePickerDialog tpd = new TimePickerDialog(ReplayActivity.this, Timelistener_end, hour_end, min_end, true);
        tpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(isfreshDT) {
                    end_time.setText(endDate + " " + endTime);
                    isfreshDT = false;
                }
            }
        });
        tpd.setButton3("日期", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDateDialog_end(end_time);
            }
        });
        tpd.show();
    }

    public void chooseTime(){
        final View popView = LayoutInflater.from(mContext).inflate(
                R.layout.time_pop, null);
        View rootView = findViewById(R.id.root_main); // 當前頁面的根佈局
        popupWindow = new PopupWindow(popView,
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // 设置弹出动画
//                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final Date date = new Date();
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        final LinearLayout time = (LinearLayout) popView.findViewById(R.id.time_detail);
        time.setVisibility(View.GONE);
        startDate = sdf.format(date);
        endDate = sdf.format(date);
        startTime = " 00:00:00";
        endTime = " 23:59:59";
        ((RadioGroup) popView.findViewById(R.id.rg_timestatus)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_today:
                        isCustom = false;
                        time.setVisibility(View.GONE);
                        startDate = sdf.format(date);
                        endDate = sdf.format(date);
                        startTime = " 00:00:00";
                        endTime = " 23:59:59";
                        break;
                    case R.id.rb_yesterday:
                        isCustom = false;
                        time.setVisibility(View.GONE);
                        calendar.add(calendar.DATE, -1);// 整数往后推,负数往前移动
                        startDate = sdf.format(calendar.getTime());
                        endDate = sdf.format(calendar.getTime());
                        startTime = " 00:00:00";
                        endTime = " 23:59:59";
                        break;
                    case R.id.rb_bfyesterday:
                        isCustom = false;
                        time.setVisibility(View.GONE);
                        calendar.add(calendar.DATE, -2);// 整数往后推,负数往前移动
                        startDate = sdf.format(calendar.getTime());
                        endDate = sdf.format(calendar.getTime());
                        startTime = " 00:00:00";
                        endTime = " 23:59:59";
                        break;
                    case R.id.rb_custom:
                        isCustom = true;
                        time.setVisibility(View.VISIBLE);
//                        DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
//                                ReplayActivity.this, "2014年8月23日 17:44");
//                        dateTimePicKDialog.dateTimePicKDialog((TextView)popView.findViewById(R.id.time_start));
                        final TextView start_time = (TextView)popView.findViewById(R.id.time_start);
                        final TextView end_time = (TextView)popView.findViewById(R.id.time_end);
                        start_time.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyDateDialog_start(start_time);
                            }
                        });
                        end_time.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                MyDateDialog_end(end_time);
                            }
                        });
                        startDate = "";
                        startTime = "";
                        endDate = "";
                        endTime = "";
                        if(start_time.getText().toString().length() == 19) {
                            startDate = start_time.getText().toString().substring(0, 10);
                            startTime = start_time.getText().toString().substring(11);
                        }
                        if(end_time.getText().toString().length() == 19) {
                            endDate = end_time.getText().toString().substring(0, 10);
                            endTime = end_time.getText().toString().substring(11);
                        }
                        break;
                }
            }
        });
        ((TextView) popView.findViewById(R.id.btn_cancle)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
                Date mydate = new Date(); //获取当前日期Date对象
                mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
                int year = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
                int month = mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
                int day = mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
                year_start = year;
                month_start = month;
                day_start = day;
                year_end = year;
                month_end = month;
                day_end = day;
                popupWindow.dismiss();
            }
        });
        ((TextView) popView.findViewById(R.id.btn_sure)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startDate.equals("") || startTime.equals("")) {
                    Toast.makeText(ReplayActivity.this, "请选择开始日期与时间", Toast.LENGTH_LONG).show();
                    return;
                }
                if(endDate.equals("") || endTime.equals("")) {
                    Toast.makeText(ReplayActivity.this, "请选择结束日期与时间", Toast.LENGTH_LONG).show();
                    return;
                }
                popupWindow.dismiss();
                replayBean = null;
                isCheck = true;
                addCustomElements();
            }
        });
    }

    void setSeekBarListener() {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress = seekBar.getProgress();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < seekBar.getMax()) {
                    LatLng latlng = new LatLng(points.get(progress).getBLat(), points.get(progress).getBLng());
                    Point p = mBaiduMap.getProjection().toScreenLocation(latlng);
                    System.out.println("p,x,y" + p.x + "," + p.y);
                    System.out.println("bottom-->"+bottom.getHeight());
                    if (p.x < 0 || p.x > mMapView.getWidth() || p.y < 0 || p.y > mMapView.getHeight()) {
                        // 当前坐标点跑出屏幕外 将地图拉回
                        MapStatusUpdate u2 = MapStatusUpdateFactory.newLatLngZoom(latlng,
                                mBaiduMap.getMapStatus().zoom); // 设置地图中心点以及缩放级别
                        mBaiduMap.animateMapStatus(u2);
                    }
                    gps_time.setText("时间：" + replayBean.getDataList().get(progress).getPositionTime() +
                            "   速度：" + replayBean.getDataList().get(progress).getSpeed() + "公里/小时");

                    if (highFrequency) {
                        marker.setPosition(latlng);
                    } else {// 低频
//                        LayoutInflater inflaterMarker2 = LayoutInflater.from(getApplicationContext());
//
//                        View linear = inflaterMarker2.inflate(R.layout.infowindow_tabreplaymarker2, null);
//                        TextView time = (TextView) linear.findViewById(R.id.infowindow_tr_time);
//                        time.setText("定位时间:" + replayBean.getDataList().get(progress).getPositionTime());

						/*
						 * view - InfoWindow 展示的 view position - InfoWindow
						 * 显示的地理位置 yOffset - InfoWindow Y 轴偏移量 listener -
						 * InfoWindow 点击监听者
						 */
//                        mInfoWindow = new InfoWindow(linear, latlng, -47);
//                        mBaiduMap.showInfoWindow(mInfoWindow);
                    }

                }

            }
        });
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

        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    public class GPSReplayTimerTask extends TimerTask {
        public ReplayActivity activity;

        public GPSReplayTimerTask(ReplayActivity act) {
            activity = act;
        }

        @Override
        public void run() {
        }
    }

    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Constant.NOK3:
                    Toast.makeText(ReplayActivity.this, "错误信息：" + message, Toast.LENGTH_LONG).show();
                    break;
                case 99:// timertask 结束播放
                    System.out.println("播放结束");
                    play_statu = !play_statu;
                    isplay.setImageDrawable(getResources().getDrawable(R.mipmap.icon_play));
                    stopTimer();
                    break;
            }
        }
    };

}
