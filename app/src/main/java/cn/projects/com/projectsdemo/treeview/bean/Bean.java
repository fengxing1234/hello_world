package cn.projects.com.projectsdemo.treeview.bean;

import cn.projects.com.projectsdemo.treeview.TreeNodeId;
import cn.projects.com.projectsdemo.treeview.TreeNodeLabel;
import cn.projects.com.projectsdemo.treeview.TreeNodePId;

/**
 * Created by fengxing on 2017/4/29.
 */

public class Bean {
    @TreeNodeId
    private int id;
    @TreeNodePId
    private int pId;
    @TreeNodeLabel
    private String label;

    public Bean()
    {
    }

    public Bean(int id, int pId, String label)
    {
        this.id = id;
        this.pId = pId;
        this.label = label;
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

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }
}
