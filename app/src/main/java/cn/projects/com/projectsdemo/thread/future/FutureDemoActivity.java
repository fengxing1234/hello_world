package cn.projects.com.projectsdemo.thread.future;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.projects.com.projectsdemo.R;

/**
 * Future 和 run 的区别
 * 在于 run 无返回值
 * future有返回值
 * Created by fengxing on 2017/5/12.
 */

public class FutureDemoActivity extends AppCompatActivity {

    private static final String TAG = FutureDemoActivity.class.getSimpleName();

    @BindView(R.id.id_btn_future_start)
    Button btn_start;

    @BindView(R.id.id_btn_future_cancel)
    Button btn_cancel;

    @BindView(R.id.id_tv_future_result)
    TextView tv_result;
    private FutureTask<Number> futureTask;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_task);
        ButterKnife.bind(this);
        //FutureTest();
        //FutureResult();
        //futureCancel();
        //futureTaskTest();
    }

    @OnClick(R.id.id_btn_future_start)
    public void futureTaskTest() {
        CustomCallable customCallable = new CustomCallable();
        futureTask = new FutureTask<Number>(customCallable) {
            @Override
            protected void done() {
                try {
                    String name = Thread.currentThread().getName();
                    final Number number = futureTask.get();
                    Log.d(TAG, "number : " + number.number + " name : " + name);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_result.setText("" + number.number);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (CancellationException e) {
                    Log.d(TAG, "因为取消了所以异常了 ");
                }
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(futureTask);
    }

    @OnClick(R.id.id_btn_future_cancel)
    public void futureCancelBtn() {
        // true 表示强制取消，会发生异常；　false表示等待任务结束后取消
        futureTask.cancel(true);
    }

    private void futureCancel() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Number> future = executorService.submit(new CustomCallable());
        try {
            TimeUnit.SECONDS.sleep(10);
            //get方法会阻塞线程

            future.cancel(true);
            boolean cancelled = future.isCancelled();
            Log.d(TAG, "canceled : " + cancelled);

            if (cancelled) {
                executorService.shutdownNow();
                Log.d(TAG, "isShutdown: " + executorService.isShutdown());
            } else {
                Number number = future.get();
                System.out.println("任务结束于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                Log.d(TAG, "isDone: " + future.isDone());
                if (future.isDone()) {
                    System.out.println("任务执行完毕  result=" + number.number);
                    executorService.shutdown();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void FutureResult() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Number> future = executorService.submit(new CustomCallable());
        try {
            //get方法会阻塞线程
            Number number = future.get();
            Log.d(TAG, "FutureResult: " + Thread.currentThread().getName());
            if (future.isDone()) {
                Log.d(TAG, "任务执行完毕  result=" + number.number);
                executorService.shutdown();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void FutureTest() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<?> submit = executorService.submit(new CustomRunnable());
        Log.d(TAG, "任务开始于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
                Log.d(TAG, "主线程" + Thread.currentThread().getName() + "仍然可以执行");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Object object = submit.get();
            Log.d(TAG, "任务结束于" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "  result=" + object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "线程池状态:" + executorService.isShutdown());
        executorService.shutdown();
        Log.d(TAG, "关闭线程池:" + executorService.isShutdown());

    }

    private static class CustomCallable implements Callable<Number> {

        @Override
        public Number call() throws Exception {
            Log.d(TAG, "call: " + Thread.currentThread().getName());

            Number number = new Number();

            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(2);
                Log.d(TAG, "i : " + i);
                number.number = i;
            }

            return number;
        }
    }

    private static class Number {
        public int number;
    }

    private static class CustomRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    TimeUnit.NANOSECONDS.sleep(2);
                    Log.d(TAG, "工作线程" + Thread.currentThread().getName() + "正在执行  i=" + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
