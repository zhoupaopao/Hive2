package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
 * Created by 狄飞 on 2016/9/20.
 */
public class AlertMap extends Activity {
    private ImageView back;
    private TextView title;
    private BaiduMap mBaiduMap;
    private double longitude2=120.3;//车经度
    private double latitude2=30.43;//车纬度
    private ImageView carplace;
    MapView mMapView = null;    // 地图View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertmap);
        init();
    }
    private void init()
    {
        back=(ImageView)findViewById(R.id.imageView2);
        title=(TextView)findViewById(R.id.title_name1);
        mMapView = (MapView) findViewById(R.id.bmapView);
        carplace=(ImageView)findViewById(R.id.imageView7);
        final Intent intent = getIntent();
        mBaiduMap = mMapView.getMap();
        title.setText("警报地图");
        final double lat = Double.valueOf(intent.getStringExtra("Lat"));
        final double lng=Double.valueOf(intent.getStringExtra("Lng"));
        LatLng markerll = new LatLng(lat,lng);
        BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.car_static1);
        OverlayOptions oo = new MarkerOptions().icon(bd).position(markerll);// 图标
        Marker marker = (Marker) (mBaiduMap.addOverlay(oo));
        LatLng ll = new LatLng(lat,lng);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(10.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        carplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng ll = new LatLng(lat,lng);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(10.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
