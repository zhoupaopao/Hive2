package com.hivee2.mvp.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.hivee2.R;
import com.hivee2.adapter.ImagePickerAdapter;
import com.hivee2.adapter.ListViewAddDelAdapter1;
import com.hivee2.content.Api;
import com.hivee2.content.Constant;
import com.hivee2.mvp.model.bean.AddCar;
import com.hivee2.mvp.model.biz.GlideImageLoader;
import com.hivee2.mvp.model.biz.HttpUtil;
import com.hivee2.mvp.model.biz.MyStringCallBack;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.lzy.imagepicker.view.CropImageView;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.okhttpfinal.HttpCycleContext;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.JsonHttpRequestCallback;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.Call;
import okhttp3.Headers;

/**
 * Created by Lenovo on 2018/5/8.
 */
//添加设备信息界面，有图片和添加的列表
public class AddBendActivity extends Activity implements HttpCycleContext,ImagePickerAdapter.OnRecyclerViewItemClickListener {
    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;
    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 8;

    private ProgressDialog progressDialog;


    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();
    private ImageView back;
    private TextView title;
    private RelativeLayout addbend;
    private ListView lv_bend;
    private ListViewAddDelAdapter1 listViewAddDelAdapter1;
    private TextView unbind;
    private SharedPreferences sp=null;
    private Button submit;
    private String token;
    private String carId;
    private String userid;
    private String PledgerName;
    private String PledgerID;
    private String carInfo;
    private String message2;
    private ArrayList<String> bend_list=new ArrayList<>();
//    private ImageView chosepic;
    /**获取到的图片路径*/
    private String picPath;
    private Intent lastIntent ;
    private HttpUtil httpUtil;
    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

