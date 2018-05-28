package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.UserchengeBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/21.
 */
public class AtychildAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<UserchengeBean.messageBean> ais;


    public AtychildAdapter(Context context, List<UserchengeBean.messageBean> ais)
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
            convertView=mInflater.inflate(R.layout.list_item3, null);
            holder = new ViewHolder();

            holder.label = (TextView) convertView.findViewById(R.id.id_treenode_label);
            holder.qty=(TextView)convertView.findViewById(R.id.textView47);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        Log.e("login1", "123321" + position);

        holder.label.setText(ais.get(position).getCustomerName());
        holder.qty.setText(ais.get(position).getAleaQty()+"/"+ais.get(position).getTempQty());
        return convertView;
    }
    private static class ViewHolder {

        TextView label;
        TextView qty;

    }
}
