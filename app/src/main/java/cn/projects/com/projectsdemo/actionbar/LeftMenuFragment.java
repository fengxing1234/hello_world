package cn.projects.com.projectsdemo.actionbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/21.
 */

public class LeftMenuFragment extends ListFragment {

    private static final int MENU_ITEM_SIZE = 3;

    private MenuItem[] mItems  = new MenuItem[MENU_ITEM_SIZE];

    public static final String[] MENU_TEXT = new String[]{"冯星","大俊哥","林超"};

    private LayoutInflater mInflater;
    private LeftMenuAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInflater = LayoutInflater.from(getActivity());

        MenuItem menuItem = null;

        for(int i = 0 ; i < MENU_ITEM_SIZE ; i ++){

            menuItem = new MenuItem(MENU_TEXT[i],
                    false,
                    R.mipmap.ic_collections_white_36dp,
                    R.mipmap.ic_launcher);
            mItems[i] = menuItem;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new LeftMenuAdapter(getActivity(), mItems);
        setListAdapter(mAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if(mMenuItemSelectedListener!=null){
            MenuItem item = (MenuItem) getListAdapter().getItem(position);
            mMenuItemSelectedListener.menuItemSelected(item.text);
        }
        mAdapter.setSelected(position);
    }

    //选择回调的接口
    public interface OnMenuItemSelectedListener {
        void menuItemSelected(String title);
    }

    private OnMenuItemSelectedListener mMenuItemSelectedListener;

    public void setOnMenuItemSelectedListener(OnMenuItemSelectedListener menuItemSelectedListener) {
        this.mMenuItemSelectedListener = menuItemSelectedListener;
    }
}
