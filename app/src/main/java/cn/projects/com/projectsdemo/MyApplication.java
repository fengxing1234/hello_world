package cn.projects.com.projectsdemo;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by fengxing on 2017/4/22.
 */

public class MyApplication extends Application {

    private static Application mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        //dbFlow数据库
        FlowManager.init(this);
        //Hawk小型的数据库
        initHawk();
    }

    private void initHawk() {
        Hawk.init(this).build();

    }

    public static Application getApplication(){
        return mApplication;
    }
}
