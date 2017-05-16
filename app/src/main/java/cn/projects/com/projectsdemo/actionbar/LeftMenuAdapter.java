package cn.projects.com.projectsdemo.actionbar;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/21.
 */
public class LeftMenuAdapter extends ArrayAdapter<MenuItem>{

    private final LayoutInflater mInflater;
    private int mSelected;

    public LeftMenuAdapter(Context context, MenuItem[] mItems) {
        super(context,-1,mItems);

        mInflater = LayoutInflater.from(context);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item_left_menu,parent,false);
        }
        ImageView iv = (ImageView) convertView.findViewById(R.id.id_item_icon);
        TextView title = (TextView) convertView.findViewById(R.id.id_item_title);
        title.setText(getItem(position).text);
        iv.setImageResource(getItem(position).icon);
        convertView.setBackgroundColor(Color.TRANSPARENT);

        if (position == mSelected) {
            iv.setImageResource(getItem(position).iconSelected);
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.state_menu_item_selected));
        }

        return convertView;
    }

    public void setSelected(int position){
        this.mSelected = position;
        notifyDataSetChanged();
    }
}
