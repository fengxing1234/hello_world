package cn.projects.com.projectsdemo.permission;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengxing on 2017/5/8.
 */

public abstract class CheckPermissionActivity extends AppCompatActivity {

    protected static final int REQUEST_PERMISSION_CODE = 0x123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public boolean needRequestPermissions(String[] permissions) {

        boolean isNeedCheckout = true;

        if (Build.VERSION_CODES.M > Build.VERSION.SDK_INT) {
            return true;
        }

        for (String permission : permissions) {

            if (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                isNeedCheckout = false;
            } else {
                isNeedCheckout = true;
                break;
            }

        }

        if (!isNeedCheckout) {
            return true;
        }

        checkPermission(permissions);

        return false;
    }

    private void checkPermission(String... permissions) {
        List<String> deniedPermissions = getDeniedPermissions(permissions);

        if (deniedPermissions != null && deniedPermissions.size() > 0) {
            requestPermissions(deniedPermissions.toArray(
                    new String[deniedPermissions.size()])
                    , REQUEST_PERMISSION_CODE);
        }
    }

    private List<String> getDeniedPermissions(String[] permissions) {
        List<String> deniedPermission = new ArrayList<>();
        for (String permission : permissions) {
            //boolean shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale(permission);
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED
                //      || shouldShowRequestPermissionRationale) {
                    ) {

                deniedPermission.add(permission);

            }
        }
        return deniedPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (verifyPermissions(grantResults)) {
                checkPermissionResult(true);
            } else {
                checkPermissionResult(false);
            }
        }
    }

    protected abstract void checkPermissionResult(boolean result);

    private boolean verifyPermissions(int[] grantResults) {
        if (grantResults.length == 0) {
            return true;
        }
        for (int request : grantResults) {
            if (request != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}