package com.hivee2.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.ui.AddBendActivity;
import com.hivee2.mvp.ui.BorrowDetail;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by Lenovo on 2018/5/8.
 */

public class ListViewAddDelAdapter2 extends BaseAdapter {
    private BorrowDetail context;
    private List<String > datas = new ArrayList<String>();
    private SharedPreferences  sp;
    private ProgressDialog progressDialog;

    public ListViewAddDelAdapter2(BorrowDetail context) {
        this.context = context;
    }

    /** 添加item数据 */
    public void addData(String text) {
        if (datas != null)
            datas.add(text);
//            datas.add(text);// 添加数据
    }

    /** 移除item数据 */
    public void delData(int pos) {
        if (datas != null && datas.size() > 0)
            datas.remove(pos);// 移除最后一条数据
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (datas == null)
            return 0;
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public List getDta(){
        return datas;
    }

    /**
     * listview要判断item的位置，第一条，最后一条和中间的item是不一样的。
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        // 判断datas数据条数是否>1

        convertView = View.inflate(context, R.layout.bend_item, null);
//        String text = datas.get(position).getSbm();
//        String text1 = datas.get(position).getSblx();
//        String text2=datas.get(position).getAzwz();
        String text = datas.get(position);
        final String[]aa=text.split(",");
        // 设置文本
        ((RelativeLayout) convertView.findViewById(R.id.rl_azwz)).setVisibility(View.GONE);
        ((RelativeLayout) convertView.findViewById(R.id.rl_sbh)).setVisibility(View.GONE);
        ((TextView) convertView.findViewById(R.id.sbm)).setText(aa[0]);
        ((TextView) convertView.findViewById(R.id.sblx)).setText(aa[1]);



        ((TextView) convertView.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示");
                builder.setMessage("你是否确定要解绑该设备？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sp=context.getSharedPreferences("hive2", 0);
                        RequestParams params = new RequestParams(context);
                        params.addFormDataPart("carDeviceID", aa[2]);
                        params.addFormDataPart("tokenString",sp.getString(Constant.sp_token, ""));
                        HttpRequest.post(Api.SETCAR_UNBIND_DEVICE, params, new JsonHttpRequestCallback() {
                            @Override
                            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                                super.onSuccess(headers, jsonObject);
                                BaseApiResponse cardevice = JSONObject.parseObject(jsonObject.toString(), BaseApiResponse.class);
                                if (cardevice.getResult() == 0) {
                                    Toast.makeText(context, "删除成功！", Toast.LENGTH_SHORT).show();
                                    context.del(position);
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
                                progressDialog=new ProgressDialog(context);
                                progressDialog.setMessage("正在刷新");
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
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(BorrowDetail.this, "对不起，该辆车绑定了设备，请解绑设备后再删除", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();

            }
        });
        return convertView;
    }

}
