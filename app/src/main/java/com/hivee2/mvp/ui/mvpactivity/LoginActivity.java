package com.hivee2.mvp.ui.mvpactivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hivee2.R;
import com.hivee2.content.Constant;
import com.hivee2.mvp.basemvp.BaseMvpActivity;
import com.hivee2.mvp.model.bean.LoginBean;
import com.hivee2.mvp.presenter.UserLoginPresenter;
import com.hivee2.mvp.ui.SysApplication;
import com.hivee2.mvp.ui.view.IUserLoginView;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import java.util.HashSet;
import java.util.Set;

import cn.jiguang.analytics.android.api.BrowseEvent;
import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jiguang.analytics.android.api.RegisterEvent;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class LoginActivity extends BaseMvpActivity<IUserLoginView,UserLoginPresenter> implements IUserLoginView{
    private EditText username;// 登陆账号
    private EditText password;// 密码
    private TextView loginbtn;// 登录按钮
    private CheckBox rememberPwd;
    private CheckBox autoLogin;
    private SharedPreferences sp1=null;
    public String check1;
    public String check2;
    public String check3;
    private static final int BAIDU_READ_PHONE_STATE =100;
    public String user="";
    public String message="";
    public String passName="";
    public String display="";
    private ProgressDialog progressDialog;
    private SharedPreferences sp=null;// 存放用户信息
    private boolean autologinflag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BrowseEvent bEvent = new BrowseEvent("browse_id","深圳热点新闻","news",30);
        bEvent.addKeyValue("key1","value1").addKeyValue("key2","value2");
        if (Build.VERSION.SDK_INT>=23){
            showContacts();
        }else{
            initView();
            initListener();
        }
//        initView();
//        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("onResume: ", "1 ");
        JAnalyticsInterface.onPageStart(this,this.getClass().getCanonicalName());
        RegisterEvent rEvent = new RegisterEvent("sina",true);
        rEvent.addKeyValue("key1","value1").addKeyValue("key2","value2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("onPause: ", "1 ");
        JAnalyticsInterface.onPageEnd(this,this.getClass().getCanonicalName());
    }

    public void showContacts(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("哪个问题", "1");
        }
        if( ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("哪个问题", "2");
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("哪个问题", "3");
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("哪个问题", "4");
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("哪个问题", "5");
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            Log.i("哪个问题", "6");
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(getApplicationContext(),"没有权限,请手动开启定位权限",Toast.LENGTH_SHORT).show();
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(LoginActivity.this,new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, BAIDU_READ_PHONE_STATE);

        }else{
            initView();
            initListener();
        }
    }


    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAIDU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    initView();
                    initListener();
                } else {
                    // 没有获取到权限，做特殊处理
//                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public UserLoginPresenter initPresenter() {
        return new UserLoginPresenter();
    }

    void initView(){


        String aa="a";
        String a="a";
        JPushInterface.setAliasAndTags(this, "test", new HashSet<String>(), new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("Homepage", String.valueOf(i));
            }
        });

        JPushInterface.setAlias(this, "test", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.d("Homepage", String.valueOf(i));
            }
        });


        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
         check1= sp.getString("check1", "");
         check2=sp.getString("check2", "");
         check3=sp.getString("check3","");
         user=sp.getString("usename","");
         message=sp.getString("message","");
         display=sp.getString(Constant.sp_display,"11111111");

         passName=sp.getString("password","");
