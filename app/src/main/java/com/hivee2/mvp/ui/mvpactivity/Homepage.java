package com.hivee2.mvp.ui.mvpactivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Text;
import com.baidu.mapapi.model.LatLng;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.basemvp.BaseMvpActivity;
import com.hivee2.mvp.model.bean.DeviceBean;
import com.hivee2.mvp.model.bean.LoginBean;
import com.hivee2.mvp.model.bean.RoleRightsBeans;
import com.hivee2.mvp.model.bean.TrackBean;
import com.hivee2.mvp.presenter.HomepagePresenter;
import com.hivee2.mvp.ui.AccountSetting;
import com.hivee2.mvp.ui.Alertmessage;
import com.hivee2.mvp.ui.BorrowActivity;
import com.hivee2.mvp.ui.Car_List;
import com.hivee2.mvp.ui.Detailcar;
import com.hivee2.mvp.ui.FenceActivity;
import com.hivee2.mvp.ui.NoticeActivity;
import com.hivee2.mvp.ui.OrderManagerActivity;
import com.hivee2.mvp.ui.ReplayActivity;
import com.hivee2.mvp.ui.SysApplication;
import com.hivee2.mvp.ui.SystemActivity;
import com.hivee2.mvp.ui.TraceActivity;
import com.hivee2.mvp.ui.WirelessActivity;
import com.hivee2.mvp.ui.view.IHomepageView;

import com.hivee2.utils.HiveUtil;
import com.hivee2.widget.dragLayout.DragLayout;
import com.nineoldandroids.view.ViewHelper;
import com.hivee2.mvp.ui.Atychildaccount;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.util.ArrayList;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import okhttp3.Headers;


public class Homepage extends BaseMvpActivity<IHomepageView,HomepagePresenter> implements IHomepageView {
    private DragLayout dl;
    private ListView lv;
    private LinearLayout firstlayout;
    private LinearLayout nell;
    private LinearLayout secondlayout;
    private LinearLayout gdlayout;
    private LinearLayout borrowLinear;
    private LinearLayout forthlayout;
    private LinearLayout fifthlayout;
    private LinearLayout sixthlayout;
    private LinearLayout leftMenuLinear;
    private LinearLayout Search;
    private String check2;
    private String longitude1;//人经度
    private String latitude1;//人纬度
    private String longitude2;//车经度
    private String latitude2;//车纬度
    private String cardeviceid;
    private ImageView ivIcon;
    private ImageView carListImgView;
    private ImageView alert;
    private MapView mapView;
    private BaiduMap baiduMap;
    private ImageView mapImageView;
    private ProgressDialog progressDialog;
    private SharedPreferences sp=null;
    private LatLng myLoc;
    private ImageView leftNext;
    private ImageView rightNext;
    private  TextView showstatus;
    private TextView IDNAME;
    private TextView IDNAME1;
//    private TextView SBM;
//    private TextView DL;
    private String display;
    private InfoWindow mInfoWindow;// 点击覆盖物显示的窗口
    // 定位相关声明
    public LocationClient locationClient = null;
    public String CarNumber;
    public String Carinforid;
    public String BorrowMan1;
    public String Device1;
    public String Address1;
    public String Cardname1;
    public String Imei1;
    public String Style1;
    public String Start1;
    public String End1;
    public String BL1;
    public String BS1;
    public String token;
    public String lineCount1;
    public String PositionTime1;
    public String  customer;
    private Bitmap tempBitmap;
    private int HighFrequency;
    private String ProblemTypeName;
    private String CarStateName;
    private Boolean isopen;
    private int zoomLevel=13;//地图缩放尺寸
    private ImageView starMap;
    private ImageView big;
    private ImageView small;
    private ImageView myPlace;
    private int changmap=1;
    private int start=1;
//    private ImageView iv_BS;
    private ImageView lukuang;
    private Boolean nav_map_statu = false;
    private Boolean ispeople=true;
    private MapStatus mapStatus;//百度地图状态




