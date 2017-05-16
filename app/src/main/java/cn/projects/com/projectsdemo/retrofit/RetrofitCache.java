package cn.projects.com.projectsdemo.retrofit;

import com.orhanobut.hawk.Hawk;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by fengxing on 2017/4/24.
 */

public class RetrofitCache {


    /**
     * @param key         要缓存的key
     * @param fromNetwork
     * @param isCache     是否缓存
     * @param isRefresh   是否强制刷新
     * @param <T>
     * @return
     */
    public static <T> Observable<T> cache(final String key,
                                          Observable<T> fromNetwork,
                                          boolean isCache,
                                          boolean isRefresh) {
        Observable cache = Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                T cache = (T) Hawk.get(key);
                if (cache != null) {
                    subscriber.onNext(cache);
                } else {
                    subscriber.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        if (isCache) {
            /**
             * 这里的fromNetwork 不需要指定Schedule,在handleRequest中已经变换了
             */
            fromNetwork = fromNetwork.map(new Func1<T, T>() {
                @Override
                public T call(T t) {
                    Hawk.put(key, t);
                    return t;
                }
            });
        }
        //强制刷新
        if (isRefresh) {
            return fromNetwork;
        } else {
            return fromNetwork.concat(cache, fromNetwork).first();
        }
        /**
         * 几个参数注释上面已经写得很清楚了，不需要过多的解释。
         * 这里我们先取了一个Observable<T>对象fromCache,里面的操作很简单，
         * 去缓存里面找个key对应的缓存，如果有就发射数据。
         * 在fromNetwork里面做的操作仅仅是缓存数据这一操作。
         * 最后判断如果强制刷新就直接返回fromNetwork反之用Observable.concat()做一个合并。
         * concat操作符将多个Observable结合成一个Observable并发射数据。
         * 这里又用了first()。fromCache和fromNetwork任何一步一旦发射数据后面的操作都不执行。
         */
    }
}
