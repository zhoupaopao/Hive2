package com.hivee2.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.GetDeviceBean;

import java.util.List;

/**
 * Created by 狄飞 on 2016/7/21.
 */
public class GetDeviceAdapter extends BaseAdapter  {
    private LayoutInflater mInflater;
    private Context context;
    private List<GetDeviceBean.DataListBean> ais;
    public String[]arr=new String[4];


    public GetDeviceAdapter(Context context, List<GetDeviceBean.DataListBean> ais)
    {
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.ais = ais;

    }

    public String[] getArr(){
        return arr;
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
            convertView=mInflater.inflate(R.layout.nell_item2, null);
            holder = new ViewHolder();
            holder.nellNumber=(TextView)convertView.findViewById(R.id.textView29);
            holder.nellclock=(EditText)convertView.findViewById(R.id.editText14);
            holder.sure=(Button)convertView.findViewById(R.id.button2);
            convertView.setTag(holder);
        } else {
            holder=(ViewHolder) convertView.getTag();
        }
        Log.e("login1", "123321aaa" + position);
//        holder.nellNumber.setText("闹铃" + ais.get(position).getRepeatmode() + ":");
        int pp=position+1;
        holder.nellNumber.setText("闹铃" + pp + ":");
        holder.nellclock.setText(ais.get(position).getClock());
        arr[position]=ais.get(position).getClock();
        if(String.valueOf(holder.nellclock.getText()).equals(""))
        {
            arr[position]=ais.get(position).getClock();
            Log.e("shijian",String.valueOf(holder.nellclock.getText()));
            holder.nellclock.setText(ais.get(position).getClock());
        }


        holder.sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("shijian",String.valueOf(holder.nellclock.getText()));
                arr[position]=String.valueOf(holder.nellclock.getText());
                Toast.makeText(context, "修改闹铃"+"成功",
                        Toast.LENGTH_SHORT).show();
            }


        });

        return convertView;
    }
    private static class ViewHolder {

        private TextView nellNumber;
        private EditText nellclock;
        private Button sure;

    }
}
