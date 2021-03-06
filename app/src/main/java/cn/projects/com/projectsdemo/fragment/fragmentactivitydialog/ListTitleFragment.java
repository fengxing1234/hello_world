package cn.projects.com.projectsdemo.fragment.fragmentactivitydialog;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import cn.projects.com.projectsdemo.R;


/**
 * Created by fengxing on 17/3/28.
 */

public class ListTitleFragment extends ListFragment {

    public static final int REQUEST_DETAIL = 0x110;
    private List<String> mTitles = Arrays.asList("冯星", "梁玉莹", "老干妈");
    private int mCurrentPos;
    private ArrayAdapter<String> mAdapter;
    private ContentFragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, mTitles));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCurrentPos = position;
        //Intent intent= new Intent(getActivity(),ContentActivity.class);
        //intent.putExtra(ContentFragment.ARGUMENT, mTitles.get(position));
        //startActivityForResult(intent,REQUEST_DETAIL);
        FragmentManager fm = getActivity().getFragmentManager();
        if(fragment == null) {
            fragment = new ContentFragment();
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(ContentFragment.ARGUMENT, mTitles.get(position));
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.id_fragment_container, fragment).addToBackStack(null).commit();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TAG", "onActivityResult");
        if (data != null && requestCode == REQUEST_DETAIL) {
            mTitles.set(mCurrentPos, mTitles.get(mCurrentPos) + " -- " + data.getStringExtra(ContentFragment.RESPONSE));
            mAdapter.notifyDataSetChanged();
        }
    }
}
