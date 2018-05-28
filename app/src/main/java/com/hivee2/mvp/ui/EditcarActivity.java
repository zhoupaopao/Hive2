package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.CarBrandBean;
import com.hivee2.mvp.model.bean.CarMessage_Bean;
import com.hivee2.mvp.model.bean.CarModelBean;
import com.hivee2.mvp.model.bean.CarSeries;
import com.hivee2.utils.HiveUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/10.
 */
public class EditcarActivity extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private RelativeLayout delete;
    private TextView title;
    private TextView new1;
    private EditText platenumber;
    private EditText frame;
    private TextView version;
    private String CarBrand;
    private int carpp;

    private EditText color1;
    private EditText year1;
    private EditText money1;
    private EditText legend;
    private Spinner genre;//类型
    private Spinner brand;//品牌
    private int[] brandid=new int[1000];
    private int brand_id=0;
    private Spinner model1;//型号
    private int[] seriesid=new int[1000];
    private int series_id=0;
    private int[] modelid=new int[1000];
    private int model_id=0;
    private String chexi="请先选择车系";
    private int nuuu=1;
    private Spinner model2;
    private String type;
    private String brand1;
    private String model;
    private String PledgerID;
    private String CarCategory="1";
    private String[] type2={"小型车","紧凑车","中型车","中型SUV","中大型车","中大型SUV","皮卡","其他"};
    private List<String> allType=new ArrayList<String>();
    private List<String> allBrand=new ArrayList<String>();
    private List<String> allmodel1=new ArrayList<String>();
    private List<String> allmodel2=new ArrayList<String>();
    private  ArrayAdapter typeAdapter;
    private  ArrayAdapter brandAdapter;
    private  ArrayAdapter model1Adapter;
    private  ArrayAdapter  model2Adapter;
    private  RelativeLayout startdata;
    private  RelativeLayout enddata;
    private String startdata2;
    private String enddata2;
    private String CarInfoID;
    private  TextView  startdata1;
    private  TextView  enddata1;
    private int year;
    private int month;
    private int day;
    private int year2;
    private int month2;
    private int day2;
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";
    private String useage="";
    private String mileage="";
    private String carvalue="";
    private String Source="";
    private TextView tv2;
    private TextView tv3;
    private TextView newtime1;
    private TextView newtime2;
//    private String carnumber="";
//    private String PledgerName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit1);
        intview();
        initinfomation();
        initinfomation2();
        initinfomation3();

    }
    private void intview() {

        Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
        Date mydate = new Date(); //获取当前日期Date对象
        mycalendar.setTime(mydate);////为Calendar对象设置时间为当前日期
        year2=year = mycalendar.get(Calendar.YEAR); //获取Calendar对象中的年
        month2=month = mycalendar.get(Calendar.MONTH);//获取Calendar对象中的月
        month2= month=month+1;
        day2= day = mycalendar.get(Calendar.DAY_OF_MONTH);//获取这个月的第几天
        Log.e("DANGTIAN", "year" + year + "-" + month + "-" + day);
        startdata2=year+"-"+month+"-"+day;
        enddata2=year2+"-"+month2+"-"+day2;
        Intent intent=getIntent();
        PledgerID=intent.getStringExtra("pledgerID");
        CarInfoID=intent.getStringExtra("PledgeCarID");
        Source=intent.getStringExtra("Source");
//        PledgerName=intent.getStringExtra("PledgerName");

        delete = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title_name);
        new1 = (TextView) findViewById(R.id.title_select);
        platenumber = (EditText) findViewById(R.id.editText);
        frame = (EditText) findViewById(R.id.editText1);
        version = (TextView) findViewById(R.id.label4);
        color1 = (EditText) findViewById(R.id.editText11);
        genre=(Spinner)findViewById(R.id.spinner2);
        brand=(Spinner)findViewById(R.id.spinner3);
        model1=(Spinner)findViewById(R.id.spinner6);
        model2=(Spinner)findViewById(R.id.spinner5);
        year1 = (EditText) findViewById(R.id.editText9);
        money1 = (EditText) findViewById(R.id.editText8);
        legend = (EditText) findViewById(R.id.editText17);
        tv2= (TextView) findViewById(R.id.tv_spinner2);
        tv3= (TextView) findViewById(R.id.tv_spinner3);
