package cn.projects.com.projectsdemo.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import cn.projects.com.projectsdemo.MainActivity;
import cn.projects.com.projectsdemo.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

import static java.util.Arrays.asList;
import static rx.Observable.interval;

/**
 * Created by fengxing on 2017/4/19.
 */

public class DemoRxJavaActivity extends AppCompatActivity {

    private static final String TAG = DemoRxJavaActivity.class.getSimpleName();
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_java_demo_activity);
        imageView = (ImageView) findViewById(R.id.iv_rx_java_activity);
        imageView.setImageResource(R.drawable.guide);
        //demoOne();
        //demoCreate();
        //demoFrom();
        //demoJust();
        //demoDefer();
        //demoTimer();
        //demoInterval();
        //demoRange();
        //demoRepeat();
        //demoSwitchMap();
        //demoConcatMap();
        //demoGroupBy();
        //demoScan();
        //demoDistinct();
        //demoSample();
        //demoCombineLatest();
        //demoJoin();
        demoZipWith();
    }

    private void demoZipWith() {
        Observable.range(10,6).zipWith(Observable.range(1, 3), new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                return integer+"-"+integer2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.d(TAG, "call: "+s);
            }
        });
    }

    private void demoJoin() {
        Observable<String> left = Observable.interval(100, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                return "L" + aLong;
            }
        });

        Observable<String> right = interval(200, TimeUnit.MILLISECONDS).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                return "R" + aLong;
            }
        });

        left.join(right, new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                Log.d(TAG, "never: "+s);
                //不发送数据也不终止
                return Observable.never();
            }
        }, new Func1<String, Observable<Long>>() {
            @Override
            public Observable<Long> call(String s) {
                Log.i(TAG, s + "=====");
                return Observable.timer(0,TimeUnit.SECONDS);
            }
        }, new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + "-" + s2;
            }
        }).take(20)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "fengxing"+s);
                    }
                });
    }

    private void demoCombineLatest() {
        Observable.combineLatest(Observable.range(5, 2), Observable.range(10, 4), new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                Log.d(TAG, "call: " + integer);
                return integer + "==" + integer2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s + "=combineLatest");
            }
        });
    }

    private void demoSample() {
        interval(1, TimeUnit.SECONDS).sample(3, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.i(TAG, aLong.toString() + "==sample ");
            }
        });
    }

    private void demoDistinct() {
        Observable.range(1, 5).distinct(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                Log.d(TAG, "call: " + integer);
                if (integer <= 2) return "1";// 定义过滤规则
                return "2";
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call 1 ===" + integer.toString());
            }
        });
        Observable.just(1, 2, 3, 1, 2).distinct().subscribe(new Action1<Integer>() { // 使用默认规则
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call 2 === " + integer.toString());
            }
        });
    }


    /**
     * 他的作用是连续对数据序列的每一项应用一个func，然后连续发射结果。例子
     * <p>
     * 他的第一个参数是上一次计算的结果传入，第二个参数是被观察的序列值，从2开始，
     * 第三个是返回类型，三者是类型一样的。使用一个默认的种子计算的，比如
     */
    private void demoScan() {
        Observable.range(10, 4).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                Log.d(TAG, "integer 1 : " + integer + "   integer2 ==" + integer2);
                return integer + integer2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d(TAG, "call: " + integer);
            }
        });
    }

    private void demoGroupBy() {
        Observable.range(10, 10).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer % 3;
            }
        }).subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
            @Override
            public void call(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                final Integer key = integerIntegerGroupedObservable.getKey();
                Log.d(TAG, "key : " + key);
                integerIntegerGroupedObservable.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "key : " + integerIntegerGroupedObservable.getKey() + "=====" + integer.toString());
                    }
                });
            }
        });
    }

    /**
     * 他是会按照顺序输出，flatMap不一定，这种情况是在你需要返回一个Observable对象的时候，他是通过异步去获取的
     * <p>
     * <p>
     * <p>
     * <p>
     * 使用flatMap的话并不一定能保证顺序。在测试过程发现假如把Schedulers.from(Executors.newFixedThreadPool(1))
     * 替换为Schedulers.io()或者是Schedulers.newThread()等是顺序不会变的，
     * 目前还不知道为什么，就是使用自定义的线程池的时候会。
     */
    private void demoConcatMap() {
        List<String> list = Arrays.asList("Z", "F", "B", "T", "A");
        Observable.from(list).concatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String integer) {
                Log.d(TAG, "call  1: " + Thread.currentThread().getName());
                return Observable.just(integer).subscribeOn(Schedulers.from(Executors.newFixedThreadPool(1)));
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String integer) {
                Log.d(TAG, "call   2: " + Thread.currentThread().getName());
                Log.d(TAG, "call: " + integer);

            }
        });
