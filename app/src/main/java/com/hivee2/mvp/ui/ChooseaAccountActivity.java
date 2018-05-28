package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.hivee2.R;
import com.hivee2.adapter.AtychildAdapter;
import com.hivee2.adapter.NewSimpleTreeAdapter;
import com.hivee2.adapter.SimpleTreeAdapter;
import com.hivee2.adapter.TreeListViewAdapter;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.ChildAccountBean;
import com.hivee2.mvp.model.bean.ChooseAccountBean;
import com.hivee2.mvp.model.bean.FileBean;
import com.hivee2.mvp.model.bean.NewFileBean;
import com.hivee2.mvp.model.bean.SearchAccountBean;
import com.hivee2.mvp.model.bean.TokenBean;
import com.hivee2.mvp.model.bean.UserchengeBean;
import com.hivee2.utils.HiveUtil;
import com.zhy.tree.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Headers;

/**
 * Created by 狄飞 on 2016/7/28.
 */
public class ChooseaAccountActivity extends Activity implements HttpCycleContext {
    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private List<NewFileBean> mDatas = new ArrayList<NewFileBean>();
    List<UserchengeBean.messageBean> userList=new ArrayList<UserchengeBean.messageBean>();
    private ListView mTree;
    private TreeListViewAdapter mAdapter;
    private SharedPreferences sp=null;// 存放用户信息
    public String token="";
    public String userid="";

