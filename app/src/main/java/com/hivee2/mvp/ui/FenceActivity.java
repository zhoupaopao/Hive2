package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.FenceAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.FencelistBean;
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
public class FenceActivity extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";
    public String type1="circle";
    public String inorout="false";
    private RelativeLayout back;
    private FenceAdapter adapter;
    private TextView title;
    private TextView select;
    private ListView listView;
    private Context mContext = null;
    private LinearLayout out;
    private LinearLayout in;
    private ImageView outphoto;
    private ImageView inphoto;
    private TextView outtext;
    private TextView intext;
    private PopupWindow popupWindow;
    private TextView cancel;
    private String longitude1;//人经度
    private String latitude1;//人纬度


    private String queryString="";
    private TextView create;
    private ProgressDialog progressDialog;
    private RadioGroup fencestate;
    private EditText eSearch;
    List<FencelistBean.DataListBean> fenceList = new ArrayList<FencelistBean.DataListBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fence);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");
        init();
        initlisten();
        information();
        set_eSearch_TextChanged();
    }
    public void information()
    {
        RequestParams params2 = new RequestParams(FenceActivity.this);
        params2.addFormDataPart("QueryString",queryString);
        params2.addFormDataPart("UserID", userid);
        params2.addFormDataPart("page",1);
        params2.addFormDataPart("pageSize",10000);
        params2.addFormDataPart("sortName","UpdateTime");
        params2.addFormDataPart("asc", "1");
        params2.addFormDataPart("TokenString", token);
        HttpRequest.post(Api.GET_GEO_FENCE_BY_USERID, params2, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("--11111111------->", jsonObject.toString());
                FencelistBean fencelistBean = JSONObject.parseObject(jsonObject.toString(), FencelistBean.class);
                fenceList = fencelistBean.getDataList();
                adapter = new FenceAdapter(FenceActivity.this, fenceList);
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
                //show  progressdialog
                progressDialog.setMessage("正在获取信息");
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //hide progressdialog
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }
    private void set_eSearch_TextChanged()
    {
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
    public void init()
    {
        back=(RelativeLayout)findViewById(R.id.back);
        title=(TextView)findViewById(R.id.title_name);
        select=(TextView)findViewById(R.id.title_select);
        listView=(ListView)findViewById(R.id.listView5);

        Intent intent=getIntent();
        latitude1=intent.getStringExtra("latitude1");
        longitude1=intent.getStringExtra("longitude1");
        Log.e("WEIDU","-----"+latitude1);
        progressDialog=new ProgressDialog(this);

    }
    public void initlisten()
    {
        mContext = this;
      information();
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
        title.setText("围栏列表");
        select.setText("添加围栏");
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popView = LayoutInflater.from(mContext).inflate(
                        R.layout.pop_window1, null);
                View rootView = findViewById(R.id.root_main1); // 當前頁面的根佈局
                popupWindow = new PopupWindow(popView,
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                // 设置弹出动画
//                popupWindow.setAnimationStyle(R.style.AnimationFadeBottom);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                // 顯示在根佈局的底部
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0,
                        0);
                cancel = (TextView) popView.findViewById(R.id.textView5);
                create=(TextView) popView.findViewById(R.id.textView6);
                in=(LinearLayout)popView.findViewById(R.id.in);
                out=(LinearLayout)popView.findViewById(R.id.out);
                intext=(TextView)popView.findViewById(R.id.textView58);
                outtext=(TextView)popView.findViewById(R.id.textView57);
                inphoto=(ImageView)popView.findViewById(R.id.imageView14);
                outphoto=(ImageView)popView.findViewById(R.id.imageView15);
                fencestate=(RadioGroup)popView.findViewById(R.id.group);
                outtext.setTextColor(getResources().getColor(R.color.title));
                outphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                intext.setTextColor(getResources().getColor(R.color.black));
                inphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fence2));
                fencestate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.radioButton:
                                type1="circle";
                                break;
                            case R.id.radioButton1:
                                type1="polygon";
                                break;
                            case R.id.radioButton2:
                                type1="area";
                                break;
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        Intent intent = new Intent(FenceActivity.this, AddFence.class);
                        intent.putExtra("longitude1", longitude1);
                        intent.putExtra("latitude1",latitude1);
                        intent.putExtra("type",type1);
                        intent.putExtra("inorout",inorout);
                        intent.putExtra("select","0");
                        startActivity(intent);
                    }
                });
                in.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     intext.setTextColor(getResources().getColor(R.color.title));
                     inphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                     outtext.setTextColor(getResources().getColor(R.color.black));
                     outphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fence2));
                        inorout="true";
                    }
                });
                out.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        outtext.setTextColor(getResources().getColor(R.color.title));
                        outphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fencecar1));
                        intext.setTextColor(getResources().getColor(R.color.black));
                        inphoto.setImageDrawable(getResources().getDrawable(R.mipmap.fence2));
                        inorout="false";
                    }
                });

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                System.out.println("position" + position);
                // TODAuto-generated method stub
                Intent intent = new Intent(FenceActivity.this, FenceBinding.class);
                intent.putExtra("GEOID",fenceList.get(position).getID());
                intent.putExtra("token", token);
                intent.putExtra("ParameterSets",fenceList.get(position).getParameterSets());
                Log.e("pppp","-----"+fenceList.get(position).getParameterSets());
                intent.putExtra("area",fenceList.get(position).getAreaType());
                intent.putExtra("inorout", fenceList.get(position).isInOrOut());
                intent.putExtra("adress",fenceList.get(position).getAreaName());
                startActivityForResult(intent,0);
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
        initlisten();
        HiveUtil ut=new HiveUtil();
        ut.onResumePage(this,this.getClass().getCanonicalName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HiveUtil ut=new HiveUtil();
        ut.onPausePage(this,this.getClass().getCanonicalName());
    }
}