    public BDLocationListener myBDLListener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation==null || mapView==null){
                return;
            }
            //System.out.println(bdLocation.getLatitude()+"-->"+bdLocation.getLongitude());
            MyLocationData locData = new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
            baiduMap.setMyLocationData(locData); // 设置定位数据
            longitude1=String.valueOf(bdLocation.getLongitude());
            latitude1 =String.valueOf(bdLocation.getLatitude());
            if(start==1){
                LatLng ll = new LatLng(Double.valueOf(latitude1),Double.valueOf( longitude1));
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(10.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                start=0;
            }

            Log.e("LOCATION",""+bdLocation.getLongitude());

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    };

    /**
     * 设置定位参数
     */
    private void setLocationOption() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开GPS
        // option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//
        // 设置定位模式
        option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(5000); // 设置发起定位请求的间隔时间为5000ms
        // option.setIsNeedAddress(true); // 返回的定位结果包含地址信息
        // option.setNeedDeviceDirect(true); // 返回的定位结果包含手机机头的方向
        locationClient.setLocOption(option);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        SysApplication.getInstance().addActivity(this);
        Intent intent=getIntent();
        mPresenter.setUserBean((LoginBean) intent.getSerializableExtra("userBean"));

        initView();
        initDragLayout();
        //原始获取车辆列表的位置
