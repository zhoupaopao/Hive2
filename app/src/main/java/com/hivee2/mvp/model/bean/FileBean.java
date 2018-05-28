package com.hivee2.mvp.model.bean;

import com.zhy.tree.TreeNodeLabel1;
import com.zhy.tree.TreeNodeLabel2;
import com.zhy.tree.TreeNodeUseid;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

public class FileBean
{
	@TreeNodeId
	private int _id;
	@TreeNodePid
	private int parentId;
	@TreeNodeLabel
	private String name;
	@TreeNodeLabel1
	private int qty;
	@TreeNodeLabel2
	private int temQty;
	@TreeNodeUseid
	private String useId;
	private int length;
	private String desc;

	public FileBean(int _id, int parentId, String name, int qty,int temQty,String useId)
	{
		super();
		this._id = _id;
		this.parentId = parentId;
		this.name = name;
		this.qty=qty;
		this.temQty=temQty;
		this.useId=useId;
	}

}
