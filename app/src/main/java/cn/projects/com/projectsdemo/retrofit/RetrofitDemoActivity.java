package cn.projects.com.projectsdemo.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.projects.com.projectsdemo.R;
import cn.projects.com.projectsdemo.retrofit.data.BaseResult;
import cn.projects.com.projectsdemo.retrofit.data.FileType;
import cn.projects.com.projectsdemo.retrofit.data.MyData;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fengxing on 2017/4/22.
 */

public class RetrofitDemoActivity extends BaseActivity{
    //https://10.10.1.119:8888/mcpinterf/
    //https://10.10.68.149:8888/mcpinterf/
    //http://10.10.68.149:9001/mcp/userTasks?timestamp=1492842963846
    //http://10.10.68.149:9001/mcp/
    private static final String BASE_URL = "http://10.10.68.149:9001/mcp/";
    private static final String TAG = RetrofitDemoActivity.class.getSimpleName();


    @BindView(R.id.id_retrofit_demo_btn)
    Button button;

    @BindView(R.id.id_retrofit_demo_et)
    EditText editText;
    private SimpleLoadDialog dialogHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_demo);
        ButterKnife.bind(this);

        dialogHandler = new SimpleLoadDialog(RetrofitDemoActivity.this, null, true);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RetrofitDemoActivity.this, "开始请求数据", Toast.LENGTH_SHORT).show();
//                new Thread(){
//                    @Override
//                    public void run() {
//                        FileUtils.amendFileLastNameDemo();
//                    }
//                }.start();
                //getSimpleNetData();
                //getFileType();
                getTopMovie();
                //getDataHttpManager();
            }
        });
    }

    public void getSimpleNetData() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        Retrofit build = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = build.create(ApiService.class);
        final Observable<MyData> userTask = apiService.getUserTask();
        userTask.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<MyData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: +e"+e);
                    }

                    @Override
                    public void onNext(MyData userTaskDataBaseData) {
                        Log.d(TAG, "onNext: "+userTaskDataBaseData);
                    }
                });


        //简单使用
//        Call<BaseResult<UserTaskData>> userTask = apiService.getUserTask();
//        //异步调用
//        userTask.enqueue(new Callback<BaseResult<UserTaskData>>() {
//            @Override
//            public void onResponse(Call<BaseResult<UserTaskData>> call, Response<BaseResult<UserTaskData>> response) {
//                Log.d(TAG, "onResponse: "+response);
//                Log.d(TAG, "onResponse: "+call);
//            }
//
//            @Override
//            public void onFailure(Call<BaseResult<UserTaskData>> call, Throwable t) {
//
//            }
//        });

        //同步调用
//        try {
//            Response<BaseResult<UserTaskData>> execute = userTask.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void getFileType() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(5, TimeUnit.SECONDS);

        Retrofit build = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = build.create(ApiService.class);
        Observable<BaseResult<FileType>> fileType = apiService.getFileType();
        fileType.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<BaseResult<FileType>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e);
                    }

                    @Override
                    public void onNext(BaseResult<FileType> fileType) {
                        Log.d(TAG, "onNext: "+fileType);
                    }
                });
    }

    public void getDataHttpManager() {

    }

    public void getTopMovie(){
        ApiService service = HttpManger.getInstance(this).getService(ApiService.class);
        Observable<BaseResult<FileType>> fileType = service.getFileType();

        HttpUtils.getInstance().toSubscribe(fileType, new ProgressSubscriber<FileType>(this) {
            @Override
            protected void onDoError(Throwable e) {

            }

            @Override
            protected void onDoNext(FileType fileType) {
                Log.d(TAG, "onDoNext: "+fileType);
                editText.setText(fileType.fileType.toString());
            }
        },"cacheKey",ActivityLifeCycleEvent.DESTROY,publishSubject,true,true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dialogHandler.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
    }
}


