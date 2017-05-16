package cn.projects.com.projectsdemo.retrofit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.subjects.PublishSubject;

/**
 * Created by fengxing on 2017/4/25.
 */

public class BaseActivity extends AppCompatActivity {

    public final PublishSubject<ActivityLifeCycleEvent> publishSubject = PublishSubject.create();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        publishSubject.onNext(ActivityLifeCycleEvent.CREATE);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        publishSubject.onNext(ActivityLifeCycleEvent.START);
        super.onStart();

    }

    @Override
    protected void onResume() {
        publishSubject.onNext(ActivityLifeCycleEvent.RESUME);
        super.onResume();
    }

    @Override
    protected void onPause() {
        publishSubject.onNext(ActivityLifeCycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        publishSubject.onNext(ActivityLifeCycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        publishSubject.onNext(ActivityLifeCycleEvent.DESTROY);
    }
}
