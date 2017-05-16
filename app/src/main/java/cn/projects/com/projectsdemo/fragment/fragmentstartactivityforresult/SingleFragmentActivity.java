package cn.projects.com.projectsdemo.fragment.fragmentstartactivityforresult;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/28.
 */

public abstract class SingleFragmentActivity extends FragmentActivity{

    private Fragment fragment;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_start_activity_for_result);
        FragmentManager fm = getFragmentManager();
        fragment = fm.findFragmentById(R.id.id_fragment_container);
        if(fragment == null) {
            fragment = getFragment();
            fm.beginTransaction().add(R.id.id_fragment_container,fragment).commit();

        }
    }

    protected abstract Fragment getFragment();
}
