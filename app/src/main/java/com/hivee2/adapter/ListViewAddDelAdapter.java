package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.hivee2.R;
import com.hivee2.mvp.model.bean.BendBean;
import com.hivee2.mvp.model.bean.UnbindBean;
import com.hivee2.mvp.ui.AddBendListDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/5/8.
 */

public class ListViewAddDelAdapter extends BaseAdapter {
    private AddBendListDetail context;
    private List<String > datas = new ArrayList<String>();

    public ListViewAddDelAdapter(AddBendListDetail context) {
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

        convertView = View.inflate(context, R.layout.block_gym_album_list_item, null);
//        String text = datas.get(position).getSbm();
//        String text1 = datas.get(position).getSblx();
//        String text2=datas.get(position).getAzwz();
        String text = datas.get(position);
        String[]aa=text.split(",");
        // 设置文本
        ((TextView) convertView.findViewById(R.id.sbm)).setText(aa[0]);
        ((TextView) convertView.findViewById(R.id.sblx)).setText(aa[1]);
        ((TextView) convertView.findViewById(R.id.azwz)).setText(aa[2]);
        final View finalConvertView = convertView;
        ((TextView) convertView.findViewById(R.id.azwz)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<TieBean> strings = new ArrayList<TieBean>();
                strings.add(new TieBean("后座座椅下"));
                strings.add(new TieBean("副驾驶踏板"));
                strings.add(new TieBean("后备箱"));
                strings.add(new TieBean("主驾驶保险丝盒"));
                strings.add(new TieBean("手动输入"));
                DialogUIUtils.showSheet(context, strings, "", Gravity.CENTER, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, final int position1) {
                        if(position1==4){
                            DialogUIUtils.showAlert(context, null, "", "请输入安装位置", null, "确定", "取消", false, true, true, new DialogUIListener() {
                                @Override
                                public void onPositive() {

                                }

                                @Override
                                public void onNegative() {

                                }

                                @Override
                                public void onGetInput(CharSequence input1, CharSequence input2) {
                                    super.onGetInput(input1, input2);
//                                    showToast("input1:" + input1 + "--input2:" + input2);
                                    ((TextView) finalConvertView.findViewById(R.id.azwz)).setText(input1);
//                                    datas.get(position).setAzwz(input1.toString());
                                    String[] sda=datas.get(position).split(",");
                                    sda[2]=input1.toString();
                                    datas.set(position,sda[0]+","+sda[1]+","+sda[2]+","+sda[3]);
                                }
                            }).show();
                        }else{
                            ((TextView) finalConvertView.findViewById(R.id.azwz)).setText(text);
                            Log.i("onItemClick: ",position +"");
                            String sad=text.toString();
                            String[] sda=datas.get(position).split(",");
                            sda[2]=text.toString();
                            datas.set(position,sda[0]+","+sda[1]+","+sda[2]+","+sda[3]);
                        }

                    }
                }).show();
            }
        });

        ((Button) convertView.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.del(position);
            }
        });
        return convertView;
    }

}
