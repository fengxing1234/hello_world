package cn.projects.com.projectsdemo.retrofit;

import cn.projects.com.projectsdemo.retrofit.data.BaseResult;
import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * Created by fengxing on 2017/4/25.
 * HttpUtils用来返回用户关心的数据，缓存，显示Dialog在这里面进行。
 */

public class HttpUtils {

    private HttpUtils() {}

    public static HttpUtils getInstance() {
        return Holder.HTTP_UTILS;
    }

    private static class Holder {
        private static final HttpUtils HTTP_UTILS = new HttpUtils();
    }

    //添加线程管理并订阅
    public<T> void toSubscribe(Observable<BaseResult<T>> observable, final ProgressSubscriber progressSubscriber,
                            String key, ActivityLifeCycleEvent event,PublishSubject<ActivityLifeCycleEvent> lifeCycle, boolean isCache, boolean isRefresh) {

        Observable.Transformer<BaseResult<T>, T> result = ResultRxHelp.handleResult(event,lifeCycle);
        //重用操作符
        Observable<T> fromNetwork = observable.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //progressSubscriber.setShowDialog(false);
                        progressSubscriber.showProgressDialog();
                    }
                });
        //缓存
        RetrofitCache.cache(key,fromNetwork,isCache,isRefresh).subscribe(progressSubscriber);
    }
}