//        newtime1= (TextView) findViewById(R.id.new_textView21);
//        newtime2= (TextView) findViewById(R.id.new_textView22);
         startdata=(RelativeLayout)findViewById(R.id.st_clear_Layout);
         enddata=(RelativeLayout)findViewById(R.id.st_clear_Layout1);
         startdata1=(TextView)findViewById(R.id.textView21);
         enddata1=(TextView)findViewById(R.id.textView22);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");
        RequestParams params = new RequestParams(EditcarActivity.this);
        params.addFormDataPart("carID",CarInfoID);
        params.addFormDataPart("tokenString", token);
        Log.e("--AAAAA------->", CarInfoID + "----" + token);

        HttpRequest.post(Api.GET_CAR_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--AAAAA------->", jsonObject.toString());
                CarMessage_Bean carMessage = JSONObject.parseObject(jsonObject.toString(), CarMessage_Bean.class);
                platenumber.setText(carMessage.getDataList().get(0).getCarInfo().getCarNumber());
                frame.setText(carMessage.getDataList().get(0).getCarInfo().getVIN());
                color1.setText(carMessage.getDataList().get(0).getCarInfo().getColor());
                year1.setText(carMessage.getDataList().get(0).getCarInfo().getUsedAge());
                money1.setText("" + carMessage.getDataList().get(0).getCarInfo().getCarValue());
                legend.setText("" + carMessage.getDataList().get(0).getCarInfo().getMileage());
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                carMessage.getDataList().get(0).getPledgeTime();
                int a = carMessage.getDataList().get(0).getPledgeTime().indexOf("(", 0);
                int b = carMessage.getDataList().get(0).getPledgeTime().indexOf(")", 0);
                startdata2 = format.format(Long.valueOf(carMessage.getDataList().get(0).getPledgeTime().substring(a + 1, b)));
                year = Integer.valueOf(startdata2.substring(0, 4));
                month = Integer.valueOf(startdata2.substring(5, 7));
                day = Integer.valueOf(startdata2.substring(8, 10));
                Log.e("--AAAAA------->", year + "-" + month + "-" + day);
                startdata1.setText(year + "-" + month + "-" + day);
                month = month - 1;
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                carMessage.getDataList().get(0).getRepayTime();
                int a1 = carMessage.getDataList().get(0).getRepayTime().indexOf("(", 0);
                int b1 = carMessage.getDataList().get(0).getRepayTime().indexOf(")", 0);
                enddata2 = format1.format(Long.valueOf(carMessage.getDataList().get(0).getRepayTime().substring(a1 + 1, b1)));
                year2 = Integer.valueOf(enddata2.substring(0, 4));
                month2 = Integer.valueOf(enddata2.substring(5, 7));
                day2 = Integer.valueOf(enddata2.substring(8, 10));
                Log.e("--AAAAA------->", year2 + "-" + month2 + "-" + day2);
                enddata1.setText(year2 + "-" + month2 + "-" + day2);
                month2 = month2 - 1;
                version.setText(carMessage.getDataList().get(0).getCarInfo().getCarVersion());
                CarCategory = carMessage.getDataList().get(0).getCarInfo().getCarCategory();
                CarBrand=carMessage.getDataList().get(0).getCarInfo().getCarBrand();
                chexi=carMessage.getDataList().get(0).getCarInfo().getCarVersion();
//                genre.setSelection(2);
//                Log.i("CarCategory", carMessage.getDataList().get(0).getCarInfo().getCarVersion());
                if(carMessage.getDataList().get(0).getCarInfo().getCarVersion()==null){
                    Log.i("getCarVersion", "null: ");
                }else{
                    Log.i("getCarVersion", "have: ");
                }
                if(CarCategory.equals("1"))
                {

                    genre.setSelection(0);
                }
                else if(CarCategory.equals("2"))
                {
                    genre.setSelection(1);
                }
                else if(CarCategory.equals("3"))
                {
                    genre.setSelection(2);
                }
                else if(CarCategory.equals("4"))
                {
                    genre.setSelection(3);
                }
                else if(CarCategory.equals("5"))
                {
                    genre.setSelection(4);
                }
                else if(CarCategory.equals("6"))
                {
                    genre.setSelection(5);
                }
                else if(CarCategory.equals("7"))
                {
                    genre.setSelection(6);
                }
                else if(CarCategory.equals("8"))
                {
                    genre.setSelection(7);
                }
                if(Source.equals("2")){
                    Toast.makeText(EditcarActivity.this,"该信息来自于工单不可编辑",Toast.LENGTH_SHORT).show();
                    platenumber.setFocusable(false);
                    platenumber.setFocusableInTouchMode(false);
                    frame.setFocusable(false);
                    frame.setFocusableInTouchMode(false);
                    color1.setFocusable(false);
                    color1.setFocusableInTouchMode(false);
                    year1.setFocusable(false);
                    year1.setFocusableInTouchMode(false);
                    money1.setFocusable(false);
                    money1.setFocusableInTouchMode(false);
                    legend.setFocusable(false);
                    legend.setFocusableInTouchMode(false);
//                    tv2.setVisibility(View.VISIBLE);
//                    tv3.setVisibility(View.VISIBLE);
//                    genre.setVisibility(View.GONE);
//                    brand.setVisibility(View.GONE);
//                    model1.setVisibility(View.GONE);
//                    model2.setVisibility(View.GONE);
                    genre.setClickable(false);
                    brand.setClickable(false);
                    model1.setClickable(false);
                    model2.setClickable(false);
//                    startdata1.setVisibility(View.GONE);
//                    enddata1.setVisibility(View.GONE);
//                    newtime2.setVisibility(View.VISIBLE);
//                    newtime1.setVisibility(View.VISIBLE);
                    startdata.setClickable(false);
                    enddata.setClickable(false);
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
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditcarActivity.this.finish();
            }
        });
        title.setText("编辑车辆");
        new1.setText("保存");
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ceshi1","iii");
//                                setResult(RESULT_OK);
//                finish();
                if(frame.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "车架号是必填项，请输入17位正确VIN码!!", Toast.LENGTH_SHORT).show();
                }else if(frame.getText().length()!=17){
                    Toast.makeText(getApplicationContext(), "请输入17位正确VIN码!", Toast.LENGTH_SHORT).show();
                }
                else {
                    information();
                }
