package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.DeviceInforBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/22.
 */
public class AccountAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<DeviceInforBean.DataListBean> ais;


    public AccountAdapter(Context context, List<DeviceInforBean.DataListBean> ais)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.ais = ais;
    }

    @Override
    public int getCount() {
        return ais.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return ais.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.accountlist_item, null);
            holder = new ViewHolder();
            holder.onLineType=(ImageView)convertView.findViewById(R.id.imageView10);
            holder.carname=(TextView)convertView.findViewById(R.id.textView43);
            holder.internalNum=(TextView)convertView.findViewById(R.id.textView);
            holder.isBindCar=(TextView)convertView.findViewById(R.id.textView1);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        Log.e("login1", "123321" + position);
        if(ais.get(position).isIsOnline()==true)
        {
            holder.onLineType.setImageResource(R.mipmap.onlinecar);
        }
        else {
            holder.onLineType.setImageResource(R.mipmap.offlinecar);
        }
        holder.carname.setText(ais.get(position).getInternalNum());
        holder.internalNum.setText(ais.get(position).getModel());
        if(ais.get(position).isIsBindCar()==true)
        {
            holder.isBindCar.setText("不可回收");
        }
        else {
            holder.isBindCar.setText("可回收");
        }

//
        return convertView;
    }

    private static class ViewHolder {

        private ImageView onLineType;
        private TextView carname;
        private TextView internalNum;
        private TextView isBindCar;


    }
}
