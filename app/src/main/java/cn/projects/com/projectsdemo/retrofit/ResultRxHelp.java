package cn.projects.com.projectsdemo.retrofit;

import android.util.Log;

import cn.projects.com.projectsdemo.retrofit.data.BaseResult;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by fengxing on 2017/4/24.
 */

    public class ResultRxHelp {


    private static final String TAG = ResultRxHelp.class.getSimpleName();

    /**
     * onStart()相对应的有一个方法 doOnSubscribe()，
     * 它和 onStart()同样是在subscribe()调用后而且在事件发送前执行，
     * 但区别在于它可以指定线程。默认情况下， doOnSubscribe()执行在 subscribe()发生的线程；
     * 而如果在 doOnSubscribe()之后有 subscribeOn()的话，它将执行在离它最近的subscribeOn()所指定的线程。
     * 可以看到在RxHelper中看到我们调用了两次subscribeOn，
     * 最后一个调用也就是离doOnSubscribe()最近的一次subscribeOn是指定的AndroidSchedulers.mainThread()也就是主线程。
     * 这样我们就就能保证它永远都在主线运行了。这里不得不感概RxJava的强大。
     * @param <T>
     * @return
     */
    public static <T> Observable.Transformer<BaseResult<T>, T> handleResult(final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifeCycle) {
        return new Observable.Transformer<BaseResult<T>, T>() {
            @Override
                public Observable<T> call(Observable<BaseResult<T>> baseResultObservable) {
                Observable<ActivityLifeCycleEvent> lifeCycleEventObservable = lifeCycle.takeFirst(new Func1<ActivityLifeCycleEvent, Boolean>() {
                    @Override
                    public Boolean call(ActivityLifeCycleEvent activityLifeCycleEvent) {
                        Log.d(TAG, "activityLifeCycleEvent: "+activityLifeCycleEvent);
                        return activityLifeCycleEvent.equals(event);
                    }
                });


                return baseResultObservable.flatMap(new Func1<BaseResult<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(BaseResult<T> tBaseResult) {
                        if (tBaseResult.getState().equals("200")) {
                            return createData(tBaseResult.getData());
                        }
                        //Observable.error(new ApiException(tBaseResult.getState() + ApiException.SPLIT + tBaseResult.getMsg()));
                        return Observable.error(new ApiException(Integer.parseInt(tBaseResult.getState())));
                    }
                })
                        .takeUntil(lifeCycleEventObservable)
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        ;
            }
        };
    }


    /**
     * 创建返回成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T data) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onStart();
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
