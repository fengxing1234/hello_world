package cn.projects.com.projectsdemo.fragment.fragmentactivitydialog;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;

import cn.projects.com.projectsdemo.R;


/**
 * Created by fengxing on 17/3/28.
 */

public class ListTitleActivity extends Activity {
    private ListTitleFragment mListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_start_activity_for_result);

        FragmentManager fm = getFragmentManager();
        mListFragment = (ListTitleFragment) fm.findFragmentById(R.id.id_fragment_container);

        if(mListFragment == null )
        {
            mListFragment = new ListTitleFragment();
            fm.beginTransaction().add(R.id.id_fragment_container,mListFragment).commit();
        }

    }
}
