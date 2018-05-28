package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.GenfenceBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/8.
 */
public class GoeFenceAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<GenfenceBean.DataListBean> ais;


    public GoeFenceAdapter(Context context, List<GenfenceBean.DataListBean> ais)
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
            convertView=mInflater.inflate(R.layout.fenceitem, null);
            holder = new ViewHolder();
            holder.name=(TextView)convertView.findViewById(R.id.textView52);
            holder.state=(TextView)convertView.findViewById(R.id.textView53);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        Log.e("login1", "123321" + position);

        holder.name.setText(ais.get(position).getCarNumber());
        holder.state.setText(ais.get(position).getPledgerName()+"/"+ais.get(position).getInternalNum()+"/"+ais.get(position).getModel());

        return convertView;
    }
    private static class ViewHolder {

        private TextView name;
        private TextView state;


    }

}
