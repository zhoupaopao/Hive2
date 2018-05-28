package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
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
import com.hivee2.R;
import com.hivee2.adapter.BindingAdapter;
import com.hivee2.adapter.GoeFenceAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.FenceDeviceBean;
import com.hivee2.mvp.model.bean.GenfenceBean;
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
 * Created by 狄飞 on 2016/8/7.
 */
public class FenceBinding extends Activity implements HttpCycleContext,OnGetDistricSearchResultListener{
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private SharedPreferences sp=null;// 存放用户信息
    public String userid="";
    private ImageView change;
    private boolean fullscreen=true;
    private LinearLayout message;
    private String message1="";
    private RelativeLayout back;
    private TextView name;
    private TextView edit;
    private ListView listView;
    private Context mContext = null;
    private GoeFenceAdapter adapter;
    private BindingAdapter adapter2;
    private String type1;
    private String adress;
    private TextView bind;
    private TextView cancel;
    private TextView save2;
    private TextView save1;
    private PopupWindow popupWindow;
    private EditText search;
    private ListView listView2;
    private DistrictSearch mDistrictSearch;
    private EditText mCity;
    private EditText mDistrict;
    private final int color = 0xAA00FF00;
    private BaiduMap mBaiduMap;
    private Button mSearchButton;
    private int man=0;
    private String queryString="";
    private EditText eSearch;
    String DiveID="";
    String parameterSets;
    String geoid;
    String token;
    private Boolean inorout1;
    MapView mMapView = null;    // 地图View
    List<GenfenceBean.DataListBean> genfenceList = new ArrayList<GenfenceBean.DataListBean>();
    List<FenceDeviceBean.DataListBean> bingList  = new ArrayList<FenceDeviceBean.DataListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fencebind);
        init();
        initListener();
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");


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
    public void fence()
    {
        RequestParams params = new RequestParams(FenceBinding.this);
        params.addFormDataPart("QueryString","");
        params.addFormDataPart("GeoFenceID", geoid);
        params.addFormDataPart("page", 1);
        params.addFormDataPart("pageSize", 10000);
        params.addFormDataPart("sortName", "");
        params.addFormDataPart("asc","asc");
        params.addFormDataPart("TokenString", token);
        Log.e("TOKEN",token);
        HttpRequest.post(Api.GET_BIND_DEVICE_BYGEO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--11111111------->", jsonObject.toString());
                final GenfenceBean genfenceBean = JSONObject.parseObject(jsonObject.toString(), GenfenceBean.class);
                genfenceList = genfenceBean.getDataList();
                adapter = new GoeFenceAdapter(FenceBinding.this, genfenceList);
                DiveID="";
                for (int i=0;i<genfenceList.size();i++)
                {
                  Log.e("pppp", genfenceList.get(i).getDeviceID() + "aini");
                    DiveID=DiveID+";"+genfenceList.get(i).getDeviceID();
                }
                message1=DiveID;
                Log.e("--11111111------>", DiveID);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                for (int i = 0; i < genfenceBean.getCount(); i++) {
                    LatLng markerll = new LatLng(genfenceBean.getDataList().get(i).getBLat(), genfenceBean.getDataList().get(i).getBLng());
                    BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.car_static1);
                    OverlayOptions oo = new MarkerOptions().icon(bd).position(markerll);// 图标
                    Marker marker = (Marker) (mBaiduMap.addOverlay(oo));
                    LatLng ll = new LatLng(genfenceBean.getDataList().get(i).getBLat(), genfenceBean.getDataList().get(i).getBLng());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        System.out.println("position" + position);
                        // TODAuto-generated method stub
                        genfenceList.get(position).getBLng();
                        genfenceList.get(position).getBLat();
                        LatLng ll = new LatLng(genfenceList.get(position).getBLat(),genfenceList.get(position).getBLng());
                        MapStatus.Builder builder = new MapStatus.Builder();
                        builder.target(ll).zoom(10.0f);
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                       Log.e("ppppp", genfenceList.get(position).getDeviceID()) ;
                        showDialog2("是否解绑该设备", genfenceList.get(position).getDeviceID(), 0);
//                        UNBIND_DEVICE_TOGEOFENCE
                    }
                });

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
    private void init()
    {
        change=(ImageView)findViewById(R.id.imageView6);
        message=(LinearLayout)findViewById(R.id.message);
        back=(RelativeLayout)findViewById(R.id.back);
        name=(TextView)findViewById(R.id.title_name);
        edit=(TextView)findViewById(R.id.title_select);
        listView=(ListView)findViewById(R.id.listView6);
        mMapView = (MapView) findViewById(R.id.bmapView);
        bind=(TextView)findViewById(R.id.textView55);
        mBaiduMap = mMapView.getMap();
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
    void showDialog(final String messageStr, final String useridStr,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                FenceBinding.this);

        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("SOME", geoid + "------" + useridStr);
                RequestParams params = new RequestParams(FenceBinding.this);
                params.addFormDataPart("geoFenceID",geoid);
                params.addFormDataPart("deviceID", useridStr);
                params.addFormDataPart("tokenString", token);
                HttpRequest.post(Api.BIND_DEVICE_TOGEOFENCE, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("--UUU------->", jsonObject.toString());
                        Toast.makeText(getApplicationContext(), "绑定成功",
                                Toast.LENGTH_SHORT).show();
                        fence();
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
        });
        builder.setNegativeButton("否",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    void showDialog2(final String messageStr, final String useridStr,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                FenceBinding.this);

        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.e("SOME", geoid + "------" + useridStr);
                RequestParams params = new RequestParams(FenceBinding.this);
                params.addFormDataPart("geoFenceID",geoid);
                params.addFormDataPart("deviceID", useridStr);
                params.addFormDataPart("tokenString", token);
                HttpRequest.post(Api.UNBIND_DEVICE_TOGEOFENCE, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("--UUU------->", jsonObject.toString());
                        Toast.makeText(getApplicationContext(), "解绑成功",
                                Toast.LENGTH_SHORT).show();
                        fence();
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
        });
        builder.setNegativeButton("否", null);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void  initListener()
    {

        edit.setText("编辑");
        mContext = this;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FenceBinding.this, AddFence.class);
                intent.putExtra("adress",adress);
                intent.putExtra("type",type1);
                intent.putExtra("inorout",inorout1.toString());
                intent.putExtra("select","1");
                intent.putExtra("parameterSets",parameterSets);
                intent.putExtra("geoid",geoid);
                startActivityForResult(intent, 1);
            }
        });
        name.setText("绑定设备");
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullscreen == true) {
                    message.setVisibility(View.GONE);
                    fullscreen = false;
                } else {
                    message.setVisibility(View.VISIBLE);
                    fullscreen = true;
                }
            }
        });
        Intent intent=getIntent();
        geoid=intent.getStringExtra("GEOID");
        token=intent.getStringExtra("token");
        String area=intent.getStringExtra("area");
        type1=area;
        parameterSets=intent.getStringExtra("ParameterSets");
        Log.e("pppp", "-----" + parameterSets);
        Boolean inorout=intent.getBooleanExtra("inorout", true);
        adress=intent.getStringExtra("adress");
        inorout1=inorout;
        Log.e("99999", inorout + "222");
        fence();
        if(area.toString().equals("circle"))
        {    // 添加圆
            Log.e("---777",parameterSets);
            int a=parameterSets.indexOf(",",0);
            int b=parameterSets.indexOf("|", a + 1);
            int c=parameterSets.indexOf("|", b + 1);
            Log.e("-----777",a+"----"+b+"----"+c);
            Log.e("---777", parameterSets.substring(0, a) + "----"+parameterSets.substring(a+1,b)+"----"
                    +parameterSets.substring(b+1,parameterSets.toString().length()));
             double blng=Double.valueOf(parameterSets.substring(0, a));
             double blat=Double.valueOf(parameterSets.substring(a+1,b));
             double r=Double.valueOf(parameterSets.substring(b+1,parameterSets.toString().length()));
              int r1=(int)r;
            Log.e("-----777",a+"----"+b+"----"+r1);
            LatLng llCircle = new LatLng(blat, blng);
            OverlayOptions ooCircle = new CircleOptions().fillColor(0xAA38B0DE)
                    .center(llCircle).stroke(new Stroke(5, 0xAA38B0DE)).radius(r1);
            mBaiduMap.addOverlay(ooCircle);
            LatLng ll = new LatLng(blat, blng);
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(10.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        }
        else if(area.toString().equals("polygon")&&!parameterSets.toString().equals(""))
        {
            int a=0;
            int b=0;
            int c=0;
            int d=0;
            int e=0;
            // 添加多边形
            List<LatLng> pts = new ArrayList<LatLng>();
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
                Log.e("DAYIN","*******"+pt1.longitude);
                pts.add(pt1);
                BitmapDescriptor bdmarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_blue);
                OverlayOptions ooMarker = new MarkerOptions().icon(bdmarker).position(pt1);// 图标
                Marker marker2 = (Marker) (mBaiduMap.addOverlay(ooMarker));
                builder.include(pt1);
            }
            double x = pts.get(1).latitude;
            Log.e("---7777", "----"+x);
            OverlayOptions ooPolygon = new PolygonOptions().points(pts)
                    .stroke(new Stroke(d, 0xAA38B0DE)).fillColor(0xAA38B0DE);
            mBaiduMap.addOverlay(ooPolygon);
            LatLngBounds bounds = builder.build();// 返回:创建出的地理范围对象
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLngBounds(bounds);
            mBaiduMap.animateMapStatus(u);// 以动画方式更新地图状态，动画耗时 300 ms

        }
        else if(area.toString().equals("area"))
        {
            edit.setVisibility(View.GONE);
            Log.e("---987456324",parameterSets);
            RequestParams params = new RequestParams(FenceBinding.this);
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



                       if(parameterSets.equals(String.valueOf(provinceBean.getProvinceList().get(i).getProvinceID())))
                        {
                            Log.e("SHUCHU",provinceBean.getProvinceList().get(i).getProvince()+"--"+
                                provinceBean.getProvinceList().get(i).getProvinceID()+"----"+parameterSets);
                            man=1;
                            mDistrictSearch = DistrictSearch.newInstance();
                            mDistrictSearch.setOnDistrictSearchListener(FenceBinding.this);
                            mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(provinceBean.getProvinceList().get(i).getProvince()).districtName(""));
                        }
                        else {
                           for (int j=0;j<provinceBean.getProvinceList().get(i).getCityList().size();j++)
                           {
                               if(parameterSets.equals(String.valueOf(provinceBean.getProvinceList().get(i).getCityList().get(j).getCityID())))
                               {
                                   man=1;
                                   mDistrictSearch = DistrictSearch.newInstance();
                                   mDistrictSearch.setOnDistrictSearchListener(FenceBinding.this);
                    mDistrictSearch.searchDistrict(new DistrictSearchOption().cityName(provinceBean.getProvinceList().get(i).getCityList().get(j).getCity()));
                               }

                           }
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



        bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popView = LayoutInflater.from(mContext).inflate(
                        R.layout.choose_window, null);
                View rootView = findViewById(R.id.root_main4); // 當前頁面的根佈局
                popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出动画
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// 顯示在根佈局的底部
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);
                cancel = (TextView) popView.findViewById(R.id.delete2);
                save1 = (TextView) popView.findViewById(R.id.save2);
                listView2=(ListView)popView.findViewById(R.id.listView7);
