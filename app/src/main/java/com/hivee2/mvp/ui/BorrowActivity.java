package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.hivee2.R;
import com.hivee2.adapter.ListViewCompat;
import com.hivee2.adapter.SlideView;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.BaseApiResponse;
import com.hivee2.mvp.model.bean.BorrowBean;
import com.hivee2.utils.HiveUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/10.
 */
public class BorrowActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener, SlideView.OnSlideListener,HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private RelativeLayout back;
    private TextView title;
    private TextView new1;
    private EditText eSearch;
    private ListView list1;
    private ListViewCompat mListView;
    private  int B=1;
    public String token="";
    public String userid="";
    public int choose=0;
    private int a;
    private SharedPreferences sp=null;// 存放用户信息
    private ProgressDialog progressDialog;
//    private List<MessageItem> mMessageItems = new ArrayList<BorrowActivity.MessageItem>();
    private List<BorrowBean.DataListBean> borrowList=new ArrayList<BorrowBean.DataListBean>();
    private List<BorrowBean.DataListBean> borrowList1=new ArrayList<BorrowBean.DataListBean>();
    private SlideView mLastSlideViewWithStatusOn;

    private SlideAdapter adapter;
    private String queryString="";
    int currid=0;
    String Pre_CarInfoID = null;
    String Pre_bend = null;
    String Pre_model = null;
    String Pre_bendid = null;
    private Spinner spinner2;
    private  ArrayAdapter typeAdapter;
    private List<String> allType=new ArrayList<String>();
    int searchfield=1;
     LinearLayout spinner22;
     TextView tv_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityborrow);
        intview();
        getinformation();
        set_eSearch_TextChanged();
    }

    private void intview() {
        back = (RelativeLayout) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title_name);
        new1 = (TextView) findViewById(R.id.title_select);
        mListView = (ListViewCompat)findViewById(R.id.listView2);
        spinner2= (Spinner) findViewById(R.id.spinner2);
        progressDialog=new ProgressDialog(this);
        spinner22= (LinearLayout) findViewById(R.id.spinner22);
        tv_type= (TextView) findViewById(R.id.tv_type);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title.setText("业务管理");
        new1.setText("新增借款");
        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BorrowActivity.this, BorrowAdd.class);
                intent.setClass(BorrowActivity.this, BorrowAdd.class);
                startActivityForResult(intent, 0);
            }
        });
        spinner22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("借款人"));
                strings.add(new TieBean("车架号"));
                strings.add(new TieBean("车牌号"));
                strings.add(new TieBean("设备名"));
                strings.add(new TieBean("设备号"));
                DialogUIUtils.showSheet(BorrowActivity.this, strings, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        Log.i("onItemClick: ", position+"");
                        searchfield= (int) (position+1);
                        tv_type.setText(text);
                    }
                }).show();

            }
        });
        allType.add("借款人");
        allType.add("车架号");
        allType.add("车牌号");
        allType.add("设备名");
        allType.add("设备号");
        typeAdapter=new ArrayAdapter<String>(BorrowActivity.this,android.R.layout.simple_spinner_item,allType);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(typeAdapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                type = (String) spinner2.getAdapter().getItem((int) l);
                Log.i("onItemSelected: ", l+"");
                searchfield= (int) (l+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void set_eSearch_TextChanged()
    {
        eSearch = (EditText) findViewById(R.id.childac_search1);
//
//        eSearch.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                // TODO Auto-generated method stub
//                //这个应该是在改变的时候会做的动作吧，具体还没用到过。
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
//                                          int arg3) {
//                // TODO Auto-generated method stub
//                //这是文本框改变之前会执行的动作
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // TODO Auto-generated method stub
//                /**这是文本框改变之后 会执行的动作
//                 * 因为我们要做的就是，在文本框改变的同时，我们的listview的数据也进行相应的变动，并且如一的显示在界面上。
//                 * 所以这里我们就需要加上数据的修改的动作了。
//                 */
//                if (s.length() == 0) {
//                    queryString=eSearch.getText().toString();
//                    getinformation();
////                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
//                } else {
////                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
////                    myhandler.post(eChanged);
//
//                    queryString=eSearch.getText().toString();
//                    getinformation();
//                }
//            }
//        });
        eSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    ((InputMethodManager) eSearch.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(BorrowActivity.this
                                            .getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    // 搜索，进行自己要的操作...
                    queryString=eSearch.getText().toString();
                    if(searchfield==2){
                        //是车架号，需要判断是否满6位
                        if(queryString.length()<6){
                            Toast.makeText(BorrowActivity.this,"请至少输入车架号后6位",Toast.LENGTH_SHORT).show();
                        }else{
                            getinformation();
                        }
                    }else{
                        getinformation();
                    }


                    return true;
                }
                return false;
            }
        });

    }
    public void getinformation()
    {

        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.sp_token, "");
        RequestParams params = new RequestParams(BorrowActivity.this);
