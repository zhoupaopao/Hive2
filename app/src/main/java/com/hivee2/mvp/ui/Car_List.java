package com.hivee2.mvp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.CarListAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.DeviceBean;
import com.hivee2.mvp.model.bean.Group;
import com.hivee2.mvp.ui.base.BaseActivity;
import com.hivee2.utils.HiveUtil;
import com.hivee2.widget.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

public class Car_List extends BaseActivity implements HttpCycleContext {


	private String result="";
	private RelativeLayout back;// 返回
	private TextView title;// 标题
	private TextView all_list;// 全部
	private TextView on_list;// 在线
	private TextView off_list;// 离线
	private int flag=0;//标识当前选中的是 全部0  在线1 离线2
	private TextView select;//筛选
	private EditText car_search;
	private LinearLayout requestFocuslinear;//抢焦点用
	private PullToRefreshLayout pullToRefreshLayout;

	private PopupWindow mPopupWindowDialog;// popupwindow
	private SharedPreferences sp = null; // 存放用户信息
	private ProgressDialog progressDialog;
	private DeviceBean deviceBean;

	private ExpandableListView carListView;
	private CarListAdapter carListAdapter;
	Map<String, ArrayList<DeviceBean.CarListBean>> map = null;
	private ArrayList<Group> group = new ArrayList<Group>();
	private ArrayList<DeviceBean.CarListBean> carList;





	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.car_list);

		sp=this.getSharedPreferences("hive2",MODE_PRIVATE);
		initPupwindow();
		initView();
		initListener();
		getCarListBean(getRequestParams(car_search.getText().toString(),sp.getInt(Constant.sp_page, 1) + 0));//从网络获取数据
		//



	}


	void initView(){
		all_list = (TextView) findViewById(R.id.all_list);
		on_list = (TextView) findViewById(R.id.on_list);
		off_list = (TextView) findViewById(R.id.off_list);
		flag = 0;
		all_list.setTextColor(Color.BLACK);
		all_list.setBackgroundColor(Color.parseColor("#cccccc"));
		on_list.setTextColor(Color.WHITE);
		on_list.setBackgroundColor(getResources().getColor(R.color.title));
		off_list.setTextColor(Color.WHITE);
		off_list.setBackgroundColor(getResources().getColor(R.color.title));

		carListView = (ExpandableListView) this
				.findViewById(R.id.expandableListView1);
		carListAdapter=new CarListAdapter(this,group,map);
		carListView.setAdapter(carListAdapter);
		carListView.expandGroup(0);

		progressDialog=new ProgressDialog(this);
		title = (TextView) findViewById(R.id.title_name);// 标题
		pullToRefreshLayout = (PullToRefreshLayout) findViewById(R.id.refresh_view);
		requestFocuslinear = (LinearLayout) findViewById(R.id.request_focus_linear);
		car_search = (EditText) findViewById(R.id.car_search);
		select=(TextView)findViewById(R.id.title_select);
		back = (RelativeLayout) findViewById(R.id.back);

		title.setText("车辆列表");
		car_search.setText(sp.getString(Constant.sp_queryString,""));
		requestFocuslinear.requestFocus();
	}

	void initListener(){

		all_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 0;
				all_list.setTextColor(Color.BLACK);
				all_list.setBackgroundColor(Color.parseColor("#cccccc"));
				on_list.setTextColor(Color.WHITE);
				on_list.setBackgroundColor(getResources().getColor(R.color.title));
				off_list.setTextColor(Color.WHITE);
				off_list.setBackgroundColor(getResources().getColor(R.color.title));
				initSearchList(flag);
				notifyAdapterDataChange();//数据有变化 刷新适配器

			}
		});

		on_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				flag = 1;
				all_list.setTextColor(Color.WHITE);
				all_list.setBackgroundColor(getResources().getColor(R.color.title));
				on_list.setTextColor(Color.BLACK);
				on_list.setBackgroundColor(Color.parseColor("#cccccc"));
				off_list.setTextColor(Color.WHITE);
				off_list.setBackgroundColor(getResources().getColor(R.color.title));
				initSearchList(flag);
				notifyAdapterDataChange();//数据有变化 刷新适配器
			}
		});

		off_list.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				flag = 2;
				all_list.setTextColor(Color.WHITE);
				all_list.setBackgroundColor(getResources().getColor(R.color.title));
				on_list.setTextColor(Color.WHITE);
				on_list.setBackgroundColor(getResources().getColor(R.color.title));
				off_list.setTextColor(Color.BLACK);
				off_list.setBackgroundColor(Color.parseColor("#cccccc"));
				initSearchList(flag);
				notifyAdapterDataChange();//数据有变化 刷新适配器
			}
		});

		pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
				// 刷新
			}

			@Override
			public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
				// 下拉加载
				System.out.println("开始下拉加载");
				//从网络获取数据
				Log.e("DIZHI2",Api.GET_DEVICE_BY_USERID);
				HttpRequest.post(Api.GET_DEVICE_BY_USERID, getRequestParams(sp.getString(Constant.sp_queryString, ""), sp.getInt(Constant.sp_page, 1) + 1),
						new JsonHttpRequestCallback() {
							@Override
							protected void onSuccess(Headers headers, JSONObject jsonObject) {
								super.onSuccess(headers, jsonObject);
								System.out.println(jsonObject.toString());
								Log.e("789456123----->", jsonObject.toString());
								deviceBean = JSONObject.parseObject(jsonObject.toString(), DeviceBean.class);
								Log.e("CESHI",deviceBean.getDataList().size()+"");
								for(int u=0;u<deviceBean.getDataList().size();u++)
								Log.e("7894561234----->", deviceBean.getDataList().get(u).getBS());
								initCarList();
								initSearchList(flag);
								notifyAdapterDataChange();//数据有变化 刷新适配器

								//上拉加载更多  queryString不变
								Constant.homepageRefrashCarList = true;
								SharedPreferences.Editor editor = sp.edit();
								editor.putInt(Constant.sp_page, sp.getInt(Constant.sp_page, 1) + 1);//加载成功后当前页数+1；
								editor.commit();

								// 下拉加载完毕 加载成功
								pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
							}

							@Override
							public void onFailure(int errorCode, String msg) {
								super.onFailure(errorCode, msg);
								Log.e("Carlist", errorCode + msg);
								pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.FAIL);
							}

						});


			}
		});

		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Car_List.this.finish();
			}
		});
		select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mPopupWindowDialog != null
						&& !mPopupWindowDialog.isShowing()) {
					mPopupWindowDialog.showAsDropDown(select);
				}
			}
		});
		carListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				DeviceBean.CarListBean carListBean=map.get(group.get(groupPosition).getGroupName()).get(childPosition);
				if(carListBean.getStatus()==0){// 只有status==0的才显示在地图上
					int position = map
							.get(group.get(groupPosition).getGroupName())
							.get(childPosition).getPosition();
					Intent data = new Intent();
					data.putExtra("position", position);
					Car_List.this.setResult(RESULT_OK, data);
					finish();
				}else if(carListBean.getStatus()==1){
					Toast.makeText(Car_List.this,"设备已过期",Toast.LENGTH_SHORT).show();
				}else if(carListBean.getStatus()==2){
					Toast.makeText(Car_List.this,"设备未定位",Toast.LENGTH_SHORT).show();
				}

				return false;
			}
		});

		car_search.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView v, int actionId,
										  KeyEvent event) {
				System.out.println(event);
				Log.e("ZHUL", event+"");
				if (8==8) {
					if (actionId == EditorInfo.IME_ACTION_SEARCH
							|| (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
						// 先隐藏键盘
						((InputMethodManager) car_search.getContext()
								.getSystemService(Context.INPUT_METHOD_SERVICE))
								.hideSoftInputFromWindow(Car_List.this
												.getCurrentFocus().getWindowToken(),
										InputMethodManager.HIDE_NOT_ALWAYS);
						Log.e("ZHUL",car_search.getText().toString());
						//从网络获取数据
						HttpRequest.post(Api.GET_DEVICE_BY_USERID, getRequestParams(car_search.getText().toString(), 1),
								new JsonHttpRequestCallback() {
									@Override
									protected void onSuccess(Headers headers, JSONObject jsonObject) {
										super.onSuccess(headers, jsonObject);
										System.out.println(jsonObject.toString());
										deviceBean = JSONObject.parseObject(jsonObject.toString(), DeviceBean.class);
										initCarList();
										initSearchList(flag);
										notifyAdapterDataChange();//数据有变化 刷新适配器

										//搜索成功后 将搜索数据保存下来
										Constant.homepageRefrashCarList = true;
										SharedPreferences.Editor editor = sp.edit();
										editor.putString(Constant.sp_queryString, car_search.getText().toString());
										editor.putInt(Constant.sp_page, 1);//搜索成功后当前页数置为1；
										editor.commit();
									}

									@Override
									public void onFailure(int errorCode, String msg) {
										super.onFailure(errorCode, msg);
										Log.e("Carlist", errorCode + msg);
									}

									@Override
									public void onFinish() {
										super.onFinish();
										if (progressDialog != null && progressDialog.isShowing()) {
											progressDialog.dismiss();
										}
									}

									@Override
									public void onStart() {
										super.onStart();
										progressDialog.show();
									}
								});


						return false;
					}
				}
				return false;
			}
		});
	}
	//传搜索框中的字符串 以及页数要加几  加载更多的时候就要传1 即页数要加1
	RequestParams getRequestParams(String queryString,int page){
//		if(queryString.equals("")){
//			queryString=sp.getString(Constant.sp_queryString,"");
//		}
		RequestParams params = new RequestParams(this);
		params.addFormDataPart("userID", sp.getString(Constant.sp_userId,""));
		params.addFormDataPart("onLineType", 0);
		params.addFormDataPart("isBindCar", 0);
		params.addFormDataPart("queryString", queryString);
		params.addFormDataPart("page", 1);
		params.addFormDataPart("pageSize", Constant.pageSize * page);
		params.addFormDataPart("sortName", "");
		params.addFormDataPart("asc", "");
		params.addFormDataPart("showChild", true);
		params.addFormDataPart("tokenString", sp.getString(Constant.sp_token, ""));
		Log.e("canshu",sp.getString(Constant.sp_userId,"")+"----"+sp.getString(Constant.sp_token, "")+"-----"+sp.getString(Constant.sp_userId,""));
		return params;
	}

	void getCarListBean(RequestParams params){
		HttpRequest.post(Api.GET_DEVICE_BY_USERID, params, new JsonHttpRequestCallback() {
			@Override
			protected void onSuccess(Headers headers, JSONObject jsonObject) {
				super.onSuccess(headers, jsonObject);
				System.out.println(jsonObject.toString());
				deviceBean = JSONObject.parseObject(jsonObject.toString(), DeviceBean.class);
				Log.e("CESHI",jsonObject.toString());

//				for(int u=0;u<deviceBean.getDataList().size();u++)
//					Log.e("7894561234----->", deviceBean.getDataList().get(u).getBS());
				initCarList();
				initSearchList(flag);
				notifyAdapterDataChange();//数据有变化 刷新适配器
			}

			@Override
			public void onFailure(int errorCode, String msg) {
				super.onFailure(errorCode, msg);
				Log.e("Carlist", errorCode + msg);
			}

			@Override
			public void onFinish() {
				super.onFinish();
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}

			@Override
			public void onStart() {
				super.onStart();
				progressDialog.show();
			}
		});
	}
	//遍历一次找到总共有多少分组
	void initCarList(){
		all_list.setText("全部("+deviceBean.getAllCount()+")");
		on_list.setText("在线("+deviceBean.getOnLineCount()+")");
		off_list.setText("离线("+deviceBean.getOffLineCount()+")");
		group = new ArrayList<Group>();
		//遍历一次找到总共有多少分组
		for (DeviceBean.CarListBean carListBean : deviceBean.getDataList()) {
			boolean isnewGroup = true;
			for (Group groupStr : group) {
				if (carListBean.getGroupName().equals(groupStr.getGroupName())) {
					isnewGroup = false;
				}
			}
			if (isnewGroup) {
				Group g = new Group();
				g.setGroupName(carListBean.getGroupName());
				group.add(g);
			}
		}
		Log.e("groupNub","----->"+group.size());
	}

	//往各分组中存入数据
	void initSearchList(int i) {
		switch (i) {
			case 0:
				map = new HashMap<String, ArrayList<DeviceBean.CarListBean>>();
				for (Group groupStr : group) {
					carList = new ArrayList<DeviceBean.CarListBean>();
					for(int k=0;k<deviceBean.getDataList().size();k++){
						DeviceBean.CarListBean carListBean=deviceBean.getDataList().get(k);
						if (carListBean.getGroupName().equals(groupStr.getGroupName())) {
							carListBean.setPosition(k);//分组后顺序打乱 记录下分组前的位置
							carList.add(carListBean);
						}
					}
					map.put(groupStr.getGroupName(), carList);
				}
				break;
			case 1://在线
				map = new HashMap<String, ArrayList<DeviceBean.CarListBean>>();
				for (Group groupStr : group) {
					carList = new ArrayList<DeviceBean.CarListBean>();
					for(int k=0;k<deviceBean.getDataList().size();k++){
						DeviceBean.CarListBean carListBean=deviceBean.getDataList().get(k);
						if (carListBean.getGroupName().equals(groupStr.getGroupName())
								&&carListBean.isIsOnline() == true) {
							carListBean.setPosition(k);//分组后顺序打乱 记录下分组前的位置
							carList.add(carListBean);
						}
					}
					map.put(groupStr.getGroupName(), carList);
				}
				break;
			case 2://离线
				map = new HashMap<String, ArrayList<DeviceBean.CarListBean>>();
				for (Group groupStr : group) {
					carList = new ArrayList<DeviceBean.CarListBean>();
					for(int k=0;k<deviceBean.getDataList().size();k++){
						DeviceBean.CarListBean carListBean=deviceBean.getDataList().get(k);
						if (carListBean.getGroupName().equals(groupStr.getGroupName())
								&&carListBean.isIsOnline() == false) {
							carListBean.setPosition(k);//分组后顺序打乱 记录下分组前的位置
							carList.add(carListBean);
						}
					}
					map.put(groupStr.getGroupName(), carList);
				}
				break;
			default:
				break;
		}

	}



	@Override
	public String getHttpTaskKey() {
		return HTTP_TASK_KEY;
	}


	void initPupwindow(){
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.pupwindow_select, null);
		RadioGroup radioGroup = (RadioGroup) view
				.findViewById(R.id.carlist_select_radiogroup);
		RadioButton radioAll = (RadioButton) view.findViewById(R.id.carlist_select_all);
		RadioButton radioWired = (RadioButton) view.findViewById(R.id.carlist_select_wired);
		RadioButton radioWireless = (RadioButton) view.findViewById(R.id.carlist_select_wireless);
		RadioButton radioObd = (RadioButton) view.findViewById(R.id.carlist_select_obd);
		radioAll.setChecked(true);


		radioAll.setOnClickListener(new OnClickListener() {// 所有

			@Override
			public void onClick(View v) {
			}
		});
		radioWired.setOnClickListener(new OnClickListener() {// 有线

			@Override
			public void onClick(View v) {


			}
		});
		radioWireless.setOnClickListener(new OnClickListener() {//无线

			@Override
			public void onClick(View v) {

			}
		});
		radioObd.setOnClickListener(new OnClickListener() {//obd

			@Override
			public void onClick(View v) {

			}
		});

		mPopupWindowDialog = new PopupWindow(view, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		mPopupWindowDialog.setFocusable(true);
		mPopupWindowDialog.update();
		mPopupWindowDialog.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindowDialog.setOutsideTouchable(true);
	}


	void notifyAdapterDataChange(){
		Log.e("0987890",group.size()+"");
		carListAdapter=new CarListAdapter(Car_List.this,group,map);
		carListView.setAdapter(carListAdapter);
		carListView.expandGroup(0);
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
