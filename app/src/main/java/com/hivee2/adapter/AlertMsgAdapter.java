package com.hivee2.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.hivee2.R;
import com.hivee2.mvp.model.bean.AlertBean;

import java.util.List;

public class AlertMsgAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private Context context;
	private List<AlertBean.AlertListBean> ais;
	
	
	public AlertMsgAdapter(Context context, List<AlertBean.AlertListBean> ais)
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
			convertView=mInflater.inflate(R.layout.alertmsglist1_item, null);
			holder = new ViewHolder();

			holder.alerttype=(TextView)convertView.findViewById(R.id.textView32);
		    holder.message1=(TextView)convertView.findViewById(R.id.message1);
			holder.time=(TextView)convertView.findViewById(R.id.textView29);
			convertView.setTag(holder);
		} else {
			holder=(ViewHolder) convertView.getTag();
		}
		Log.e("login1", "123321" + position);
		holder.message1.setText(String.valueOf(ais.get(position).getUsername()+"/"+ais.get(position).getCarNumber()+"/"+ais.get(position).getInternalNum()));
		Log.e("login1", "---------->" +String.valueOf(ais.get(position).getUsername()));
		holder.alerttype.setText(ais.get(position).getAlarmType());
		holder.time.setText(ais.get(position).getStarttime());
//
		return convertView;
	}
	
	private static class ViewHolder {
		

	private TextView message1;
	private TextView alerttype;
	private TextView time;
		
		
	}
	
}