//        String ppparam="{"+'"'+"tokenstring"+'"'+":'"+token+"',"+'"'+"userid"+'"'+":'"+userid+"','Querystring"+"':'"+queryString+"'"+"}";
        String ppparam="{"+'"'+"tokenstring"+'"'+":'"+token+"',"+'"'+"userid"+'"'+":'"+userid+"','Querystring"+"':'"+queryString+"','page"+"':'"+1+"','starttime"+"':'"+""+"','endtime"+"':'"+""+"','pageSize"+"':'"+50+"','searchfield"+"':'"+searchfield+"'}";
        params.addFormDataPart("param",ppparam);
        Log.i("1231", ppparam);
        Log.i("1231", Api.QUERY_LOANINFO);
//        params.addFormDataPart("queryString",queryString);
//        params.addFormDataPart("page",1);
//        params.addFormDataPart("pageSize",10000);
//        params.addFormDataPart("sortName","");
//        PledgerName
//        params.addFormDataPart("starttime","");
//        params.addFormDataPart("endtime","");
//        params.addFormDataPart("asc","asc");
//        params.addFormDataPart("TokenString",token);
//        params.addFormDataPart("userID",userid);
//        params.addFormDataPart("queryString",queryString);
//        params.addFormDataPart("page",1);
//        params.addFormDataPart("pageSize",10000);
//        params.addFormDataPart("sortName","");
//        params.addFormDataPart("asc","asc");
//        params.addFormDataPart("TokenString",token);
        //  地址  参数  回调函数
        HttpRequest.post(Api.QUERY_LOANINFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("7777777-------->", jsonObject.toString());
                BorrowBean borrowBean = JSONObject.parseObject(jsonObject.toString(), BorrowBean.class);
                borrowList=borrowBean.getDataList();
                Log.i("onSuccess: ", borrowList.size()+"");
                borrowList1.clear();
//                childList = childBean.getDataList();
//                adapter = new AccountsetAdapter(BorrowActivity.this, childList);
//                adapter.notifyDataSetChanged();

                for(int i=0;i<borrowList.size();i++){
                    BorrowBean.DataListBean dListbean=borrowList.get(i);


                    if(i==0){
                        borrowList1.add(dListbean);
                        currid=0;
                        Pre_bend= (String) dListbean.getInternalNum();
                        Pre_model=(String) dListbean.getModel();
                        Pre_bendid= (String) dListbean.getCarDeviceID();
                        Pre_CarInfoID=dListbean.getCarInfoID();
                    }else{
                        //只有不是第一项才能和前一项进行对比
//                        Log.i("Pre_bend1", dListbean.getCarInfoID());
                        if(dListbean.getCarInfoID()==null||Pre_CarInfoID==null){
                            //和前一个不同
                            currid=currid+1;
                            Pre_bend= (String) dListbean.getInternalNum();
                            Pre_model=(String) dListbean.getModel();
                            Pre_bendid= (String) dListbean.getCarDeviceID();
                            Pre_CarInfoID=dListbean.getCarInfoID();
                            borrowList1.add(dListbean);
                        }else if(dListbean.getCarInfoID().equals(Pre_CarInfoID)){
//                            Log.i("Pre_bend",dListbean.getInternalNum()+"" );
                            //当前的carinfoid和前一个相同
                            //那么要往前一个id里面存数据InternalNum
                            Pre_bend=Pre_bend+"/"+borrowList.get(i).getInternalNum();
                            Pre_model=Pre_model+"/"+dListbean.getModel();
                            Pre_bendid= Pre_bendid+"/"+dListbean.getCarDeviceID();
//                            Log.i("Pre_bendd", Pre_bendid);
                            borrowList1.get(currid).setInternalNum( Pre_bend);
                            borrowList1.get(currid).setModel(Pre_model);
                            borrowList1.get(currid).setCarDeviceID(Pre_bendid);
                        }else{
                            //和前一个不同
                            currid=currid+1;
//                            Log.i("Pre_bend1", i+"borrowList1");
                            Pre_bend= (String) dListbean.getInternalNum();
                            Pre_model= (String) dListbean.getModel();
                            Pre_bendid= (String) dListbean.getCarDeviceID();
//                            Log.i("Pre_bend1", i+"borrowList1");
                            Pre_CarInfoID=dListbean.getCarInfoID();
//                            Log.i("Pre_bendd", Pre_bendid);
                            borrowList1.add(dListbean);
                        }
                    }
                }
                Log.i("onSuccess: ", borrowList1.size()+"aaa");
                adapter = new SlideAdapter();
                mListView.setAdapter(adapter);
                mListView.setOnItemClickListener(BorrowActivity.this);

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


    private class SlideAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        SlideAdapter() {

            super();
            Log.e("huLE","8888888");
            mInflater = getLayoutInflater();
        }

        @Override
        public int getCount() {
            return borrowList1.size();
        }

        @Override
        public Object getItem(int position) {
            Log.e("huLE","8888886");
//            if(choose==0)
//            {
//                choose=1;
//            }
//            else if(choose==1){
//                choose=0;
//            }
            Log.e("choose",choose+"");
            return borrowList1.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.e("huLE","8888885");
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            SlideView slideView = (SlideView) convertView;
            if (slideView == null) {
                View itemView = mInflater.inflate(R.layout.borrow_item,
                        null);

                slideView = new SlideView(BorrowActivity.this);
                slideView.setContentView(itemView);

                holder = new ViewHolder(slideView);
                slideView.setOnSlideListener(BorrowActivity.this);
                slideView.setTag(holder);
            } else {
                holder = (ViewHolder) slideView.getTag();
            }
            BorrowBean.DataListBean item = borrowList1.get(position);
            item.slideView = slideView;
            item.slideView.shrink();

            if(borrowList1.get(position).getPledgerName()!=null)
            {
                String VVIN="";
                String CarNUM="";
//                holder.pledgerName.setText(borrowList1.get(position).getPledgerName());
                if(borrowList1.get(position).getVIN()==null){
                    VVIN="";
                }else{
                    VVIN=borrowList1.get(position).getVIN();
                }
                if(borrowList1.get(position).getCarNumber()==null){
                    CarNUM="";
                }else{
                    CarNUM=borrowList1.get(position).getCarNumber();
                }
                if(VVIN.equals("")){
                    holder.pledgerName.setText("暂无车辆");
                }else{
                    if(VVIN.equals(CarNUM)){
                        holder.pledgerName.setText(VVIN);
                    }else{
                        holder.pledgerName.setText(VVIN+"/"+CarNUM);
                    }
                }


            }
            holder.money.setText("￥" + borrowList1.get(position).getCarValue());
            String Carnumber=borrowList1.get(position).getCarNumber();
            String InternalNum= String.valueOf(borrowList1.get(position).getInternalNum());
            if(Carnumber==null)
            {
                Carnumber="";
            }
            if(InternalNum=="null")
            {
                InternalNum="";
            }
            holder.name.setText(borrowList1.get(position).getPledgerName());
            holder.sbm_item.setText(InternalNum);
            borrowList1.get(position).getPledgeTime();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");

            if(borrowList1.get(position).getPledgeTime().toString().equals(""))
            {
                holder.time.setText("");
            }
            else {
                a =borrowList1.get(position).getPledgeTime().toString().indexOf("(",0);
                int b=borrowList1.get(position).getPledgeTime().toString().indexOf(")",0);
                Log.e("xianshi",""+Long.valueOf(borrowList1.get(position).getPledgeTime().toString().substring(a+1,b)));
                holder.time.setText(format.format(Long.valueOf(borrowList1.get(position).getPledgeTime().toString().substring(a+1,b)) ));
            }



            holder.deleteHolder.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Log.e("MINE", borrowList1.get(position).getPledgementID() + "---5555--" + token);





                    RequestParams params = new RequestParams(BorrowActivity.this);
                    params.addFormDataPart("pledgerID",borrowList1.get(position).getPledgementID());
                    params.addFormDataPart("tokenString", token);
                    params.addFormDataPart("jsoncallback","");
                    HttpRequest.post(Api.DEL_PLEDGET, params, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
                            BaseApiResponse delteBean = JSONObject.parseObject(jsonObject.toString(),BaseApiResponse.class);

                            if (delteBean.getResult() == 0) {
                                borrowList1.remove(position);
                                adapter.notifyDataSetChanged();
                                Toast.makeText(BorrowActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(BorrowActivity.this, "请先删除用户下面的车！", Toast.LENGTH_SHORT).show();
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

//                    Toast.makeText(BorrowActivity.this,
//                            "删除第" + position + "个条目", 0).show();
                }
            });

            return slideView;
        }

    }

