package com.hivee2.mvp.model.bean;

import com.zhy.tree.TreeNodeLabel1;
import com.zhy.tree.TreeNodeLabel2;
import com.zhy.tree.TreeNodeUseid;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

public class NewFileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;

	private int length;
	private String desc;

	public NewFileBean(int _id, int parentId, String name)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;

	}

}
