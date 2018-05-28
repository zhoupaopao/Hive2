package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.ReplayBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/22.
 */
public class Replay_Adapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<ReplayBean.DataListBean> ais;


    public Replay_Adapter(Context context, List<ReplayBean.DataListBean> ais)
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
            convertView=mInflater.inflate(R.layout.replaylist_item, null);
            holder = new ViewHolder();

            holder.time=(TextView)convertView.findViewById(R.id.textView66);
            holder.place=(TextView)convertView.findViewById(R.id.textView67);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        if(Integer.valueOf(ais.get(position).getType())==4000){
            holder.time.setText(ais.get(position).getPositionTime()+"   "+"GPS定位");
        }else if(Integer.valueOf(ais.get(position).getType())==5000){
            holder.time.setText(ais.get(position).getPositionTime()+"   "+"wifi定位");
        }else{
            holder.time.setText(ais.get(position).getPositionTime()+"   "+"基站定位");
        }
        Log.e("login1", "123321" + position);
        holder.place.setText(ais.get(position).getAddress());






//
        return convertView;
    }

    private static class ViewHolder {

        private TextView time;
        private TextView place;


    }
}