//                if(frame.getText().toString().equals("")){
//                    Toast.makeText(getApplicationContext(), "车架号是必填项，请输入17位正确VIN码!", Toast.LENGTH_SHORT).show();
//                }else if(frame.getText().length()!=17)
//                {
//                    Toast.makeText(getApplicationContext(), "请输入17位正确VIN码!", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    carnumber=platenumber.getText().toString();
////                    information();
//                    //新增判断
//                    //如果车牌号是空的话，那么车架号直接赋值车架号
//                    if(platenumber.getText().toString().equals("")){{
//                        carnumber=frame.getText().toString();
//                    }}
//                    String message= "{"
//                            + '"' + "userid" + '"' + ":" + '"' + userid + '"' + ","
//                            + '"' + "pledgername" + '"' + ":" + '"' + PledgerName + '"' + ","
////                            + '"' + "carnumber" + '"' + ":" + '"' +platenumber.getText().toString()+ '"' + ","
//                            + '"' + "carnumber" + '"' + ":" + '"' +carnumber+ '"' + ","
//                            + '"' + "vin" + '"' + ":" + '"' +frame.getText().toString()+ '"' + ","
//                            + '"' + "tokenString" + '"' + ":" + '"'+token+ '"'
//                            + "}";
//                    RequestParams params = new RequestParams(EditcarActivity.this);
////                    params.addFormDataPart("userid", userid);
////                    params.addFormDataPart("pledgername", PledgerName);
////                    params.addFormDataPart("idcardnumber", PledgerID);
////                    params.addFormDataPart("carnumber", platenumber.getText().toString());
////                    params.addFormDataPart("vin", frame.getText().toString());
////                    params.addFormDataPart("tokenString", token);
//                    params.addFormDataPart("param", message);
//                    Log.i("新怎",message);
//                    Log.i("新怎",Api.Check_LoanValid+"?userid="+userid+"&pledgername="+PledgerName+"&carnumber="+PledgerID+"&tokenString="+token);
//
//                    HttpRequest.post(Api.Check_LoanValid, params, new JsonHttpRequestCallback() {
//                        @Override
//                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
//                            super.onSuccess(headers, jsonObject);
////                            CarSeries carSeries = JSONObject.parseObject(jsonObject.toString(), CarSeries.class);
//                            Log.i("新怎",jsonObject.toString());
//                            int result=jsonObject.getInteger("Result");
//                            if(result==0){
//                                information();
//                                Log.i("新怎","0");
//                            }else if(result==4){
//                                Log.i("新怎","4");
//                                AlertDialog alert=new AlertDialog.Builder(EditcarActivity.this).create();
//                                alert.setTitle("警告");
//                                alert.setMessage(jsonObject.getString("ErrorMsg"));
//                                //添加取消按钮
//                                alert.setButton(DialogInterface.BUTTON_NEGATIVE,"继续添加",new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        // TODO Auto-generated method stub
//                                        information();
//                                    }
//                                });
//                                //添加"确定"按钮
//                                alert.setButton(DialogInterface.BUTTON_POSITIVE,"取消", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface arg0, int arg1) {
////                                                finish();
//                                    }
//                                });
//                                alert.show();
//                            }else{
//                                Toast.makeText(EditcarActivity.this,jsonObject.getString("ErrorMsg"),Toast.LENGTH_LONG).show();
//                            }
//
//                        }
//                        @Override
//                        public void onFailure(int errorCode, String msg) {
//                            super.onFailure(errorCode, msg);
//                            Log.e("alertMsg failure", errorCode + msg);
//                        }
//                        @Override
//                        public void onStart() {
//                            super.onStart();
//                            //show  progressdialog
//                        }
//                        @Override
//                        public void onFinish() {
//                            super.onFinish();
//                            //hide progressdialog
//                        }
//                    });
//                }

            }
        });

        for(int i=0;i<type2.length;i++){
            allType.add(type2[i]);
        }

        typeAdapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allType);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genre.setAdapter(typeAdapter);

        brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                brand1 = (String) brand.getAdapter().getItem((int) id);
                brand_id=position;
                Log.e("OOO", brand1+position);
                setTitle(brand + brand1);
                initinfomation2();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        //单击第一个下拉按钮时，显示选择的值。
        genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                type = (String) genre.getAdapter().getItem((int) id);
                if(type.equals("小型车"))
                {
                    CarCategory="1";
                }
                else if(type.equals("紧凑车"))
                {
                    CarCategory="2";
                }
                else if(type.equals("中型车"))
                {
                    CarCategory="3";
                }
                else if(type.equals("中型SUV"))
                {
                    CarCategory="4";
                }
                else if(type.equals("中大型车"))
                {
                    CarCategory="5";
                }
                else if(type.equals("中大型SUV"))
                {
                    CarCategory="6";
                }
                else if(type.equals("皮卡"))
                {
                    CarCategory="7";
                }
                else if(type.equals("其他"))
                {
                    CarCategory="8";
                }
                Log.e("OOO",type);
                setTitle(genre + type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        model1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub
                model  = (String)model1.getAdapter().getItem((int) id);
                series_id=position;
                Log.e("OOO", model+position);
                setTitle(model1 + model);
                initinfomation3();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
//        单击第二个下拉按钮时，显示选择的值。
        model2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapter, View view,
                                       int position, long id) {
                String str = (String) model2.getAdapter().getItem(position);
//                setTitle(result + str);
//                Toast.makeText(EditcarActivity.this,nuuu+"",Toast.LENGTH_LONG).show();
                if(str=="请先选择车系"&&nuuu==4){

                    version.setText(chexi);
                }else{
                    version.setText(str);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        startdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditcarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startdata1.setText(String.format("%d-%d-%d", year, monthOfYear + 1, dayOfMonth));
                        startdata2=startdata1.getText().toString();
                    }
                }, year, month, day).show();

            }

        });
       enddata.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new DatePickerDialog(EditcarActivity.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                       enddata1.setText(String.format("%d-%d-%d", year, monthOfYear+1, dayOfMonth));
                       enddata2=enddata1.getText().toString();
                       Log.e("RIQI",enddata2);

                   }
               },year2, month2, day2).show();

           }

       });
    }
    public void initinfomation2()
    {

        allmodel1.clear();
        if(brand_id==0)
        {
            allmodel1.add("请先选择品牌");
            model1Adapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allmodel1);
            model1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            model1.setAdapter(model1Adapter);

        }
        else {
            RequestParams params = new RequestParams(EditcarActivity.this);
            params.addFormDataPart("brandId", brandid[brand_id]);
            params.addFormDataPart("token", token);
            Log.e("leixing",Api.GET_CAR_SERIES+"||"+ brandid[brand_id]+"||"+token);
            HttpRequest.post(Api.GET_CAR_SERIES, params, new JsonHttpRequestCallback() {
                @Override
                protected void onSuccess(Headers headers, JSONObject jsonObject) {
               super.onSuccess(headers, jsonObject);
                    CarSeries carSeries = JSONObject.parseObject(jsonObject.toString(), CarSeries.class);
//                    Log.e("leixing", "d-----f" + carSeries.getSeries_list().size());
                    allmodel1.add("未选车系");
                    if(carSeries.getSeries_list()==null){

                    }else{
                        for(int i=1;i<=carSeries.getSeries_list().size();i++){
                            allmodel1.add(carSeries.getSeries_list().get(i-1).getSeries_group_name()+" "+carSeries.getSeries_list().get(i-1).getSeries_name());
                            seriesid[i]=carSeries.getSeries_list().get(i-1).getSeries_id();

                        }
                        model1Adapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allmodel1);
                        model1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        model1.setAdapter(model1Adapter);
                    }
                    //新注视，主要在进入界面会崩
//                    for(int i=1;i<=carSeries.getSeries_list().size();i++){
//                        allmodel1.add(carSeries.getSeries_list().get(i-1).getSeries_group_name()+" "+carSeries.getSeries_list().get(i-1).getSeries_name());
//                        seriesid[i]=carSeries.getSeries_list().get(i-1).getSeries_id();
//
//                    }
//                    model1Adapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allmodel1);
//                    model1Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    model1.setAdapter(model1Adapter);

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
    public void initinfomation3()
    {

        allmodel2.clear();
        if(series_id==0)
        {
            allmodel2.add("请先选择车系");
            model2Adapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allmodel2);
            model2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            model2.setAdapter(model2Adapter);
            nuuu++;
        }
        else {
            RequestParams params = new RequestParams(EditcarActivity.this);
            params.addFormDataPart("seriesId", seriesid[series_id]);
            params.addFormDataPart("token", token);
            HttpRequest.post(Api.GET_CAR_MODEL, params, new JsonHttpRequestCallback() {
                @Override
                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                    super.onSuccess(headers, jsonObject);
                    CarModelBean carModelBean= JSONObject.parseObject(jsonObject.toString(), CarModelBean.class);

                    Log.e("leixing", "d-----f" + carModelBean.getModel_list().size());
                    allmodel2.add("未选车型");
                    for(int i=1;i<=carModelBean.getModel_list().size();i++){
                        allmodel2.add(carModelBean.getModel_list().get(i-1).getModel_name()+carModelBean.getModel_list().get(i-1).getModel_price()+"万");
                    }
                    model2Adapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allmodel2);
                    model2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    model2.setAdapter(model2Adapter);

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
    public void initinfomation()
    {
        RequestParams params = new RequestParams(EditcarActivity.this);
        params.addFormDataPart("token", token);
        HttpRequest.post(Api.GET_CAR_BRANDLIST, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                CarBrandBean carBrandBean = JSONObject.parseObject(jsonObject.toString(), CarBrandBean.class);
                Log.e("leixing", "d-----f" + carBrandBean.getBrand_list().size());
                allBrand.add("未选车品牌");
                for(int i=1;i<=carBrandBean.getBrand_list().size();i++){
                    allBrand.add(carBrandBean.getBrand_list().get(i-1).getBrand_name());
                    brandid[i]=carBrandBean.getBrand_list().get(i-1).getBrand_id();
                    Log.e("leixing", CarBrand +"||" +carBrandBean.getBrand_list().get(i-1).getBrand_name()+"||"+brandid[i]);
                    if(carBrandBean.getBrand_list().get(i-1).getBrand_name().equals(CarBrand)){
                        carpp=i;
                        Log.d("carpp", carpp+"");
                    }
                }
                brandAdapter=new ArrayAdapter<String>(EditcarActivity.this,android.R.layout.simple_spinner_item,allBrand);
                brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                brand.setAdapter(brandAdapter);
                Log.d("carpp", carpp+"");
                //新增直接选中
                brand.setSelection(carpp);

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
    public void  information()
    {
//        {"ID":"","UserID":"c0a0cfb7b2504c649fcc116a77b54c09","PledgerID":"0d0500157cb74474ad0df00f2c2c7ee9",
//                "CarNumber":"aaa","VIN":"","UsedAge":"","Color":"","CarCategory":"2","CarBrand":"","CarVersion":"",
//                "Mileage":"12","Remark":"",
//                "CarValue":"11","PledgeTime":"2016-8-19","RepayTime":"2016-8-19","CarImgURL":"","Erector":"","ErectPlace":""}
        if(year1.getText().toString().equals(""))
        {
            useage="0";
        }
        else {
            useage=year1.getText().toString();
        }
if(legend.getText().toString().equals(""))
{
    mileage="0";
}
        else {
    mileage=legend.getText().toString();
}
if(money1.getText().toString().equals(""))
{
    carvalue="0";
}
        else {
    carvalue=money1.getText().toString();
}

        Log.e("riq",enddata2);
        String message= "{"
                + '"' + "ID" + '"' + ":" + '"' +CarInfoID+ '"' + ","
                + '"' + "PledgerID" + '"' + ":" + '"' + PledgerID + '"' + ","
                + '"' + "CarNumber" + '"' + ":" + '"'  +platenumber.getText().toString()+ '"' + ","
                + '"' + "VIN" + '"' + ":" + '"' +frame.getText().toString()+ '"' + ","
                + '"' + "UsedAge" + '"' + ":" + '"' + useage + '"' + ","
                + '"' + "Color" + '"' + ":" + '"' + color1.getText().toString() + '"' + ","
                + '"' + "CarCategory" + '"' + ":" + '"' + CarCategory + '"' + ","
                + '"' + "CarBrand" + '"'+ ":" + '"' + brand1+ '"' + ","
                + '"' + "CarVersion" + '"' + ":" + '"' + version.getText().toString() + '"' + ","
                + '"' + "Mileage" + '"' + ":" + '"' + mileage + '"' + ","
                + '"' + "Remark" + '"' + ":" + '"'+ '"' + ","
                + '"' + "CarValue" + '"' + ":" + '"' +carvalue+ '"' + ","
                + '"' + "PledgeTime" + '"' + ":" + '"' + startdata2 + '"' + ","
                + '"' + "RepayTime" + '"' + ":" + '"' + enddata2 + '"' + ","
                + '"' + "CarImgURL" + '"'+ ":" + '"'+ '"' + ","
                + '"' + "Erector" + '"' + ":" + '"'+ '"' + ","
                + '"' + "ErectPlace" + '"' + ":" + '"'+ '"'
                + "}";
        RequestParams params = new RequestParams(EditcarActivity.this);
        Log.e("DUIBI", message);
        params.addFormDataPart("userID",userid);
        params.addFormDataPart("carInfo",message);
        params.addFormDataPart("tokenString", token);

        HttpRequest.post(Api.MODIFY_CARINFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
//                AddCar addCar = JSONObject.parseObject(jsonObject.toString(), AddCar.class);

                Log.e("CHENGGONG", "CHEGNGONG");
                    finish();

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
//              setResult(RESULT_OK);
//                finish();
            }
        });
    }
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
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
