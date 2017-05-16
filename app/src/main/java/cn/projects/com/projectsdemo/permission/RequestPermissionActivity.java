package cn.projects.com.projectsdemo.permission;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 2017/5/8.
 */

//public class RequestPermissionActivity extends AppCompatActivity {
public class RequestPermissionActivity extends CheckPermissionActivity {
    private static final String[] permissions = new String[]{

            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE
    };
    private static final String TAG = "RequestPermission";

    @Bind(R.id.id_btn_request_permission)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);
        ButterKnife.bind(this);

    }


    @Override
    protected void checkPermissionResult(boolean isSucceed) {
        if (isSucceed) {
            Toast.makeText(this, "校验结果：通过", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "校验结果：失败", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.id_btn_request_permission)
    public void requestPermission(View v) {
        boolean b = needRequestPermissions(permissions);
        if(b){

        }else{
            Log.d(TAG, "requestPermission: 请求权限去了");
        }
    }

}