//        mPresenter.getDeviceListByUserId();

    }



    @Override
    public HomepagePresenter initPresenter() {
        return new HomepagePresenter();
    }


    //    first, second, third, forth, fifth, sixth, seventh, eighth, ninth, tenth, eleventh, twelfth
    private void initDragLayout() {
        dl = (DragLayout) findViewById(R.id.dl);
        dl.setDragListener(new DragLayout.DragListener() {
            //界面打开的时候
            @Override
            public void onOpen() {
                baiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        tempBitmap = bitmap;
                        mapImageView.setImageBitmap(tempBitmap);
                        mapImageView.setVisibility(View.VISIBLE);
                        mapView.setVisibility(View.GONE);
                    }
                });
            }

            //界面关闭的时候
            @Override
            public void onClose() {
                if (tempBitmap != null && !tempBitmap.isRecycled()) {
                    System.out.println("recyled");
                    tempBitmap.recycle();
                    tempBitmap = null;
                }
                mapImageView.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
            }

            //界面滑动的时候
            @Override
            public void onDrag(float percent) {
                ViewHelper.setAlpha(ivIcon, 1 - percent);
            }
        });
    }


    void initView() {


        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        customer=sp.getString(Constant.sp_customer, "");
        display=sp.getString(Constant.sp_display, "");
        token=sp.getString(Constant.sp_token, "");
        Log.e("shichu",token);
        mapView = (MapView) findViewById(R.id.mapView);
        baiduMap = mapView.getMap();
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(myBDLListener); // 注册监听函数
        setLocationOption(); // 设置定位参数
        locationClient.start(); // 开始定位
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置为一般地图
        baiduMap.setTrafficEnabled(false); // 开启交通图
        baiduMap.setMyLocationEnabled(true);//隐藏原来的大小变化
        mapView.showZoomControls(false);

        starMap=(ImageView)findViewById(R.id.imageView18);
        big=(ImageView)findViewById(R.id.imageView22);
        small=(ImageView)findViewById(R.id.imageView21);
        myPlace=(ImageView)findViewById(R.id.imageView19);
        check2=sp.getString("check2", "");
        lukuang=(ImageView)findViewById(R.id.imageView20);
        IDNAME=(TextView)findViewById(R.id.childaccount_name);
        mapImageView = (ImageView) findViewById(R.id.map_imagView);
        leftNext=(ImageView)findViewById(R.id.hp_map_left);
        rightNext=(ImageView)findViewById(R.id.hp_map_right);
        leftMenuLinear=(LinearLayout)findViewById(R.id.hp_leftmenu);
        ivIcon = (ImageView) findViewById(R.id.hp_imgbtn);
        firstlayout = (LinearLayout) this.findViewById(R.id.hp_first);
        nell=(LinearLayout)this.findViewById(R.id.hp_bell);
        carListImgView=(ImageView)findViewById(R.id.hp_carlist);
        alert=(ImageView)findViewById(R.id.hp_alert);
        showstatus= (TextView) findViewById(R.id.showstatus);

        secondlayout = (LinearLayout) this.findViewById(R.id.hp_header);
        gdlayout = (LinearLayout) this.findViewById(R.id.hp_gd);
        borrowLinear = (LinearLayout) this.findViewById(R.id.hp_borrow);
        forthlayout = (LinearLayout) this.findViewById(R.id.hp_wl);
        fifthlayout = (LinearLayout) this.findViewById(R.id.hp_fg);
        sixthlayout = (LinearLayout) this.findViewById(R.id.hp_xt);
        IDNAME1=(TextView)findViewById(R.id.name);

        Search=(LinearLayout)this.findViewById(R.id.search);
        progressDialog=new ProgressDialog(this);
        IDNAME.setText(customer);
        IDNAME1.setText(customer);

        if(check2.toString().equals("true")){
            PgyUpdateManager.register(Homepage.this, "com.hivee2.zhou",
                    new UpdateManagerListener() {

                        @Override
                        public void onUpdateAvailable(final String result) {

                            // 将新版本信息封装到AppBean中
                            final AppBean appBean = getAppBeanFromString(result);
                            Log.i("resultresult",result);
                            new AlertDialog.Builder(Homepage.this)
                                    .setTitle("更新")
                                    .setMessage("系统检测到您的版本过低，请更新")
                                    .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                                        @Override

                                        public void onClick(DialogInterface dialog, int which) {

                                        }

                                    })
                                    .setNegativeButton(
                                            "确定",
                                            new DialogInterface.OnClickListener() {

                                                @Override
                                                public void onClick(
                                                        DialogInterface dialog,
                                                        int which) {
                                                    startDownloadTask(
                                                            Homepage.this,
                                                            appBean.getDownloadURL());

                                                }
                                            }).show();
                        }

                        @Override
                        public void onNoUpdateAvailable() {
//                        Toast.makeText(LoginActivity.this, "已经是最新的版本", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        OrderManager();
        lukuang.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (nav_map_statu) {// 默认为false关闭
                    baiduMap.setTrafficEnabled(false);
                    nav_map_statu = !nav_map_statu;
                    lukuang.setImageDrawable(getResources().getDrawable(
                            R.mipmap.nav_map_load_normal));
                    Toast.makeText(getApplicationContext(), "实时路况关闭",
                            Toast.LENGTH_SHORT).show();
                } else {// 本来为关闭 点击一下后打开
                    baiduMap.setTrafficEnabled(true);
                    nav_map_statu = !nav_map_statu;
                    lukuang.setImageDrawable(getResources().getDrawable(
                            R.mipmap.nav_map_load_pressed));
                    Toast.makeText(getApplicationContext(), "实时路况开启",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        leftNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.leftNextCar();
            }
        });
        rightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.rightNextCar();
            }
        });
        starMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changmap==1)
                {
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                    changmap=0;
                }
               else if(changmap==0)
                {
                    baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                    changmap=1;
                }
            }
        });
        small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapStatus=baiduMap.getMapStatus();//获取地图状态
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mapStatus.zoom - 1));
                mapStatus=baiduMap.getMapStatus();
            }
        });
        big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapStatus=baiduMap.getMapStatus();//获取地图状态
                baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mapStatus.zoom + 1));
                mapStatus=baiduMap.getMapStatus();
            }
        });

        myPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ispeople)
                {
                    LatLng ll = new LatLng(Double.valueOf(latitude1),Double.valueOf( longitude1));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(10.0f);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    myPlace.setImageDrawable(getResources().getDrawable(R.mipmap.location3_normal));
                    ispeople=false;
                }
                else {
                    LatLng ll = new LatLng(Double.valueOf(latitude2),Double.valueOf( longitude2));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(10.0f);
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    myPlace.setImageDrawable(getResources().getDrawable(R.mipmap.location2_normal));
                    ispeople=true;
                }

            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,NoticeActivity.class);
                startActivity(intent);
            }
        });
        carListImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView();
                initDragLayout();
                if(sp.getBoolean("show_shouye",true)){
                    mPresenter.getDeviceListByUserId();
                }

            }
        });
        leftMenuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                System.out.println("onclick leftMenuLinear");
                dl.open();
            }
        });

        firstlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, AccountSetting.class);
                startActivity(intent);
            }
        });
        nell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,WirelessActivity.class);
                startActivity(intent);
            }
        });
        secondlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent();
                intent.setClass(Homepage.this, Atychildaccount.class);
                startActivityForResult(intent,3);
