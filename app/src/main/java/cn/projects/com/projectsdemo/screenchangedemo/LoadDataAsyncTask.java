package cn.projects.com.projectsdemo.screenchangedemo;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by fengxing on 17/3/30.
 */
public class LoadDataAsyncTask extends AsyncTask<String,Integer,List<String>>{

    private static final String TAG = LoadDataAsyncTask.class.getSimpleName();
    private final LoadingDialog mLoadingDialog;
    private final SavedInstanceStateUsingActivity mContext;
    private ArrayList<String> datas;

    public LoadDataAsyncTask(LoadingDialog mLoadingDialog, SavedInstanceStateUsingActivity savedInstanceStateUsingActivity) {
        this.mLoadingDialog = mLoadingDialog;
        this.mContext = savedInstanceStateUsingActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute: ");
    }

    @Override
    protected List<String> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: "+params[0]);
        //更新进度信息

        publishProgress();
        datas = generateTimeConsumingDatas();
        return new ArrayList<>(Arrays.asList(params[1]));
    }

    @Override
    protected void onPostExecute(List<String> strings) {
        Log.d(TAG, "onPostExecute: ");
        mLoadingDialog.dismiss();
        mContext.setData(datas);
        mContext.initAdapter();
        super.onPostExecute(strings);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
    }

    /**
     * 模拟耗时操作
     *
     * @return
     */
    private ArrayList<String> generateTimeConsumingDatas() {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e)
        {
        }
        return new ArrayList<String>(Arrays.asList("通过Fragment保存大量数据",
                "onSaveInstanceState保存数据",
                "getLastNonConfigurationInstance已经被弃用", "RabbitMQ", "Hadoop",
                "Spark"));
    }

}
