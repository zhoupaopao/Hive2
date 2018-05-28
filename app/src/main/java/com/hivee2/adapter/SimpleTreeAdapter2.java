package com.hivee2.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hivee2.R;
import com.zhy.tree.Node2;

import java.util.List;

public class SimpleTreeAdapter2<T> extends TreeListViewAdapter2<T>
{

	public SimpleTreeAdapter2(ListView mTree, Context context, List<T> datas,
							  int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		super(mTree, context, datas, defaultExpandLevel);
	}



	@Override
	public View getConvertView(Node2 node , final int position, View convertView, ViewGroup parent)
	{

		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.list_item2, parent, false);
			viewHolder = new ViewHolder();
			ImageView icon =(ImageView) convertView
					.findViewById(R.id.id_treenode_icon);
			if(node.isExpand()){
				icon.setImageResource(R.mipmap.tree_ex);
			}else{
				icon.setImageResource(R.mipmap.tree_ec);
			}
			icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					expandOrCollapse(position);
				}
			});
			viewHolder.icon = icon;
			viewHolder.label = (TextView) convertView
					.findViewById(R.id.id_treenode_label);
			convertView.setTag(viewHolder);

		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
			if(node.isExpand()){
				viewHolder.icon.setImageResource(R.mipmap.tree_ex);
			}else{
				viewHolder.icon.setImageResource(R.mipmap.tree_ec);
			}
		}

		if (node.getIcon() == -1)
		{
			viewHolder.icon.setVisibility(View.INVISIBLE);
		} else
		{
			viewHolder.icon.setVisibility(View.VISIBLE);
//			viewHolder.icon.setImageResource(node.getIcon());
		}
		viewHolder.label.setText(node.getName());
		return convertView;
	}

	private final class ViewHolder
	{
		ImageView icon;
		TextView label;
	}

}
