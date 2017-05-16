package cn.projects.com.projectsdemo.fragment.fragmenttaskstackdemo;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/26.
 */

/**
 * ******重要******
 * 1.
 * 点击fragment1显示fragment2，点击fragment2显示fragment3
 * 当前activity继承Activity
 * 任务栈会正常返回
 * fragment3返回fragment2返回fragment1
 *
 * 如果继承自AppCompatActivity
 * 点击返回键 会全部杀死Fragment
 * 也就是在fragment3界面点击返回键直接退出Fragment
 *
 * 2.replece相当于remove+add
 *
 * 3.如果不想销毁试图（保留界面信息）使用hide
 *
 * 4.如果在第一次替换的fragment加上addToBackStack点击返回键会显示空白界面
 *
 * 5.View view = inflater.inflate(R.layout.fragment_back_stack_one, container,false);
 * 不加最后一个参数会报错 必须加上FALSE
 * Caused by: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
 * 通过LayoutInflater实例化的一个布局(View)。被重复添加了两次。而一个View只能有一个父控件。
 * 当第二次添加的时候，又要改变这个控件的父控件（虽然是同一个父控件，但是也要通过改变该View的父控件来实现）。
 * 运行时又不允许一个有父控件的子控件在有了一个父控件时，改变他的父控件。
 *
 * 参数
 * resource：需要加载布局文件的id，意思是需要将这个布局文件中加载到Activity中来操作。
 *
 * root：需要附加到resource资源文件的根控件，什么意思呢，就是inflate()会返回一个View对象，
 * 如果第三个参数attachToRoot为true，就将这个root作为根对象返回，
 * 否则仅仅将这个root对象的LayoutParams属性附加到resource对象的根布局对象上
 * ，也就是布局文件resource的最外层的View上，比如是一个LinearLayout或者其它的Layout对象。
 *
 * attachToRoot：是否将root附加到布局文件的根视图上
 *
 * 6.在屏幕旋转或者长时间在后台运行再次进入程序
 * 会导致创建多个Fragment实例
 * savedInstanceState会保存这些信息
 * 所以加入null判断
 *
 * 7.重新绘制时，Fragment发生重建，原本的数据如何保持？
 *其实和Activity类似，Fragment也有onSaveInstanceState的方法，在此方法中进行保存数据，然后在onCreate或者onCreateView或者onActivityCreated进行恢复都可以。
 *
 * 8.Fragmeny与ActionBar和MenuItem集成
 *
 *  在Fragment的onCreate中调用 setHasOptionsMenu(true);
 *  然后在Fragment子类中实现onCreateOptionsMenu
 *  如果希望在Fragment中处理MenuItem的点击，也可以实现onOptionsItemSelected；当然了Activity也可以直接处理该MenuItem的点击事件。
 *
 * 9.没有布局的Fragment的作用
 * 没有布局文件Fragment实际上是为了保存，当Activity重启时，保存大量数据准备的
 *
 * 10.fragment argument使用
 *
 * 11.fragment能够从Activity中接收返回结果，但是其自设无法产生返回结果，只有Activity拥有返回结果。
 */
public class FragmentBackStackActivity extends Activity//AppCompatActivity
{

    private static final String TAG = "FragmentBackStackActivity";
    private FragmentOne fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_back_stack_fragment);
        if (savedInstanceState ==null ) {
            fragment = FragmentOne.newInstance();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.add(R.id.id_back_stack_fragment_content, fragment, "ONE");
            //tx.addToBackStack(null);
            tx.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d(TAG, "onPostCreate: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
