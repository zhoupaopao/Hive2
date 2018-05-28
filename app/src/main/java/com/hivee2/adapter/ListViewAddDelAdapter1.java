package com.hivee2.adapter;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.dou361.dialogui.DialogUIUtils;
import com.dou361.dialogui.bean.TieBean;
import com.dou361.dialogui.listener.DialogUIItemListener;
import com.dou361.dialogui.listener.DialogUIListener;
import com.hivee2.R;
import com.hivee2.mvp.ui.AddBendActivity;
import com.hivee2.mvp.ui.AddBendListDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/5/8.
 */

public class ListViewAddDelAdapter1 extends BaseAdapter {
    private AddBendActivity context;
    private List<String > datas = new ArrayList<String>();

    public ListViewAddDelAdapter1(AddBendActivity context) {
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
        String[]aa=text.split(",");
        // 设置文本
        ((TextView) convertView.findViewById(R.id.sbm)).setText(aa[0]);
        ((TextView) convertView.findViewById(R.id.sblx)).setText(aa[1]);
        ((TextView) convertView.findViewById(R.id.azwz)).setText(aa[2]);
        ((TextView) convertView.findViewById(R.id.sbh)).setText(aa[3]);



        ((TextView) convertView.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.del(position);
            }
        });
        return convertView;
    }

}
