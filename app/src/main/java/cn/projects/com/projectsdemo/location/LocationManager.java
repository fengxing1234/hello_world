package cn.projects.com.projectsdemo.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.CoordinateConverter;

import java.net.InetAddress;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by fengxing on 2017/5/13.
 */

public class LocationManager {
    //服务每5分钟启动一次
    private static final long DEFAULT_QUERY_INTERVAL = 5 * 60 * 1000;

    public static final String LOCATION = "LOCATION";


    private static final long TIME_OUT = 3 * 60 * 1000;
    private static final String TAG = "McpLocationManager";
    private Context mContext;

    private FutureTask<Boolean> futureTask;
    private ExecutorService executorService;

    private static final long CANCEL_CHECK_NETWORK_TIME = 5 * 1000;
    private static final boolean IS_NEED_CHECK_NETWORK = true;
    //容易内存泄露
    private static LocationManager mLocationHelp;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;

    public static final int NO_LOCATION = 0;
    public static final int START_LOCATION = 1;
    public static final int FINISH_LOCATION = 2;
    private int currentLocationStatus = NO_LOCATION;


    public static LocationManager getInstance(Context context) {
        if (mLocationHelp == null) {
            synchronized (LocationManager.class) {
                if (mLocationHelp == null) {
                    mLocationHelp = new LocationManager(context);
                }
            }
        }
        return mLocationHelp;
    }

    private LocationManager(Context context) {
        this.mContext = context;
        initLocation();
    }

    private void initLocation() {
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

    public void startLocation() {
        currentLocationStatus = START_LOCATION;
        if (mLocationOption == null || mLocationClient == null) {
            initLocation();
        }
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


    public boolean isStartLocation() {
        Log.d(TAG, "isStartLocation: " + currentLocationStatus);
        if (currentLocationStatus == START_LOCATION) {
            return true;
        }
        return false;
    }

    private void location() {
        if (mLocationClient != null && mLocationOption != null) {
            Log.d(TAG, "startLocation: 开始定位");
            AMapLocationClientOption.AMapLocationMode locationMode = mLocationOption.getLocationMode();
            Log.d(TAG, "startLocation: " + locationMode);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    public void stopLocation() {
        currentLocationStatus = FINISH_LOCATION;
        if (mLocationClient != null) {
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
            currentLocationStatus = FINISH_LOCATION;
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

    private OnLocationListener onLocationListener;

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }


    private AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            Log.d(TAG, "onLocationChanged: 回调成功");
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {//成功
                    boolean isAvailable = locationIsAvailable(aMapLocation);
                    if (isAvailable) {
                        if (onLocationListener != null) {
                            onLocationListener.locationSucceed(aMapLocation);
                        }
                    } else {//数据不可用
                        returnLastLocation();
                    }
                } else {//失败
                    returnLastLocation();
                }
            } else {//aMapLocation == null
                returnLastLocation();
            }
            stopLocation();
        }
    };

    private void returnLastLocation() {
        AMapLocation lastLocation = getLastLocation();
        if (lastLocation != null) {//回去上一次定位结果
            if (onLocationListener != null) {
                onLocationListener.locationSucceed(lastLocation);
            }
        } else {//获取上一次定位结果是null
            if (onLocationListener != null) {
                onLocationListener.locationError("定位失败");
            }
        }
    }

    public AMapLocation getLastLocation() {
        if (mLocationClient != null) {
            return mLocationClient.getLastKnownLocation();
        }
        return null;
    }

    private boolean locationIsAvailable(AMapLocation aMapLocation) {
        CoordinateConverter converter = new CoordinateConverter(mContext);
        return converter.isAMapDataAvailable(aMapLocation.getLatitude(), aMapLocation.getLongitude());
    }

    public static void startPollingService(Context context, Class<?> clz) {
        startPollingService(context, clz, DEFAULT_QUERY_INTERVAL);
    }


    //开启轮询服务
    public static void startPollingService(Context context, Class<?> clz, long delay) {
        //Log.w(TAG, "startPollingService: " + "定位服务自启动" );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);

        //包装需要执行Service的intent
        Intent intent = new Intent(context, clz);
        intent.setAction(LOCATION);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //设置出发广播的其实时间
        long triggerAtTime = SystemClock.elapsedRealtime();

        //使用AlarmManger的setRepeating方法设置定期执行的时间间隔（seconds秒）和需要执行的Broadcast
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime + delay, pendingIntent);

    }

    public static void stopPollingService(Context context, Class<?> clz) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context, clz);
        intent.setAction(LOCATION);
        PendingIntent sender = PendingIntent.getService(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(sender);
    }

    /**
     * 判断用户是否开启GPS
     * GPS或者AGPS开启一个默认就是开启状态
     *
     * @param context
     * @return
     */
    public static boolean isOpenGps(Context context) {
        android.location.LocationManager lm =
                (android.location.LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = lm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = lm.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER);
        Log.d(TAG, "GPS开启了没 ：" + gps + "  AGPS开启了没 ：  " + network);
        return gps;
    }

    /**
     * 没有开启GPS 给用户友好的提醒
     *
     * @param mContext
     */
    public static void locationWarn(Context mContext) {
        if (!LocationManager.isOpenGps(mContext)) {
            Toast.makeText(mContext, "请开启GPS，定位更准确!", Toast.LENGTH_LONG).show();
            LocationManager.openGps(mContext);
        }
    }


    /**
     * 强制打开gps
     */
    public static void openGps(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private class CheckNetWorkCallable implements Callable<Boolean> {

        private static final String HOST_NAME = "www.baidu.com";

        @Override
        public Boolean call() throws Exception {
            try {
                Log.d(TAG, "ThreadName: " + Thread.currentThread().getName());
                long startTime = System.currentTimeMillis();
                InetAddress ip = InetAddress.getByName(HOST_NAME);
                long endTime = System.currentTimeMillis();
                Log.d(TAG, "一共用时: " + (endTime - startTime));
                return !ip.equals("");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

        }
    }

}
