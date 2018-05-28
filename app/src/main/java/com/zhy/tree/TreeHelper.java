package com.zhy.tree;

import com.hivee2.R;
import com.zhy.tree.bean.TreeNodeId;
import com.zhy.tree.bean.TreeNodeLabel;
import com.zhy.tree.bean.TreeNodePid;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;



public class TreeHelper
{

    /**
     * 过滤出所有可见的Node
     *
     * @param nodes
     * @return
     */
    public static List<Node> filterVisibleNode(List<Node> nodes)
    {
        List<Node> result = new ArrayList<Node>();

        for (Node node : nodes)
        {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() || node.isParentExpand())
            {
                setNodeIcon(node);
                result.add(node);
            }
        }

        return result;
    }

    /**
     * 把一个节点上的所有的内容都挂上去
     *
     *
     */
    public static void addNode(List<Node> nodes, Node node,
                               int defaultExpandLeval, int currentLevel)
    {

        nodes.add(node);
        if (defaultExpandLeval >= currentLevel)
        {
            node.setExpand(true);
        }

        if (node.isLeaf())
            return;
        for (int i = 0; i < node.getChildren().size(); i++)
        {
            addNode(nodes, node.getChildren().get(i), defaultExpandLeval,
                    currentLevel + 1);
        }
    }

    public static List<Node> getRootNodes(List<Node> nodes)
    {
        List<Node> root = new ArrayList<Node>();
        for (Node node : nodes)
        {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }

    /**
     * 将我们的数据转化为树的节点
     *
     * @param datas
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> List<Node> convetData2Node(List<T> datas)
            throws IllegalArgumentException, IllegalAccessException

    {
        List<Node> nodes = new ArrayList<Node>();
        Node node = null;

        for (T t : datas)
        {
            int id = -1;
            int pId = -1;
            String label = null;
            String useid=null;
            String customer=null;
            int aty=-1;
            int temQty=-1;
            Class<? extends Object> clazz = t.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field f : declaredFields)
            {
                if (f.getAnnotation(TreeNodeId.class) != null)
                {
                    f.setAccessible(true);
                    id = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodePid.class) != null)
                {
                    f.setAccessible(true);
                    pId = f.getInt(t);
                }
                if (f.getAnnotation(TreeNodeLabel.class) != null)
                {
                    f.setAccessible(true);
                    label = (String) f.get(t);
                }
                if (f.getAnnotation(TreeNodeLabel1.class) != null)
                {
                    f.setAccessible(true);
                    aty = (int) f.get(t);
                }
                if (f.getAnnotation(TreeNodeLabel2.class) != null)
                {
                    f.setAccessible(true);
                    temQty = (int) f.get(t);
                }
                if (f.getAnnotation(TreeNodeUseid.class) != null)
                {
                    f.setAccessible(true);
                    useid= (String) f.get(t);
                }
                if (f.getAnnotation(TreeNodeCustomer.class) != null)
                {
                    f.setAccessible(true);
                   customer= (String) f.get(t);
                }
                if (id != -1 && aty!= -1 && temQty!=-1 &&pId != -1 && label != null&& useid!=null&&customer!=null)
                {
                    break;
                }
            }

            node = new Node(id, pId, label,aty,temQty,useid,customer);
            nodes.add(node);
        }

        /**
         * 设置Node间，父子关系;让每两个节点都比较一次，即可设置其中的关系
         */
        for (int i = 0; i < nodes.size(); i++)
        {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++)
            {
                Node m = nodes.get(j);
                if (m.getpId() == n.getId())
                {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId())
                {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        // 设置图片
        for (Node n : nodes)
        {
            setNodeIcon(n);
        }
        return nodes;
    }

    /**
     * 传入我们的普通bean，转化为我们排序后的Node
     *
     * @param datas
     * @return
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static <T> List<Node> getSortedNodes(List<T> datas,
                                                int defaultExpandLevel) throws IllegalArgumentException,
            IllegalAccessException

    {
        List<Node> result = new ArrayList<Node>();
        List<Node> nodes = convetData2Node(datas);
        List<Node> rootNodes = getRootNodes(nodes);
        for (Node node : rootNodes)
        {
            addNode(result, node, defaultExpandLevel, 1);
        }

        return result;
    }

    /**
     * 设置节点的图标
     *
     * @param node
     */
    public static void setNodeIcon(Node node)
    {
        if (node.getChildren().size() > 0 && node.isExpand())
        {
            node.setIcon(R.drawable.tree_ex);
        } else if (node.getChildren().size() > 0 && !node.isExpand())
        {
            node.setIcon(R.drawable.tree_ec);
        } else
            node.setIcon(-1);

    }

}