    /***
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";

    private static final String TAG = "SelectPicActivity";

    private Uri photoUri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbend);
        initView();
        initImagePicker();
        initData();
        initListener();
    }

    private void initView() {
        httpUtil=new HttpUtil();
        progressDialog=new ProgressDialog(this);
        sp=getSharedPreferences("hive2", MODE_PRIVATE);
        token=sp.getString(Constant.sp_token, "");
        userid= sp.getString(Constant.sp_userId, "");
        back= (ImageView) findViewById(R.id.imageView2);
        title= (TextView) findViewById(R.id.title_name1);
        addbend= (RelativeLayout) findViewById(R.id.addbend);
        carInfo=getIntent().getStringExtra("carInfo");
        message2=getIntent().getStringExtra("paramm");
        PledgerName=getIntent().getStringExtra("PledgerName");
        PledgerID=getIntent().getStringExtra("PledgerId");
        unbind= (TextView) findViewById(R.id.unbind);
//        chosepic= (ImageView) findViewById(R.id.chosepic);
        lv_bend= (ListView) findViewById(R.id.lv_bend);
        listViewAddDelAdapter1=new ListViewAddDelAdapter1(AddBendActivity.this);
        lv_bend.setAdapter(listViewAddDelAdapter1);
        lastIntent = getIntent();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        submit= (Button) findViewById(R.id.submit);
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);                      //显示拍照按钮
        imagePicker.setCrop(false);                            //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
        imagePicker.setSelectLimit(maxImgCount);              //选中数量限制
        imagePicker.setMultiMode(true);                      //多选
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
    }
    public void del(int pos) {
        listViewAddDelAdapter1.delData(pos);
        listViewAddDelAdapter1.notifyDataSetChanged();
        // 判断是否>0

    }
    private void initData() {

    }

    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("添加设备");
        addbend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddBendActivity.this,AddBendListDetail.class);
                intent.putStringArrayListExtra("cutList", (ArrayList<String>) listViewAddDelAdapter1.getDta());
                startActivityForResult(intent, 1);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先进行二押判断
                    RequestParams params = new RequestParams(AddBendActivity.this);
                    params.addFormDataPart("param", message2);
                    Log.i("新怎",Api.Check_LoanValid+"?userid="+userid+"&pledgername="+PledgerName+"&carnumber="+PledgerID+"&tokenString="+token);

                    HttpRequest.post(Api.Check_LoanValid, params, new JsonHttpRequestCallback() {
                        @Override
                        protected void onSuccess(Headers headers, JSONObject jsonObject) {
                            super.onSuccess(headers, jsonObject);
//                            CarSeries carSeries = JSONObject.parseObject(jsonObject.toString(), CarSeries.class);
                            Log.i("新怎1",jsonObject.toString());
                            int result=jsonObject.getInteger("Result");
                            if(result==0){
                                //新建车辆
                                information1();
                                Log.i("新怎2","0");
                            }else if(result==4){
                                Log.i("新怎","4");
                                AlertDialog alert=new AlertDialog.Builder(AddBendActivity.this).create();
                                alert.setTitle("警告");
                                alert.setMessage(jsonObject.getString("ErrorMsg"));
                                //添加取消按钮
                                alert.setButton(DialogInterface.BUTTON_NEGATIVE,"继续添加",new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // TODO Auto-generated method stub
                                        information1();
                                    }
                                });
                                //添加"确定"按钮
                                alert.setButton(DialogInterface.BUTTON_POSITIVE,"取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
//                                                finish();
                                    }
                                });
                                alert.show();
                            }else{
                                Toast.makeText(AddBendActivity.this,jsonObject.getString("ErrorMsg"),Toast.LENGTH_LONG).show();
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
                            //show  progressdialog
                            progressDialog.setMessage("加载中");
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




//               String url=Api.UpLoad_file+"?carid="+carId+"&fileName="+"IMG_20180510_104820.jpg";
//                Log.i(TAG, url);
//                Log.i(TAG, token);
//                uploadImage(selImageList);


            }
        });
//        chosepic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                List<TieBean> strings = new ArrayList<TieBean>();
//                strings.add(new TieBean("拍照"));
//                strings.add(new TieBean("相册"));
//                DialogUIUtils.showSheet(AddBendActivity.this, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
//                    @Override
//                    public void onItemClick(CharSequence text, int position) {
//                        Log.i("picici", (text + "---" + position));
//                        if(position==0){
//                            //拍照
////                            takePhoto();
//                        }else{
////                            pickPhoto();
//                        }
//                    }
//
//                    @Override
//                    public void onBottomBtnClick() {
//
//                    }
//                }).show();
//            }
//        });
    }

    private void information1() {
        RequestParams params = new RequestParams(AddBendActivity.this);
        Log.e("DUIBI",carInfo);
        params.addFormDataPart("carInfo",carInfo);
        params.addFormDataPart("tokenString", token);
        params.addFormDataPart("jsoncallback", "");
        HttpRequest.post(Api.ADD_CAR_INFO, params, new JsonHttpRequestCallback() {
            @Override
            protected void onSuccess(Headers headers, JSONObject jsonObject) {
                super.onSuccess(headers, jsonObject);
                Log.i("ADD_CAR_INFO",jsonObject.toString() );
                AddCar addCar = JSONObject.parseObject(jsonObject.toString(), AddCar.class);

                Log.e("CHENGGONG", "CHEGNGONG");
                if(addCar.getResult()==0)
                {
                    //绑定设备功能
//                    Intent intent = new Intent(AddcarActivity.this, Bend_List.class);
//                    intent.putExtra("carID",addCar.getCarInfoID());
//                    startActivityForResult(intent, 1);
                    carId=addCar.getCarInfoID();
                    //添加设备
                    addBendList();
//                    Intent intent = new Intent(AddBendActivity.this, AddBendActivity.class);
//                    intent.putExtra("carID",addCar.getCarInfoID());ss
//                    startActivityForResult(intent, 1);
//                    setResult(RESULT_OK);
//                    finish();
                }else{
                    Toast.makeText(AddBendActivity.this,  addCar.getErrorMsg()+"",Toast.LENGTH_SHORT).show();
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
                //show  progressdialog
                progressDialog.setMessage("加载中");
                progressDialog.show();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                //hide progressdialog
//              setResult(RESULT_OK);
//                finish();
                if (progressDialog.isShowing() && progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });

    }

    private void addBendList() {
        List datalist=listViewAddDelAdapter1.getDta();
        if(datalist.size()==0){
            setResult(RESULT_OK);
            finish();
        }else{
            RequestParams params=new RequestParams();

            String dal="";
            for(int i=0;i<datalist.size();i++){
                String sds= (String) datalist.get(i);
                String[]lis=sds.split(",");
                //判断是否是最后一组数据
                if(i==datalist.size()-1){
                    dal=dal+ "{\"deviceid\":" +lis[3]+ ",\"erectplace\":\"" +lis[2]+ "\"}";
                }else{
                    dal=dal+ "{\"deviceid\":" +lis[3]+ ",\"erectplace\":\"" +lis[2]+ "\"},";
                }

            }

            String Strparam= "{\"carid\":\""+carId+"\",DataList:["+dal+"],\"tokenstring\":\""+token+"\"}";
            params.addFormDataPart("param",Strparam);
            Log.i(TAG, Api.Car_BindDevice);
            Log.i(TAG, Strparam);
            HttpRequest.post(Api.Car_BindDevice,params,new JsonHttpRequestCallback(){
                @Override
                protected void onSuccess(Headers headers, JSONObject jsonObject) {
                    super.onSuccess(headers, jsonObject);
                    Log.i("Car_BindDevice",jsonObject.toString());
                    if(jsonObject.getInteger("Result")==0){
                        Toast.makeText(AddBendActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    }else{
                        Toast.makeText(AddBendActivity.this,"添加出错，请重试",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(int errorCode, String msg) {
                    super.onFailure(errorCode, msg);
                    Toast.makeText(AddBendActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onStart() {
                    super.onStart();
                    progressDialog.setMessage("加载中");
                    progressDialog.show();
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    if (progressDialog.isShowing() && progressDialog != null) {
                        progressDialog.dismiss();
                    }
                }
            });
            Log.i(TAG, dal.toString());
        }
    }


    private void uploadImage(ArrayList<ImageItem> pathList) {
        String url=Api.UpLoad_file+"?carid="+carId+"&fileName="+"IMG_20180510_104820.jpg";
//        Map<String, String> params=new HashMap<>();
//        params.put("orderid","1805090003280");
        httpUtil.postFileRequest(url, null, pathList, new MyStringCallBack() {

            @Override
            public void onError(Call call, Exception e, int id) {
                super.onError(call, e, id);
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onResponse(String response, int id) {
                super.onResponse(response, id);
                //返回图片的地址
                Log.i(TAG, "onResponse: ");

            }
        });
    }
    /**
     * 拍照获取图片
     */
    private void takePhoto() {
        //执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if(SDState.equals(Environment.MEDIA_MOUNTED))
        {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//"android.media.action.IMAGE_CAPTURE"
            /***
             * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
             * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
             * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
             */
            ContentValues values = new ContentValues();
            photoUri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
            /**-----------------*/
            startActivityForResult(intent, SELECT_PIC_BY_TACK_PHOTO);
        }else{
            Toast.makeText(this,"内存卡不存在", Toast.LENGTH_LONG).show();
        }
    }
    /***
     * 从相册中取图片
     */
    private void pickPhoto() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(1== requestCode && resultCode == 3){
            bend_list=data.getStringArrayListExtra("sb_list");
            for(int i=0;i<bend_list.size();i++){
                listViewAddDelAdapter1.addData(bend_list.get(i));
            }
            lv_bend.setVisibility(View.VISIBLE);
            unbind.setVisibility(View.GONE);
            listViewAddDelAdapter1.notifyDataSetChanged();

        }
//        if(resultCode == Activity.RESULT_OK)
//        {
//            doPhoto(requestCode,data);
//        }

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null){
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                if (images != null){
                    selImageList.clear();
                    selImageList.addAll(images);
                    adapter.setImages(selImageList);
                }
            }
        }
    }
    /**
     * 选择图片后，获取图片的路径
     * @param requestCode
     * @param data
     */
    private void doPhoto(int requestCode,Intent data)
    {
        if(requestCode == SELECT_PIC_BY_PICK_PHOTO )  //从相册取图片，有些手机有异常情况，请注意
        {
            if(data == null)
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
            photoUri = data.getData();
            Log.i("doPhoto: ", photoUri.toString());
            if(photoUri == null )
            {
                Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
                return;
            }
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(photoUri, pojo, null, null,null);
        if(cursor != null )
        {
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            picPath = cursor.getString(columnIndex);
            cursor.close();
        }
        Log.i(TAG, "imagePath = "+picPath);
        if(picPath != null && ( picPath.endsWith(".png") || picPath.endsWith(".PNG") ||picPath.endsWith(".jpg") ||picPath.endsWith(".JPG")  ))
        {
            lastIntent.putExtra(KEY_PHOTO_PATH, picPath);
            Log.i(TAG, "最终选择的图片="+picPath);
            Bitmap bm = BitmapFactory.decodeFile(picPath);
//            chosepic.setImageBitmap(bm);
        }else{
            Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public String getHttpTaskKey() {
        return HTTP_TASK_KEY;
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("onItemClick: ",position+"相册" );
        switch (position) {
            case IMAGE_ITEM_ADD:
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("拍照"));
                strings.add(new TieBean("相册"));
                DialogUIUtils.showSheet(AddBendActivity.this, strings, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        Log.i("picici", (text + "---" + position));
                        if(position==0){
                            //拍照
//                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                            Intent intent = new Intent(AddBendActivity.this, ImageGridActivity.class);
                            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                            startActivityForResult(intent, REQUEST_CODE_SELECT);
                        }else{
//                            //打开选择,本次允许选择的数量
                            ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                            Intent intent1 = new Intent(AddBendActivity.this, ImageGridActivity.class);
                            startActivityForResult(intent1, REQUEST_CODE_SELECT);
                        }
                    }

                    @Override
                    public void onBottomBtnClick() {

                    }
                }).show();
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                intentPreview.putExtra(ImagePicker.EXTRA_FROM_ITEMS,true);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }
}
