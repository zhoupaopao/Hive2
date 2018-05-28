package com.hivee2.mvp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.ListViewAddDelAdapter;
import com.hivee2.adapter.UnbindAdapter2;
import com.hivee2.adapter.UnbindAdapter3;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BendBean;
import com.hivee2.mvp.model.bean.UnbindBean;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Lenovo on 2018/5/8.
 */

public class AddBendListDetail extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private Button add;
    private LinearLayout bend_list;
    private Context mContext;
    private ListView lv;
    private ListViewAddDelAdapter adapter;
    private String text;
    private int count=0;
    private TextView title;
    private TextView cancel1;
    private TextView sure;
    private TextView chooseorder;
    private PopupWindow popupWindow;
    private TextView cancel;
    private TextView save2;
    private EditText eSearch;
    private ListView listView;
    private String queryString="";
    private String  userid;
    private  String token;
    private SharedPreferences sp=null;// 存放用户信息
    private UnbindAdapter3 adapter2;
    Intent getintent=getIntent();
    ArrayList<String> oldlist=new ArrayList<>();
    List<UnbindBean.DataListBean> unbindList  = new ArrayList<UnbindBean.DataListBean>();
    UnbindBean.DataListBean dataListBean=new UnbindBean.DataListBean();
    BendBean bendBean=new BendBean();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbend_listdetail);
        mContext=this;

        initView();
        initData();
        initListener();
    }

    private void initView() {
        oldlist=getIntent().getStringArrayListExtra("cutList");
        add= (Button) findViewById(R.id.add);
        bend_list= (LinearLayout) findViewById(R.id.bend_list);
        cancel1= (TextView) findViewById(R.id.cancel);
        title= (TextView) findViewById(R.id.title_name);
        sure= (TextView) findViewById(R.id.sure);
        chooseorder= (TextView) findViewById(R.id.chooseorder);
        lv= (ListView) findViewById(R.id.lv);
        adapter = new ListViewAddDelAdapter(AddBendListDetail.this);
        // listview绑定adapter适配器
        lv.setAdapter(adapter);
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");

    }

    private void initData() {

    }

    private void initListener() {
        add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
//                TextView tv=new TextView(this);
//                tv.setText("示例文本框");
//                tv.setId(1);//设置ID，可有可无，也可以在R文件中添加字符串，然后在这里使用引用的方式使用
//                linear.addView(tv);
//                bend_list.addView(addView1());
//                View hiddenView = getLayoutInflater().inflate( R.layout.block_gym_album_list_item, bend_list, false ) ; //hiddenView是隐藏的View，
//                bend_list.addView ( hiddenView ) ;
                if(chooseorder.getText().toString().trim().equals("请选择设备")){
                    Toast.makeText(AddBendListDetail.this,"请选择设备后添加",Toast.LENGTH_SHORT).show();
                }else{
                    text = chooseorder.getText().toString();
                    List<String> curlist=adapter.getDta();
                    boolean candd=true;
                    if(curlist.size()==0){
                        for(int j=0;j<oldlist.size();j++){
                            Log.i("onClick: ", oldlist.toString());
                            String[] curst1= oldlist.get(j).split(",");
                            if(dataListBean.getInternalNum().equals(curst1[0])){
                                Toast.makeText(AddBendListDetail.this,"设备已添加",Toast.LENGTH_SHORT).show();
                                candd=false;
                            }
                            if(j==oldlist.size()-1&&candd){
                                bendBean.setSbm(dataListBean.getInternalNum());
                                bendBean.setSblx(dataListBean.getModel());
                                bendBean.setAzwz("请选择安装位置");
                                String asdasd=dataListBean.getInternalNum()+","+dataListBean.getModel()+","+"请选择安装位置"+","+dataListBean.getDeviceID();
                                adapter.addData(asdasd);
                                adapter.notifyDataSetChanged();
                                count++;
                            }
                        }
                        if(oldlist.size()==0){
                            bendBean.setSbm(dataListBean.getInternalNum());
                            bendBean.setSblx(dataListBean.getModel());
                            bendBean.setAzwz("请选择安装位置");
                            String asdasd=dataListBean.getInternalNum()+","+dataListBean.getModel()+","+"请选择安装位置"+","+dataListBean.getDeviceID();
                            adapter.addData(asdasd);
                            adapter.notifyDataSetChanged();
                            count++;
                        }

                    }else{
                        for(int i=0;i<curlist.size();i++){
                            String[] curst=curlist.get(i).split(",");
                            Log.i("onClick: ", dataListBean.getInternalNum()+"|"+curst[0]);
                            if(dataListBean.getInternalNum().equals(curst[0])){
                                Toast.makeText(AddBendListDetail.this,"设备已添加",Toast.LENGTH_SHORT).show();
                                candd=false;
                            }
                            Log.i("onClick: ", oldlist.toString()+"123");
                            for(int j=0;j<oldlist.size();j++){
                                Log.i("onClick: ", oldlist.toString());
                                String[] curst1= oldlist.get(j).split(",");
                                if(curst[0].equals(curst1[0])){
                                    Toast.makeText(AddBendListDetail.this,"设备已添加",Toast.LENGTH_SHORT).show();
                                    candd=false;
                                }
                            }
                            if(i==curlist.size()-1&&candd){
//                            Log.i("onClick: ", curlist.size()+"");
                                //是循环的最后一位，同时也没有重复的设备，可以添加
                                bendBean.setSbm(dataListBean.getInternalNum());
                                bendBean.setSblx(dataListBean.getModel());
                                bendBean.setAzwz("请选择安装位置");
                                String asdasd=dataListBean.getInternalNum()+","+dataListBean.getModel()+","+"请选择安装位置"+","+dataListBean.getDeviceID();
                                adapter.addData(asdasd);
                                adapter.notifyDataSetChanged();
                                count++;
                                i=i+1;
                            }
                        }
                    }
                }



            }
        });
        cancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("添加设备");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String>BendDatas=adapter.getDta();
                if(BendDatas.size()==0){
                    finish();
                }else{
                    boolean cansure=true;
                    //查看设备中是否有安装位置没有设置的
                    for(int i=0;i<BendDatas.size();i++){
                        String[]aztt=BendDatas.get(i).split(",");
                        if(aztt[2].equals("请选择安装位置")||aztt[2].trim().equals("")){
                            //没有设置
                            cansure=false;
                            Toast.makeText(AddBendListDetail.this,"请选择安装位置",Toast.LENGTH_SHORT).show();
                        }
                        if(cansure&&i==BendDatas.size()-1){
                            Intent intent=new Intent();
                            intent.putStringArrayListExtra("sb_list", (ArrayList<String>) BendDatas);
                            AddBendListDetail.this.setResult(3,intent);
                            finish();
                        }
                    }
                }
