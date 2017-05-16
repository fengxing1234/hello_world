package cn.projects.com.projectsdemo.location;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;


/**
 * Created by fengxing on 2017/5/9.
 */

public class LocationHelp {


    private static final String TAG = "LocationHelp";
    private static final long TIME_OUT = 120 * 1000;
    private static final long CANCEL_CHECK_NETWORK_TIME = 5 * 1000;
    private static final boolean IS_NEED_CHECK_NETWORK = true;
    private Context mContext;

    private static LocationHelp mLocationHelp;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private FutureTask<Boolean> futureTask;
    private ExecutorService executorService;
    public static final int NO_LOCATION = 0;
    public static final int START_LOCATION = 1;
    public static final int FINISH_LOCATION = 2;
    private int currentLocationStatus = NO_LOCATION;

    public static LocationHelp getInstance(Context context) {
        if (mLocationHelp == null) {
            synchronized (LocationHelp.class) {
                if (mLocationHelp == null) {
                    mLocationHelp = new LocationHelp(context);
                }
            }
        }
        return mLocationHelp;
    }

    private LocationHelp(Context context) {
        this.mContext = context;
        initLocation();
    }

    public void initLocation() {
        mLocationClient = new AMapLocationClient(mContext);
        mLocationOption = getDefaultOption();
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(locationListener);
    }

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setGpsFirst(true);
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        aMapLocationClientOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setOnceLocationLatest(true);
        aMapLocationClientOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        aMapLocationClientOption.setSensorEnable(true);
        aMapLocationClientOption.setHttpTimeOut(TIME_OUT);
        return aMapLocationClientOption;
    }

    public void setModeAndLocation(AMapLocationClientOption.AMapLocationMode mode) {
        if (mLocationOption != null) {
            mLocationOption.setLocationMode(mode);
        }
    }

    public void startLocation() {
        currentLocationStatus = START_LOCATION;
        Log.d(TAG, "startLocation: " + mLocationOption);
        Log.d(TAG, "startLocation: " + mLocationClient);
        CheckNetWorkCallable checkNetWorkCallable = new CheckNetWorkCallable();
        futureTask = new FutureTask<Boolean>(checkNetWorkCallable) {
            @Override
            protected void done() {
                Boolean isNetwork = false;
                try {
                    isNetwork = futureTask.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (CancellationException e) {
                    e.printStackTrace();
                } finally {
                    Log.d(TAG, "done: " + isNetwork);
                    setModeAndLocation(isNetwork);
                }
            }
        };
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(futureTask);
        if (IS_NEED_CHECK_NETWORK) {
            cancelCheckNetWorkThread();
        }
    }


    private void cancelCheckNetWorkThread() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (futureTask != null) {
                    if (!futureTask.isDone() && !futureTask.isCancelled()) {
                        Log.d(TAG, "任务取消");
                        futureTask.cancel(true);
                    }
                    //已经取消了任务 就不在发送信息 直接抛出异常
                    //shutdownThread();
                    Log.d(TAG, "isCanceled: " + futureTask.isCancelled());
                }
            }
        };
        timer.schedule(task, CANCEL_CHECK_NETWORK_TIME);
    }

    private void setModeAndLocation(boolean isNetWork) {
        if (mLocationOption != null) {
            if (isNetWork) {
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            } else {
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            }
        }
        location();
    }

    // TODO: 2017/5/13 这个方法无效 需要自己写
    public boolean isStarted() {
        Log.d(TAG, "currentLocationStatus: " + currentLocationStatus);
        if (currentLocationStatus == START_LOCATION) {
            return true;
        } else {
            return false;
        }
    }


    private void location() {
        if (mLocationClient != null && mLocationOption != null) {
            Log.d(TAG, "startLocation: 开始定位");
            //resetOption();
            AMapLocationClientOption.AMapLocationMode locationMode = mLocationOption.getLocationMode();
            Log.d(TAG, "startLocation: " + locationMode);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    public void stopLocation() {
        Log.d(TAG, "stopLocation: " + mLocationClient);
        if (mLocationClient != null) {
            currentLocationStatus = NO_LOCATION;
            Log.d(TAG, "stopLocation: 停止定位");
            mLocationClient.stopLocation();
        }
    }

    /**
     * Cancel本身就停止了线程并且抛出异常 所以不需要shutdown
     * 停止线程
     */
    private void shutdownThread() {
        if (executorService != null) {
            boolean shutdown = executorService.isShutdown();
            Log.d(TAG, "shutdown: " + shutdown);
            List<Runnable> runnables = executorService.shutdownNow();
            Log.d(TAG, "shutdown: " + executorService.isShutdown());
            Log.d(TAG, "shutdownSize: " + runnables.size());
            for (Runnable runnable : runnables) {
                Log.d(TAG, "shutdowns: " + runnable.toString());
            }
        }
    }


    public void destroyLocation() {
        if (mLocationClient != null) {
            currentLocationStatus = NO_LOCATION;
            Log.d(TAG, "destroyLocation: 销毁定位");
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
            mLocationHelp = null;
        }
    }


    /**
     * 重新设置参数
     */
    private void resetOption() {
        if (mLocationOption != null) {
            mLocationOption.setNeedAddress(true);
            /**
             * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
             * 注意：只有在高精度模式下的单次定位有效，其他方式无效
             */
            mLocationOption.setGpsFirst(true);
            // 设置是否开启缓存
            mLocationOption.setLocationCacheEnable(true);
        }
    }

    public AMapLocation getLastLocation() {
        if (mLocationClient != null) {
            return mLocationClient.getLastKnownLocation();
        }
        return null;
    }


    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d(TAG, "onLocationChanged: 回调成功");
            currentLocationStatus = FINISH_LOCATION;
            StringBuilder sb = new StringBuilder();
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {//定位成功
                    sb.append("定位成功" + "\n");
                    sb.append("定位类型: " + aMapLocation.getLocationType() + "\n");
                    sb.append("经    度    : " + aMapLocation.getLongitude() + "\n");
                    sb.append("纬    度    : " + aMapLocation.getLatitude() + "\n");
                    sb.append("精    度    : " + aMapLocation.getAccuracy() + "米" + "\n");
                    sb.append("提供者    : " + aMapLocation.getProvider() + "\n");

                    sb.append("速    度    : " + aMapLocation.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + aMapLocation.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    sb.append("星    数    : " + aMapLocation.getSatellites() + "\n");
                    sb.append("国    家    : " + aMapLocation.getCountry() + "\n");
                    sb.append("省            : " + aMapLocation.getProvince() + "\n");
                    sb.append("市            : " + aMapLocation.getCity() + "\n");
                    sb.append("城市编码 : " + aMapLocation.getCityCode() + "\n");
                    sb.append("区            : " + aMapLocation.getDistrict() + "\n");
                    sb.append("区域 码   : " + aMapLocation.getAdCode() + "\n");
                    sb.append("地    址    : " + aMapLocation.getAddress() + "\n");
                    sb.append("兴趣点    : " + aMapLocation.getPoiName() + "\n");
                    //定位完成的时间
                    sb.append("定位时间: " + formatUTC(aMapLocation.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                } else {//定位失败
                    getLastLocation();
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + aMapLocation.getErrorCode() + "\n");
                    sb.append("错误信息:" + aMapLocation.getErrorInfo() + "\n");
                    sb.append("错误描述:" + aMapLocation.getLocationDetail() + "\n");
                }
            } else {
                AMapLocation lastLocation = getLastLocation();
                sb.append("aMapLocation is null");
            }

            if (locationResult != null) {
                locationResult.onResult(sb.toString());
            }
        }
    };

    private static SimpleDateFormat sdf = null;

    public static String formatUTC(long l, String strPattern) {
        if (TextUtils.isEmpty(strPattern)) {
            strPattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (sdf == null) {
            try {
                sdf = new SimpleDateFormat(strPattern, Locale.CHINA);
            } catch (Throwable e) {
            }
        } else {
            sdf.applyPattern(strPattern);
        }
        return sdf == null ? "NULL" : sdf.format(l);
    }

    private LocationResult locationResult;

    public void setLocationResult(LocationResult locationResult) {
        this.locationResult = locationResult;
    }

    public interface LocationResult {
        void onResult(String result);
    }

    private class CheckNetWorkCallable implements Callable<Boolean> {

        private static final String HOST_NAME = "www.baidu.com";

        @Override
        public Boolean call() throws Exception {
            try {
                Log.d(TAG, "ThreadName: " + Thread.currentThread().getName());
                long startTime = System.currentTimeMillis();
                InetAddress ipAddr = InetAddress.getByName(HOST_NAME);
                long endTime = System.currentTimeMillis();
                Log.d(TAG, "一共用时: " + (endTime - startTime));
                return !ipAddr.equals("");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
    }
}