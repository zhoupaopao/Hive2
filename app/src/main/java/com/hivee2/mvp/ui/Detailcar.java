package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.CarMessage_Bean;
import com.hivee2.utils.HiveUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/20.
 */
public class Detailcar extends Activity implements HttpCycleContext  {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private TextView CarName;
    private TextView BorrowMan;
    private TextView Device;
    private TextView Address;
    private TextView Cardname;
    private TextView Imei;
    private TextView Style;
    private TextView Start;
    private TextView End;
    private TextView BL;
    private TextView BS;
    private TextView lineCount;
    private TextView PositionTime;
    private TextView carriage;
    private TextView color;
    private TextView type1;
    private TextView brand;
    private TextView size;
    private TextView copy;
    private ImageView back;
    private TextView title;
    private String startdata;
    private String enddata;
    private String Carinforid="";
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cardetail);
         init();
         initlisten();
        information();
    }
    public void information()
    {
        RequestParams params = new RequestParams(Detailcar.this);
        params.addFormDataPart("carID",Carinforid);
        params.addFormDataPart("tokenString", token);
        HttpRequest.post(Api.GET_CAR_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--AAAAA------->", jsonObject.toString());
                Log.e("--AAAAA------->", jsonObject.getString("DataList").trim());
                if(jsonObject.getString("DataList").trim().equals("[]")){
                    Log.e("--AAAAA------->", jsonObject.getString("DataList").trim());
                    Toast.makeText(Detailcar.this,"对不起，不存在该车辆信息",Toast.LENGTH_LONG).show();

                }else{
                    CarMessage_Bean carMessage = JSONObject.parseObject(jsonObject.toString(), CarMessage_Bean.class);
                    carriage.setText(carMessage.getDataList().get(0).getCarInfo().getVIN());
                    if(carMessage.getDataList().get(0).getCarInfo().getCarCategory().equals("1"))
                    {
                        type1.setText("SUV");
                    }
                    else if(carMessage.getDataList().get(0).getCarInfo().getCarCategory().equals("1"))
                    {

                        type1.setText("轿车");
                    }
                    else if(carMessage.getDataList().get(0).getCarInfo().getCarCategory().equals("3"))
                    {
                        type1.setText("皮卡");
                    }

                    brand.setText(carMessage.getDataList().get(0).getCarInfo().getCarBrand());
                    size.setText(carMessage.getDataList().get(0).getCarInfo().getCarVersion());
                    color.setText(carMessage.getDataList().get(0).getCarInfo().getColor());
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
    private void init()
    {
        Intent intent = getIntent();
        CarName=(TextView)findViewById(R.id.textView33);
        copy=(TextView)findViewById(R.id.textView);
        back=(ImageView)findViewById(R.id.imageView2);
        title=(TextView)findViewById(R.id.title_name1);
        BorrowMan=(TextView)findViewById(R.id.textView2);
        Device=(TextView)findViewById(R.id.textView3);
        Address=(TextView)findViewById(R.id.textView5);
        Cardname=(TextView)findViewById(R.id.putout);
        Imei=(TextView)findViewById(R.id.putout1);
        Style=(TextView)findViewById(R.id.putout9);
        Start=(TextView)findViewById(R.id.putout11);
        End=(TextView)findViewById(R.id.putout12);
        BL=(TextView)findViewById(R.id.putout13);
        BS=(TextView)findViewById(R.id.putout14);
        lineCount=(TextView)findViewById(R.id.putout15);
        PositionTime=(TextView)findViewById(R.id.putout16);
        carriage=(TextView)findViewById(R.id.putout17);
        color=(TextView)findViewById(R.id.putout18);
        type1=(TextView)findViewById(R.id.putout19);
        brand=(TextView)findViewById(R.id.putout20);
        size=(TextView)findViewById(R.id.putout21);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");


        title.setText("车辆详情");
        CarName.setText(intent.getStringExtra("CarNumber"));
        BorrowMan.setText("借款人："+intent.getStringExtra("BorrowMan"));
        Device.setText("设备名："+intent.getStringExtra("Device"));
        Address.setText("地址："+intent.getStringExtra("Address"));
        Cardname.setText(intent.getStringExtra("Cardname"));
        Carinforid=intent.getStringExtra("Carinforid");

        Imei.setText(intent.getStringExtra("Imei"));
        Style.setText(intent.getStringExtra("Style"));
        startdata=intent.getStringExtra("Start").substring(6,19);
        enddata=intent.getStringExtra("End").substring(6,19);
        String formatedDate = sdf.format(new Date(Long.valueOf(startdata)));
        String formatedDate1 = sdf.format(new Date(Long.valueOf(enddata)));
        Start.setText(formatedDate);
        End.setText(formatedDate1);
        BL.setText(intent.getStringExtra("BL") + "%");
        BS.setText(intent.getStringExtra("BS"));
        if(Boolean.valueOf(intent.getStringExtra("lineCount"))==false)
        {

            lineCount.setText("离线");
        }
        else if(Boolean.valueOf(intent.getStringExtra("lineCount"))==true)
        {
            lineCount.setText("在线");
        }

//        lineCount.setText(Integer.valueOf(intent.getStringExtra("lineCount")));
        PositionTime.setText(intent.getStringExtra("PositionTime"));
    }
    private void  initlisten()
    {
        back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
        copy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 得到剪贴板管理器
                ClipboardManager cmb = (ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
                cmb.setText(CarName.getText());
                Toast.makeText(Detailcar.this, "已成功复制车牌号", Toast.LENGTH_SHORT).show();
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
