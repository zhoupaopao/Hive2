package com.hivee2.mvp.ui;

import android.content.BroadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 狄飞 on 2016/10/13.
 */
public class MyJPushReceiver extends BroadcastReceiver   {


    private static String TAG = "pushreceiver";
   private String title="";
    private  String message="";
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";
    public String customer="";
    public String parentid="";
    public String usename="";
    public String password="";
    public String check1="";
    public String check2="";
    public String check3="";
    private String login_token;
    public String display = "";



    //    private static String printBundle(Bundle bundle) {
//        StringBuilder sb = new StringBuilder();
//        for (String key : bundle.keySet()) {
//            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
//                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
//            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
//                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
//            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
//                if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
//                    Log.i(TAG, "This message has no Extra data");
//                    continue;
//                }
//
//                try {
//                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
//                    Iterator<String> it =  json.keys();
//
//                    Log.e("tiantian",bundle.getString(JPushInterface.EXTRA_EXTRA)+"");
//                    while (it.hasNext()) {
//                        String myKey = it.next().toString();
//                        sb.append("\nkey:" + key + ", value: [" +
//                                myKey + " - " +json.optString(myKey) + "]");
//                    }
//                } catch (JSONException e) {
//                    Log.e(TAG, "Get message extra JSON error!");
//                }
//
//            } else {
//                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
//            }
//        }
//        return sb.toString();
//    }
//private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
//    @Override
//    public void gotResult(int code, String alias, Set<String> tags) {
//        String logs ;
//        switch (code) {
//            case 0:
//                logs = "Set tag and alias success";
//                Log.i(TAG, logs);
//                // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//                break;
//            case 6002:
//                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                Log.i(TAG, logs);
//                // 延迟 60 秒来调用 Handler 设置别名
//                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
//                break;
//            default:
//                logs = "Failed with errorCode = " + code;
//                Log.e(TAG, logs);
//        }
//        ExampleUtil.showToast(logs, getApplicationContext());
//    }
//};
//private static final int MSG_SET_ALIAS = 1001;
//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case MSG_SET_ALIAS:
//                    Log.d(TAG, "Set alias in handler.");
//                    // 调用 JPush 接口来设置别名。
//                    JPushInterface.setAliasAndTags(getApplicationContext(),
//                            (String) msg.obj,
//                            null,
//                            mAliasCallback);
//                    break;
//                default:
//                    Log.i(TAG, "Unhandled msg - " + msg.what);
//            }
//        }

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        sp=context.getSharedPreferences("hive2",Context.MODE_PRIVATE);
        userid= sp.getString("usename", "");
//        printBundle(bundle);
        Log.d(TAG, "onReceive - " + intent.getAction());
        JPushInterface.setAlias(context, userid,
                new TagAliasCallback() {

                    @Override
                    public void gotResult(int responseCode,
                                          String alias, Set<String> tags) {
                        Log.e("responseCode", responseCode + "time");
                        if (responseCode == 0) {
                            Log.e("jieguo", "成功");

                        } else if (responseCode == 6002) {
//                            mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000* 60);
                        }
                    }
                });
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String title=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            Log.e("jieguo","收到了通知1");
            Log.e("xiaoxi","收到了自定义消息1消息内容是:"+ content);
            Log.e("xiaoxi","收到了自定义消息2消息内容是:"+ extra);
            System.out.println("收到了自定义消息@@消息内容是:"+ content);
            Log.e("xiaoxi","收到了自定义消息2消息内容是:"+ title);
            System.out.println("收到了自定义消息@@消息extra是:"+ extra);

            //**************解析推送过来的json数据并存放到集合中 begin******************
            //修改的是初始安装崩
            if(extra==null){

            }else{
                Map<String, Object> map = new HashMap<String, Object>();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(extra);
                    String type = jsonObject.getString("type");
                    map.put("type11", type);
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                map.put("content", content);
                //获取接收到推送时的系统时间
                Calendar rightNow = Calendar.getInstance();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                String date = fmt.format(rightNow.getTime());
                map.put("date", date);
            }


//            MyApp.data.add(map);
            //**************解析推送过来的json数据并存放到集合中 end******************
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            // 自定义消息不会展示在通知栏，完全要开发者写代码去处理
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String title=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            Log.e("jieguo","到底是啥");
            Log.e("jieguo","收到了通知1");
            Log.e("xiaoxi","收到了自定义消息1消息内容是:"+ content);
            Log.e("xiaoxi", "收到了自定义消息2消息内容是:" + extra);
            System.out.println("收到了自定义消息@@消息内容是:" + content);
            Log.e("xiaoxi", "收到了自定义消息2消息内容是:" + title);
            System.out.println("收到了自定义消息@@消息extra是:" + extra);



            //**************解析推送过来的json数据并存放到集合中 begin******************
            Map<String, Object> map = new HashMap<String, Object>();
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(extra);
                String type = jsonObject.getString("type");
                map.put("type11", type);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map.put("content", content);
            //获取接收到推送时的系统时间
            Calendar rightNow = Calendar.getInstance();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String date = fmt.format(rightNow.getTime());
            map.put("date", date);
//            MyApp.data.add(map);
            //**************解析推送过来的json数据并存放到集合中 end******************


        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extra = bundle.getString(JPushInterface.EXTRA_NOTI_TYPE);
            String title=bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
            String title2 = bundle.getString(JPushInterface.EXTRA_TITLE);

            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Notification notification=


            Log.e("xiaoxi", "收到了自定义消息1消息内容是:" + content);
            Log.e("xiaoxi", "收到了自定义消息2消息内容是:" + extras);
            Log.e("xiaoxi", "收到了自定义消息3消息内容是:" + title);
            message=extras;
            Log.e("happy",message);
            SharedPreferences.Editor editor=sp.edit();
                editor.putString("message",message);
               editor.commit();

            System.out.println("收到了通知");
            Log.e("jieguo","收到了通知");
            // 在这里可以做些统计，或者做些其他工作
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            System.out.println("用户点击打开了通知");
            // 在这里可以自己写代码去定义用户点击后的行为
            Log.e("xiaoxi", "收到了自定义消息3消息内容是:" + title+message);
            Intent i = new Intent(context, NoticeActivity.class); // 自定义打开的界面

            i.putExtra("message",message);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

}
