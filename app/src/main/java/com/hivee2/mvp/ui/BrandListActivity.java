package com.hivee2.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.SortAdapter;
import com.hivee2.content.Api;
import com.hivee2.mvp.model.bean.CarBrandBean;
import com.hivee2.mvp.presenter.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Lenovo on 2018/3/19.
 */

public class BrandListActivity extends Activity {
    private SideBar sideBar;
    private TextView dialog;
    private ListView sortListView;
    private SharedPreferences sp = null;
    String token;
    private TextView title;
    ImageView back;
    private SortAdapter adapter;
    ArrayList<String> indexString = new ArrayList<>();
    ArrayList<CarBrandBean.BrandListBean> brand_list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brandlist);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sp = getSharedPreferences("hive2", MODE_PRIVATE);
        token = sp.getString("Token", "");
        back= (ImageView) findViewById(R.id.imageView2);
        title= (TextView) findViewById(R.id.title_name1);
        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        initDatas();
        initEvents();
        setAdapter();
    }

    private void initDatas() {
        title.setText("选择品牌");
        sideBar.setTextView(dialog);
        RequestParams params = new RequestParams();
        params.addFormDataPart("token", sp.getString("Token", ""));
        HttpRequest.post(Api.GET_CAR_BRANDLIST, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.i("jsonObject", jsonObject.toString());
                CarBrandBean carBrandBean = JSONObject.parseObject(jsonObject.toString(), CarBrandBean.class);
                brand_list = (ArrayList<CarBrandBean.BrandListBean>) carBrandBean.getBrand_list();
//                brandlist.add("选择品牌");
                for (int i = 0; i < brand_list.size(); i++) {
                    CarBrandBean.BrandListBean brandListBean = brand_list.get(i);
                    String shou=brand_list.get(i).getInitial();
                    if (shou.toUpperCase().matches("[A-Z]")) {
                        if (!indexString.contains(shou)) {
                            indexString.add(shou);
                            Log.i("onSuccess", shou);
                        }
                    }

//                    brandlist.add(brand_list.get(i).getBrand_name());

                }
                Log.i("onsuccess", indexString.toString());
                sideBar.setIndexText(indexString);

                adapter = new SortAdapter(BrandListActivity.this, brand_list);
                sortListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
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
    }
    private void initEvents() {
//设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position );
                }
            }
        });

        //ListView的点击事件
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                mTvTitle.setText(((CarBrandBean.BrandListBean) adapter.getItem(position - 1)).getBrand_name());
//                Toast.makeText(getApplication(), ((CarBrandBean.BrandListBean) adapter.getItem(position)).getBrand_name(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent();

                intent.putExtra("brand_name",((CarBrandBean.BrandListBean) adapter.getItem(position)).getBrand_name()); //将计算的值回传回去
                intent.putExtra("brand_id", ((CarBrandBean.BrandListBean) adapter.getItem(position)).getBrand_id()); //将计算的值回传回去
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(3, intent);

                finish();
            }
        });
    }

    private void setAdapter() {

        Log.i("setAdapter", indexString.toString());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