//        Observable.from(list).flatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String integer) {
//                Log.d(TAG, "call  1: "+Thread.currentThread().getName());
//                return Observable.just(integer).subscribeOn(Schedulers.from(Executors.newFixedThreadPool(1)));
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String integer) {
//                Log.d(TAG, "call   2: "+Thread.currentThread().getName());
//                Log.d(TAG, "call: "+integer);
//
//            }
//        });
    }

    private void demoSwitchMap() {
        List<String> list = asList("a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a");
        Observable.from(list).switchMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                System.out.println(s);
                return Observable.just(s).subscribeOn(Schedulers.newThread());
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.print(Thread.currentThread().getName());
            }
        });
    }

    private void demoRepeat() {
        Observable.just(1, 2, 3, 4, 5).repeatWhen(new Func1<Observable<? extends Void>, Observable<?>>() {
            @Override
            public Observable<?> call(final Observable<? extends Void> observable) {
                return observable
                        .zipWith(observable.range(1, 3), new Func2<Void, Integer, Integer>() {
                            @Override
                            public Integer call(Void aVoid, Integer integer) {
                                return integer;
                            }
                        })
                        .flatMap(new Func1<Integer, Observable<?>>() {
                            @Override
                            public Observable<?> call(Integer integer) {
                                Log.d(TAG, "call count: " + integer);
                                return observable.timer(3, TimeUnit.SECONDS);
                            }
                        });
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer.toString());
            }
        });
    }

    private void demoRange() {
        Observable.range(1, 9, Schedulers.io()).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer.toString() + "  ThreadName:" + Thread.currentThread().getName());
            }
        });
    }

    /**
     * interval操作符
     * 操作符默认情况下是运行在一个新线程上的，当然你可以通过传入参数来修改其运行的线程。
     */
    private void demoInterval() {
        interval(3, 3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNext: " + aLong);
                if (aLong == 10)
                    unsubscribe();
            }
        });
    }


    /**
     * timer操作符是创建一串连续的数字，产生这些数字的时间间隔是一定的；
     * 这里有两种情况：
     * <p>
     * 一种是隔一段时间产生一个数字，然后就结束，可以理解为延迟产生数字
     * <p>
     * 一种是每隔一段时间就产生一个数字，没有结束符，也就是是可以产生无限个连续的数字
     * <p>
     * timer操作符默认情况下是运行在一个新线程上的，当然你可以通过传入参数来修改其运行的线程
     */
    private void demoTimer() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG, "onNextTimer: " + aLong.toString());
            }
        });
    }

    /**
     * defer操作符是直到有订阅者订阅时，
     * 才通过Observable的工厂方法创建Observable并执行，
     * defer操作符能够保证Observable的状态是最新的
     */
    private Object i;

    private void demoDefer() {
        i = 10;
        Observable<Object> just = Observable.just(i);
        i = 12;
        Observable<Object> defer = Observable.defer(new Func0<Observable<Object>>() {
            @Override
            public Observable<Object> call() {
                return Observable.just(i);
            }
        });
        i = 16;
        just.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(Object integer) {
                Log.d(TAG, "onNext: " + integer.toString());
            }
        });

        defer.subscribe(new Subscriber<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object integer) {
                Log.d(TAG, "onNext: " + integer.toString());
            }
        });

        /**
         * 可以看到，just操作符是在创建Observable就进行了赋值操作，
         * 而defer是在订阅者订阅时才创建Observable，
         * 此时才进行真正的赋值操作
         */
    }

    /**
     * just操作符也是把其他类型的对象和数据类型转化成Observable，
     * 它和from操作符很像，只是方法的参数有所差别，其流程图例如下：
     */
    private void demoJust() {
        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5);
        just.subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer.toString());
            }
        });
    }

    /**
     * from操作符是把其他类型的对象和数据类型转化成Observable，其流程图例如下
     */
    private void demoFrom() {
        Integer[] integer = {1, 2, 3, 4, 5};
        Observable<Integer> from = Observable.from(integer);
        from.subscribe(
                new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "Action1   call: " + integer);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, "call2: " + throwable);
                    }
                },
                new Action0() {
                    @Override
                    public void call() {
                        Log.d(TAG, "call3: ");
                    }
                });
    }

    private void demoCreate() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    for (int i = 0; i < 5; i++) {
                        subscriber.onNext(i);
                    }
                    subscriber.onCompleted();
                }
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer.toString());
            }
        });
    }

    private void demoTwo() {
        //mergeDelayError的意思是合并几个不同的Observable
        Observable.mergeDelayError(
                //在IO线程中加载本地缓存图片
                loadBitmapFromLocal().subscribeOn(Schedulers.io()),
                //在新的线程中加载图片
                loadBitmapFromNet().subscribeOn(Schedulers.newThread()),
                Observable.timer(3, TimeUnit.SECONDS).map(new Func1<Long, Observable<?>>() {
                    @Override
                    public Observable<?> call(Long aLong) {
                        return null;
                    }
                })
                        //每隔2秒获取加载数据
                        .sample(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).flatMap(new Func1<Observable<?>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<?> b) {
                        if (b == null)  //如果没有获取到图片，直接跳转到主页面
                            return Observable.empty();
                        else { //如果获取到图片，则停留2秒再跳转到主页面
                            //imageView.setImageDrawable(r);
                            return Observable.timer(2, TimeUnit.SECONDS);
                        }
                    }
                })


        );
    }

    private Observable<? extends Observable<?>> loadBitmapFromNet() {
        return null;
    }


    private Observable<? extends Observable<?>> loadBitmapFromLocal() {
        return null;
    }

    private void demoOne() {
        Observable.
                //timer操作符，它的意思是延迟执行某个操作
                        timer(2000, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                //map操作符，它的意思是转换某个执行结果。
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        Intent intent = new Intent(DemoRxJavaActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return null;
                    }
                }).subscribe();
    }

}
