package com.hivee2.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.NewOrderListBean;
import com.hivee2.mvp.model.bean.OrderListBean;
import com.hivee2.mvp.ui.OrderDetailActivity;
import com.hivee2.mvp.ui.OrderManagerActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/3/13.
 */

public class OrderListAdapter extends BaseAdapter {
    private OrderManagerActivity context;
    private List<NewOrderListBean.NewOrderBean> orderlist;

    private LayoutInflater mInflater;
    public OrderListAdapter(OrderManagerActivity context, List<NewOrderListBean.NewOrderBean> orderlist){
        this.context=context;
        this.orderlist=orderlist;
        mInflater= LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return orderlist.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(contentView==null){
            viewHolder=new ViewHolder();
            contentView=mInflater.inflate(R.layout.order_list_item,null);
            viewHolder.orderid= (TextView) contentView.findViewById(R.id.orderid);
            viewHolder.createtime= (TextView) contentView.findViewById(R.id.createtime);
            viewHolder.pledger_name= (TextView) contentView.findViewById(R.id.pledger_name);
            viewHolder.car_vin= (TextView) contentView.findViewById(R.id.car_vin);
            viewHolder.need= (TextView) contentView.findViewById(R.id.need);
            viewHolder.install_address= (TextView) contentView.findViewById(R.id.install_address);
            viewHolder.status= (TextView) contentView.findViewById(R.id.status);
            viewHolder.detail= (LinearLayout) contentView.findViewById(R.id.detail);
            contentView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) contentView.getTag();
        }
        final NewOrderListBean.NewOrderBean orderBean;
        orderBean=orderlist.get(i);
        ArrayList<NewOrderListBean.NewOrderBean.NewSBBean>newSBBeans=orderBean.getDeviceg();
        viewHolder.orderid.setText("工单号:"+orderBean.getId());
        viewHolder.createtime.setText(orderBean.getCreatetime());
        viewHolder.pledger_name.setText("借款人:"+orderBean.getPledgername());
        viewHolder.car_vin.setText("车架号:"+orderBean.getCarvin());
        if(newSBBeans==null){
            viewHolder.need.setText("要求安装设备:无");
        }else{
            String nedd="要求安装设备:";
            for(int j=0;j<newSBBeans.size();j++){

                if(j==0){
                    nedd= nedd+newSBBeans.get(j).getType()+"("+newSBBeans.get(j).getCount()+"个)";
                }else{
                    nedd= nedd+"、"+newSBBeans.get(j).getType()+"("+newSBBeans.get(j).getCount()+"个)";
                }
            }
            viewHolder.need.setText(nedd);
        }

        viewHolder.install_address.setText("安装地址:"+orderBean.getInstalladdress());
        if(orderBean.getStatusname().equals("已撤销")){
            viewHolder.status.setTextColor(0xffff0000);
            viewHolder.status.setText(orderBean.getStatusname());
        }else{
            viewHolder.status.setTextColor(0xff6593cb);
            viewHolder.status.setText(orderBean.getStatusname());
        }
        viewHolder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(context, OrderDetailActivity.class);
                intent.putExtra("orderid",orderBean.getId());
                intent.putExtra("createtime",orderBean.getCreatetime());
//                intent.putExtra("createtime",orderBean.getCreatetime());
//                intent.putExtra("status",orderBean.getCreatetime());
                context.startActivity(intent);
            }
        });
        return contentView;
    }
    public static class ViewHolder{
        private TextView orderid;
        private TextView createtime;
        private TextView pledger_name;
        private TextView car_vin;
        private TextView need;//这个暂时没有
        private TextView install_address;
        private TextView status;
        private LinearLayout detail;
    }
}
