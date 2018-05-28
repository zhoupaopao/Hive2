package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.GetDeviceAdapter;
import com.hivee2.adapter.WirlessAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.DeviceBean2;
import com.hivee2.mvp.model.bean.GetDeviceBean;
import com.hivee2.mvp.model.bean.SetDeviceBean;
import com.hivee2.utils.HiveUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/10/23.
 */
public class WirelessActivity extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private ListView listView;
    //    private ListView listView2;
    private TextView ednl1;
    private Spinner sp_jgsc;
    final int TIME_DIALOG = 1;
    final int TIME_DIALOG1 = 2;
    final int TIME_DIALOG2 = 3;
    final int TIME_DIALOG3 = 4;
    final int XQTIME_DIALOG = 5;

    private ArrayAdapter<String> arr_adapter;
    private List<String> allType = new ArrayList<String>();
    private TextView ednl2;
    private TextView ednl3;
    private TextView ednl4;
    private CheckBox jgsbms;
    private EditText eSearch;
    private PopupWindow popupWindow;
    private TextView cancel;
    private TextView save1;
    private CheckBox nzms;
    private CheckBox xqms;
    private CheckBox xqyi;
    private CheckBox xqer;
    private CheckBox xqsan;
    private CheckBox xqsi;
    private CheckBox xqwu;
    private CheckBox xqliu;
    private CheckBox xqri;
    private EditText timexq;
    private LinearLayout llxq;
    private LinearLayout llnz;
    private int[] shuzu;
    List weeklist = new ArrayList<Integer>();
    private static final String SEP1 = "#";
    private static final String SEP2 = ",";
    private static final String SEP3 = "=";
    private String deviceid = "";
    private Context mContext = null;
    private String queryString = "";
    private String clock = "";
    ArrayList<String> list1;
    ArrayList<EditText> ETlist;
    private String userid = "";
    private String token = "";
    private TextView title;
    private ImageView back;
    private int size;
    ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    ArrayList<HashMap<String, Object>> list2 = new ArrayList<HashMap<String, Object>>();
    private WirlessAdapter adapter;
    private GetDeviceAdapter adapter1;
    private SharedPreferences sp = null;// 存放用户信息
    private ProgressDialog progressDialog;
    public String[] arr = new String[100];
    List<DeviceBean2.DataListBean> deviceList = new ArrayList<DeviceBean2.DataListBean>();
    List<GetDeviceBean.DataListBean> getdeviceList = new ArrayList<GetDeviceBean.DataListBean>();

    private int hour;
    private int minute;
    private String time1;
    private String time2;
    private String dateStr;
    private AlertDialog dialog;
    private CheckBox cb1;
    private CheckBox cb2;
    private CheckBox cb3;
    private CheckBox cb4;
    private String brand1;
    private int istrack=0;
    int mHour,mMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wirless);
        init();
        listen();
        information();
        set_eSearch_TextChanged();
    }

    private boolean check(String a) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        try {
            df.parse(a);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void init() {
        mContext = this;
        listView = (ListView) findViewById(R.id.listView4);

        back = (ImageView) findViewById(R.id.imageView2);
        title = (TextView) findViewById(R.id.title_name1);
        title.setText("闹铃设置");
        sp = this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid = sp.getString(Constant.sp_userId, "");
        token = sp.getString(Constant.sp_token, "");
        progressDialog = new ProgressDialog(this);
        final Calendar ca = Calendar.getInstance();
        mHour = ca.get(Calendar.HOUR_OF_DAY);
        mMinutes=ca.get(Calendar.MINUTE);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {

            case TIME_DIALOG:
                return new TimePickerDialog(this,mtimeListener,mHour,mMinutes,true);
            case TIME_DIALOG1:
                return new TimePickerDialog(this,mtimeListener1,mHour,mMinutes,true);
            case TIME_DIALOG2:
                return new TimePickerDialog(this,mtimeListener2,mHour,mMinutes,true);
            case TIME_DIALOG3:
                return new TimePickerDialog(this,mtimeListener3,mHour,mMinutes,true);
            case XQTIME_DIALOG:
                return new TimePickerDialog(this,mtimeListener4,mHour,mMinutes,true);

        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */

    public void display1() {
        if (mHour < 10) {
            time1 = "0" + mHour;
        } else {
            time1 = mHour + "";
        }
        if (mMinutes < 10) {
            time2 = "0" + mMinutes;
        } else {
            time2 = mMinutes + "";
        }
        ednl1.setText(new StringBuffer().append(time1).append(":").append(time2).append(" "));
//        ednl4.setText(dateStr);
        cb1.setChecked(true);
    }
    public void display2() {
        if (mHour < 10) {
            time1 = "0" + mHour;
        } else {
            time1 = mHour + "";
        }
        if (mMinutes < 10) {
            time2 = "0" + mMinutes;
        } else {
            time2 = mMinutes + "";
        }
        ednl2.setText(new StringBuffer().append(time1).append(":").append(time2).append(" "));
//        ednl4.setText(dateStr);
        cb2.setChecked(true);
    }
    public void display3() {
        if (mHour < 10) {
            time1 = "0" + mHour;
        } else {
            time1 = mHour + "";
        }
        if (mMinutes < 10) {
            time2 = "0" + mMinutes;
        } else {
            time2 = mMinutes + "";
        }
        ednl3.setText(new StringBuffer().append(time1).append(":").append(time2).append(" "));
//        ednl4.setText(dateStr);
        cb3.setChecked(true);
    }
    public void display4() {
        if (mHour < 10) {
            time1 = "0" + mHour;
        } else {
            time1 = mHour + "";
        }
        if (mMinutes < 10) {
            time2 = "0" + mMinutes;
        } else {
            time2 = mMinutes + "";
        }
        ednl4.setText(new StringBuffer().append(time1).append(":").append(time2).append(" "));
        cb4.setChecked(true);
    }
    public void display5() {
        if (mHour < 10) {
            time1 = "0" + mHour;
        } else {
            time1 = mHour + "";
        }
        if (mMinutes < 10) {
            time2 = "0" + mMinutes;
        } else {
            time2 = mMinutes + "";
        }
        timexq.setText(new StringBuffer().append(time1).append(":").append(time2).append(" "));
    }
    private TimePickerDialog.OnTimeSetListener mtimeListener=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinutes = minute;

            display1();
        }
    };
    private TimePickerDialog.OnTimeSetListener mtimeListener1=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinutes = minute;

            display2();
        }
    };
    private TimePickerDialog.OnTimeSetListener mtimeListener2=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinutes = minute;

            display3();
        }
    };
    private TimePickerDialog.OnTimeSetListener mtimeListener3=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinutes = minute;

            display4();
        }
    };
    private TimePickerDialog.OnTimeSetListener mtimeListener4=new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            mHour = hourOfDay;
            mMinutes = minute;

            display5();
        }
    };

    public void ednlbt(View v) {

        if (v.getId() == R.id.ednl4) {

            showDialog(TIME_DIALOG3);

        } else if (v.getId() == R.id.ednl1) {
            showDialog(TIME_DIALOG);
//            dialog = new AlertDialog.Builder(WirelessActivity.this).create();
//            dialog.show();
//            dialog.getWindow().setContentView(R.layout.time_dialog);
//
//            Calendar c = Calendar.getInstance();
//            //当前时间
//            hour = c.get(Calendar.HOUR_OF_DAY);
//            minute = c.get(Calendar.MINUTE);
////                                hour=Integer.valueOf(ednl1.getText().toString().substring(0,2));
////                                minute=Integer.valueOf(ednl1.getText().toString().substring(3,5));
//            Log.i("hourhour", hour + ":" + minute);
////                                String fir_hour="";
////                                String fir_min="";
//
//            if (hour < 10) {
//                time1 = "0" + hour;
//            } else {
//                time1 = hour + "";
//            }
//            if (minute < 10) {
//                time2 = "0" + minute;
//            } else {
//                time2 = minute + "";
//            }
//            dateStr = time1 + ":" + time2;
//
//            Log.i("time------------->", "" + dateStr);
////                                ednl1.setText(fir_hour+":"+fir_min);
//            ((TimePicker) dialog.getWindow().findViewById(R.id.time_picker)).setIs24HourView(true);
//            ((TimePicker) dialog.getWindow().findViewById(R.id.time_picker)).setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                    Log.i("hourhour", hourOfDay + ":" + minute);
//                    WirelessActivity.this.hour = hourOfDay;
//                    WirelessActivity.this.minute = minute;
//
//                    time1 = Integer.toString(WirelessActivity.this.hour);
//                    time2 = Integer.toString(WirelessActivity.this.minute);
//                    if (Integer.valueOf(time1) < 10) {
//                        time1 = "0" + time1;
//                    }
//                    if (Integer.valueOf(time2) < 10) {
//                        time2 = "0" + time2;
//                    }
//                    dateStr = time1 + ":" + time2;
//
//                    Log.i("time-------------->", "" + dateStr);
//                }
//            });
//            dialog.getWindow().findViewById(R.id.time_sure).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
//                    try {
//                        df.parse(time1 + ":" + time2);
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    ednl1.setText(dateStr);
//                    cb1.setChecked(true);
//                    dialog.dismiss();
//                }
//            });

        } else if (v.getId() == R.id.ednl2) {
            showDialog(TIME_DIALOG1);
        } else if (v.getId() == R.id.ednl3) {
            showDialog(TIME_DIALOG2);
        }else if (v.getId() == R.id.edxq) {
            showDialog(XQTIME_DIALOG);
//            dialog = new AlertDialog.Builder(WirelessActivity.this).create();
//            dialog.show();
//            dialog.getWindow().setContentView(R.layout.time_dialog);
//
//            Calendar c = Calendar.getInstance();
//            //当前时间
//            hour = c.get(Calendar.HOUR_OF_DAY);
//            minute = c.get(Calendar.MINUTE);
////                                hour=Integer.valueOf(ednl1.getText().toString().substring(0,2));
////                                minute=Integer.valueOf(ednl1.getText().toString().substring(3,5));
//            Log.i("hourhour", hour + ":" + minute);
////                                String fir_hour="";
////                                String fir_min="";
//
//            if (hour < 10) {
//                time1 = "0" + hour;
//            } else {
//                time1 = hour + "";
//            }
//            if (minute < 10) {
//                time2 = "0" + minute;
//            } else {
//                time2 = minute + "";
//            }
//            dateStr = time1 + ":" + time2;
//
//            Log.i("time------------->", "" + dateStr);
////                                ednl1.setText(fir_hour+":"+fir_min);
//            ((TimePicker) dialog.getWindow().findViewById(R.id.time_picker)).setIs24HourView(true);
//            ((TimePicker) dialog.getWindow().findViewById(R.id.time_picker)).setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                    Log.i("hourhour", hourOfDay + ":" + minute);
//                    WirelessActivity.this.hour = hourOfDay;
//                    WirelessActivity.this.minute = minute;
//
//                    time1 = Integer.toString(WirelessActivity.this.hour);
//                    time2 = Integer.toString(WirelessActivity.this.minute);
//                    if (Integer.valueOf(time1) < 10) {
//                        time1 = "0" + time1;
//                    }
//                    if (Integer.valueOf(time2) < 10) {
//                        time2 = "0" + time2;
//                    }
//                    dateStr = time1 + ":" + time2;
//
//                    Log.i("time-------------->", "" + dateStr);
//                }
//            });
//            dialog.getWindow().findViewById(R.id.time_sure).setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//
//                    SimpleDateFormat df = new SimpleDateFormat("HH:mm");
//                    try {
//                        df.parse(time1 + ":" + time2);
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                    timexq.setText(dateStr);
////                    cb3.setChecked(true);
//                    dialog.dismiss();
//                }
//            });
        }


    }

    private void listen() {

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                System.out.println("position" + position);
                Log.e("OK", deviceList.get(position).getDeviceID() + "--" + token);
                deviceid = deviceList.get(position).getDeviceID();
                // TODAuto-generated method stub
                if (deviceList.get(position).getCanSetClock() == "true") {
                    if (deviceList.get(position).isHighFrequency()) {
                        Toast.makeText(getApplicationContext(), "有线没有闹钟!",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        //请求接口
                        RequestParams params1 = new RequestParams(WirelessActivity.this);
                        information2(deviceList.get(position).getDeviceID());
                        View popView = LayoutInflater.from(mContext).inflate(
                                R.layout.bell_window, null);
                        final View rootView = findViewById(R.id.root_main3); // 當前頁面的根佈局
                        popupWindow = new PopupWindow(popView,
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        // 设置弹出动画
//                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
                        popupWindow.setBackgroundDrawable(new BitmapDrawable());
                        popupWindow.setFocusable(true);
                        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        String ppparam = "{" + '"' + "tokenstring" + '"' + ":'" + token + "'," + '"' + "userid" + '"' + ":'" + userid + "','deviceid" + "':'" + deviceid + "'" + "}";
                        Log.e("dsdasdasdasdas", ppparam);
                        params1.addFormDataPart("param", ppparam);
                        HttpRequest.post(Api.GetDeviceTiming, params1, new JsonHttpRequestCallback() {
                            @Override
                            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                super.onSuccess(headers, jsonObject);
                                Log.e("dsdasdasdasdas", jsonObject.toString());
                                istrack=jsonObject.getInteger("istrack");
                                if(istrack==1){
                                    Log.i("popupWindow", "追踪中");
                                    new AlertDialog.Builder(WirelessActivity.this)
                                            .setTitle("提示")
                                            .setMessage("当前设备处于追踪模式，是否要继续设置闹铃？")
                                            .setPositiveButton("否", new DialogInterface.OnClickListener() {

                                                @Override

                                                public void onClick(DialogInterface dialog, int which) {

                                                }

                                            })
                                            .setNegativeButton(
                                                    "是",
                                                    new DialogInterface.OnClickListener() {

                                                        @Override
                                                        public void onClick(
                                                                DialogInterface dialog,
                                                                int which) {

                                                            popupWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, 0, 0);
                                                            Log.i("popupWindow", "sure");
                                                        }
                                                    }).show();
                                }else{
                                    Log.i("popupWindow", "不追踪");
                                    popupWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, 0, 0);
                                }
//                            Toast.makeText(WirelessActivity.this,jsonObject.getString("Result"),Toast.LENGTH_LONG).show();

                                    //非间隔上报模式
                                    if (jsonObject.getInteger("Result") == 0) {

//                                    Toast.makeText(WirelessActivity.this,"333",Toast.LENGTH_LONG).show();
                                        if(jsonObject.getInteger("interval") != null){
                                            //间隔上报模式
                                            //间隔上报模式
                                            cb1.setClickable(false);
                                            cb2.setClickable(false);
                                            cb3.setClickable(false);
                                            cb4.setClickable(false);
                                            ednl1.setClickable(false);
                                            ednl2.setClickable(false);
                                            ednl3.setClickable(false);
                                            ednl4.setClickable(false);
                                            xqyi.setClickable(false);
                                            xqer.setClickable(false);
                                            xqsan.setClickable(false);
                                            xqsi.setClickable(false);
                                            xqwu.setClickable(false);
                                            xqliu.setClickable(false);
                                            xqri.setClickable(false);
                                            jgsbms.setChecked(true);
                                            if (jsonObject.getInteger("interval") == 15) {
                                                sp_jgsc.setSelection(0);
//                                        brand.setSelection(carpp);
                                            } else if (jsonObject.getInteger("interval") == 30) {
                                                sp_jgsc.setSelection(1);
                                            } else if (jsonObject.getInteger("interval") == 60) {
                                                sp_jgsc.setSelection(2);
                                            } else if (jsonObject.getInteger("interval") == 120) {
                                                sp_jgsc.setSelection(3);
                                            } else if (jsonObject.getInteger("interval") == 180) {
                                                sp_jgsc.setSelection(4);
                                            }
                                        }else if (jsonObject.getString("isweek") == "true") {
                                            ednl1.setClickable(false);
                                            ednl2.setClickable(false);
                                            ednl3.setClickable(false);
                                            ednl4.setClickable(false);
                                            xqms.setChecked(true);
                                            nzms.setChecked(false);
                                            xqyi.setClickable(true);
                                            xqer.setClickable(true);
                                            xqsan.setClickable(true);
                                            xqsi.setClickable(true);
                                            xqwu.setClickable(true);
                                            xqliu.setClickable(true);
                                            xqri.setClickable(true);
                                            cb1.setClickable(false);
                                            cb2.setClickable(false);
                                            cb3.setClickable(false);
                                            cb4.setClickable(false);
                                        } else if (jsonObject.getString("isweek") == "false") {
                                            ednl1.setClickable(true);
                                            ednl2.setClickable(true);
                                            ednl3.setClickable(true);
                                            ednl4.setClickable(true);
                                            cb1.setClickable(true);
                                            cb2.setClickable(true);
                                            cb3.setClickable(true);
                                            cb4.setClickable(true);
                                            nzms.setChecked(true);
                                            xqms.setChecked(false);
                                        }
//                                Toast.makeText(WirelessActivity.this,"dsds",Toast.LENGTH_LONG).show();
                                        //这里没进来
                                        if (jsonObject.getString("cansetclock") == "false") {
                                            llnz.setVisibility(View.GONE);
                                            Toast.makeText(WirelessActivity.this, "闹钟模式不可点击", Toast.LENGTH_LONG).show();
                                            if (jsonObject.getString("cansetweek") == "true") {
                                                nzms.setClickable(false);
                                                xqms.setClickable(true);
                                            } else {
                                                nzms.setClickable(false);
                                                xqms.setClickable(false);
                                            }

                                        } else {
                                            if (jsonObject.getString("cansetweek") == "true") {
                                                nzms.setClickable(true);
                                                xqms.setClickable(true);

                                            } else {
                                                nzms.setClickable(true);
                                                xqms.setClickable(false);
                                                llxq.setVisibility(View.GONE);
//                                                Toast.makeText(WirelessActivity.this, "星期模式不可点击", Toast.LENGTH_LONG).show();
                                            }

                                        }
                                    } else {
                                        Toast.makeText(WirelessActivity.this, jsonObject.getString("ErrorMsg"), Toast.LENGTH_LONG).show();
                                    }
                                    com.alibaba.fastjson.JSONArray llp = jsonObject.getJSONArray("DataList");
                                if(jsonObject.getInteger("interval") != null){
                                    //间隔上报模式
                                    //间隔上报模式
                                    jgsbms.setChecked(true);
                                    if (jsonObject.getInteger("interval") == 15) {
                                        sp_jgsc.setSelection(0);
//                                        brand.setSelection(carpp);
                                    } else if (jsonObject.getInteger("interval") == 30) {
                                        sp_jgsc.setSelection(1);
                                    } else if (jsonObject.getInteger("interval") == 60) {
                                        sp_jgsc.setSelection(2);
                                    } else if (jsonObject.getInteger("interval") == 120) {
                                        sp_jgsc.setSelection(3);
                                    } else if (jsonObject.getInteger("interval") == 180) {
                                        sp_jgsc.setSelection(4);
                                    }
                                }else if (jsonObject.getString("isweek") == "true") {
//                                Toast.makeText(WirelessActivity.this,llp.getJSONObject(0).getString("clock"),Toast.LENGTH_LONG).show();

                                        //星期模式
                                        com.alibaba.fastjson.JSONArray lap = jsonObject.getJSONArray("DataList");
                                        weeklist.clear();
                                        for (int i = 0; i < lap.size(); i++) {
//                                        Toast.makeText(WirelessActivity.this,"32323",Toast.LENGTH_LONG).show();
                                            int a = lap.getJSONObject(i).getInteger("weekday");
                                            timexq.setText(lap.getJSONObject(i).getString("clock"));
                                            while (a == 1) {
                                                xqyi.setChecked(true);
                                                break;
                                            }
                                            while (a == 2) {
                                                xqer.setChecked(true);
                                                break;
                                            }
                                            while (a == 3) {
                                                xqsan.setChecked(true);
                                                break;
                                            }
                                            while (a == 4) {
                                                xqsi.setChecked(true);
                                                break;
                                            }
                                            while (a == 5) {
                                                xqwu.setChecked(true);
                                                break;
                                            }
                                            while (a == 6) {
                                                xqliu.setChecked(true);
                                                break;
                                            }
                                            while (a == 7) {
                                                xqri.setChecked(true);
                                                break;
                                            }
                                            Log.e("weekday", a + "");
                                            weeklist.add(a);
                                        }
                                        Log.i("dssd", "323");
                                    }
                                    else {
                                        for (int i = 0; i < llp.size(); i++) {
                                            String a = llp.getJSONObject(i).getString("clock");
                                            Log.i("dssd", "323");
                                            Log.i("dssde", i + "");
                                            if (i == 0) {
                                                ednl1.setFocusableInTouchMode(true);
                                                ednl1.setFocusable(true);
                                                ednl1.requestFocus();
                                                cb1.setChecked(true);
                                                ednl1.setText(a);
                                            } else if (i == 1) {
                                                ednl2.setFocusableInTouchMode(true);
                                                ednl2.setFocusable(true);
                                                ednl2.requestFocus();
                                                cb2.setChecked(true);
                                                ednl2.setText(a);
                                            } else if (i == 2) {
                                                ednl3.setFocusableInTouchMode(true);

                                                ednl3.setFocusable(true);
                                                cb3.setChecked(true);
                                                ednl3.requestFocus();
                                                ednl3.setText(a);
                                            } else if (i == 3) {
                                                ednl4.setFocusableInTouchMode(true);

                                                ednl4.setFocusable(true);
                                                cb4.setChecked(true);
                                                ednl4.requestFocus();
                                                ednl4.setText(a);
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
                                progressDialog.setMessage("正在获取信息");
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


                        // 顯示在根佈局的底部
//                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
//                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        Log.i("istrack", istrack+"");
//                        if(istrack==1){
//                            Log.i("popupWindow", "追踪中");
//                            new AlertDialog.Builder(WirelessActivity.this)
//                                    .setTitle("提示")
//                                    .setMessage("当前设备处于追踪模式，是否要继续设置闹铃？")
//                                    .setPositiveButton("否", new DialogInterface.OnClickListener() {
//
//                                        @Override
//
//                                        public void onClick(DialogInterface dialog, int which) {
//
//                                        }
//
//                                    })
//                                    .setNegativeButton(
//                                            "是",
//                                            new DialogInterface.OnClickListener() {
//
//                                                @Override
//                                                public void onClick(
//                                                        DialogInterface dialog,
//                                                        int which) {
//
//                                                    popupWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, 0, 0);
//                                                    Log.i("popupWindow", "sure");
//                                                }
//                                            }).show();
//                        }else{
//                            Log.i("popupWindow", "不追踪");
//                            popupWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, 0, 0);
//                        }
//                        popupWindow.showAtLocation(rootView, Gravity.TOP | Gravity.LEFT, 0, 0);
                        cancel = (TextView) popView.findViewById(R.id.textView5);
                        save1 = (TextView) popView.findViewById(R.id.textView6);
//                        listView2=(ListView)popView.findViewById(R.id.listView9);
                        ednl1 = (TextView) popView.findViewById(R.id.ednl1);
                        ednl2 = (TextView) popView.findViewById(R.id.ednl2);
                        ednl3 = (TextView) popView.findViewById(R.id.ednl3);
                        ednl4 = (TextView) popView.findViewById(R.id.ednl4);
                        cb1 = (CheckBox) popView.findViewById(R.id.nl1);
                        cb2 = (CheckBox) popView.findViewById(R.id.nl2);
                        cb3 = (CheckBox) popView.findViewById(R.id.nl3);
                        cb4 = (CheckBox) popView.findViewById(R.id.nl4);
                        allType = new ArrayList<String>();
                        allType.add("15分钟");
                        allType.add("30分钟");
                        allType.add("1小时");
                        allType.add("2小时");
                        allType.add("3小时");
                        sp_jgsc = (Spinner) popView.findViewById(R.id.sp_jgsc);
                        arr_adapter = new ArrayAdapter<String>(WirelessActivity.this, android.R.layout.simple_spinner_item, allType);

                        //设置样式
                        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        加载适配器
                        sp_jgsc.setAdapter(arr_adapter);

                        nzms = (CheckBox) popView.findViewById(R.id.cb2);
                        xqms = (CheckBox) popView.findViewById(R.id.cb1);
                        jgsbms = (CheckBox) popView.findViewById(R.id.cb3);
                        xqyi = (CheckBox) popView.findViewById(R.id.xqy);
                        xqer = (CheckBox) popView.findViewById(R.id.xqer);
                        xqsan = (CheckBox) popView.findViewById(R.id.xqsan);
                        xqsi = (CheckBox) popView.findViewById(R.id.xqsi);
                        xqwu = (CheckBox) popView.findViewById(R.id.xqwu);
                        xqliu = (CheckBox) popView.findViewById(R.id.xqliu);
                        xqri = (CheckBox) popView.findViewById(R.id.xqqi);
                        timexq = (EditText) popView.findViewById(R.id.edxq);
                        llnz = (LinearLayout) popView.findViewById(R.id.llnz);
                        llxq = (LinearLayout) popView.findViewById(R.id.llxq);
//                        llxq.setVisibility(View.GONE);
                        sp_jgsc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapter, View view,
                                                       int position, long id) {
                                // TODO Auto-generated method stub
                                brand1 = (String) sp_jgsc.getAdapter().getItem((int) id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub
                            }
                        });
                        nzms.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xqyi.setClickable(false);
                                xqer.setClickable(false);
                                xqsan.setClickable(false);
                                xqsi.setClickable(false);
                                xqwu.setClickable(false);
                                xqliu.setClickable(false);
                                xqri.setClickable(false);
                                ednl1.setClickable(true);
                                ednl2.setClickable(true);
                                ednl3.setClickable(true);
                                ednl4.setClickable(true);
                                cb1.setClickable(true);
                                cb2.setClickable(true);
                                cb3.setClickable(true);
                                cb4.setClickable(true);
                                if (!((CheckBox) v).isChecked()) {
                                    nzms.setChecked(true);
                                    xqms.setChecked(false);
                                    jgsbms.setChecked(false);

                                } else {
                                    if (!ednl1.getText().toString().trim().isEmpty()) {
                                        cb1.setChecked(true);
                                    }
                                    if (!ednl2.getText().toString().trim().isEmpty()) {
                                        cb2.setChecked(true);
                                    }
                                    if (!ednl3.getText().toString().trim().isEmpty()) {
                                        cb3.setChecked(true);
                                    }
                                    if (!ednl4.getText().toString().trim().isEmpty()) {
                                        cb4.setChecked(true);
                                    }

                                    nzms.setChecked(true);
                                    xqms.setChecked(false);
                                    jgsbms.setChecked(false);
                                    xqyi.setChecked(false);
                                    xqer.setChecked(false);
                                    xqsan.setChecked(false);
                                    xqsi.setChecked(false);
                                    xqwu.setChecked(false);
                                    xqliu.setChecked(false);
                                    xqri.setChecked(false);
                                }
                            }
                        });
//                        ednl1.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                if (ednl1.getText().toString().trim().isEmpty()) {
////                                    cb1.setChecked(false);
//                                } else {
//                                    cb1.setChecked(true);
//                                }
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//
//                            }
//                        });
//                        ednl2.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                if (ednl2.getText().toString().trim().isEmpty()) {
////                                    cb2.setChecked(false);
//                                } else {
//                                    cb2.setChecked(true);
//                                }
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//
//                            }
//                        });
//                        ednl3.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                if (ednl3.getText().toString().trim().isEmpty()) {
////                                    cb3.setChecked(false);
//                                } else {
//                                    cb3.setChecked(true);
//                                }
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//
//                            }
//                        });
//                        ednl4.addTextChangedListener(new TextWatcher() {
//                            @Override
//                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                                if (ednl4.getText().toString().trim().isEmpty()) {
////                                    cb4.setChecked(false);
//                                } else {
//                                    cb4.setChecked(true);
//                                }
//                            }
//
//                            @Override
//                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                            }
//
//                            @Override
//                            public void afterTextChanged(Editable s) {
//
//                            }
//                        });

                        jgsbms.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.i("231234","123");
                                xqyi.setClickable(false);
                                xqer.setClickable(false);
                                xqsan.setClickable(false);
                                xqsi.setClickable(false);
                                xqwu.setClickable(false);
                                xqliu.setClickable(false);
                                xqri.setClickable(false);
                                ednl1.setClickable(false);
                                ednl2.setClickable(false);
                                ednl3.setClickable(false);
                                ednl4.setClickable(false);
                                cb1.setClickable(false);
                                cb2.setClickable(false);
                                cb3.setClickable(false);
                                cb4.setClickable(false);
                                if (!((CheckBox) v).isChecked()) {
                                    jgsbms.setChecked(true);
                                    xqms.setChecked(false);
                                    nzms.setChecked(false);

                                } else {
                                    jgsbms.setChecked(true);
                                    cb1.setChecked(false);
                                    cb2.setChecked(false);
                                    cb3.setChecked(false);
                                    cb4.setChecked(false);
                                    xqms.setChecked(false);
                                    nzms.setChecked(false);
                                    xqyi.setChecked(false);
                                    xqer.setChecked(false);
                                    xqsan.setChecked(false);
                                    xqsi.setChecked(false);
                                    xqwu.setChecked(false);
                                    xqliu.setChecked(false);
                                    xqri.setChecked(false);
                                }
                            }
                        });
                        xqms.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                xqyi.setClickable(true);
                                xqer.setClickable(true);
                                xqsan.setClickable(true);
                                xqsi.setClickable(true);
                                xqwu.setClickable(true);
                                xqliu.setClickable(true);
                                xqri.setClickable(true);
                                ednl1.setClickable(false);
                                ednl2.setClickable(false);
                                ednl3.setClickable(false);
                                ednl4.setClickable(false);
                                cb1.setClickable(false);
                                cb2.setClickable(false);
                                cb3.setClickable(false);
                                cb4.setClickable(false);
                                Log.i("checker", weeklist.size() + "");
                                for (int i = 0; i < weeklist.size(); i++) {
                                    Log.i("checker", "323");
                                    if ((Integer) weeklist.get(i) == 1) {
                                        xqyi.setChecked(true);
                                    }
                                    if ((Integer) weeklist.get(i) == 2) {
                                        xqer.setChecked(true);
                                    }
                                    if ((Integer) weeklist.get(i) == 3) {
                                        xqsan.setChecked(true);
                                    }
                                    if ((Integer) weeklist.get(i) == 4) {
                                        xqsi.setChecked(true);
                                    }
                                    if ((Integer) weeklist.get(i) == 5) {
                                        xqwu.setChecked(true);
                                    }
                                    if ((Integer) weeklist.get(i) == 6) {
                                        xqliu.setChecked(true);
                                    }
                                    if ((Integer) weeklist.get(i) == 7) {
                                        xqri.setChecked(true);
                                    }
                                }
                                if (!((CheckBox) v).isChecked()) {
                                    xqms.setChecked(true);
                                    nzms.setChecked(false);
                                    jgsbms.setChecked(false);
                                } else {
                                    xqms.setChecked(true);
                                    nzms.setChecked(false);
                                    jgsbms.setChecked(false);
                                    cb1.setChecked(false);
                                    cb2.setChecked(false);
                                    cb3.setChecked(false);
                                    cb4.setChecked(false);
                                }
                            }
                        });
                        cb1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!((CheckBox) v).isChecked()) {
                                    cb1.setChecked(false);
                                    ednl1.setText("");
                                } else {

                                    cb1.setChecked(true);
                                }
                            }
                        });
                        cb2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!((CheckBox) v).isChecked()) {
                                    cb2.setChecked(false);
                                    ednl2.setText("");
                                } else {

                                    cb2.setChecked(true);
                                }
                            }
                        });
                        cb3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!((CheckBox) v).isChecked()) {
                                    cb3.setChecked(false);
                                    ednl3.setText("");
                                } else {

                                    cb3.setChecked(true);
                                }
                            }
                        });
                        cb4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!((CheckBox) v).isChecked()) {
                                    cb4.setChecked(false);
                                    ednl4.setText("");
                                } else {

                                    cb4.setChecked(true);
                                }
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "未保存修改",
                                        Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                                weeklist.clear();
                            }
                        });
                        save1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//我注释的

                                clock = "";
                                arr = adapter1.getArr();
                                clock = arr[0];
                                //可以获得
