package cn.projects.com.projectsdemo.testsim;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import cn.projects.com.projectsdemo.R;
import cn.projects.com.projectsdemo.utils.permission.CheckPermissionActivity;

/**
 * Created by fengxing on 2017/10/3.
 */

public class SimNo1Activity extends CheckPermissionActivity {

    private static final String TAG = SimNo1Activity.class.getSimpleName();
    private static final int REQUEST_CODE = 0x01;
    private static final String PHONE_PERMISSION = Manifest.permission.READ_PHONE_STATE;
    private static final String[] PERMISSION = {PHONE_PERMISSION};
    private TelephonyManager manager;
    private TextView tv_sim;
    private TextView tv_state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sim);
        needRequestPermissions(PERMISSION);
    }


    @Override
    protected void checkPermissionResult(boolean result) {
        if(result){
            initView();
            getSimData();
        }else {
            finish();
        }
    }

    private void initView() {
        tv_state = (TextView) findViewById(R.id.tv_show_sim_state);
        tv_sim = (TextView) findViewById(R.id.tv_show_sim_no1);
    }

    private void getSimData() {
        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        int simState = manager.getSimState();
        String state = "正在初始化";
        switch (manager.getSimState()) {
            case TelephonyManager.SIM_STATE_READY:
                state = "良好";
                break;
            case TelephonyManager.SIM_STATE_ABSENT:
                state = "无SIM卡";
                break;
            default:
                state = ("SIM卡被锁定或未知状态");
                break;
        }
        tv_state.setText(state);


        String simSerialNumber = manager.getSimSerialNumber();
        tv_sim.setText(simSerialNumber);

    }

}
