package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.UnbindBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/8.
 */
public class UnbindAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<UnbindBean.DataListBean> ais;


    public UnbindAdapter(Context context, List<UnbindBean.DataListBean> ais)
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
            convertView=mInflater.inflate(R.layout.unbinditem, null);
            holder = new ViewHolder();
            holder.message=(TextView)convertView.findViewById(R.id.textView65);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        Log.e("login1", "123321" + position);
        holder.message.setText(ais.get(position).getInternalNum()+"-"+ais.get(position).getModel()+"-"+ais.get(position).getDeviceID());
        return convertView;
    }
    private static class ViewHolder {

        private TextView message;
    }

}
