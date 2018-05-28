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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.district.DistrictResult;
import com.baidu.mapapi.search.district.DistrictSearch;
import com.baidu.mapapi.search.district.DistrictSearchOption;
import com.baidu.mapapi.search.district.OnGetDistricSearchResultListener;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.model.bean.ProvinceBean;
import com.hivee2.utils.HiveUtil;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/8/9.
 */
public class AddFence extends Activity implements HttpCycleContext ,OnGetDistricSearchResultListener , OnGetGeoCoderResultListener {
    private SharedPreferences sp=null;// 存放用户信息
    GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private RelativeLayout back;
    private RelativeLayout seek;
    private LinearLayout inout;
    private RelativeLayout change;
    private LinearLayout out;
    private LinearLayout in;
    private ImageView outphoto;
    private ImageView inphoto;
    private TextView outtext;
    private TextView intext;
    private ImageView reduce;
    private ImageView increase;
    private SeekBar seekbar;
    private TextView save;
    private PopupWindow popupWindow;
    private LinearLayout sure;
    private int radius=4000;
    private TextView radiusText;
    private MapStatusUpdate u;
    private TextView cancel;
    private LatLng llCircle;// 圆心
    private CircleOptions ooCircle;
    private String type;
    private String adress;
    private int maxProgress = 100;
    private int progress = 10;
    private Context mContext = null;
    private int radiusLevel;
    private DistrictSearch mDistrictSearch;
    private EditText mCity;
    private EditText mDistrict;
    private final int color = 0xAA00FF00;
    private BaiduMap mBaiduMap;
    private Button mSearchButton;
    private LinearLayout choosecity;
    private LinearLayout choosecity1;
    private int man=0;
    private String select="0";
    private TextView title;
    private EditText name;
    private ImageView delete;
    private TextView save1;
    private TextView city;
    private String cityname;
    private String citynumber;
    public String token="";
    public String userid="";
    public String inorout;
    public String parameterSets;
    public String geoid;
    private double longitude1=120;//人经度
    private double latitude1=40;//人纬度
    private ProgressDialog progressDialog;
    public LocationClient locationClient = null;
    public LatLng currentPt;
    public static final int REQUSET = 1;
    public int d=0;
    public int index;
    private List<LatLng> pts = new ArrayList<LatLng>();
    float zoom = 16;
    MapView mMapView = null;    // 地图View
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fenceadd);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        init();
        initListener();
    }
    public void onGetDistrictResult(DistrictResult districtResult) {
        mBaiduMap.clear();
        if (districtResult == null) {
            return;
        }
        if (districtResult.error == SearchResult.ERRORNO.NO_ERROR) {
            List<List<LatLng>> polyLines = districtResult.getPolylines();
            if (polyLines == null) {
                return;
            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (List<LatLng> polyline : polyLines) {
                OverlayOptions ooPolyline11 = new PolylineOptions().width(10)
                        .points(polyline).dottedLine(true).color(color);
                mBaiduMap.addOverlay(ooPolyline11);
                OverlayOptions ooPolygon = new PolygonOptions().points(polyline)
                        .stroke(new Stroke(5, 0xAA00FF88)).fillColor(0xAAFFFF00);
                mBaiduMap.addOverlay(ooPolygon);
                for (LatLng latLng : polyline) {
                    builder.include(latLng);
                }
            }
            mBaiduMap.setMapStatus(MapStatusUpdateFactory
                    .newLatLngBounds(builder.build()));

        }
    }
    void changeMap() {
        mBaiduMap.clear();
        u = MapStatusUpdateFactory.newLatLngZoom(
                mBaiduMap.getMapStatus().target, zoom); // 获取地图中心点 设置地图中心点以及缩放级别
        // MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
        ooCircle.center(mBaiduMap.getMapStatus().target);
        ooCircle.radius(radius);
        mBaiduMap.addOverlay(ooCircle);
        mBaiduMap.animateMapStatus(u);
       longitude1=mBaiduMap.getMapStatus().target.longitude;
       latitude1=mBaiduMap.getMapStatus().target.latitude;
        Log.e("jindu", longitude1 + "-----" + latitude1 + "-----" + radius);
    }

    void moveMap() {
        mBaiduMap.clear();
        ooCircle.center(mBaiduMap.getMapStatus().target);
        ooCircle.radius(radius);
        mBaiduMap.addOverlay(ooCircle);
        longitude1=mBaiduMap.getMapStatus().target.longitude;
        latitude1=mBaiduMap.getMapStatus().target.latitude;
    }
    public BDLocationListener myBDLListener=new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if(bdLocation==null || mMapView==null){
                return;
            }
            //System.out.println(bdLocation.getLatitude()+"-->"+bdLocation.getLongitude());
            MyLocationData locData = new MyLocationData.Builder().accuracy(bdLocation.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(bdLocation.getLatitude()).longitude(bdLocation.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData); // 设置定位数据
            longitude1=bdLocation.getLongitude();
            latitude1 =bdLocation.getLatitude();
            Log.e("1LOCATION",""+bdLocation.getLongitude());

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    };

    public void circle()
    {
        choosecity1.setVisibility(View.VISIBLE);
     if(select.toString().equals("0"))
     {
     }
else {
         int a=parameterSets.indexOf(",",0);
         int b=parameterSets.indexOf("|", a + 1);
         int c=parameterSets.indexOf("|", b + 1);
         longitude1=Double.valueOf(parameterSets.substring(0, a));
         latitude1=Double.valueOf(parameterSets.substring(a + 1, b));
         double r=Double.valueOf(parameterSets.substring(b + 1, parameterSets.toString().length()));
         radius=(int)r;
     }
        change.setVisibility(View.VISIBLE);
        seek.setVisibility(View.VISIBLE);
        progress = radius / 100;
        llCircle = new LatLng(latitude1,longitude1);
        ooCircle = new CircleOptions().fillColor(0x88c65022).center(llCircle)
                .stroke(new Stroke(3, 0xffc65022)).radius(radius);
        mBaiduMap.addOverlay(ooCircle);
        // }
        // 初始化百度地图的状态
        u = MapStatusUpdateFactory.newLatLngZoom(llCircle, 13); // 设置地图中心点以及缩放级别
        mBaiduMap.animateMapStatus(u);
        radiusText.setText("半径:" + radius + "米");
        seekbar.setMax(maxProgress);
        seekbar.setProgress(progress);
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {

            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                // changeMap();
                moveMap();
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
                // changeMap();
                // moveMap();
            }
        });
    }
    public void polygon()
    {
        choosecity1.setVisibility(View.VISIBLE);
        if(select.toString().equals("0"))
        {
            LatLng ll = new LatLng(latitude1, longitude1);
            MapStatus.Builder builder1 = new MapStatus.Builder();
            builder1.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));
            d=0;
        }
      else {
            int a=0;
            int b=0;
            int c=0;
            int e=0;
            // 添加多边形
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            Log.e("---7777", "----"+parameterSets);
            while (b!=-1)
            {
                a=parameterSets.indexOf(",",b);
                if(e==0)
                {
                    c=b;
                }
                else {
                    c=b+1;
                }
                e=1;
                b=parameterSets.indexOf("|", a + 1);
                double blng=Double.valueOf(parameterSets.substring(c, a));
                d++;
                double blat=0.0;
                if(b==-1)
                {
                    blat=Double.valueOf(parameterSets.substring(a+1,parameterSets.toString().length()));
                }
                else
                {
                    Log.e("---7777", "----"+b);
                    blat=Double.valueOf(parameterSets.substring(a+1,b));
                }
                LatLng pt1 = new LatLng(blat, blng);
                pts.add(pt1);

                BitmapDescriptor bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue);
                OverlayOptions ooMarker = new MarkerOptions().icon(bdmarker).position(pt1);// 图标
                Marker marker2 = (Marker) (mBaiduMap.addOverlay(ooMarker));
                marker2.setTitle(d+"");
                builder.include(pt1);
            }
            if(d>=3){
                double x = pts.get(1).latitude;
                Log.e("---7777", "----" + x);
                OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                        .stroke(new Stroke(d, 0xAA38B0DE)).fillColor(0xAA38B0DE);
                mBaiduMap.addOverlay(ooPolygon);
                LatLngBounds bounds = builder.build();// 返回:创建出的地理范围对象
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
                mBaiduMap.animateMapStatus(u);// 以动画方式更新地图状态，动画耗时 300 ms
            }

        }
        mBaiduMap.setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {

            @Override
            public void onTouch(MotionEvent event) {

            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            /**
             * 单击地图
             */
            public void onMapClick(LatLng point) {
                currentPt = point;
                Log.e("XIANSHI", "-----" + currentPt.latitude);
                updateMapState();
            }

            @Override
            public boolean onMapPoiClick(MapPoi poi) {
                currentPt = poi.getPosition();
                updateMapState();
                return false;
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                index = Integer.parseInt(marker.getTitle());
                Log.e("DIJIGE", "" + index);
                showCloseDialog("删除提示",
                        "请确定要删除该点吗？", 1);
                return true;
            }
        });
    }
    void showCloseDialog(String titleString,String messageStr,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                AddFence.this);
        builder.setTitle(titleString);
        builder.setMessage(messageStr);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogFlag == 1) {
                    pts.remove(index - 1);
                    d--;
                    mBaiduMap.clear();
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (int i = 0; i < d; i++) {

                        BitmapDescriptor bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue);
                        OverlayOptions ooMarker = new MarkerOptions().icon(bdmarker).position(pts.get(i));// 图标
                        Marker marker2 = (Marker) (mBaiduMap.addOverlay(ooMarker));
                        int j = i + 1;
                        marker2.setTitle(j + "");
                        builder.include(pts.get(i));

                    }
                    if (d >= 3) {
                        Log.e("--------", pts.get(0).toString() + "---" + pts.get(1).toString() + "---" + pts.get(2).toString() + "---");
                        OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                                .stroke(new Stroke(d, 0xAA38B0DE)).fillColor(0xAA38B0DE);
                        mBaiduMap.addOverlay(ooPolygon);
                        LatLngBounds bounds = builder.build();// 返回:创建出的地理范围对象
                        MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
                        mBaiduMap.animateMapStatus(u);// 以动画方式更新地图状态，动画耗时 300 ms
                    }

                }
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void updateMapState() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        pts.add(currentPt);
        BitmapDescriptor bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue);
        OverlayOptions ooMarker = new MarkerOptions().icon(bdmarker).position(currentPt);// 图标
        Marker marker2 = (Marker) (mBaiduMap.addOverlay(ooMarker));
        d=d+1;
        marker2.setTitle(d + "");
        builder.include(currentPt);
        if(d>=3){
            Log.e("--------",pts.get(0).toString()+"---"+pts.get(1).toString()+"---"+pts.get(2).toString()+"---");
            OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                    .stroke(new Stroke(d,0xAA38B0DE)).fillColor(0xAA38B0DE);
            mBaiduMap.addOverlay(ooPolygon);
            LatLngBounds bounds = builder.build();// 返回:创建出的地理范围对象
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);// 以动画方式更新地图状态，动画耗时 300 ms
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        //requestCode标示请求的标示   resultCode表示有数据
        if (requestCode == AddFence.REQUSET && resultCode == RESULT_OK) {
            cityname=data.getStringExtra("NAME");
            citynumber=data.getStringExtra("NUMBER");
            city.setText(cityname);
//            Toast.makeText(getApplicationContext(), data.getStringExtra("NAME")+data.getStringExtra("NUMBER"),
//                                    Toast.LENGTH_SHORT).show();
            mDistrictSearch = DistrictSearch.newInstance();
            mDistrictSearch.setOnDistrictSearchListener(AddFence.this);
            if(cityname.equals("选择全省"))
            {
         RequestParams params = new RequestParams(AddFence.this);
        params.addFormDataPart("tokenString", token);
        HttpRequest.post(Api.GET_PROVINCE_CITY, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--2222------->", jsonObject.toString());
                ProvinceBean provinceBean=JSONObject.parseObject(jsonObject.toString(), ProvinceBean.class);
                provinceBean.getProvinceList().size();
                for (int i=0;i<provinceBean.getProvinceList().size();i++)
                {



                    if(citynumber.equals(String.valueOf(provinceBean.getProvinceList().get(i).getProvinceID())))
                    {

                        man=1;
                        mDistrictSearch = DistrictSearch.newInstance();
                        mDistrictSearch.setOnDistrictSearchListener(AddFence.this);
                        mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(provinceBean.getProvinceList().get(i).getProvince()).districtName(""));
                        cityname=provinceBean.getProvinceList().get(i).getProvince();
                        city.setText(cityname);
                    }
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
            else
            {
                mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(cityname));
            }

        }
    }
    public void area()
    {
        choosecity.setVisibility(View.VISIBLE);
        choosecity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddFence.this,
                        City_List.class);
                startActivityForResult(intent, REQUSET);

            }
        });
    }
    public void init()
    {
        sure=(LinearLayout)findViewById(R.id.sure);
        back=(RelativeLayout)findViewById(R.id.back);
        reduce = (ImageView) findViewById(R.id.wl_reduce);
        increase = (ImageView) findViewById(R.id.wl_increase);
        seekbar = (SeekBar) findViewById(R.id.wl_seekbar);
        title=(TextView)findViewById(R.id.title_name);
        radiusText = (TextView) findViewById(R.id.wl_radius);
        mMapView = (MapView) findViewById(R.id.wl_bmapView);
        seek=(RelativeLayout)findViewById(R.id.wl_seekbar_layout);
        change=(RelativeLayout)findViewById(R.id.change);
        save=(TextView)findViewById(R.id.title_select);
        city=(TextView)findViewById(R.id.city);
        choosecity=(LinearLayout)findViewById(R.id.choosecity);
        choosecity1=(LinearLayout)findViewById(R.id.choosecity1);
        choosecity.setVisibility(View.GONE);
        choosecity1.setVisibility(View.GONE);
        progressDialog=new ProgressDialog(this);

        mBaiduMap = mMapView.getMap();
        locationClient = new LocationClient(getApplicationContext()); // 实例化LocationClient类
        locationClient.registerLocationListener(myBDLListener); // 注册监听函数
        setLocationOption(); // 设置定位参数
        locationClient.start(); // 开始定位
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); // 设置为一般地图
        mBaiduMap.setMyLocationEnabled(true);
      }
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
    public void initListener()
    {

        change.setVisibility(View.GONE);
        seek.setVisibility(View.GONE);
        mContext = this;
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        Log.e("SHUSHU",type);
        inorout=intent.getStringExtra("inorout");
        parameterSets=intent.getStringExtra("parameterSets");
        geoid=intent.getStringExtra("geoid");
        select=intent.getStringExtra("select");
        adress=intent.getStringExtra("adress");

        save.setText("保存");
        if(select.toString().equals("0"))
        {
            latitude1=Double.valueOf(intent.getStringExtra("latitude1"));
            longitude1=Double.valueOf(intent.getStringExtra("longitude1"));
            title.setText("添加围栏");
        }
        else if(select.toString().equals("1"))
        {
            title.setText("编辑围栏");
        }
        if(type.toString().equals("circle"))
        {
            circle();
        }
        else if(type.toString().equals("polygon"))
        {
            polygon();
        }
        else if(type.toString().equals("area"))
        {
            area();
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popView = LayoutInflater.from(mContext).inflate(
                        R.layout.save_window, null);
                View rootView = findViewById(R.id.root_main2); // 當前頁面的根佈局
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
                cancel = (TextView) popView.findViewById(R.id.textView5);
                name = (EditText) popView.findViewById(R.id.editText14);
                delete = (ImageView) popView.findViewById(R.id.imageView17);
                save1 = (TextView) popView.findViewById(R.id.textView6);
                in = (LinearLayout) popView.findViewById(R.id.in);
                out = (LinearLayout) popView.findViewById(R.id.out);
                intext = (TextView) popView.findViewById(R.id.textView58);
                outtext = (TextView) popView.findViewById(R.id.textView57);
                inphoto = (ImageView) popView.findViewById(R.id.imageView14);
                outphoto = (ImageView) popView.findViewById(R.id.imageView15);
                inout = (LinearLayout) popView.findViewById(R.id.inout);
                name.setText(adress);
                if (select.toString().equals("0")) {
                    inout.setVisibility(View.GONE);
                } else {
                    if (inorout.toString().equals("true")) {
                        intext.setTextColor(getResources().getColor(R.color.title));
                        inphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                    } else if (inorout.toString().equals("false")) {
                        outtext.setTextColor(getResources().getColor(R.color.title));
                        outphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                    }
                    in.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intext.setTextColor(getResources().getColor(R.color.title));
                            inphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                            outtext.setTextColor(getResources().getColor(R.color.black));
                            outphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fence2));
                            inorout = "true";
                        }
                    });
                    out.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            outtext.setTextColor(getResources().getColor(R.color.title));
                            outphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                            intext.setTextColor(getResources().getColor(R.color.black));
                            inphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fence2));
                            inorout = "false";
                        }
                    });
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        name.setText(null);
                    }
                });
              if(type.toString().equals("area"))
                {
                    Log.e("987456--------", cityname);
                    name.setText(cityname);;
                }
                save1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (name.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "必须输入围栏名称",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (type.toString().equals("circle")) {
                                parameterSets = String.valueOf(longitude1) + "," + String.valueOf(latitude1) + "|" + String.valueOf(radius);
                            } else if (type.toString().equals("polygon")) {
                                parameterSets="";
                                for (int i = 0; i < d - 1; i++) {
                                    parameterSets=parameterSets+String.valueOf(pts.get(i).longitude)+","+String.valueOf(pts.get(i).latitude)+"|";
                                }
                                parameterSets=parameterSets+String.valueOf(pts.get(d - 1).longitude)+","+String.valueOf(pts.get(d - 1).latitude);
                            }
                            else if(type.toString().equals("area"))
                            {
                                parameterSets=cityname;
                            }
                            if (select.toString().equals("0")) {
                                String name1=name.getText().toString();
                                if(type.toString().equals("area"))
                                {
                                    name1=citynumber;
                                }
                                String message = "{" + '"' + "AreaName" + '"' + ":" + '"' + name1 + '"' + ","
                                        + '"' + "UserID" + '"' + ":" + '"' + userid + '"' + ","
                                        + '"' + "AreaType" + '"' + ":" + '"' + type + '"' + "," + '"' + "ParameterSets" + '"'
                                        + ":" + '"' + parameterSets + '"' + "," + '"' + "InOrOut" + '"'
                                        + ":" + inorout + "}";
                                RequestParams params2 = new RequestParams(AddFence.this);
                                params2.addFormDataPart("areaScope", message);
                                params2.addFormDataPart("mapType", "");
                                params2.addFormDataPart("tokenString", token);
                                Log.e("987456--------", message);
                                HttpRequest.post(Api.ADD_AREA_SCOPE, params2, new JsonHttpRequestCallback() {
                                    @Override
                                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                        super.onSuccess(headers, jsonObject);
                                        Log.e("--11111111------->", jsonObject.toString());
                                        BaseApiResponse response = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
                                        if (response.getResult() == 0) {
                                            popupWindow.dismiss();
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "信息有错",
                                                    Toast.LENGTH_SHORT).show();
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
                                        progressDialog.setMessage("正在获取信息");
                                        progressDialog.show();
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                        //hide progressdialog
                                        if (progressDialog.isShowing() && progressDialog != null) {
                                            progressDialog.dismiss();
                                        }

                                    }
                                });
                            } else if (select.toString().equals("1")) {
                                String message1 = "{" + '"' + "AreaName" + '"' + ":" + '"' + name.getText().toString() + '"' + ","
                                        + '"' + "UserID" + '"' + ":" + '"' + userid + '"' + ","
                                        + '"' + "ID" + '"' + ":" + '"' + geoid + '"' + ","
                                        + '"' + "AreaType" + '"' + ":" + '"' + type + '"' + "," + '"' + "ParameterSets" + '"'
                                        + ":" + '"' + parameterSets + '"' + "," + '"' + "InOrOut" + '"'
                                        + ":" + inorout + "}";
                                RequestParams params2 = new RequestParams(AddFence.this);
                                params2.addFormDataPart("areaScope", message1);
                                params2.addFormDataPart("mapType", "");
                                params2.addFormDataPart("tokenString", token);
                                Log.e("9874561--------", message1);
                                HttpRequest.post(Api.MODIFY_AREA_SCOPE, params2, new JsonHttpRequestCallback() {
                                    @Override
                                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                        super.onSuccess(headers, jsonObject);
                                        Log.e("--11111111------->", jsonObject.toString());
                                        BaseApiResponse response = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
                                        if (response.getResult() == 0) {
                                            popupWindow.dismiss();
                                            setResult(RESULT_OK);
                                            finish();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "信息有错",
                                                    Toast.LENGTH_SHORT).show();
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
                                        progressDialog.setMessage("正在获取信息");
                                        progressDialog.show();
                                    }

                                    @Override
                                    public void onFinish() {
                                        super.onFinish();
                                        //hide progressdialog
                                        if (progressDialog.isShowing() && progressDialog != null) {
                                            progressDialog.dismiss();
                                        }

                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
        reduce.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progress = progress - 1;
                if (progress < 0) {
                    progress = 0;
                }
                seekbar.setProgress(progress);
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progress = progress + 1;
                if (progress > maxProgress) {
                    progress = maxProgress;
                }
                seekbar.setProgress(progress);

            }
        });
        seek.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mMapView.requestDisallowInterceptTouchEvent(false);
                } else {
                    mMapView.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                progress = seekBar.getProgress();// 获取停止滑动时的 progress
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                // isSeekChange=true;
                radius = progress * 100;
                radiusText.setText("半径:" + radius + "米");

                if (radius <= 700) {
                    radiusLevel = radius;
                    zoom = 16;
                } else if (radius > 700 && radius <= 1500) {
                    radiusLevel = radius - 700;
                    zoom = 15;
                } else if (radius > 1500 && radius <= 2900) {
                    radiusLevel = radius - 1500;
                    zoom = 14;
                } else if (radius > 2900 && radius <= 5000) {
                    radiusLevel = radius - 2900;
                    zoom = 13;
                } else if (radius > 5000 && radius <= 8000) {
                    radiusLevel = radius - 5000;
                    zoom = 12;
                } else {
                    radiusLevel = radius - 8000;
                    zoom = 11;
                }
                changeMap();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                EditText editGeoCodeKey = (EditText) findViewById(R.id.car_search);
                // Geo搜索
                mSearch.geocode(new GeoCodeOption().city(
                        "").address(editGeoCodeKey.getText().toString()));
            }
        });

    }

    public void onGetGeoCodeResult(GeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(AddFence.this, "抱歉，未能找到结果", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        mBaiduMap.clear();
        mBaiduMap.addOverlay(new MarkerOptions().position(result.getLocation())
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.icon_marka)));
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(result
                .getLocation()));
        String strInfo = String.format("纬度：%f 经度：%f",
                result.getLocation().latitude, result.getLocation().longitude);
//        Toast.makeText(AddFence.this, strInfo, Toast.LENGTH_LONG).show();
        if(type.toString().equals("circle"))
        {
            moveMap();
        }

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {

    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        HiveUtil ut=new HiveUtil();
        //新建围栏
        ut.onResumePage(this,this.getClass().getCanonicalName());
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理


    }
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理

    }
    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

    }
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }


}
