package cn.projects.com.projectsdemo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import cn.projects.com.projectsdemo.kotlin.KotlinDemoActivity;
import cn.projects.com.projectsdemo.location.GaoDeLocationActivity;
import cn.projects.com.projectsdemo.permission.RequestPermissionActivity;
import cn.projects.com.projectsdemo.retrofit.RetrofitDemoActivity;
import cn.projects.com.projectsdemo.rxjava.DemoRxJavaActivity;
import cn.projects.com.projectsdemo.thread.ThreadPoolExecutorActivity;
import cn.projects.com.projectsdemo.thread.future.FutureDemoActivity;
import cn.projects.com.projectsdemo.treeview.TreeViewActivity;

/**
 * 1. appCompatActivity 与 Activity的区别
 * 2. 生命周期又出现了两个好玩的东西
 * 3. onConfigurationChanged 什么时候调用
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    private ActionBarDrawerToggle actionBarDrawerToggle;

    private GridView mGridView;

    private String[] datas = {"RxJava", "Retrofit", "TreeView", "权限封装", "高德定位", "线程池", "Future","树结构","Kotlin"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.main_grid_view);
        mGridView.setAdapter(new MainGridAdapter(this, Arrays.asList(datas)));
        mGridView.setOnItemClickListener(new GridViewListener());
    }

    private class GridViewListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent();
            switch (position) {
                case 0:
                    intent = new Intent(MainActivity.this, DemoRxJavaActivity.class);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this, RetrofitDemoActivity.class);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, TreeViewActivity.class);
                    break;
                case 3:
                    intent = new Intent(MainActivity.this, RequestPermissionActivity.class);
                    break;
                case 4:
                    intent = new Intent(MainActivity.this, GaoDeLocationActivity.class);
                    break;
                case 5:
                    intent = new Intent(MainActivity.this, ThreadPoolExecutorActivity.class);
                    break;
                case 6:
                    intent = new Intent(MainActivity.this, FutureDemoActivity.class);
                    break;
                case 7:
                    intent = new Intent(MainActivity.this, TreeViewActivity.class);
                    break;
                case 8:
                    intent = new Intent(MainActivity.this, KotlinDemoActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }

    private class MainGridAdapter extends BaseAdapter {

        private List<String> mData;
        private Context mContext;

        public MainGridAdapter(Context context, List<String> data) {
            this.mData = data;
            this.mContext = context;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHold viewHold;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout
                        .item_grid_view, null, false);
                viewHold = new ViewHold();
                convertView.setTag(viewHold);
                viewHold.button = (TextView) convertView.findViewById(R.id.id_grid_view_btn);
            } else {
                viewHold = (ViewHold) convertView.getTag();
            }
            viewHold.button.setText(mData.get(position));
//            viewHold.button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d(TAG, "onClick: ");
//                }
//            });
            return convertView;
        }
    }

    public static class ViewHold {
        TextView button;

    }


    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    /**
     * 继承activity的 无效
     *
     * @param savedInstanceState
     * @param persistentState
     */
    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        Log.d(TAG, "onPostCreate: 继承 Activity");
        super.onPostCreate(savedInstanceState, persistentState);
    }

    /**
     * 在onStart之后 OnResume之前调用
     *
     * @param savedInstanceState
     */
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onPostCreate: 继承 AppCompatActivity");
        super.onPostCreate(savedInstanceState);
        //actionBarDrawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    /**
     * 在onResume之后调用
     * 表示当前activity彻底可以显示
     */
    @Override
    protected void onPostResume() {
        Log.d(TAG, "onPostResume: ");
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    /**
     * 当屏幕旋转 或者打开隐藏键盘 会触发这个事件
     *
     * @param newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.d(TAG, "onConfigurationChanged: ");
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     *
     */
//    @Override
//    public void onBackPressed() {
//        boolean drawerOpen = mDrawerLayout.isDrawerOpen(listView);
//        if(drawerOpen){
//            mDrawerLayout.closeDrawer(listView);
//        }else{
//            super.onBackPressed();
//        }
//    }


    /**
     * 此方法用于初始化菜单，其中menu参数就是即将要显示的Menu实例。 返回true则显示该menu,false 则不显示;
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }


    /**
     * 在onCreateOptionsMenu执行后，菜单被显示前调用；
     * 如果菜单已经被创建，则在菜单显示前被调用。 同样的，
     * 返回true则显示该menu,false 则不显示;
     * 可以通过此方法动态的改变菜单的状态，比如加载不同的菜单等）
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu: ");
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 每次菜单被关闭时调用.
     * （菜单被关闭有三种情形，menu按钮被再次点击、back按钮被点击或者用户选择了某一个菜单项）
     *
     * @param menu
     */
    @Override
    public void onOptionsMenuClosed(Menu menu) {
        Log.d(TAG, "onOptionsMenuClosed: ");
        super.onOptionsMenuClosed(menu);
    }

    /**
     * 菜单项被点击时调用，也就是菜单项的监听方法。
     * 通过这几个方法，可以得知，对于Activity，同一时间只能显示和监听一个Menu 对象。
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: ");
        boolean itemSelected = actionBarDrawerToggle.onOptionsItemSelected(item);
        if (itemSelected) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
