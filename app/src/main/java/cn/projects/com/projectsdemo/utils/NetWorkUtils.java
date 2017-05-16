package cn.projects.com.projectsdemo.utils;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by fengxing on 17/2/28.
 */

public class NetWorkUtils {

    private static final String HOST_NAME = "www.baidu.com";
    private static final String TAG = NetWorkUtils.class.getSimpleName();

    public static Handler mHandler;

    static {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mHandler = new Handler();
        } else {
            mHandler = new Handler(Looper.getMainLooper());
        }
    }

    public static void isNetworkAvailable(final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    final InetAddress inetAddress = InetAddress.getByName(HOST_NAME);
                    Log.d(TAG, "inetAddress : " + inetAddress);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            sendCallBack(callBack, !inetAddress.equals(""), mHandler);
                        }
                    });

                } catch (UnknownHostException e) {
                    sendCallBack(callBack, false, mHandler);
                    e.printStackTrace();
                }
            }
        }.start();
    }


    public static void ping(final CallBack callBack) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Process exec = Runtime.getRuntime().exec("ping -c 3 -w 5 " + HOST_NAME);
                    int cood = exec.waitFor();
                    if (cood == 0) {
                        sendCallBack(callBack, true, mHandler);
                    } else {
                        sendCallBack(callBack, false, mHandler);
                    }
                } catch (IOException e) {
                    sendCallBack(callBack, false, mHandler);
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    sendCallBack(callBack, false, mHandler);
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static <T> void sendCallBack(final CallBack callBack, final T result, Handler handler) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onCallBack(result);
            }
        });
    }
}