//                Log.i("onClick: ", BendDatas.get(0).toString());


            }
        });
        chooseorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popView = LayoutInflater.from(mContext).inflate(
                        R.layout.binddevice_window1, null);
                View rootView = findViewById(R.id.root_main5); // 當前頁面的根佈局
                popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出动画
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setFocusable(true);
                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);// 顯示在根佈局的底部
                popupWindow.showAtLocation(rootView, Gravity.BOTTOM | Gravity.LEFT, 0, 0);

                listView=(ListView)popView.findViewById(R.id.listView8);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Log.i("onItemClick: ", i+" ");
                        popupWindow.dismiss();
                        dataListBean=unbindList.get(i);
                        chooseorder.setText(unbindList.get(i).getInternalNum());

                    }
                });
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

//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                    myhandler.post(eChanged);

                            queryString = eSearch.getText().toString();
                            RequestParams params = new RequestParams(AddBendListDetail.this);
                            params.addFormDataPart("userID", userid);
                            params.addFormDataPart("onLineType","0");
                            params.addFormDataPart("isBindCar","2");
                            params.addFormDataPart("queryString",queryString);
                            params.addFormDataPart("page", 1);
                            params.addFormDataPart("pageSize", 10000);
                            params.addFormDataPart("sortName", "");
                            params.addFormDataPart("asc","asc");
                            params.addFormDataPart("TokenString", token);

                            HttpRequest.post(Api.GET_DEVICE_BY_USERID, params, new JsonHttpRequestCallback() {
                                @Override
                                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                    super.onSuccess(headers, jsonObject);
                                    Log.e("--ttt------->", jsonObject.toString());
                                    UnbindBean unbindBean = JSONObject.parseObject(jsonObject.toString(), UnbindBean.class);
                                    unbindBean.getDataList();
                                    unbindList = unbindBean.getDataList();
                                    adapter2 = new UnbindAdapter3(AddBendListDetail.this, unbindList);
////                        adapter2.notifyDataSetChanged();
                                    listView.setAdapter(adapter2);
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
                RequestParams params = new RequestParams(AddBendListDetail.this);
                params.addFormDataPart("userID", userid);
                params.addFormDataPart("onLineType","0");
                params.addFormDataPart("isBindCar","2");
                params.addFormDataPart("queryString",queryString);
                params.addFormDataPart("page", 1);
                params.addFormDataPart("pageSize", 10000);
                params.addFormDataPart("sortName", "");
                params.addFormDataPart("asc","asc");
                params.addFormDataPart("TokenString", token);
                HttpRequest.post(Api.GET_DEVICE_BY_USERID, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("--ttt------->", jsonObject.toString());
                        UnbindBean unbindBean = JSONObject.parseObject(jsonObject.toString(), UnbindBean.class);
                        unbindBean.getDataList();
                        unbindList = unbindBean.getDataList();
                        adapter2 = new UnbindAdapter3(AddBendListDetail.this, unbindList);
////                        adapter2.notifyDataSetChanged();
                        listView.setAdapter(adapter2);
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
    }
    /** 移除item */
    public void del(int pos) {
        adapter.delData(pos);
        adapter.notifyDataSetChanged();
        // 判断是否>0

    }
    private View addView1() {
        // TODO 动态添加布局(xml方式)
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);       //LayoutInflater inflater1=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//      LayoutInflater inflater2 = getLayoutInflater();
        LayoutInflater inflater3 = LayoutInflater.from(mContext);
        View view = inflater3.inflate(R.layout.block_gym_album_list_item, null);
        view.setLayoutParams(lp);
        return view;
    }
    @Override
    public String getHttpTaskKey() {
        return null;
    }
}
