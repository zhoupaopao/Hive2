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
import com.hivee2.mvp.model.bean.AddPledgementBean;
import com.hivee2.utils.HiveUtil;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/10.
 */
public class BorrowAdd extends Activity implements HttpCycleContext {
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
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addborrow);
        intview();
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
    private void intview() {
        delete = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title_name);
        new1 = (TextView) findViewById(R.id.title_select);
        pledgerName=(EditText)findViewById(R.id.editText);
        idCardNumber=(EditText)findViewById(R.id.editText1);
        contactPhone=(EditText)findViewById(R.id.editText4);
        address=(EditText)findViewById(R.id.editText5);
        contactName=(EditText)findViewById(R.id.editText6);
        remak=(EditText)findViewById(R.id.editText7);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");
        progressDialog=new ProgressDialog(this);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BorrowAdd.this.finish();
            }
        });
        title.setText("添加借款人");
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
               + '"' + "PledgerImage" + '"' + ":" + '"'  + '"' + ","
               + '"' + "IDCardType" + '"' + ":" + '"' + '"' + ","
               + '"' + "IDCardNumber" + '"' + ":" + '"' + idCardNumber1 + '"' + ","
               + '"' + "Remak" + '"' + ":" + '"' + remak1 + '"' + ","
                + '"' + "Address" + '"' + ":" + '"' + address1 + '"' + "," + '"' + "ContactName" + '"'
                + ":" + '"' + contactName1 + '"' + "," + '"' + "ContactPhone" + '"'
                + ":" +'"'+ contactPhone1+'"' + "}";
        Log.e("88888",message);
        RequestParams params = new RequestParams(BorrowAdd.this);
            params.addFormDataPart("pledgement",message);
        params.addFormDataPart("tokenString", token);
        HttpRequest.post(Api.ADD_PLEDGEMENT, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                AddPledgementBean addPledgementBean = JSONObject.parseObject(jsonObject.toString(), AddPledgementBean.class);
                Intent intent = new Intent(BorrowAdd.this, AddcarActivity.class);
                intent.putExtra("pledgerID",addPledgementBean.getPledgementID());
                intent.putExtra("pledgername",pledgerName1);
                startActivityForResult(intent, 1);

                Log.e("88888",addPledgementBean.getPledgementID()+"tttt");
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
                if(progressDialog.isShowing()&&progressDialog!=null){
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
