package cn.projects.com.projectsdemo.architecturecomponents;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.OnLifecycleEvent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import cn.projects.com.projectsdemo.utils.CallBack;
import cn.projects.com.projectsdemo.utils.NetWorkUtils;

/**
 * Created by fengxing on 2017/5/23.
 */

public class ArchitectureComponentsDemoActivity extends LifecycleActivity{
    LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    private MyLocationListener locationListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        lifecycleRegistry.addObserver(new MyObserver());

        locationListener = new MyLocationListener(this, getLifecycle(),new MyLocationListener.CallBack() {
            @Override
            public void callBack() {
                //update UI
            }
        });


        MyViewHModel myViewHModel = ViewModelProviders.of(this).get(MyViewHModel.class);
        LiveData<List<User>> users = myViewHModel.getUsers();
        users.observe(this,users1 -> {
            // update UI
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        NetWorkUtils.isNetworkAvailable(new CallBack<Boolean>() {
            @Override
            public void onCallBack(Boolean result) {
                //在这里  如果没有回调完成  就调用onStop怎么办  没有开始 就已经结束了
                if (result) {
                    locationListener.start();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationListener.stop();
    }

}

class MyLocationListener {

    private Lifecycle lifecycle;

    private boolean enabled;

    public interface CallBack {
        void callBack();
    }

    public MyLocationListener(Context context, Lifecycle lifecycle,CallBack callBack) {
        this.lifecycle = lifecycle;
    }

    void start() {
        // connect to system location service
    }

    void stop() {
        // disconnect from system location service
    }

    public void enable() {
        enabled = true;
        //if (lifecycle.getState().isAtLeast(STARTED)) {
        if(lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)){
            // connect if not connected
        }
    }

}

class MyObserver implements LifecycleObserver {

    private static final String TAG = "MyObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeDemo() {
        Log.d(TAG, "onResumeDemo: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void OnPauseDemo() {
        Log.d(TAG, "OnPauseDemo: ");
    }

    @OnLifecycleEvent({Lifecycle.Event.ON_START, Lifecycle.Event.ON_STOP})
    public void onStopOrOnStart() {
        Log.d(TAG, "onStopOrOnStart: 在start和stop都调用");
    }

    /**
     * 可以接受到0，1，2个参数
     * 第一个必须是
     */
    @OnLifecycleEvent({Lifecycle.Event.ON_START, Lifecycle.Event.ON_STOP})
    public void onStopOrOnStartDemo(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        Log.d(TAG, "onStopOrOnStart: 在start和stop都调用");
        Log.d(TAG, "onStopOrOnStartDemo: " + lifecycleOwner.getLifecycle().toString());
        Log.d(TAG, "onStopOrOnStartDemo: " + event.name());
    }
}
