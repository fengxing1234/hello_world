package cn.projects.com.projectsdemo.dspider.singlefactorymode;

import android.util.Log;

/**
 * Created by fengxing on 17/3/16.
 */

public class Car {
    protected static final String TAG = Car.class.getSimpleName();

    public void engine(){
        Log.d(TAG, "engine: ");
    }
}
