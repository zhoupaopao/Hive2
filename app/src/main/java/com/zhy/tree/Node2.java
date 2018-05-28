package com.zhy.tree;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Node2
{

    private int id;
    /**
     * 根节点pId为0
     */
    private int pId = 0;
    private String name;

    private int number;


    /**
     * 当前的级别
     */
    private int level;
    /**
     * 是否展开
     */
    private boolean isExpand = false;

    private int icon;

    /**
     * 下一级的子Node
     */
    private List<Node2> children = new ArrayList<Node2>();

    /**
     * 父Node
     */
    private Node2 parent;

    public Node2()
    {
    }

    public Node2(int id, int pId, String name, int number)
    {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.number=number;

    }

    public int getIcon()
    {
        return icon;
    }

    public void setIcon(int icon)
    {
        this.icon = icon;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getpId()
    {
        return pId;
    }

    public void setpId(int pId)
    {
        this.pId = pId;
    }


    public int getNumber()
    {
        return  number;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setNumber(int number){this.number=number;}

    public void setLevel(int level)
    {
        this.level = level;
    }

    public boolean isExpand()
    {
        return isExpand;
    }

    public void setExpand(boolean isExpand)
    {
        this.isExpand = isExpand;
        if (!isExpand)
        {

            Log.e("TAG", name + " , " + "shousuo ");
            for (Node2 node : children)
            {
                node.setExpand(isExpand);
            }
        }
    }

    public List<Node2> getChildren()
    {
        return children;
    }

    public void setChildren(List<Node2> children)
    {
        this.children = children;
    }

    public Node2 getParent()
    {
        return parent;
    }

    public void setParent(Node2 parent)
    {
        this.parent = parent;
    }

    /**
     * 是否为跟节点
     *
     * @return
     */
    public boolean isRoot()
    {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand()
    {
        if (parent == null)
            return false;
        return parent.isExpand();
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf()
    {
        return children.size() == 0;
    }

    /**
     * 获取level
     */
    public int getLevel()
    {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    @Override
    public String toString()
    {
        return "Node [name=" + name + ", isExpand=" + isExpand + ", children="
                + children.size() + ", parent="
                + (parent == null ? "" : parent.name) + ", icon=" + icon+ "]";
    }

}