//    public class MessageItem {
//        public String name;
//        public String phonenumber;
//        public String address;
//        public SlideView slideView;
//    }

    private static class ViewHolder {
        public ImageButton ivButton;
        public TextView pledgerName;
        public TextView money;
        public TextView name;
        public TextView time;
        public ViewGroup deleteHolder;
        public TextView sbm_item;

        ViewHolder(View view) {
            pledgerName = (TextView) view.findViewById(R.id.textView60);
            money = (TextView) view.findViewById(R.id.textView62);
            name = (TextView) view.findViewById(R.id.textView61);
            time=(TextView) view.findViewById(R.id.textView63);
            deleteHolder = (ViewGroup) view.findViewById(R.id.holder);
            sbm_item= (TextView) view.findViewById(R.id.sbm_item);
        }
    }
//    {"PledgementID":" ","PledgerName":"sadasd","Remak":"",
//            "PledgeCarID":"fce34a9c5ed244888c47dd2db7f5215b","CreateTime":"/Date(1469790322750)/",
//            "CarInfoID":"20e30fff43d247ec8af8b299debe97a0","CarNumber":"aadasdasd","CarBrand":"",
//            "CarVersion":"","VIN":"","CarRemark":"","CarValue":0,"PledgeTime":"/Date(1469721600000)/",
//            "RepayTime":"/Date(1469721600000)/","ConCarID":null,"ContractID":null,"ContractName":null,
//            "ReplayDay":null,"RepayMethod":null,"RateCycle":null,"Rate":null,"RepayCycle":null,"LoanTerm":null,
//            "CarDeviceID":null,"DeviceID":null, "InternalNum":null,"IMEI":null,"Model":null,"PositionID":null,
//            "PositionTime":null,"Address":null,"BS":null,"BL":null,"BLat":null,"BLng":null
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Log.i("onitemc", "jinlai");

        if(choose==0)
        {
            Log.e("huLE","8888883");
            Intent intent = new Intent(BorrowActivity.this, BorrowDetail.class);
            intent.putExtra("PledgerName",borrowList1.get(position).getPledgerName());
            intent.putExtra("carID",borrowList1.get(position).getCarInfoID());
            intent.putExtra("CarDeviceID",String.valueOf(borrowList1.get(position).getCarDeviceID())+"");
//            intent.putExtra("Status",borrowList.get(position).getStatus()+"");
            intent.putExtra("Source",borrowList1.get(position).getSource()+"");
            intent.putExtra("Status",borrowList1.get(position).getStatus()+"");
            Log.e("getCarDeviceID", borrowList1.get(position).getCarInfoID()+"");
            Log.e("getCarDeviceID", String.valueOf(borrowList1.get(position).getCarDeviceID()));
            intent.putExtra("CarNumber",borrowList1.get(position).getCarNumber());
            intent.putExtra("CarBrand",borrowList1.get(position).getCarBrand()+ "");
            intent.putExtra("CarValue", borrowList1.get(position).getCarValue() + "");
            Log.e("VALUE", borrowList1.get(position).getCarValue() + "");
            intent.putExtra("PledgeTime", borrowList1.get(position).getPledgeTime() + "");
            intent.putExtra("RepayTime", borrowList1.get(position).getRepayTime() + "");
            intent.putExtra("InternalNum",String.valueOf(borrowList1.get(position).getInternalNum())+"");
            intent.putExtra("IMEI",String.valueOf(borrowList1.get(position).getIMEI())+"");
            intent.putExtra("Model",String.valueOf(borrowList1.get(position).getModel())+"");
            intent.putExtra("BS",String.valueOf(borrowList1.get(position).getBS())+"");
            intent.putExtra("BL",String.valueOf(borrowList1.get(position).getBL())+"");
            intent.putExtra("Address",String.valueOf(borrowList1.get(position).getAddress())+"");
            intent.putExtra("PledgementID",borrowList1.get(position).getPledgementID());
            intent.putExtra("VIN",borrowList1.get(position).getVIN());
            intent.putExtra("CarVersion",borrowList1.get(position).getCarVersion());
            intent.putExtra("PledgeCarID",borrowList1.get(position).getCarInfoID());
            intent.setClass(BorrowActivity.this, BorrowDetail.class);
            startActivityForResult(intent, 3);
        }
        else if(choose==3)
        {
            choose=0;
        }



    }

    @Override
    public void onSlide(View view, int status) {
        if (mLastSlideViewWithStatusOn != null
                && mLastSlideViewWithStatusOn != view) {
            mLastSlideViewWithStatusOn.shrink();

        }

        if (status == SLIDE_STATUS_ON) {
            Log.e("huLE","8888889");
            mLastSlideViewWithStatusOn = (SlideView) view;
//            choose=3;
            Log.e("huLE","8888881");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.holder:

                Log.e("huLE","8888882");
                break;

            default:

                Log.e("huLE","8888883");
                break;
        }
    }
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getinformation();
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



