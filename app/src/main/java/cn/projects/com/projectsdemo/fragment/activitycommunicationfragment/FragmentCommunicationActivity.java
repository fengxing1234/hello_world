package cn.projects.com.projectsdemo.fragment.activitycommunicationfragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/27.
 */

/**
 * Fragment与Activity通信
 *
 * 1.如果你Activity中包含自己管理的Fragment的引用，可以通过引用直接访问所有的Fragment的public方法
 * 2.如果Activity中未保存任何Fragment的引用，那么没关系，每个Fragment都有一个唯一的TAG或者ID,
 * 可以通过getFragmentManager.findFragmentByTag()或者findFragmentById()获得任何Fragment实例，然后进行操作。
 * 3.在Fragment中可以通过getActivity得到当前绑定的Activity的实例，然后进行操作。
 * 4.如果在Fragment中需要Context，可以通过调用getActivity(),如果该Context需要在Activity被销毁后还存在，则使用getActivity().getApplicationContext()。
 *
 * ****
 */
public class FragmentCommunicationActivity extends Activity {


    private FragmentOne fragmentOne;
    private FragmentTwo fTwo;
    private FragmentThree fThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_stack_fragment);
        if (savedInstanceState ==null ) {
            fragmentOne = new FragmentOne();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.id_back_stack_fragment_content, fragmentOne, "ONE");
            tx.addToBackStack(null);
            tx.commit();
        }
        fragmentOne.setFragmentOneClickListener(new FragmentOne.FragmentOneClickListener() {
            @Override
            public void onOneClick() {
                fTwo = new FragmentTwo();
                fTwo.setFragmentTwoClickListener(new FragmentTwo.FragmentTwoClickListener() {
                    @Override
                    public void onTwoClick() {
                        fThree = new FragmentThree();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction tx = fm.beginTransaction();
                        tx.hide(fTwo);
                        tx.add(R.id.id_back_stack_fragment_content , fThree, "THREE");
//      tx.replace(R.id.id_content, fThree, "THREE");
                        tx.addToBackStack(null);
                        tx.commit();
                    }
                });

                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.id_back_stack_fragment_content, fTwo, "TWO");
                tx.addToBackStack(null);
                tx.commit();

            }
        });
        if(fTwo!=null){

        }
    }
}
