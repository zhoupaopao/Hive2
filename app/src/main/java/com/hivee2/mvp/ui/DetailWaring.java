package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.hivee2.R;
import com.hivee2.utils.HiveUtil;

/**
 * Created by 狄飞 on 2016/7/16.
 */
public class DetailWaring extends Activity {
    private BaiduMap mBaiduMap;
    private ImageView findcar;
    private ImageView change;
    private ImageView back;
    private LinearLayout message;
    private FrameLayout layout;
    private TextView title;
    private TextView Address;
    private TextView AlarmType;
    private TextView CarNumber;
    private TextView PledgerName;
    private TextView DeviceID;
    private TextView PositionTime;
    private boolean fullscreen=true;
    private double longitude2=120.3;//车经度
    private double latitude2=30.43;//车纬度
    MapView mMapView = null;    // 地图View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warning_detail);
        init();
        initListener();

    }
    private void init()
    {
        title=(TextView)findViewById(R.id.title_name1);
        findcar=(ImageView)findViewById(R.id.imageView7);
        back=(ImageView)findViewById(R.id.imageView2);
        change=(ImageView)findViewById(R.id.imageView6);
        layout=(FrameLayout)findViewById(R.id.title);
        Address=(TextView)findViewById(R.id.textView27);
        AlarmType=(TextView)findViewById(R.id.editText2);
        CarNumber=(TextView)findViewById(R.id.editText1);
        PledgerName=(TextView)findViewById(R.id.editText3);
        DeviceID=(TextView)findViewById(R.id.editText4);
        PositionTime=(TextView)findViewById(R.id.editText5);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        title.setText("预警详情");
        Intent intent=getIntent();
        String String1=intent.getStringExtra("alert1");
        String String2=intent.getStringExtra("alert2");
        String String3=intent.getStringExtra("alert3");
        String String4=intent.getStringExtra("alert4");
        String String5=intent.getStringExtra("alert5");
        String String6=intent.getStringExtra("alert6");
        longitude2=intent.getDoubleExtra("alert8",0);
        latitude2=intent.getDoubleExtra("alert7",0);
        Log.e("111111----->",""+latitude2);
        Address.setText(String1);
        AlarmType.setText(String2);
        CarNumber.setText(String3);
        PledgerName.setText(String4);
        DeviceID.setText(String5);
        PositionTime.setText(String6);
        message=(LinearLayout)findViewById(R.id.message);
        LatLng markerll = new LatLng(latitude2, longitude2);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.car_static1);
        OverlayOptions oo = new MarkerOptions().icon(bd).position(markerll);// 图标
        Marker marker = (Marker) (mBaiduMap.addOverlay(oo));
        LatLng ll = new LatLng(latitude2, longitude2);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

    }
   private void  initListener()
   {
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
       findcar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

                   LatLng ll = new LatLng(latitude2, longitude2);
                   MapStatus.Builder builder = new MapStatus.Builder();
               builder.target(ll).zoom(18.0f);
               mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
           }
       });
       change.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
           if(fullscreen==true) {
               message.setVisibility(View.GONE);
               layout.setVisibility(View.GONE);
               fullscreen=false;
           }
               else {
               message.setVisibility(View.VISIBLE);
               layout.setVisibility(View.VISIBLE);
               fullscreen=true;
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
}
