package cn.projects.com.projectsdemo.actionbar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/21.
 */

public class ActionBarDemoActivity extends AppCompatActivity{

    private static final String TAG = "ActionBarDemoActivity";
    private Toolbar toolbar;
    private static final String KEY_TITLLE = "key_title";
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private String mTitle;
    private FragmentManager fm;
    private ContentFragment mCurrentFragment;
    private LeftMenuFragment leftFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_actionbar_drawerlayout);
        initToolbar();
        initViews();

        //恢复title
        restoreTitle(savedInstanceState);

        fm = getSupportFragmentManager();

        mCurrentFragment = (ContentFragment)fm.findFragmentByTag(mTitle);

        if (mCurrentFragment == null) {
            mCurrentFragment = ContentFragment.getInstance(mTitle);
            fm.beginTransaction().add(R.id.id_content_container, mCurrentFragment, mTitle).commit();
        }

        leftFragment = (LeftMenuFragment)fm.findFragmentById(R.id.id_left_menu_container);
        if (leftFragment== null) {
            leftFragment = new LeftMenuFragment();
            fm.beginTransaction().add(R.id.id_left_menu_container, leftFragment).commit();
        }

        leftFragment.setOnMenuItemSelectedListener(new LeftMenuFragment.OnMenuItemSelectedListener() {
            @Override
            public void menuItemSelected(String title) {
                FragmentManager fm = getSupportFragmentManager();
                ContentFragment fragment = (ContentFragment) getSupportFragmentManager().findFragmentByTag(title);
                if (fragment == mCurrentFragment) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                    return;
                }

                FragmentTransaction transaction = fm.beginTransaction();
                transaction.hide(mCurrentFragment);

                if (fragment == null) {
                    fragment = ContentFragment.getInstance(title);
                    transaction.add(R.id.id_content_container, fragment, title);
                } else {
                    transaction.show(fragment);
                }
                transaction.commit();

                mCurrentFragment = fragment;
                mTitle = title;
                toolbar.setTitle(mTitle);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            }
        });
    }

    private void restoreTitle(Bundle savedInstanceState) {
        if (savedInstanceState != null)
            mTitle = savedInstanceState.getString(KEY_TITLLE);

        if (TextUtils.isEmpty(mTitle)) {
            mTitle = LeftMenuFragment.MENU_TEXT[0];
        }

        toolbar.setTitle(mTitle);
    }


    private void initToolbar() {
        toolbar = (Toolbar)findViewById(R.id.custom_toolbar);
        toolbar.setTitle(LeftMenuFragment.MENU_TEXT[0]);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_collections_white_36dp);
    }

    private void initViews() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_drawer);

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mActionBarDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }

}
