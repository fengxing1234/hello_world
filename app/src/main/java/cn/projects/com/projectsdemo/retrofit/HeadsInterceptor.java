package cn.projects.com.projectsdemo.retrofit;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by fengxing on 2017/4/26.
 */

class HeadsInterceptor implements Interceptor {
    private static final String TAG = "HeadsInterceptor";
    private Map<String, String> heads;

    public HeadsInterceptor() {

    }

    public HeadsInterceptor(Map<String, String> map) {
        this.heads = map;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String name = Thread.currentThread().getName();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "intercept: "+name);
        Request request = chain.request();
        Response proceed = chain.proceed(request);
        Response.Builder builder = proceed.newBuilder();
        if (heads != null) {
            for (Map.Entry<String, String> entry : heads.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }
}
