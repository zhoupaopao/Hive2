package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.DeviceBean2;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/21.
 */
public class WirlessAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<DeviceBean2.DataListBean> ais;


    public WirlessAdapter(Context context, List<DeviceBean2.DataListBean> ais)
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
            convertView=mInflater.inflate(R.layout.nell_item, null);
            holder = new ViewHolder();
            holder.deviceNumber=(TextView)convertView.findViewById(R.id.textView29);
            holder.devicewirless=(TextView)convertView.findViewById(R.id.textView30);
            holder.devicemessage=(TextView)convertView.findViewById(R.id.message1);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        Log.e("login1", "123321" + position);
        String message1="";
        message1=String.valueOf(ais.get(position).getDeviceID());
        Log.e("CHUXIAN",message1);
        holder.deviceNumber.setText("设备号："+message1);
        if(ais.get(position).isHighFrequency())
        {
            holder.devicewirless.setText("有线");
        }
       else {
            holder.devicewirless.setText("无线");
        }
        String message2=String.valueOf(ais.get(position).getInternalNum());
        String message3=String.valueOf(ais.get(position).getPledgerName());
        String message4=String.valueOf(ais.get(position).getCarNumber());
        Log.i("dsdasdad",String.valueOf(ais.get(position).getCanSetClock()));
        holder.devicemessage.setText(message2+"/"+message3+"/"+message4);
        return convertView;
    }
    private static class ViewHolder {

        private TextView deviceNumber;
        private TextView devicewirless;
        private TextView devicemessage;

    }
}