    private ProgressDialog progressDialog;
    private int i;
    private int j;
    private int n;
    private int m=0;
    private ImageView back;
    private TextView title;
    private EditText serach;
    private AtychildAdapter adapter1;
    ArrayList<String>idlist=new ArrayList<>();
    ArrayList<SearchAccountBean.AccountBean>accountBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.atychildaccount);
        init();
        sp=this.getSharedPreferences("hive2", MODE_PRIVATE);
        userid= sp.getString(Constant.sp_userId, "");
        token=sp.getString(Constant.login_token, "");

        Log.e("1222-------->", userid + "");
          initDatas();
        run();

    }
    void showDialog(final String messageStr, final String useridStr, final String customerStr ,final int dialogFlag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(
                ChooseaAccountActivity.this);

        builder.setMessage(messageStr);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                userid=useridStr;
                RequestParams params = new RequestParams(ChooseaAccountActivity.this);
                params.addFormDataPart("userID",userid);
                params.addFormDataPart("tokenString",token);
                //  地址  参数  回调函数
                Log.e("1222-------->", userid + "");
                mTree = (ListView) findViewById(R.id.ac_listview);
                HttpRequest.post(Api.GET_SON_USER_TOKEN, params, new JsonHttpRequestCallback() {
                    @Override
                    protected void onSuccess(Headers headers, JSONObject jsonObject) {
                        super.onSuccess(headers, jsonObject);
                        Log.e("-1222-------->", Api.GET_SON_USER_TOKEN+"---"+userid+"-----"+token);
                        Log.e("-1222-------->", jsonObject.toString());
                       TokenBean tokenBean = JSONObject.parseObject(jsonObject.toString(), TokenBean.class);
                        String sdasda=tokenBean.getResponse_Customer().getCustomerName();
    //获取了新的用户名
                        JPushInterface.setAlias(ChooseaAccountActivity.this, sdasda.toString(),
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
                        Log.e("USEID",userid+"-----"+token+"===="+sdasda);
                        token=tokenBean.getSonUserToken();
                        Log.e("USEID", userid + "-----" + token);
                        Log.e("name", sp.getString("username",""));
                        SharedPreferences.Editor editor=sp.edit();
                        editor.putString(Constant.sp_userId, userid);
                        editor.putString(Constant.sp_token,token);
                        editor.commit();
                        Constant.atychildaccoumt=true;
                        finish();

                    }



                    @Override
                    public void onFailure(int errorCode, String msg) {
                        super.onFailure(errorCode, msg);
                        Log.e("alertMsg failure", errorCode + msg);
                        Toast.makeText(ChooseaAccountActivity.this,"网络异常",Toast.LENGTH_SHORT).show();

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
                        if(progressDialog.isShowing()&&progressDialog!=null){
                            progressDialog.dismiss();
                        }
                        //hide progressdialog
                    }
                });


            }
        });
        builder.setNegativeButton("否",null);
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }
    private void child1(ChildAccountBean childAccountBean)
    {
        for(int o=0;o<childAccountBean.getCustomerName().length()-serach.getText().toString().length()+1;o++)
        {
            Log.e("OKM",childAccountBean.getCustomerName().substring(o,o+serach.getText().toString().length()));

            if(childAccountBean.getCustomerName().substring(o,o+serach.getText().toString().length()).equals(serach.getText().toString()))
            {
                userList.add(m, new UserchengeBean.messageBean());
                userList.get(m).setAleaQty(childAccountBean.getAleaQty());
                userList.get(m).setTempQty(childAccountBean.getTempQty());
                userList.get(m).setCustomerName(childAccountBean.getCustomerName());
                userList.get(m).setUserID(childAccountBean.getUserID());
            }
        }
//        m++;
        List<ChildAccountBean> childs1= childAccountBean.getChildren();
        if(childs1!=null){
            for(ChildAccountBean child : childs1){
                child1(child);
            }
        }
    }
    private void initDatas2(){

        RequestParams params = new RequestParams(ChooseaAccountActivity.this);
        params.addFormDataPart("TokenString", token);
        params.addFormDataPart("CustomerName", serach.getText().toString());
        //  地址  参数  回调函数
        Log.e("1222-------->", userid + "");
        mTree = (ListView) findViewById(R.id.ac_listview);
        HttpRequest.post(Api.GET_USERIDBYCN, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                SearchAccountBean childAccountBean = JSONObject.parseObject(jsonObject.toString(), SearchAccountBean.class);
                accountBeans=childAccountBean.getDataList();

                ArrayList<String> al=new ArrayList<>();
                for(int i=0;i<accountBeans.size();i++){
                    al.add(accountBeans.get(i).getUserName());
                }
                ArrayAdapter arrayAdapter=new ArrayAdapter(ChooseaAccountActivity.this,android.R.layout.simple_list_item_1,al);
                mTree.setAdapter(arrayAdapter);
//                UserchengeBean userchengeBean= new
//
//                userchengeBean.getDataList().get(1).setAleaQty(childAccountBean.getAleaQty());

//                childAccountBean.getAleaQty();
//                userchengeBean.getDataList().set(i).getAleaQty()=
//                UserchengeBean userchengeBean= JSONObject.parseObject(jsonObject.toString(), UserchengeBean.class);
//                userList =userchengeBean.getDataList();
//                userList.get(1).setAleaQty();

//                 m=0;
//
//                serach.getText().toString().length();
//                Log.e("leng", serach.getText().toString().length() + "" + childAccountBean.getCustomerName() + "" + childAccountBean.getCustomerName().length());
//
//                for(int o=0;o<childAccountBean.getCustomerName().length()-serach.getText().toString().length()+1;o++)
//                {
//                    Log.e("OKM",childAccountBean.getCustomerName().substring(o,o+serach.getText().toString().length()));
//
//                    if(childAccountBean.getCustomerName().substring(o,o+serach.getText().toString().length()).equals(serach.getText().toString()))
//                    {
//                        userList.add(m, new UserchengeBean.messageBean());
//                        userList.get(m).setAleaQty(childAccountBean.getAleaQty());
//                        userList.get(m).setTempQty(childAccountBean.getTempQty());
//                        userList.get(m).setCustomerName(childAccountBean.getCustomerName());
//                        userList.get(m).setUserID(childAccountBean.getUserID());
//                    }
//                }
//
//                List<ChildAccountBean> childs1= childAccountBean.getChildren();
//                if(childs1!=null){
//                    for(ChildAccountBean child : childs1){
//                        child1(child);
//                    }
//                }
////                while (childAccountBean.getChildren()!=null)
////                {
////
////
////
////                    userList.add(m, new UserchengeBean.messageBean());
////                    userList.get(m).setAleaQty(childAccountBean.getAleaQty());
////                    userList.get(m).setTempQty(childAccountBean.getTempQty());
////                    userList.get(m).setCustomerName(childAccountBean.getCustomerName());
////                    userList.get(m).setUserID(childAccountBean.getUserID());
////                }
//
//
//                adapter1 = new AtychildAdapter(ChooseaAccountActivity.this, userList);
//                adapter1.notifyDataSetChanged();
//                mTree.setAdapter(adapter1);

            }



            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Log.e("alertMsg failure", errorCode + msg);
                Toast.makeText(ChooseaAccountActivity.this,"网络异常",Toast.LENGTH_SHORT).show();

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
                if(progressDialog.isShowing()&&progressDialog!=null){
                    progressDialog.dismiss();
                }
                //hide progressdialog
            }
        });
        mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {//+ item点击事件监听器
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                System.out.println("position" + position);
                Intent intent = new Intent();
                // 获取用户计算后的结果
                intent.putExtra("Name", accountBeans.get(position).getUserName()); //将计算的值回传回去
                Log.i("ID",  accountBeans.get(position).getUserID());
                intent.putExtra("ID", accountBeans.get(position).getUserID()); //将计算的值回传回去
                //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                setResult(2, intent);

                finish();
                // TODAuto-generated method stub
//                showDialog("是否切换账户"+userList.get(position).getCustomerName(),userList.get(position).getUserID(),
//                        userList.get(position).getCustomerName(), 0);

            }
        });

    }
    public void run()
    {
        serach.addTextChangedListener(new TextWatcher() {

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
                    mDatas.clear();
                    userList.clear();
                    initDatas();
//                    ivDeleteText.setVisibility(View.GONE);//当文本框为空时，则叉叉消失
                } else {
//                    ivDeleteText.setVisibility(View.VISIBLE);//当文本框不为空时，出现叉叉
//                    myhandler.post(eChanged);

                    mDatas.clear();
                    userList.clear();
                    initDatas2();

                }
            }
        });

    }
    public void init()
    {
        back=(ImageView)findViewById(R.id.imageView2);
        title=(TextView)findViewById(R.id.title_name1);
        serach=(EditText)findViewById(R.id.childac_search);
//        serach2.setVisibility(View.GONE);
        title.setText("选择账号");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog=new ProgressDialog(this);
    }
   public void child(int parentId,ChooseAccountBean childAccountBean)
    {
        mDatas.add(new NewFileBean(mDatas.size()+1,parentId,childAccountBean.getText()));//将当前节点（父节点）加入树
        idlist.add(childAccountBean.getId());
        parentId = mDatas.size();
        List<ChooseAccountBean> childs = childAccountBean.getChildren();
        if(childs!=null){
            for(ChooseAccountBean child : childs){
                child(parentId,child);
            }
        }
//        int m=childAccountBean.getChildren().size();
//        j=n;
//        for(i=0;i<m;i++)
//        {
//            mDatas.add(new FileBean(n+1, z, childAccountBean.getChildren().get(i).getCustomerName()));
//            n=n+1;
//        }
//        for(i=0;i<m;i++)
//        {
//            ChildAccountBean bean=childAccountBean.getChildren().get(i);
//            child(j+1,bean);
//        }
    }

    private void initDatas()
    {


//        serach.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            public boolean onEditorAction(TextView v, int actionId,
//                                          KeyEvent event) {
//                System.out.println(event);
//                if (event != null) {
//                    if (actionId == EditorInfo.IME_ACTION_SEARCH
//                            || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                        // 先隐藏键盘
//                        ((InputMethodManager) serach.getContext()
//                                .getSystemService(Context.INPUT_METHOD_SERVICE))
//                                .hideSoftInputFromWindow(Atychildaccount.this
//                                                .getCurrentFocus().getWindowToken(),
//                                        InputMethodManager.HIDE_NOT_ALWAYS);
//
//                        System.out.println("query-->"
//                                + serach.getText().toString());
//                        if (serach.getText().toString().equals("")) {
////                            flag = Constant.GetChildAccount;
////                        } else {
////                            flag = Constant.SearchChildAccount;
//                        }
//
//                        progressDialog = ProgressDialog.show(Atychildaccount.this, "",
//                                "正在搜索,请稍候。。。");
//
//                        progressDialog.setCancelable(true);
////                        startthread();
//                        return false;
//                    }
//                }
//                return false;
//            }
//        });
        RequestParams params = new RequestParams(ChooseaAccountActivity.this);
        params.addFormDataPart("UserID",userid);
        params.addFormDataPart("TokenString", token);
        //  地址  参数  回调函数
        Log.i("initDatas", Api.GET_CHILD_TREE+"?UserID="+userid+"&TokenString="+token);
        mTree = (ListView) findViewById(R.id.ac_listview);
        HttpRequest.post(Api.GET_CHILD_TREE, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.e("-1222-------->", jsonObject.toString());
                ChooseAccountBean childAccountBean = JSONObject.parseObject(jsonObject.toString(), ChooseAccountBean.class);
                child(0,childAccountBean);
                if(childAccountBean.getChildren()==null){
                    Toast.makeText(ChooseaAccountActivity.this,"该账户下没有子账户",Toast.LENGTH_LONG).show();
                }
//                j = childAccountBean.getChildren().size();
//                for (i = 2; i <= j+1; i++)
//                {
//                    mDatas.add(new FileBean(i, 1, childAccountBean.getChildren().get(i-2).getCustomerName()));
//                    n=j+1;
//                }
//                for(i = 2; i <= j+1; i++)
//                {
//                  ChildAccountBean bean=childAccountBean.getChildren().get(i - 2);
//                     child(i,bean);
//                }
                try
                {
                    mAdapter = new NewSimpleTreeAdapter(mTree, ChooseaAccountActivity.this, mDatas, 10);
                    mAdapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener()
                    {
                        @Override
                        public void onClick(Node node, int position)
                        {
                            Log.e("---------->852",""+node.getName()+node.getId());
                            Log.e("---------->852",""+node.getName()+idlist.get(node.getId()-1));
                            Intent intent = new Intent();
                                             // 获取用户计算后的结果
                                             intent.putExtra("Name", node.getName()); //将计算的值回传回去
                                                intent.putExtra("ID", idlist.get(node.getId()-1)); //将计算的值回传回去
                                             //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
                                             setResult(2, intent);

                                             finish();
//                            showDialog("是否切换账户"+node.getName(),node.getUseid(),node.getName(), 0);
//
//                                Toast.makeText(getApplicationContext(), node.getName(),
//                                        Toast.LENGTH_SHORT).show();
                        }


                    });

                    mTree.setAdapter(mAdapter);
                } catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                mTree.setAdapter(mAdapter);
            }



            @Override
            public void onFailure(int errorCode, String msg) {
                super.onFailure(errorCode, msg);
                Log.e("alertMsg failure", errorCode + msg);
                Toast.makeText(ChooseaAccountActivity.this,"网络异常",Toast.LENGTH_SHORT).show();
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
                if(progressDialog.isShowing()&&progressDialog!=null){
                    progressDialog.dismiss();
                }
                //hide progressdialog
            }
        });





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
