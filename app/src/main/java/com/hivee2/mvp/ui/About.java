package com.hivee2.mvp.ui;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hivee2.R;
import com.hivee2.mvp.ui.mvpactivity.Homepage;
import com.hivee2.mvp.ui.mvpactivity.LoginActivity;
import com.hivee2.utils.HiveUtil;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;


public class About extends Activity {
	
	private ImageView back;//返回
	private TextView title;//标题
	private TextView phone;//联系方式
	private TextView tel;//紧急联系方式
	private TextView about_versions;//版本号
	private	 TextView about_versions_new;//是否为最新版本
	  protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        setContentView(R.layout.about);

	        //  requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		  about_versions= (TextView) findViewById(R.id.about_versions);
		  about_versions_new= (TextView) findViewById(R.id.about_versions_new);
		  try {
			  about_versions.setText(getVersionName().toString());
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
		  PgyUpdateManager.register(About.this, "com.hivee2.zhou",
				  new UpdateManagerListener() {

					  @Override
					  public void onUpdateAvailable(final String result) {

						  // 将新版本信息封装到AppBean中
						  final AppBean appBean = getAppBeanFromString(result);
						  Log.i("resultresult",result);
						  about_versions_new.setText("版本过低，请更新！");
						  new AlertDialog.Builder(About.this)
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
														  About.this,
														  appBean.getDownloadURL());

											  }
										  }).show();
					  }

					  @Override
					  public void onNoUpdateAvailable() {
//                        Toast.makeText(LoginActivity.this, "已经是最新的版本", Toast.LENGTH_SHORT).show();
					  }
				  });


		  back = (ImageView) findViewById(R.id.imageView2);
			back.setVisibility(View.VISIBLE);
			back.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					About.this.finish();
				}
			});
 			title = (TextView)findViewById(R.id.title_name1);//标题
			title.setText("关于");
	          
			phone = (TextView)findViewById(R.id.about_phone);
			phone.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// 调用系统拨号界面拨打电话
					String number2 = phone.getText().toString().trim(); // 获取电话接听者号码
					if (!number2.equals("")) {
						Uri callUri = Uri.parse("tel:" + number2);
						Intent intent = new Intent(Intent.ACTION_DIAL, callUri);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
					} else {
						Toast.makeText(About.this,
								"接听者号码不能为空,请填写!", Toast.LENGTH_SHORT).show();
					}
				}
			});
//	        tel = (TextView)findViewById(R.id.about_tel);
//	        tel.setOnClickListener(new View.OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					// 调用系统拨号界面拨打电话
//					String number1 = tel.getText().toString().trim(); // 获取电话接听者号码
//					if (!number1.equals("")) {
//						Uri callUri = Uri.parse("tel:" + number1);
//						Intent intent = new Intent(Intent.ACTION_DIAL, callUri);
//						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//						startActivity(intent);
//					} else {
//						Toast.makeText(About.this,
//								"接听者号码不能为空,请填写!", Toast.LENGTH_SHORT).show();
//					}
//				}
//			});

	    }
	private String getVersionName() throws Exception
	{
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(),0);
		String version = packInfo.versionName;
		return version;
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
