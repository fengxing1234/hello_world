package cn.projects.com.projectsdemo.treeview.bean;

import cn.projects.com.projectsdemo.treeview.TreeNodeId;
import cn.projects.com.projectsdemo.treeview.TreeNodeLabel;
import cn.projects.com.projectsdemo.treeview.TreeNodePId;

/**
 * Created by fengxing on 2017/4/29.
 */

public class FileBean extends Node{
    @TreeNodeId
    private int _id;
    @TreeNodePId
    private int parentId;
    @TreeNodeLabel
    private String name;

    private long length;
    private String desc;

    public FileBean(int _id, int parentId, String name) {
        super(_id,parentId,name);
        this._id = _id;
        this.parentId = parentId;
        this.name = name;
    }


}
