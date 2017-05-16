package cn.projects.com.projectsdemo.fragment.fragmentstartactivityforresult;

import android.app.Fragment;
import android.app.FragmentManager;


/**
 * Created by fengxing on 17/3/28.
 */
public class ContentActivity extends SingleFragmentActivity{

    private ContentFragment mContentFragment;
    private FragmentManager fm;
    private ContentFragment contentFragment;

//    @Override
//    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        setContentView(R.layout.activity_fragment_start_activity_for_result);
//
//        fm = getFragmentManager();
//        mContentFragment = (ContentFragment) fm.findFragmentById(R.id.id_fragment_container);
//        if(mContentFragment == null){
//            String extra = getIntent().getStringExtra(ContentFragment.ARGUMENT);
//            contentFragment = ContentFragment.newInstance(extra);
//            fm.beginTransaction().add(R.id.id_fragment_container,contentFragment).commit();
//        }
//    }

    @Override
    protected Fragment getFragment() {
        String title = getIntent().getStringExtra(ContentFragment.ARGUMENT);

        return ContentFragment.newInstance(title);
    }
}