//                search=(EditText)popView.findViewById(R.id.choose);
//                search.setOnClickListener(new View.OnClickListener() {
//                                              @Override
//                                              public void onClick(View v) {
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
                            RequestParams params = new RequestParams(FenceBinding.this);
                            params.addFormDataPart("queryString",queryString);
                            params.addFormDataPart("geoFenceID", geoid);
                            params.addFormDataPart("page", 1);
                            params.addFormDataPart("pageSize", 10000);
                            params.addFormDataPart("tokenString", token);
                            HttpRequest.post(Api.GET_DEVICE_BYGEOFENCE, params, new JsonHttpRequestCallback() {
                                @Override
                                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                    super.onSuccess(headers, jsonObject);
                                    Log.e("--2222------->", jsonObject.toString());
                                    FenceDeviceBean fenceDeviceBean = JSONObject.parseObject(jsonObject.toString(), FenceDeviceBean.class);
                                    bingList = fenceDeviceBean.getDataList();

                                    adapter2 = new BindingAdapter(FenceBinding.this, bingList);
                                    adapter2.notifyDataSetChanged();
                                    listView2.setAdapter(adapter2);
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
                            RequestParams params = new RequestParams(FenceBinding.this);
                            params.addFormDataPart("queryString",queryString);
                            params.addFormDataPart("geoFenceID", geoid);
                            params.addFormDataPart("page", 1);
                            params.addFormDataPart("pageSize", 10000);
                            params.addFormDataPart("tokenString", token);
                            HttpRequest.post(Api.GET_DEVICE_BYGEOFENCE, params, new JsonHttpRequestCallback() {
                                @Override
                                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                    super.onSuccess(headers, jsonObject);
                                    Log.e("--2222------->", jsonObject.toString());
                                    FenceDeviceBean fenceDeviceBean = JSONObject.parseObject(jsonObject.toString(), FenceDeviceBean.class);
                                    bingList = fenceDeviceBean.getDataList();
                                    adapter2 = new BindingAdapter(FenceBinding.this, bingList);
                                    adapter2.notifyDataSetChanged();
                                    listView2.setAdapter(adapter2);
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
                Log.e("IDT", "----" + geoid);
                RequestParams params = new RequestParams(FenceBinding.this);
                params.addFormDataPart("queryString",queryString);
                params.addFormDataPart("geoFenceID", geoid);
                params.addFormDataPart("page", 1);
                params.addFormDataPart("pageSize", 10000);
                params.addFormDataPart("tokenString", token);
                HttpRequest.post(Api.GET_DEVICE_BYGEOFENCE, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("--2222------->", jsonObject.toString());
                        FenceDeviceBean fenceDeviceBean = JSONObject.parseObject(jsonObject.toString(), FenceDeviceBean.class);
                        bingList = fenceDeviceBean.getDataList();
                        adapter2 = new BindingAdapter(FenceBinding.this, bingList);
                        adapter2.notifyDataSetChanged();
                        listView2.setAdapter(adapter2);
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
                listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {

                        System.out.println("position" + position);
                        // TODAuto-generated method stub
                        Log.e("SHUSHU", bingList.get(position).getDeviceID());
//                        showDialog("是否绑定该设备",DiveID+";"+bingList.get(position).getDeviceID(), 0);


                    }
                });
//            }
//        });
//                save1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                    }
//            });
                save1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Log.i("BIND_OGEOFENC1111",bingList.size()+"");
                        for(int i=0;i<bingList.size();i++)
                        {
                            if(adapter2.getmessage().substring(i,i+1).equals("1"))
                            {
                                message1=message1+";"+bingList.get(i).getDeviceID()+";";
                            }
                        }
                        RequestParams params = new RequestParams(FenceBinding.this);
                        params.addFormDataPart("geoFenceID",geoid);
                        params.addFormDataPart("deviceID",message1);
                        params.addFormDataPart("tokenString", token);
                        Log.i("message1",message1);
                        HttpRequest.post(Api.BIND_DEVICE_TOGEOFENCE, params, new JsonHttpRequestCallback() {
                            @Override
                            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                super.onSuccess(headers, jsonObject);
                                Log.e("--UUU------->", jsonObject.toString());
                                Toast.makeText(getApplicationContext(), "绑定成功",
                                        Toast.LENGTH_SHORT).show();
                                fence();
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
                        popupWindow.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

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
        if(man==1)
        {
                    mDistrictSearch.destroy();
        }

        mMapView.onDestroy();
    }
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
}