//        sp1=this.getSharedPreferences("1hive2",MODE_PRIVATE);
        username=(EditText)findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        rememberPwd = (CheckBox) findViewById(R.id.rememberpwd);
        autoLogin = (CheckBox) findViewById(R.id.autologin);
        loginbtn = (TextView) findViewById(R.id.loginbtn);
        username.setText(user);
        password.setText(passName);
        progressDialog=new ProgressDialog(this);
    }

   public void initListener(){
       Intent intent = getIntent();
       autologinflag = intent.getBooleanExtra("autologinflag", false);
       if (autologinflag == true) {
//           SysApplication.getInstance().exit();
       }
       if(check1.toString().equals("true")) {
           rememberPwd.setChecked(true);
       }
       else {
           rememberPwd.setChecked(false);
       }
       if(check2.toString().equals("true"))
       {
           Log.i("autoLogin", "true ");
           autoLogin.setChecked(true);
           if(check3.toString().equals("true")){
               //不是自动登录，直接提示升级
               Log.i("autoLogin", "true ");
               PgyUpdateManager.register(LoginActivity.this, "com.hivee2.zhou",
                       new UpdateManagerListener() {

                           @Override
                           public void onUpdateAvailable(final String result) {

                               // 将新版本信息封装到AppBean中
                               final AppBean appBean = getAppBeanFromString(result);
                               Log.i("resultresult",result);
                               new AlertDialog.Builder(LoginActivity.this)
                                       .setTitle("更新")
                                       .setMessage("系统检测到您的版本过低，请更新")
                                       .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                                           @Override

                                           public void onClick(DialogInterface dialog, int which) {

                                           }

                                       })
                                       .setNegativeButton(
                                               "确定",
                                               new DialogInterface.OnClickListener() {

                                                   @Override
                                                   public void onClick(
                                                           DialogInterface dialog,
                                                           int which) {
                                                       startDownloadTask(
                                                               LoginActivity.this,
                                                               appBean.getDownloadURL());

                                                   }
                                               }).show();
                           }

                           @Override
                           public void onNoUpdateAvailable() {
//                        Toast.makeText(LoginActivity.this, "已经是最新的版本", Toast.LENGTH_SHORT).show();
                           }
                       });
           }
           else
           {
               Log.i("mPresenter", "initListener: ");
               mPresenter.login();}
       }
       else {
           Log.i("autoLogin", "false ");
           PgyUpdateManager.register(LoginActivity.this, "com.hivee2.zhou",
                   new UpdateManagerListener() {

                       @Override
                       public void onUpdateAvailable(final String result) {

                           // 将新版本信息封装到AppBean中
                           final AppBean appBean = getAppBeanFromString(result);
                           Log.i("resultresult",result);
                           new AlertDialog.Builder(LoginActivity.this)
                                   .setTitle("更新")
                                   .setMessage("系统检测到您的版本过低，请更新")
                                   .setPositiveButton("取消", new DialogInterface.OnClickListener() {

                                       @Override

                                       public void onClick(DialogInterface dialog, int which) {

                                       }

                                   })
                                   .setNegativeButton(
                                           "确定",
                                           new DialogInterface.OnClickListener() {

                                               @Override
                                               public void onClick(
                                                       DialogInterface dialog,
                                                       int which) {
                                                   startDownloadTask(
                                                           LoginActivity.this,
                                                           appBean.getDownloadURL());

                                               }
                                           }).show();
                       }

                       @Override
                       public void onNoUpdateAvailable() {
//                        Toast.makeText(LoginActivity.this, "已经是最新的版本", Toast.LENGTH_SHORT).show();
                       }
                   });
           autoLogin.setChecked(false);
       }

           rememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

               @Override
               public void onCheckedChanged(CompoundButton buttonView,
                                            boolean isChecked) {
                   // TODO Auto-generated method stub
                   if (!rememberPwd.isChecked()) {
                       autoLogin.setChecked(false);
                   }
               }
           });

       autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

           @Override
           public void onCheckedChanged(CompoundButton buttonView,
                                        boolean isChecked) {
               // TODO Auto-generated method stub
               if (autoLogin.isChecked()) {
                   rememberPwd.setChecked(true);
               }
           }
       });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.login();
            }
        });


    }

    @Override
    public String getUserName() {
        return username.getText().toString();
    }
    public String getMessage(){return message;}

    @Override
    public String getPassword() {
        return password.getText().toString();
    }
    public String getDisplay() {
        return display;
    }
    @Override

    public Boolean getcheck1()
    {
        return rememberPwd.isChecked();
    }
    public Boolean getcheck2()
    {
        return autoLogin.isChecked();
    }

    @Override

    public void showLoading() {

        progressDialog.setMessage("正在登录");
        progressDialog.show();

    }

    @Override
    public void hideLoading() {
        if(progressDialog.isShowing()&&progressDialog!=null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showToast(String toastStr) {
        Toast.makeText(this, toastStr,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toMainActivity(LoginBean user) {
        JPushInterface.setAlias(this, username.getText().toString(),
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
        Intent intent =new Intent(LoginActivity.this,Homepage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("userBean",user);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    public void showFailedError() {

    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return sp;
    }


    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            SysApplication.getInstance().exit();
//                System.exit(0);

        }
        return true;
    }
}
