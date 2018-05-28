package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.model.bean.PledgerBean;
import com.hivee2.utils.HiveUtil;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/10.
 */
public class EditBorrowMen extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private RelativeLayout delete;
    private TextView title;
    private TextView new1;
    private EditText pledgerName;
    private EditText idCardNumber;
    private EditText contactPhone;
    private EditText address;
    private EditText contactName;
    private EditText remak;
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";
    private String pledgerName1="";
    private String idCardNumber1="";
    private String contactPhone1="";
    private String address1="";
    private String contactName1="";
    private String remak1="";
    private String PledgementID;
    private  String imgurl="";
    private String Source="";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addborrow);
        intview();
        initinformation();
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
    private void initinformation()
    {
        Intent intent = getIntent();
        PledgementID=intent.getStringExtra("PledgementID");
        RequestParams params = new RequestParams(EditBorrowMen.this);
        params.addFormDataPart("pledgerID",PledgementID);
        params.addFormDataPart("tokenString", token);
        Log.e("YYY",PledgementID+"  "+token+"    "+userid);
        HttpRequest.post(Api.GET_PLEDGER_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                PledgerBean pledgerBean = JSONObject.parseObject(jsonObject.toString(), PledgerBean.class);
                Log.e("PLED", jsonObject.toString() + pledgerBean.getDataList().size());


                if(pledgerBean.getDataList().toString().equals("[]"))
                {}
                else {
                    pledgerName.setText(pledgerBean.getDataList().get(0).getPledgerName());
                    idCardNumber.setText(pledgerBean.getDataList().get(0).getIDCardNumber());
                    contactPhone.setText(pledgerBean.getDataList().get(0).getContactPhone());
                    address.setText(pledgerBean.getDataList().get(0).getAddress());
                    contactName.setText(pledgerBean.getDataList().get(0).getContactName());
                    remak.setText(pledgerBean.getDataList().get(0).getRemak());
//                    pledgerBean.getDataList().get(0).getPledgerImage();
                    imgurl=pledgerBean.getDataList().get(0).getPledgerImage();
                    Log.e("weqweqweqwe", pledgerBean.getDataList().get(0).getPledgerImage()+"3213123123123");
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
    private void intview() {
        Intent intent = getIntent();
        Source=intent.getStringExtra("Source");
        delete = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title_name);
        new1 = (TextView) findViewById(R.id.title_select);
        pledgerName=(EditText)findViewById(R.id.editText);
        idCardNumber=(EditText)findViewById(R.id.editText1);
        contactPhone=(EditText)findViewById(R.id.editText4);
        address=(EditText)findViewById(R.id.editText5);
        contactName=(EditText)findViewById(R.id.editText6);
        remak=(EditText)findViewById(R.id.editText7);
        if(Source.equals("2")){
            pledgerName.setFocusable(false);
            pledgerName.setFocusableInTouchMode(false);
            idCardNumber.setFocusable(false);
            idCardNumber.setFocusableInTouchMode(false);
            contactPhone.setFocusable(false);
            contactPhone.setFocusableInTouchMode(false);
            address.setFocusable(false);
            address.setFocusableInTouchMode(false);
            contactName.setFocusable(false);
            contactName.setFocusableInTouchMode(false);
            remak.setFocusable(false);
            remak.setFocusableInTouchMode(false);
            Toast.makeText(EditBorrowMen.this,"该信息来自于工单不可编辑",Toast.LENGTH_SHORT).show();
        }

        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditBorrowMen.this.finish();
            }
        });
        title.setText("编辑借款人");
            new1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pledgerName1=pledgerName.getText().toString();
                    idCardNumber1=idCardNumber.getText().toString();
                    contactPhone1=contactPhone.getText().toString();
                    address1=address.getText().toString();
                    contactName1=contactName.getText().toString();
                    remak1=remak.getText().toString();
                    if (pledgerName1.equals(""))
                    {
                        Toast.makeText(getApplicationContext(), "姓名必须输入!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(idCardNumber1.length()==18||idCardNumber1.equals(""))
                        {
                            getinformation();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "必须输入有效的身份证号码！", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });
        new1.setText("保存");
    }

    public void getinformation()
    {

       String message= "{" + '"' + "UserID" + '"' + ":" + '"' + userid + '"' + ","
                + '"' + "PledgerName" + '"' + ":" + '"' + pledgerName1 + '"' + ","
               + '"' + "imgurl" + '"' + ":" + '"' + imgurl + '"' + ","
               + '"' + "ID" + '"' + ":" + '"' + PledgementID+ '"' + ","
               + '"' + "IDCardType" + '"' + ":" + '"' +"1"+ '"' + ","
               + '"' + "IDCardNumber" + '"' + ":" + '"' + idCardNumber1 + '"' + ","
               + '"' + "Remark" + '"' + ":" + '"' + remak1 + '"' + ","
                + '"' + "Address" + '"' + ":" + '"' + address1 + '"' + "," + '"' + "ContactName" + '"'
                + ":" + '"' + contactName1 + '"' + "," + '"' + "ContactPhone" + '"'
                + ":" +'"'+ contactPhone1+'"' + "}";
        Log.e("oooooo",message);
        Log.e("oooooo",token);
        RequestParams params = new RequestParams(EditBorrowMen.this);
            params.addFormDataPart("param",message);
        params.addFormDataPart("tokenString", token);
        HttpRequest.post(Api.MODIFY_PLEDGER, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                BaseApiResponse modifyBean = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
                Log.e("dbdPLED", jsonObject.toString() );
                Intent intent = new Intent(EditBorrowMen.this, BorrowDetail.class);
                intent.putExtra("PledgerName",pledgerName1);
                Log.e("UU",pledgerName1);
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
