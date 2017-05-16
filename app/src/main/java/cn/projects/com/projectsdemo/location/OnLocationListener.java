package cn.projects.com.projectsdemo.location;

import com.amap.api.location.AMapLocation;

/**
 * Created by fengxing on 2017/5/16.
 */

interface OnLocationListener {
    void locationSucceed(AMapLocation aMapLocation);

    void locationError(String error);
}