//                            Toast.makeText(WirelessActivity.this,arr+"",Toast.LENGTH_LONG).show();
                                Log.e("shijian", "----" + clock);
//                            for(int i=1;i<size;i++)
//                            {
//                                clock=clock+";"+arr[i];
//                                Log.i("aaaaaaaaaaaas",arr[i] );
//                            }
                                RequestParams params = new RequestParams(WirelessActivity.this);
                                if (nzms.isChecked()) {
                                    String message = "[";
                                    if (ednl1.getText().toString().trim().isEmpty() && ednl2.getText().toString().trim().isEmpty() && ednl3.getText().toString().trim().isEmpty() && ednl4.getText().toString().trim().isEmpty()) {
                                        Toast.makeText(WirelessActivity.this, "闹钟模式最少需要一组", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (ednl1.getText().toString().trim().isEmpty()) {

                                    } else {
                                        if (check(ednl1.getText().toString().trim())) {

                                        } else {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (Integer.valueOf(ednl1.getText().toString().trim().substring(0, 2)) > 23 || Integer.valueOf(ednl1.getText().toString().trim().substring(3, 5)) > 59) {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        message = message + "{"
                                                + '"' + "weekday" + '"' + ":" + '"' + 0 + '"' + ","
                                                + '"' + "clock" + '"' + ":" + '"' + ednl1.getText().toString().trim() + '"' + ","
                                                + '"' + "repeatMode" + '"' + ":" + '"' + 1 + '"'
                                                + "},";
                                    }
                                    if (ednl2.getText().toString().trim().isEmpty()) {

                                    } else {
                                        if (check(ednl2.getText().toString().trim())) {

                                        } else {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (Integer.valueOf(ednl2.getText().toString().trim().substring(0, 2)) > 23 || Integer.valueOf(ednl2.getText().toString().trim().substring(3, 5)) > 59) {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        message = message + "{"
                                                + '"' + "weekday" + '"' + ":" + '"' + 0 + '"' + ","
                                                + '"' + "clock" + '"' + ":" + '"' + ednl2.getText().toString().trim() + '"' + ","
                                                + '"' + "repeatMode" + '"' + ":" + '"' + 1 + '"'
                                                + "},";
                                    }
                                    if (ednl3.getText().toString().trim().isEmpty()) {

                                    } else {
                                        if (check(ednl3.getText().toString().trim())) {

                                        } else {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if (Integer.valueOf(ednl3.getText().toString().trim().substring(0, 2)) > 23 || Integer.valueOf(ednl3.getText().toString().trim().substring(3, 5)) > 59) {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        message = message + "{"
                                                + '"' + "weekday" + '"' + ":" + '"' + 0 + '"' + ","
                                                + '"' + "clock" + '"' + ":" + '"' + ednl3.getText().toString().trim() + '"' + ","
                                                + '"' + "repeatMode" + '"' + ":" + '"' + 1 + '"'
                                                + "},";
                                    }

//                                    else if(ednl3.getText().toString().trim().length()==5&&ednl3.getText().toString().trim().substring(2,3).equals(":")&&Integer.valueOf(ednl3.getText().toString().trim().substring(0,2))<24&&Integer.valueOf(ednl3.getText().toString().trim().substring(3,5))<60){
//                                        message= message+"{"
//                                                + '"' + "weekday" + '"' + ":" + '"' + 0 + '"' + ","
//                                                + '"' + "clock" + '"' + ":" + '"' + ednl3.getText().toString().trim() + '"' + ","
//                                                + '"' + "repeatMode" + '"' + ":" + '"'  +1+ '"'
//                                                + "},";
//                                    }
//                                    else{
//                                        Toast.makeText(WirelessActivity.this,"请输入正确的时间格式，如“08:10”",Toast.LENGTH_SHORT).show();
//                                        return;
//                                    }
                                    if (ednl4.getText().toString().trim().isEmpty()) {

                                    } else {
                                        if (check(ednl4.getText().toString().trim())) {

                                        } else {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Log.i("jinlai", "jin");
                                        if (Integer.valueOf(ednl4.getText().toString().trim().substring(0, 2)) > 23 || Integer.valueOf(ednl4.getText().toString().trim().substring(3, 5)) > 59) {
                                            Toast.makeText(WirelessActivity.this, "设置错误.clock格式不正确，应为08:00格式", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        message = message + "{"
                                                + '"' + "weekday" + '"' + ":" + '"' + 0 + '"' + ","
                                                + '"' + "clock" + '"' + ":" + '"' + ednl4.getText().toString().trim() + '"' + ","
                                                + '"' + "repeatMode" + '"' + ":" + '"' + 1 + '"'
                                                + "},";
                                    }
//                                    for(int i=0;i<arr.length;i++){
//                                        if(arr[i]==null){
//
//                                        }else{
//                                            message= message+"{"
//                                                    + '"' + "weekday" + '"' + ":" + '"' + 0 + '"' + ","
//                                                    + '"' + "clock" + '"' + ":" + '"' + arr[i] + '"' + ","
//                                                    + '"' + "repeatMode" + '"' + ":" + '"'  +1+ '"'
//                                                    + "},";
//                                        }
//
//                                    }

                                    message = message.substring(0, message.length() - 1) + "]";
                                    String pparam = "{" + '"' + "tokenstring" + '"' + ":'" + token + "'," + '"' + "userid" + '"' + ":'" + userid + "','deviceid" + "':'" + deviceid + "','DataList'" + ":" + message + "}";

                                    Log.i("pparam", pparam);
                                    params.addFormDataPart("param", pparam);
//                            params.addFormDataPart("userid",userid);
//                            params.addFormDataPart("tokenstring", token);
                                    //  地址  参数  回调函数
                                    HttpRequest.post(Api.SET_DEVICE_CLOCK, params, new JsonHttpRequestCallback() {
                                        @Override
                                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                            super.onSuccess(headers, jsonObject);
                                            Log.e("-1222-------->", jsonObject.toString());
                                            SetDeviceBean device = JSONObject.parseObject(jsonObject.toString(), SetDeviceBean.class);
                                            if (device.getResult() == 0) {

                                                Toast.makeText(getApplicationContext(), "保存成功",
                                                        Toast.LENGTH_SHORT).show();
                                                popupWindow.dismiss();
                                            } else {
                                                Toast.makeText(getApplicationContext(), device.getErrorMsg(),
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
                                            progressDialog.setMessage("正在获取信息");
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
                                } else if (xqms.isChecked()) {
                                    String time = timexq.getText().toString();
                                    String message = "[";
                                    shuzu = new int[7];
                                    if (xqyi.isChecked() || xqer.isChecked() || xqsan.isChecked() || xqsi.isChecked() || xqwu.isChecked() || xqliu.isChecked() || xqri.isChecked()) {
                                        if (xqyi.isChecked()) {
                                            shuzu[0] = 1;
                                        } else if (!xqyi.isChecked()) {
                                            shuzu[0] = 0;
                                        }
                                        if (xqer.isChecked()) {
                                            shuzu[1] = 2;
                                        } else if (!xqer.isChecked()) {
                                            shuzu[1] = 0;
                                        }
                                        if (xqsan.isChecked()) {
                                            shuzu[2] = 3;
                                        } else if (!xqsan.isChecked()) {
                                            shuzu[2] = 0;
                                        }
                                        if (xqsi.isChecked()) {
                                            shuzu[3] = 4;
                                        } else if (!xqsi.isChecked()) {
                                            shuzu[3] = 0;
                                        }
                                        if (xqwu.isChecked()) {
                                            shuzu[4] = 5;
                                        } else if (!xqwu.isChecked()) {
                                            shuzu[4] = 0;
                                        }
                                        if (xqliu.isChecked()) {
                                            shuzu[5] = 6;
                                        } else if (!xqliu.isChecked()) {
                                            shuzu[5] = 0;
                                        }
                                        if (xqri.isChecked()) {
                                            shuzu[6] = 7;
                                        } else if (!xqri.isChecked()) {
                                            shuzu[6] = 0;
                                        }
                                        for (int i = 0; i < shuzu.length; i++) {
                                            if (shuzu[i] == 0) {

                                            } else {
                                                message = message + "{"
                                                        + '"' + "weekday" + '"' + ":" + '"' + shuzu[i] + '"' + ","
                                                        + '"' + "clock" + '"' + ":" + '"' + time + '"' + ","
                                                        + '"' + "repeatMode" + '"' + ":" + '"' + 1 + '"'
                                                        + "},";
                                            }
                                        }
                                        Log.i("pparamsds", shuzu[2] + "");
                                        message = message.substring(0, message.length() - 1) + "]";
                                        Log.i("pparamsds", message);
//                                    String message= "[{"
//                                            + '"' + "weekday" + '"' + ":" + '"' + 1 + '"' + ","
//                                            + '"' + "clock" + '"' + ":" + '"' + "08:00" + '"' + ","
//                                            + '"' + "repeatMode" + '"' + ":" + '"'  +1+ '"'
//                                            + "}]";
                                        String pparam = "{" + '"' + "tokenstring" + '"' + ":'" + token + "'," + '"' + "userid" + '"' + ":'" + userid + "','deviceid" + "':'" + deviceid + "','DataList'" + ":" + message + "}";

                                        Log.i("pparam", pparam);
                                        params.addFormDataPart("param", pparam);
//                            params.addFormDataPart("userid",userid);
//                            params.addFormDataPart("tokenstring", token);
                                        //  地址  参数  回调函数
                                        HttpRequest.post(Api.SET_DEVICE_CLOCK, params, new JsonHttpRequestCallback() {
                                            @Override
                                            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                                super.onSuccess(headers, jsonObject);
                                                Log.e("-1222-------->", jsonObject.toString());
                                                SetDeviceBean device = JSONObject.parseObject(jsonObject.toString(), SetDeviceBean.class);
                                                if (device.getResult() == 0) {

                                                    Toast.makeText(getApplicationContext(), "保存成功",
                                                            Toast.LENGTH_SHORT).show();
                                                    popupWindow.dismiss();
                                                } else {
                                                    Toast.makeText(getApplicationContext(), device.getErrorMsg(),
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
                                                progressDialog.setMessage("正在获取信息");
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
                                    } else {
                                        Toast.makeText(WirelessActivity.this, "请选择星期", Toast.LENGTH_LONG).show();
                                    }

                                } else if (jgsbms.isChecked()) {
//                                    String jgtime=sp_jgsc.getSelectedItem());
//                                    Toast.makeText(WirelessActivity.this,brand1,Toast.LENGTH_SHORT).show();
                                    int time_jiange = 0;
                                    if (brand1.equals("15分钟")) {
                                        time_jiange = 15;
                                    } else if (brand1.equals("30分钟")) {
                                        time_jiange = 30;
                                    } else if (brand1.equals("1小时")) {
                                        time_jiange = 60;
                                    } else if (brand1.equals("2小时")) {
                                        time_jiange = 120;
                                    } else if (brand1.equals("3小时")) {
                                        time_jiange = 180;
                                    }
                                    String pparam = "{" + '"' + "tokenstring" + '"' + ":'" + token + "'," + '"' + "userid" + '"' + ":'" + userid + "','deviceid" + "':'" + deviceid + "','interval'" + ":" + time_jiange + "}";

                                    Log.i("pparam", pparam);
                                    params.addFormDataPart("param", pparam);
//                            params.addFormDataPart("userid",userid);
//                            params.addFormDataPart("tokenstring", token);
                                    //  地址  参数  回调函数
                                    HttpRequest.post(Api.SET_DEVICE_CLOCK, params, new JsonHttpRequestCallback() {
                                        @Override
                                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                            super.onSuccess(headers, jsonObject);
                                            Log.e("-1222-------->", jsonObject.toString());
                                            SetDeviceBean device = JSONObject.parseObject(jsonObject.toString(), SetDeviceBean.class);
                                            if (device.getResult() == 0) {

                                                Toast.makeText(getApplicationContext(), "保存成功",
                                                        Toast.LENGTH_SHORT).show();
                                                popupWindow.dismiss();
                                            } else {
                                                Toast.makeText(getApplicationContext(), device.getErrorMsg(),
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
                                            progressDialog.setMessage("正在获取信息");
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

                                } else {
                                    Toast.makeText(WirelessActivity.this, "请选择一种模式", Toast.LENGTH_LONG).show();
                                }
                                weeklist.clear();
                            }
                        });

                    }
                } else {
                    Toast.makeText(WirelessActivity.this, "该设备不能设置闹铃", Toast.LENGTH_LONG).show();
                }


            }
        });
    }

    public static String MapToString(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        // 遍历map
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {
                sb.append(key.toString() + SEP1 + ListToString((List<?>) value));
                sb.append(SEP1);
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString()
                        + MapToString((Map<?, ?>) value));
                sb.append(SEP2);
            } else {
                sb.append(key.toString() + SEP3 + value.toString());
                sb.append(SEP2);
            }
        }
        return sb.toString();
    }

    public static String ListToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i)));

                } else if (list.get(i) instanceof Map) {
                    sb.append(MapToString((Map<?, ?>) list.get(i)));

                } else {
                    sb.append(list.get(i));

                }
            }
        }
        return "[{" + sb.toString() + "}]";
    }

    private void information2(String device) {
        Log.i("ddvice", device);
        RequestParams params = new RequestParams(WirelessActivity.this);
        params.addFormDataPart("deviceID", device);
        params.addFormDataPart("tokenString", token);
        //  地址  参数  回调函数
        HttpRequest.post(Api.GRT_DEVICE_CLOCK, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                GetDeviceBean device = JSONObject.parseObject(jsonObject.toString(), GetDeviceBean.class);
                com.alibaba.fastjson.JSONArray lap = jsonObject.getJSONArray("DataList");

                size = device.getDataList().size();
                getdeviceList = device.getDataList();
                adapter1 = new GetDeviceAdapter(WirelessActivity.this, getdeviceList);
                adapter1.notifyDataSetChanged();
//                listView2.setAdapter(adapter1);
                arr = adapter1.getArr();
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Log.e("alertMsg failure", errorCode + msg);
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog.setMessage("正在获取信息");
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


    private void information() {

        RequestParams params = new RequestParams(WirelessActivity.this);
        Log.i("userID", userid);
        Log.i("tokenString", token);
        Log.i("tokenString", queryString);
        Log.i("tokenString", Api.Device_List);

        params.addFormDataPart("sortName", "");
        params.addFormDataPart("queryString", queryString);
        params.addFormDataPart("userID", userid);
        params.addFormDataPart("validDays", "");
        params.addFormDataPart("isTrack", "");
        params.addFormDataPart("page", 1);
        params.addFormDataPart("pageSize", 10000);
        params.addFormDataPart("sortName", "");
        params.addFormDataPart("asc", "asc");
        params.addFormDataPart("tokenString", token);
        Log.i("paramsparams", params.toString());
        //  地址  参数  回调函数
        HttpRequest.post(Api.Device_List, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {

                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                DeviceBean2 device = JSONObject.parseObject(jsonObject.toString(), DeviceBean2.class);
                deviceList = device.getDataList();
                adapter = new WirlessAdapter(WirelessActivity.this, deviceList);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Log.e("alertMsg failure", errorCode + msg);
            }

            @Override
            public void onStart() {
                super.onStart();
                progressDialog.setMessage("正在获取信息");
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

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    private void set_eSearch_TextChanged() {
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
                    information();
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                    myhandler.post(eChanged);
                    queryString = eSearch.getText().toString();
                    information();
                }
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