//                Intent intent = new Intent(Homepage.this, Atychildaccount.class);
//                startActivity(intent);
            }
        });
        gdlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, OrderManagerActivity.class);
                startActivity(intent);
            }
        });
        borrowLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, BorrowActivity.class);
                startActivity(intent);
            }
        });
        forthlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, FenceActivity.class);
                Log.e("WEIDU","-----"+latitude1);
                intent.putExtra("longitude1",longitude1);
                intent.putExtra("latitude1",latitude1);
                startActivity(intent);
            }
        });
        fifthlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this, Alertmessage.class);
                startActivity(intent);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Homepage.this, Car_List.class);
                SharedPreferences.Editor editor=sp.edit();
                editor.putInt(Constant.sp_page, 20);
                editor.commit();
                startActivityForResult(intent, Constant.Homepage_CarList_forResult);
            }
        });

        sixthlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Homepage.this,SystemActivity.class);
                startActivity(intent);
            }
        });
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //之前把carListBean的索引放入了 marker.title
                int index=Integer.parseInt(marker.getTitle());
                mPresenter.showInfowindow(index);
                return true;
            }
        });
    }

    private void OrderManager() {
        //获取工单管理显示
        RequestParams params=new RequestParams();
        String userid=sp.getString(Constant.sp_userId,"");
        String token=sp.getString(Constant.sp_token,"");
        String paramss = "{userid:'"+userid+"',tokenstring:'" + token + "'}";
        Log.i("paramss", paramss);
        params.addFormDataPart("param",paramss);
        Log.i("api", Api.GETROLEBYUSERID);
        HttpRequest.post(Api.GETROLEBYUSERID,params,new JsonHttpRequestCallback(){
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.i("onSuccess", jsonObject.toString());
                RoleRightsBeans roleRightsBeans=JSONObject.parseObject(jsonObject.toString(),RoleRightsBeans.class);
                if(roleRightsBeans.getResult()==0){
                    //成功
                    ArrayList<RoleRightsBeans.RoleRight>DataList=roleRightsBeans.getDataList();
                    for(int i=0;i<DataList.size();i++){
                        RoleRightsBeans.RoleRight roleRight= DataList.get(i);
                        if(roleRight.getMenu_id()==4){
                            //是工单管理对应的状态
                            if(roleRight.isCan_show()){
                                //可以看见
                                gdlayout.setVisibility(View.VISIBLE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putBoolean("permit_code",roleRight.isCan_manage());
                                editor.putBoolean("can_show",roleRight.isCan_show());
                                //可以创建
                                editor.commit();
                            }
                        }else if(roleRight.getMenu_id()==2){
                            //车辆监控
                            if(!roleRight.isCan_show()){
                                //车辆监控不可见
                                //首页搜索框不显示，左侧右侧按钮不显示
                                //注意刷新
                                Search.setVisibility(View.GONE);
                                leftNext.setVisibility(View.GONE);
                                rightNext.setVisibility(View.GONE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putBoolean("show_shouye",roleRight.isCan_show());
                                //可以创建
                                editor.commit();
                            }else {
                                mPresenter.getDeviceListByUserId();
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putBoolean("show_shouye",roleRight.isCan_show());
                                //可以创建
                                editor.commit();
                            }

                        }else if(roleRight.getMenu_id()==3){
                            //业务管理
                            if(!roleRight.isCan_show()){
                                //不可见
                                borrowLinear.setVisibility(View.GONE);
                            }

                        }else if(roleRight.getMenu_id()==6){
                            //围栏管理
                            if(!roleRight.isCan_show()){
                                //不可见
                                forthlayout.setVisibility(View.GONE);
                            }
                        }else if(roleRight.getMenu_id()==7){
                            //报表统计 风控预警
                            if(!roleRight.isCan_show()){
                                //不可见
                                fifthlayout.setVisibility(View.GONE);
                            }
                        }else if(roleRight.getMenu_id()==8){
                            //贷前评估
                        }else if(roleRight.getMenu_id()==9){
                            //用户信息 系统设置
                            if(!roleRight.isCan_show()){
                                //不可见
//                                sixthlayout.setVisibility(View.GONE);
                                nell.setVisibility(View.GONE);
                            }
                        }
                    }
                }else{
                    Toast.makeText(Homepage.this,roleRightsBeans.getErrorMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFinish() {
                super.onFinish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("onActivityResult", requestCode+""+resultCode);
        if (Constant.Homepage_CarList_forResult == requestCode && resultCode == RESULT_OK) {
            System.out.println("onacticityResult");

            int carlist_pos = data.getIntExtra("position", -1);
            mPresenter.setCurrentDeviceIndex(carlist_pos-1);
            if(Constant.homepageRefrashCarList){
                Constant.homepageRefrashCarList=false;
                if(sp.getBoolean("show_shouye",true)){
                    mPresenter.getDeviceListByUserId();
                    Log.i("getDeviceListByUserId", "1");
                }
//                Log.i("",)
            }else{
                mPresenter.currentCar();
                Log.i("getDeviceListByUserId", "2");
            }
        }
        else if(2== requestCode && resultCode == RESULT_OK)
        {
            initView();
            initDragLayout();
            if(sp.getBoolean("show_shouye",true)){
                mPresenter.getDeviceListByUserId();
            }

        }else if(3== requestCode && resultCode == 3){
//            intent.putExtra("qhzh",qhzh);
//            intent.putExtra("gdgl",gdgl);
//            intent.putExtra("ywgl",ywgl);
//            intent.putExtra("wlgl",wlgl);
//            intent.putExtra("nlsz",nlsz);
//            intent.putExtra("fkyj",fkyj);
//            intent.putExtra("xtsz",xtsz);
//            intent.putExtra("cljk",cljk);
//            在这里进行判断操作
            Log.i("onActivityResult", "1");
//            boolean qhzh=data.getBooleanExtra("qhzh",true);
            boolean gdgl=data.getBooleanExtra("gdgl",true);
            boolean ywgl=data.getBooleanExtra("ywgl",true);
            boolean wlgl=data.getBooleanExtra("wlgl",true);
            boolean nlsz=data.getBooleanExtra("nlsz",true);
            boolean fkyj=data.getBooleanExtra("fkyj",true);
            boolean cljk=data.getBooleanExtra("cljk",true);
            if(gdgl){
                //显示工单管理
                gdlayout.setVisibility(View.VISIBLE);
            }else{
                gdlayout.setVisibility(View.GONE);
            }
            if(ywgl){
                //显示业务管理
                borrowLinear.setVisibility(View.VISIBLE);
            }else{
                borrowLinear.setVisibility(View.GONE);
            }
            if(wlgl){
                //显示围栏管理
                forthlayout.setVisibility(View.VISIBLE);
            }else{
                forthlayout.setVisibility(View.GONE);
            }
            if(nlsz){
                //显示闹铃设置
                nell.setVisibility(View.VISIBLE);
//                sixthlayout.setVisibility(View.VISIBLE);
            }else{
                nell.setVisibility(View.GONE);
//                sixthlayout.setVisibility(View.GONE);
            }
            if(fkyj){
                //显示风控预警
                fifthlayout.setVisibility(View.VISIBLE);
            }else{
                fifthlayout.setVisibility(View.GONE);
            }
            if(cljk){
                //显示车辆监控
                Search.setVisibility(View.VISIBLE);
                leftNext.setVisibility(View.VISIBLE);
                rightNext.setVisibility(View.VISIBLE);
            }else{
                Search.setVisibility(View.GONE);
                leftNext.setVisibility(View.GONE);
                rightNext.setVisibility(View.GONE);
            }


        }


    }

    @Override
    public void showLoading() {
        progressDialog.setMessage("正在获取信息");
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()&&progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String toastStr) {
        Toast.makeText(this,toastStr,Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return sp;
    }

    @Override
    public BaiduMap getBaiduMap() {
        return baiduMap;
    }

    @Override
    public void showInfowindow(DeviceBean.CarListBean carListBean) {
        showstatus.setVisibility(View.INVISIBLE);
        baiduMap.hideInfoWindow();// 隐藏infowindow
        mInfoWindow = null;
        System.out.println("------------>showinfowindow");
        LatLng ll = new LatLng(carListBean.getBLat(), carListBean.getBLng());
        Log.e("111111----->", "" + carListBean.getBLat());
        MapStatusUpdate mu = MapStatusUpdateFactory.newLatLngZoom(ll, zoomLevel); // 设置地图中心点以及缩放级别
        baiduMap.animateMapStatus(mu);

        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

        View linear = inflater.inflate(R.layout.marker_window, null);
        TextView mile=(TextView)linear.findViewById(R.id.icon_acc7);
        TextView mile1=(TextView)linear.findViewById(R.id.icon_acc71);
//        TextView BS = (TextView) linear.findViewById(R.id.icon_BS);
//        TextView BS2=(TextView) linear.findViewById(R.id.icon_BS1);
        ImageView iv_bs= (ImageView) linear.findViewById(R.id.iv_BS);
        TextView SBM=(TextView) linear.findViewById(R.id.icon_SBM);
        TextView DL1=(TextView) linear.findViewById(R.id.icon_DL);
        TextView model = (TextView) linear.findViewById(R.id.icon_EqpName);
        TextView speed = (TextView) linear.findViewById(R.id.icon_speed);
        TextView speed1=(TextView) linear.findViewById(R.id.icon_speed1);
//        TextView BL = (TextView) linear.findViewById(R.id.icon_BL);
        TextView time = (TextView) linear.findViewById(R.id.icon_time);
        TextView follow=(TextView)linear.findViewById(R.id.icon_acc1);
        TextView follow1=(TextView)linear.findViewById(R.id.icon_acc111);
        TextView bind=(TextView)linear.findViewById(R.id.icon_acc2);
        TextView bind1=(TextView)linear.findViewById(R.id.icon_acc21);

        TextView pledgerName = (TextView) linear.findViewById(R.id.icon_PledgerName);
        TextView typeText=(TextView)linear.findViewById(R.id.icon_type);
        TextView typeText1=(TextView)linear.findViewById(R.id.icon_type1);
        TextView model11 = (TextView) linear.findViewById(R.id.icon_acc11);
        TextView model1 = (TextView) linear.findViewById(R.id.icon_acc);
        TextView address = (TextView) linear.findViewById(R.id.icon_address);
        TextView getTrackInfo = (TextView) linear.findViewById(R.id.icon_GetTrackInfo);
        TextView response_GPS_Replay = (TextView) linear.findViewById(R.id.icon_Response_GPS_Replay);
        TextView carsetting = (TextView) linear.findViewById(R.id.icon_carsetting);
        LinearLayout turnto = (LinearLayout) linear.findViewById(R.id.icon_turnto);

        if(display.substring(0,1).equals("1"))
        {
            speed.setVisibility(View.VISIBLE);
            speed1.setVisibility(View.VISIBLE);
        }
        else {
            speed.setVisibility(View.GONE);
            speed1.setVisibility(View.GONE);
        }
        if(display.substring(1,2).equals("1"))
        {
            model1.setVisibility(View.VISIBLE);
            model11.setVisibility(View.VISIBLE);
        }
        else {
            model1.setVisibility(View.GONE);
            model11.setVisibility(View.GONE);
        }
//        if(display.substring(2,3).equals("1"))
//        {
//            BS2.setVisibility(View.VISIBLE);
//            BS.setVisibility(View.VISIBLE);
//        }
//        else {
//            BS2.setVisibility(View.GONE);
//            BS.setVisibility(View.GONE);
//        }
        if(display.substring(3,4).equals("1"))
        {
            DL1.setVisibility(View.VISIBLE);
        }
        else {
            DL1.setVisibility(View.GONE);
        }
        if(display.substring(4,5).equals("1"))
        {
            bind1.setVisibility(View.VISIBLE);
            bind.setVisibility(View.VISIBLE);
        }
        else {
            bind.setVisibility(View.GONE);
            bind1.setVisibility(View.GONE);
        }
        if(display.substring(5,6).equals("1"))
        {
            mile1.setVisibility(View.VISIBLE);
            mile.setVisibility(View.VISIBLE);
        }
        else {
            mile1.setVisibility(View.GONE);
            mile.setVisibility(View.GONE);
        }
        if(display.substring(6,7).equals("1"))
        {
            follow.setVisibility(View.VISIBLE);
            follow1.setVisibility(View.VISIBLE);
        }
        else {
            follow.setVisibility(View.GONE);
            follow1.setVisibility(View.GONE);
        }
        if(display.substring(7,8).equals("1"))
        {
            typeText.setVisibility(View.VISIBLE);
            typeText1.setVisibility(View.VISIBLE);
        }
        else {
            typeText.setVisibility(View.GONE);
            typeText1.setVisibility(View.GONE);
        }
        String type="";

        if(carListBean.getType()==4000){
            type="GPS";
        }else if(carListBean.getType()==5000){
            type="wifi";
        }else{
            type="基站";
        }

        model1.setText(carListBean.getModel());
        if(carListBean.isIsTrack()||carListBean.isHighFrequency())
        {
            follow.setText("已开启");
        }
        else {
            follow.setText("未开启");
        }
        if(carListBean.isIsBindCar())
        {bind.setText("已绑车");}
        else
        { bind.setText("未绑车");}
        typeText.setText(type);
        pledgerName.setText(carListBean.getPledgerName());
        Log.e("LICHENG", carListBean.getYdayMileage() + "");
        mile.setText("" + carListBean.getYdayMileage());
//        BL.setText("电量:" + carListBean.getBL() + "%");
        DL1.setText(carListBean.getBL() + "%");
        SBM.setText(carListBean.getInternalNum());
        final ImageButton icon_close = (ImageButton) linear.findViewById(R.id.icon_close);
//        String carrname=carListBean.getCarNumber() + "/" + carListBean.getInternalNum();
        String carrname=carListBean.getCarNumber();
        Log.e("markerinfo", carrname);
        model.setText(carrname);
        speed.setText(carListBean.getStatusInfo());
        //直接获取当前状态
//        if (carListBean.getSpeed() > 0) {
////            speed.setText("行驶" + " (" + carListBean.getSpeed() + "km/h)");
//            if (carListBean.isIsOnline()) {// true为在线
//                speed.setText("行驶" + " (" + carListBean.getSpeed() + "km/h)");
//            } else {
//                speed.setText("离线");
////                speed.setText(" ("+carListBean.getStatusInfo()+")");
//            }
//        } else {
//            speed.setText("静止");
//        }
        //直接更滑插拔状态图片
        if(carListBean.getBS().equals("正常")){
            iv_bs.setImageResource(R.drawable.nomall45);
        }else if(carListBean.getBS().equals("光感异常")){
            iv_bs.setImageResource(R.drawable.ggyc45);
        }else if(carListBean.getBS().equals("断电")){
            iv_bs.setImageResource(R.drawable.dd45);
        }else if(carListBean.getBS().equals("拔除")){
            iv_bs.setImageResource(R.drawable.ggyc45);
        }
//        BS.setText(carListBean.getBS());
        time.setText(carListBean.getPositionTime());
        address.setText(carListBean.getAddress());

        Address1=carListBean.getAddress();
        longitude2=String.valueOf(carListBean.getBLng());
        latitude2=String.valueOf(carListBean.getBLat());
        cardeviceid= carListBean.getDeviceID();
        Log.e("TTTTTT------->",carListBean.getInternalNum());
        Log.e("963852741------->",cardeviceid);
        RequestParams params = new RequestParams(Homepage.this);
//            params2.addFormDataPart("UserID",userid);
        params.addFormDataPart("DeviceID",cardeviceid);
        params.addFormDataPart("TokenString", token);
        HttpRequest.post(Api.GET_TRACK, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--565656------->", jsonObject.toString());
                TrackBean trackBean = JSONObject.parseObject(jsonObject.toString(), TrackBean.class);
                HighFrequency=trackBean.getIsSocket();
                isopen=trackBean.isIsTrack();
                Log.e("shuchu1111",isopen+"");
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
//        longitude1=
//        latitude1=
        getTrackInfo.setOnClickListener(new View.OnClickListener() {//车辆追踪

            @Override
            public void onClick(View v) {//车辆追踪
                Intent intent = new Intent(Homepage.this, TraceActivity.class);
                intent.putExtra("Address",Address1);
                intent.putExtra("longitude2",longitude2);
                intent.putExtra("latitude2", latitude2);
                Log.e("LONGTITUDE", "" + latitude1 + latitude2);
                intent.putExtra("longitude1", longitude1);
                intent.putExtra("latitude1",latitude1);
                intent.putExtra("cardeviceid",cardeviceid);
                intent.putExtra("ProblemTypeName",ProblemTypeName);
                intent.putExtra("token",token);
                intent.putExtra("HighFrequency",HighFrequency);
                Log.e("shifuo",isopen+"");
                intent.putExtra("isopen",isopen);
                startActivityForResult(intent, 2);
            }
        });
        response_GPS_Replay.setOnClickListener(new View.OnClickListener() {//历史回放
            @Override
            public void onClick(View v) {//历史回放
                Intent intent = new Intent(Homepage.this, ReplayActivity.class);
                intent.putExtra("Address",Address1);
                intent.putExtra("longitude2",longitude2);
                intent.putExtra("latitude2", latitude2);
                Log.e("LONGTITUDE", "" + latitude1 + latitude2);
                intent.putExtra("longitude1", longitude1);
                intent.putExtra("latitude1",latitude1);
                intent.putExtra("cardeviceid",cardeviceid);
                intent.putExtra("token",token);
                intent.putExtra("HighFrequency",HighFrequency);
                intent.putExtra("isopen",isopen);
                startActivity(intent);
            }
        });
        Log.e("121------>", "-------" + carListBean.getValidEnd());
        CarNumber=carListBean.getCarNumber();
        Carinforid=carListBean.getCarInfoID();
       BorrowMan1=carListBean.getPledgerName();
       Device1=carListBean.getInternalNum();
       Address1=carListBean.getAddress();
       Cardname1=carListBean.getSimNum();
       Imei1=carListBean.getIMEI();
       Style1=carListBean.getModel();
       Start1=carListBean.getValidFrom();
       End1=carListBean.getValidEnd();
        BL1=String.valueOf(carListBean.getBL());
       BS1=carListBean.getBS();
        ProblemTypeName=carListBean.getProblemTypeName();
        CarStateName=carListBean.getCarStateName();
       lineCount1=String.valueOf(carListBean.isIsOnline());
       PositionTime1=carListBean.getPositionTime();
        carsetting.setOnClickListener(new View.OnClickListener() {//车辆详情
            @Override
            public void onClick(View v) {//车辆详情
                Intent intent = new Intent(Homepage.this, Detailcar.class);

                intent.putExtra("CarNumber",CarNumber);
                intent.putExtra("Carinforid",Carinforid);
                intent.putExtra("BorrowMan",BorrowMan1);
                intent.putExtra("Device",Device1);
                intent.putExtra("Address",Address1);
                intent.putExtra("Cardname",Cardname1);
                intent.putExtra("Imei",Imei1);
                intent.putExtra("Style",Style1);
                intent.putExtra("Start",Start1);
                intent.putExtra("End",End1);
                intent.putExtra("BL",BL1);
                intent.putExtra("BS",BS1);
                intent.putExtra("lineCount",lineCount1);
                intent.putExtra("PositionTime",PositionTime1);
                startActivity(intent);
            }
        });

        turnto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        icon_close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                baiduMap.hideInfoWindow();// 隐藏infowindow
            }
        });


        // OnInfoWindowClickListener 用自定义的view传入 infowindow的构造方法
        // 否则 OnInfoWindowClickListener不能获取infowindow内部控件的点击事件
        // 因为其只是生成一个bitmap

		/*
		 * view - InfoWindow 展示的 view position - InfoWindow 显示的地理位置 yOffset -
		 * InfoWindow Y 轴偏移量 listener - InfoWindow 点击监听者
		 */
		Log.i("ProblemTypeName",ProblemTypeName);
		if(ProblemTypeName.equals("")){
            showstatus.setVisibility(View.INVISIBLE);
        }else if(ProblemTypeName.equals("拆除")){
            showstatus.setVisibility(View.VISIBLE);
            showstatus.setText("此设备可能被拆除");
        }else if(ProblemTypeName.equals("屏蔽")){
            showstatus.setVisibility(View.VISIBLE);
            showstatus.setText("此设备可能被屏蔽");
        }else if(ProblemTypeName.equals("拆除且屏蔽")){
            showstatus.setVisibility(View.VISIBLE);
            showstatus.setText("此设备可能被拆除且屏蔽");
        }else{
            showstatus.setVisibility(View.INVISIBLE);
            showstatus.setText("");
        }
//        showstatus.setVisibility(View.VISIBLE);
        Log.i("ProblemTypeName",ProblemTypeName+"123");
        mInfoWindow = new InfoWindow(linear, ll, -47);
        baiduMap.showInfoWindow(mInfoWindow);
    }

    @Override
    protected void onResume() {
        Log.i("onActivityResult", "2 ");
        Log.i("onResume", sp.getBoolean("can_show",true)+"|");
        super.onResume();
        HiveUtil ut=new HiveUtil();
        ut.onResumePage(this,this.getClass().getCanonicalName());
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token,"");
        customer=sp.getString(Constant.sp_customer,"");
        display=sp.getString(Constant.sp_display,"");
        Log.e("TTT",customer);
        IDNAME.setText(customer);
        IDNAME1.setText(customer);
        mapView.onResume();
        if(!sp.getBoolean("show_shouye",true)) {
            BaiduMap baiduMap = getBaiduMap();
            baiduMap.clear();
        }
        if(Constant.homepageRefrashCarList){
            Constant.homepageRefrashCarList=false;
            //在carList中有关于数据更新的操作  重新获取
            if(sp.getBoolean("show_shouye",true)){
                mPresenter.getDeviceListByUserId();
            }
        }
        if(Constant.atychildaccoumt)
        {
            Constant.atychildaccoumt=false;
            sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
            customer=sp.getString(Constant.sp_customer, "");
            IDNAME.setText(customer);
            IDNAME1.setText(customer);
            if(sp.getBoolean("show_shouye",true)){
                mPresenter.getDeviceListByUserId();
            }
        }
//        if(sp.getBoolean("can_show",true)){
//            //可见
//            gdlayout.setVisibility(View.VISIBLE);
//        }else{
//            //不可见
//            gdlayout.setVisibility(View.GONE);
//
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

}
