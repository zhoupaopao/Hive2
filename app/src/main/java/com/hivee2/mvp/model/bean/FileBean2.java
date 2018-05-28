package com.hivee2.mvp.model.bean;

import com.zhy.tree.TreeNodeLabel2;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

public class FileBean2
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	@TreeNodeLabel2
	private int number;

	private int length;
	private String desc;

	public FileBean2(int _id, int parentId, String name, int number)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
		this.number=number;
	}
}
