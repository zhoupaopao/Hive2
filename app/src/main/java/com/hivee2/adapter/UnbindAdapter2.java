package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.UnbindBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/8/8.
 */
public class UnbindAdapter2 extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<UnbindBean.DataListBean> ais;
    public    String messageword="";


    public String getmessage()
    {
        return messageword;
    }
    public void setErrorMsg(String messageword) {
        this.messageword = messageword;
    }
    public UnbindAdapter2(Context context, List<UnbindBean.DataListBean> ais)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.ais = ais;
        for(int i=0;i<ais.size();i++)
        {
            messageword=messageword+"0";
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.unbinditem1, null);
            holder = new ViewHolder();
            holder.message=(TextView)convertView.findViewById(R.id.textView65);
            holder.checkBox=(CheckBox)convertView.findViewById(R.id.checkBox8);
            holder.deleteHolder=(ViewGroup)convertView.findViewById(R.id.all);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
            Log.e("login1", "123321" + position+"--"+holder.checkBox.isChecked());
        }

        Log.e("CHECK", "" + holder.checkBox.isChecked());
        holder.message.setText(ais.get(position).getInternalNum() + "-" + ais.get(position).getModel() + "-" + ais.get(position).getDeviceID());
        for(int i=0;i<ais.size();i++)
        {
            if(messageword.substring(position,position+1).equals("1"))
            {
                holder.checkBox.setChecked(true);
            }
            else {
                holder.checkBox.setChecked(false);
            }
        }

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("login1", "123321" + position+"--"+holder.checkBox.isChecked());
                if(holder.checkBox.isChecked())
                {
                    if(position==0)
                    {
                        messageword="1"+messageword.substring(1,ais.size());
                    }
                    else if(position==(ais.size()-1))
                    {
                        messageword=messageword.substring(0,ais.size()-1)+"1";
                    }
                    else {
                        messageword=messageword.substring(0,position)+"1"+messageword.substring(position+1,ais.size());
                    }
                }
                else {
                    if(position==0)
                    {
                        messageword="0"+messageword.substring(1,ais.size());
                    }
                    else if(position==(ais.size()-1))
                    {
                        messageword=messageword.substring(0,ais.size()-1)+"0";
                    }
                    else {
                        messageword=messageword.substring(0,position)+"0"+messageword.substring(position+1,ais.size());
                    }
                }
            }
        });

        return convertView;
    }
    private static class ViewHolder {

        private TextView message;
        private CheckBox checkBox;
        public ViewGroup deleteHolder;
    }

}
