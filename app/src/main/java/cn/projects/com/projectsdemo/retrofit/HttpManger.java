package cn.projects.com.projectsdemo.retrofit;

import android.content.Context;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fengxing on 2017/4/24.
 */

public class HttpManger {

    private static final long TIME_OUT_SECONDS = 5;
    private static final long READ_TIME_OUT_SECONDS = 5;
    private static final long WRITE_TIME_OUT_SECONDS = 5;
    private static final String BASE_URL = "http://10.10.68.149:9001/mcp/";
    public static final int CACHE_MAX_SIZE = 1024 * 1024 * 100;
    private Context mContext;


    private static HttpManger mHttpManager;
    private OkHttpClient.Builder httpClientBuilder;
    private Retrofit mRetrofit;

    private HttpManger(Context context) {
        this.mContext = context;
    }

    public static HttpManger getInstance(Context context) {
        if (mHttpManager == null) {
            synchronized (HttpManger.class) {
                if (mHttpManager == null) {
                    mHttpManager = new HttpManger(context);
                }
            }
        }
        return mHttpManager;
    }

    private OkHttpClient.Builder getHttpClientBuilder() {
        if (httpClientBuilder == null) {
            httpClientBuilder = new OkHttpClient.Builder();

            httpClientBuilder.cache(setCacheFile(mContext));

            httpClientBuilder.connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT_SECONDS, TimeUnit.SECONDS);
            httpClientBuilder.addInterceptor(new HeadsInterceptor());
            //确保你没有不小心多次添加拦截器
            if (!httpClientBuilder.interceptors().contains(logging)) {
                httpClientBuilder.addInterceptor(logging);
            }
        }
        return httpClientBuilder;
    }


    private Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit =
                    new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(getHttpClientBuilder().build())
                            .build();
        }
        return mRetrofit;
    }


    public <T> T getService(Class<T> t) {
        return getRetrofit().create(t);
    }

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * @param builder
     */
    public static void setupUnsafeOkHttpClient(OkHttpClient.Builder builder) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {

                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
//            ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                    .cipherSuites(CipherSuite.values())
//                    .supportsTlsExtensions(true)
//                    .tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0)
//                    .build();
//            builder.connectionSpecs(Collections.singletonList(spec));
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Cache setCacheFile(Context context) {
        return new Cache(context.getCacheDir(), CACHE_MAX_SIZE);
    }

}
