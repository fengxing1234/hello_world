package cn.projects.com.projectsdemo.treeview.bean;

import java.util.ArrayList;
import java.util.List;

import cn.projects.com.projectsdemo.treeview.TreeNodeId;
import cn.projects.com.projectsdemo.treeview.TreeNodeLabel;
import cn.projects.com.projectsdemo.treeview.TreeNodePId;

/**
 * Created by fengxing on 2017/4/28.
 */

public class Node {

    @TreeNodeId
    public int id;

    /**
     * 根结点的Pid为0
     */
    @TreeNodePId
    public int pid = 0;

    @TreeNodeLabel
    public String name;

    public int level;

    /**
     * 是否展开
     */
    public boolean isExpand = false;

    public int icon;

    /**
     * 下一级的子Node
     */
    public List<Node> children = new ArrayList<>();

    public Node parent;


    public Node() {
    }

    public Node(int id, int pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    /**
     * 是否是根结点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        } else {
            return parent.isExpand;
        }
    }

    /**
     * 判断是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 获取等级
     *
     * @return
     */
    public int getLevel() {
        if (parent == null) {
            return 0;
        } else {
            level = parent.level+1;
            return level;
        }
    }

    /**
     * 设置展开
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(isExpand);
            }
        }
    }

}
