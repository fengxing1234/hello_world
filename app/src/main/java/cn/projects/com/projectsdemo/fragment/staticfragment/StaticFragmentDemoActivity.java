package cn.projects.com.projectsdemo.fragment.staticfragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/25.
 * 静态使用Fragment
 */

public class StaticFragmentDemoActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "Activity";
    private LieBiaoFragment lieBiaoFragment;
    private ShiShangFragment shiShangFragment;
    private CarFragment carFragment;
    private DaMaFragment damaFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.static_activity_demo_two);
        findViewById(R.id.fragment_liebiao).setOnClickListener(this);
        findViewById(R.id.fragment_shishang).setOnClickListener(this);
        findViewById(R.id.fragment_car).setOnClickListener(this);
        findViewById(R.id.fragment_dama).setOnClickListener(this);
        // 设置默认的Fragment
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        lieBiaoFragment = new LieBiaoFragment();
        ft.replace(R.id.fragment_content, lieBiaoFragment);
        ft.commit();
    }


    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (v.getId()){
            case R.id.fragment_liebiao:
                if(lieBiaoFragment == null){
                    lieBiaoFragment = new LieBiaoFragment();
                }
                fragmentTransaction.replace(R.id.fragment_content, lieBiaoFragment);
                break;
            case R.id.fragment_shishang:
                if(shiShangFragment==null){
                    shiShangFragment = new ShiShangFragment();
                }
                fragmentTransaction.replace(R.id.fragment_content,shiShangFragment);
                break;
            case R.id.fragment_car:
                if(carFragment==null){
                    carFragment = new CarFragment();
                }
                fragmentTransaction.replace(R.id.fragment_content,carFragment);
                break;
            case R.id.fragment_dama:
                if(damaFragment==null){
                    damaFragment = new DaMaFragment();
                }
                fragmentTransaction.replace(R.id.fragment_content,damaFragment);
                break;
        }
        fragmentTransaction.commit();
    }
}
