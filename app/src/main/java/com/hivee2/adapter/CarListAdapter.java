package com.hivee2.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hivee2.R;
import com.hivee2.mvp.model.bean.DeviceBean;
import com.hivee2.mvp.model.bean.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gewubin on 2016/7/17
 * email: gewubin95@qq.com
 */
public class CarListAdapter extends BaseExpandableListAdapter{
    Map<String, ArrayList<DeviceBean.CarListBean>> map = new HashMap<String, ArrayList<DeviceBean.CarListBean>>();
    private LayoutInflater mInflater;
    private Context context;
    private ArrayList<Group> group=new ArrayList<Group>();

    public CarListAdapter(Context context, ArrayList<Group> group, Map<String, ArrayList<DeviceBean.CarListBean>> map){
        this.context=context;
        this.group=group;
        this.map=map;
    }


    @Override
    public int getGroupCount() {
        Log.e("OOOOOT",group.size()+"");
        return group.size();

    }

    // 获取当前父item下的子item的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        if(group.size()==0){
            return 0;
        }
        Group key = group.get(groupPosition);
        int size = map.get(key.getGroupName()).size();
        Log.e("OOOOOT",size+"");
        return size;
    }

    // 获取当前父item的数据
    @Override
    public Object getGroup(int groupPosition) {
        if(group.size()==0){
            return 0;
        }
        return group.get(groupPosition);
    }

    // 得到子item需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if(group.size()==0){
            return 0;
        }
        Group key = group.get(groupPosition);
        return (map.get(key).get(childPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        if(group.size()==0){
            return 0;
        }
        return groupPosition;
    }

    // 得到子item的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_parent, null);
        }
        TextView tv = (TextView) convertView
                .findViewById(R.id.parent_textview);
        tv.setText("         "
                + group.get(groupPosition).getGroupName()
                + "(" + getChildrenCount(groupPosition) + ")");
        return tv;
    }

    // 设置子item的组件
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Group key = group.get(groupPosition);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_children, null);
            holder = new ViewHolder();
            holder.lc_name = (TextView) convertView
                    .findViewById(R.id.lc_name);
            holder.lc_isTrack=(TextView) convertView
                    .findViewById(R.id.lc_isTrack);
            holder.lc_carnum = (TextView) convertView
                    .findViewById(R.id.lc_carnum);
//				holder.lc_img = (ImageView) convertView
//						.findViewById(R.id.lc_img);
            holder.lc_isonline = (TextView) convertView
                    .findViewById(R.id.lc_isonline);
            holder.lc_person = (TextView) convertView
                    .findViewById(R.id.lc_person);
            holder.lc_state = (TextView) convertView
                    .findViewById(R.id.lc_state);
            holder.lc_adress = (TextView) convertView
                    .findViewById(R.id.adress);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //CategoryName参数暂时没有
//        if(map.get(key.getGroupID()).get(childPosition)
//                .getCategoryName().equals("无线设备")){
//            holder.lc_person.setTextColor(Color.RED);
//        }else if(map.get(key.getGroupID()).get(childPosition)
//                .getCategoryName().equals("有线设备")){
//            holder.lc_person.setTextColor(Color.BLUE);
//        }else if(map.get(key.getGroupID()).get(childPosition)
//                .getCategoryName().equals("OBD")){
//            holder.lc_person.setTextColor(Color.parseColor("#ffdd00"));
//        }
        if(map.get(key.getGroupName()).get(childPosition)
                .isIsTrack()){//已经开启追踪
            holder.lc_isTrack.setText("追踪开启");
            holder.lc_isTrack.setTextColor(Color.RED);
        }else{
            holder.lc_isTrack.setText("追踪关闭");
            holder.lc_isTrack.setTextColor(Color.GRAY);
        }

        holder.lc_name.setText("设备名："+map.get(key.getGroupName()).get(childPosition)
                .getInternalNum());
        holder.lc_carnum.setText( map.get(key.getGroupName()).get(childPosition)
                .getCarNumber());
        String me;
        me=map.get(key.getGroupName()).get(childPosition)
                .getPledgerName();
        Log.e("RRRR",me);
        if(me.equals("暂无"))
        {
            holder.lc_person.setText("借款人：暂无");
        }
        else {
            holder.lc_person.setText("借款人："
                    + map.get(key.getGroupName()).get(childPosition)
                    .getPledgerName());
        }
if(map.get(key.getGroupName()).get(childPosition).getStatus()==2){
    holder.lc_adress.setText("设备暂无定位"
           );
}
else if (map.get(key.getGroupName()).get(childPosition).getStatus()==1){
    holder.lc_adress.setText("设备过期，请充值查看");
}else {
    holder.lc_adress.setText("地址："
            + map.get(key.getGroupName()).get(childPosition)
            .getAddress());
}

//        System.out.println("childPos-->" + childPosition + "BS-->"
//                + map.get(key.getGroupName()).get(childPosition).getBS());

        Log.e("SHUCHU",map.get(key.getGroupName()).get(childPosition).getBS());
        if (map.get(key.getGroupName()).get(childPosition).getBS().equals("正常")) {
            holder.lc_state.setText("正常");
            holder.lc_state.setTextColor(Color.BLACK);
        } else if (map.get(key.getGroupName()).get(childPosition).getBS().equals("光感异常")) {
            holder.lc_state.setText("光感异常");
            holder.lc_state.setTextColor(Color.RED);
        } else if (map.get(key.getGroupName()).get(childPosition).getBS().equals("断电")) {
            holder.lc_state.setText("断电");
            holder.lc_state.setTextColor(Color.RED);
        }  else if (map.get(key.getGroupName()).get(childPosition).getBS().equals("拔除")) {
            holder.lc_state.setText("光感异常");
            holder.lc_state.setTextColor(Color.RED);
        } else {
            holder.lc_state.setText("");
            holder.lc_state.setTextColor(Color.BLACK);
        }
        // else
        // if(map.get(key.getGroupID()).get(childPosition).getBS()==-1){
        // holder.lc_state.setText("插拔状态:正常");
        // }
        Log.e("OOOOOTT",map.get(key.getGroupName()).size()+"")  ;
        if (map.get(key.getGroupName()).get(childPosition).isIsOnline() == true) {
            holder.lc_isonline.setText("车辆在线");
            holder.lc_isonline.setTextColor(Color.GREEN);
            //holder.lc_img.setImageResource(R.drawable.car_online1);
        }else if(map.get(key.getGroupName()).get(childPosition).getStatus()==2){
            //车辆无定位
            holder.lc_isonline.setText("未启用");
            holder.lc_isonline.setTextColor(Color.GRAY);
        } else {
            holder.lc_isonline.setText("车辆离线");
            holder.lc_isonline.setTextColor(Color.GRAY);
            //holder.lc_img.setImageResource(R.drawable.car_static1);
        }
        return convertView;


    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private static class ViewHolder {

        private TextView lc_name;
        private TextView lc_carnum;
        //private ImageView lc_img;
        private TextView lc_isonline;
        private TextView lc_isTrack;
        private TextView lc_person;
        private TextView lc_state;
        private TextView lc_adress;
    }
}
