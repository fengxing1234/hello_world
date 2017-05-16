package cn.projects.com.projectsdemo.screenchangedemo;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by fengxing on 17/3/30.
 */
public class SavedInstanceStateUsingActivity extends ListActivity{

    private static final String TAG = "SavedInstanceStateUsingActivity";
    private static final String DATA = "data";
    private ArrayList<String> mData;
    private LoadDataAsyncTask mLoadDataAsyncTask;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initData(savedInstanceState);
    }

    private void initData(Bundle savedInstanceState) {
        if(savedInstanceState != null){
            mData = savedInstanceState.getStringArrayList(DATA);
        }
        if(mData == null){
            LoadingDialog mLoadingDialog = new LoadingDialog();
            mLoadingDialog.show(getFragmentManager(), "LoadingDialog");
            mLoadDataAsyncTask = new LoadDataAsyncTask(mLoadingDialog,this);
            mLoadDataAsyncTask.execute("冯星","高渐离");
        }else{
            initAdapter();
        }
    }

    public void initAdapter() {
        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mData);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: "+outState);
        outState.putStringArrayList(DATA,mData);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        Log.d(TAG, "onRestoreInstanceState: "+state);
    }

    public void setData(ArrayList<String> datas) {
        mData = datas;
    }
}
