package cn.projects.com.projectsdemo.treeview.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

import cn.projects.com.projectsdemo.treeview.TreeHelper;
import cn.projects.com.projectsdemo.treeview.bean.Node;

/**
 * Created by fengxing on 2017/4/28.
 */

public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    private Context mContext;
    protected List<Node> mAllData;
    protected List<Node> mVisibleNodes;
    protected LayoutInflater mInflater;

    public interface OnTreeNodeClickListener{
        void onClick(Node node ,int position);
    }

    public OnTreeNodeClickListener treeNodeClickListener;

    public void  setTreeNodeClickListener(OnTreeNodeClickListener treeNodeClickListener){
        this.treeNodeClickListener = treeNodeClickListener;
    }

    /**
     * @param context
     * @param treeView
     * @param data
     * @param defaultExpandLevel 默认展示几级树
     */
    public TreeListViewAdapter(Context context, final ListView treeView, List<T> data, int defaultExpandLevel) {

        this.mContext = context;

        //对所有的node进行排序
        mAllData = TreeHelper.getSortedNodes(data, defaultExpandLevel);

        //过滤出所有可见的Node
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllData);

        mInflater = LayoutInflater.from(context);

        //设置节点 点击时 可以展开和关闭  并且将事件可以继续往外公布
        treeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                expandOrCollapse(position);

                if(treeNodeClickListener != null){
                    treeNodeClickListener.onClick(mVisibleNodes.get(position),position);
                }
            }
        });
    }

    /**
     * 根据点击的item 展开或者折叠
     * @param position
     */
    private void expandOrCollapse(int position) {
        Node node = mVisibleNodes.get(position);
        if(node!=null){

            if(!node.isLeaf()){
                node.setExpand(!node.isExpand);
                mVisibleNodes = TreeHelper.filterVisibleNodes(mAllData);
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = mVisibleNodes.get(position);
        convertView = getConvertView(node,position,convertView,parent);
        int left = node.getLevel() * 50;
        convertView.setPadding(left,3,3,3);
        return convertView;
    }

    public abstract View getConvertView(Node node, int position, View convertView, ViewGroup parent);
}
