package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.AlertMsgAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.AlertBean;
import com.hivee2.utils.DateTimePickDialogUtil;
import com.hivee2.utils.HiveUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/18.
 */
public class SelectAlert extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private TextView title;
    private ImageView back;
    private Button confirm;
    JSONArray jsarray;
    private TextView startDateTime;
    private TextView endDateTime;
    private Spinner type1;
    private CheckBox showchild;
    private ArrayAdapter adapter_type;
    public int pos = 0;
    String[] dataa;
    private SharedPreferences sp=null;// 存放用户信息
    private String initStartDateTime =""; // 初始化开始时间
    private String initEndDateTime =""; // 初始化结束时间

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
    private boolean isfreshDT = false;//true 确定 false 取消 用于区分点击的按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alertselect);
        init();
        initlisten();

    }

    private void init() {
        title=(TextView)findViewById(R.id.title_name1);
        back=(ImageView)findViewById(R.id.imageView2);
        confirm=(Button)findViewById(R.id.button);
        Date date=new Date();
        DateFormat format=new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        initStartDateTime=format.format(date);
        initEndDateTime=format.format(date);
        startDateTime = (TextView) findViewById(R.id.time_start);
        endDateTime = (TextView) findViewById(R.id.time_end);
        type1 = (Spinner) findViewById(R.id.type);
        showchild = (CheckBox) findViewById(R.id.showchild);
        title.setText("预警");

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

        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        String token=sp.getString(Constant.sp_token, "");
        RequestParams params = new RequestParams(SelectAlert.this);
//        String pparam="{'tokenstring':'"+token+"'}";
        params.addFormDataPart("TokenString",token);
        //  地址  参数  回调函数
        HttpRequest.post(Api.GET_ALARM_TYPE, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);

                Log.e("--------->", jsonObject.getJSONArray("DataList").toString());
                jsarray=jsonObject.getJSONArray("DataList");
                String[] data=new String[jsarray.size()+1];
                data[0]="全部类型";
                for(int i=1;i<jsarray.size()+1;i++){

                    JSONObject message=jsarray.getJSONObject(i-1);
                    String a=jsarray.getJSONObject(i-1).getString("Name");
                    Log.e("--------->", a.toString());
                    data[i]=a;
                }
                dataa=data;
                adapter_type = new ArrayAdapter(SelectAlert.this, R.layout.spinner_dropdrow_item, data);
                adapter_type.setDropDownViewResource(R.layout.spinner_dropdrow_item);
                type1.setAdapter(adapter_type);

            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Log.e("alertMsg failure", errorCode + msg);

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




//        adapter_type = new ArrayAdapter(this, R.layout.spinner_dropdrow_item, data);
//        adapter_type.setDropDownViewResource(R.layout.spinner_dropdrow_item);
//        type.setAdapter(adapter_type);
    }
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    private void initlisten() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startDate.equals("") || startTime.equals("")) {
                    Toast.makeText(SelectAlert.this, "请选择开始时间", Toast.LENGTH_LONG).show();
                    return;
                }
                if(endDate.equals("") || endTime.equals("")) {
                    Toast.makeText(SelectAlert.this, "请选择结束时间", Toast.LENGTH_LONG).show();
                    return;
                }
                if((startDate + startTime).compareTo(endDate + endTime) >= 0){
                    Toast.makeText(SelectAlert.this, "结束时间需大于开始时间", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("StartTime", startDate + " " + startTime);
                intent.putExtra("EndTime", endDate + " " + endTime);

                    for(int i=0;i<jsarray.size();i++){
                        JSONObject message=jsarray.getJSONObject(i);
                        String aa=jsarray.getJSONObject(i).getString("Name");
                        if(dataa[pos]==aa){
                            intent.putExtra("type", jsarray.getJSONObject(i).getString("Code"));
                        }
                    }
//                intent.putExtra("type", dataa[pos]);
                if(showchild.isChecked())
                    intent.putExtra("showChild", "true");
                else
                    intent.putExtra("showChild", "false");
                setResult(1, intent);
                finish();
            }
        });

        type1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //不仅这里面进的是下面那个方法
                Log.i("initStartDateTime", initStartDateTime);
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        SelectAlert.this, initStartDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        SelectAlert.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });

        startDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDateDialog_start(startDateTime);
            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDateDialog_end(endDateTime);
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
        DatePickerDialog dpd = new DatePickerDialog(SelectAlert.this, Datelistener_start, year_start, month_start, day_start-1);
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
        DatePickerDialog dpd = new DatePickerDialog(SelectAlert.this, Datelistener_end, year_end, month_end, day_end);
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
        TimePickerDialog tpd = new TimePickerDialog(SelectAlert.this, Timelistener_start, hour_start, min_start, true);
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
        TimePickerDialog tpd = new TimePickerDialog(SelectAlert.this, Timelistener_end, hour_end, min_end, true);
        tpd.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isfreshDT) {
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
