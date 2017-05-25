package cn.projects.com.projectsdemo.architecturecomponents;

import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleFragment;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by fengxing on 2017/5/24.
 */

public class MyFragment extends LifecycleFragment {


    private static final String TAG = MyFragment.class.getSimpleName();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LiveData<Location> locationLiveData = LocationLiveData.get(getActivity());
        // TODO: 2017/5/24  lambda学习
//        LiveData<String> map = Transformations.map(locationLiveData, location -> {
//            
//        });
        Transformations.map(locationLiveData, new Function<Location, String>() {
            @Override
            public String apply(Location location) {
                return location.getAccuracy()+"";
            }
        });
        locationLiveData.observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location o) {
                Log.d(TAG, "onChanged: ");
            }
        });
    }
}
