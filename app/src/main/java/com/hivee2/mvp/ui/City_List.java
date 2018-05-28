package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.SimpleTreeAdapter2;
import com.hivee2.adapter.TreeListViewAdapter2;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.FileBean2;
import com.hivee2.mvp.model.bean.ProvinceBean;
import com.hivee2.utils.HiveUtil;
import com.zhy.tree.Node2;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/9/14.
 */
public class City_List extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private List<FileBean2> mDatas = new ArrayList<FileBean2>();
    private ListView mTree;
    private TreeListViewAdapter2 mAdapter;
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    private ImageView back;
    private TextView title;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);
        init();
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        initDatas();
    }
    public void init()
    {
        back=(ImageView)findViewById(R.id.imageView2);
        title= (TextView) findViewById(R.id.title_name1);
        title.setText("城市列表");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog=new ProgressDialog(this);

    }
    void showDialog(final String messageStr, final String name, final int number ,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                City_List.this);

        builder.setMessage(messageStr);
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.putExtra("NAME", name);
                intent.putExtra("NUMBER", number + "");
                setResult(RESULT_OK, intent);
                finish();

//                Constant.atychildaccoumt = true;

            }
        });
        builder.setNegativeButton("否", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void initDatas() {
        RequestParams params = new RequestParams(City_List.this);
        params.addFormDataPart("TokenString", token);
        //  地址  参数  回调函数

        mTree = (ListView) findViewById(R.id.ac_listview);
        Log.e("-1222-------->", Api.GET_PROVINCE_CITY.toString());
        HttpRequest.post(Api.GET_PROVINCE_CITY, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                ProvinceBean provinceBean = JSONObject.parseObject(jsonObject.toString(), ProvinceBean.class);

                for (int i=1;i<=provinceBean.getProvinceList().size();i++)
                {
                    mDatas.add(new FileBean2(i,0,provinceBean.getProvinceList().get(i-1).getProvince(),
                            provinceBean.getProvinceList().get(i-1).getProvinceID()));
                }
                for (int i=1;i<=provinceBean.getProvinceList().size();i++)
                {
                    for(int j=0;j<provinceBean.getProvinceList().get(i-1).getCityList().size();j++)
                    mDatas.add(new FileBean2(mDatas.size()+1,i,provinceBean.getProvinceList().get(i-1).getCityList().get(j).getCity(),
                            provinceBean.getProvinceList().get(i-1).getCityList().get(j).getCityID()));
                }
                try {
                    mAdapter = new SimpleTreeAdapter2<FileBean2>(mTree, City_List.this, mDatas, 10);
                    mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter2.OnTreeNodeClickListener() {
                        @Override
                        public void onClick(Node2 node, int position) {
                            showDialog("是否选择该地区", node.getName(),node.getNumber(), 0);

                        }

                    });

                    mTree.setAdapter(mAdapter);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                mTree.setAdapter(mAdapter);
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
