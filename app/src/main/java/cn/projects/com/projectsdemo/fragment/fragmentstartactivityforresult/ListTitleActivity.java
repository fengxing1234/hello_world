package cn.projects.com.projectsdemo.fragment.fragmentstartactivityforresult;

import android.app.Fragment;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by fengxing on 17/3/28.
 */

public class ListTitleActivity extends SingleFragmentActivity {
    private ListTitleFragment mListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected Fragment getFragment() {
        ListTitleFragment listTitleFragment = new ListTitleFragment();
        return listTitleFragment;
    }

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment_start_activity_for_result);
//
//        FragmentManager fm = getFragmentManager();
//        mListFragment = (ListTitleFragment) fm.findFragmentById(R.id.id_fragment_container);
//
//        if(mListFragment == null )
//        {
//            mListFragment = new ListTitleFragment();
//            fm.beginTransaction().add(R.id.id_fragment_container,mListFragment).commit();
//        }
//
//    }
}
