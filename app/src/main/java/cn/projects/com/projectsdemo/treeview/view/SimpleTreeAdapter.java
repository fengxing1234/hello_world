package cn.projects.com.projectsdemo.treeview.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import cn.projects.com.projectsdemo.R;
import cn.projects.com.projectsdemo.treeview.bean.Node;

/**
 * Created by fengxing on 2017/4/29.
 */

public class SimpleTreeAdapter<T> extends TreeListViewAdapter {
    private int icon;

    /**
     * @param context
     * @param treeView
     * @param data
     * @param defaultExpandLevel 默认展示几级树
     */
    public SimpleTreeAdapter(Context context, ListView treeView, List data, int defaultExpandLevel) {
        super(context, treeView, data, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node node, int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.tree_view_item_list, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.id_treenode_icon);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.id_treenode_label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (node.icon == -1) {
            viewHolder.imageView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.imageView.setVisibility(View.VISIBLE);
            viewHolder.imageView.setImageResource(node.icon);
        }
        viewHolder.textView.setText(node.name);
        return convertView;
    }


    private static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
