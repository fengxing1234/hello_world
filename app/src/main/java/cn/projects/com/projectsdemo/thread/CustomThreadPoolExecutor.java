package cn.projects.com.projectsdemo.thread;

import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by fengxing on 2017/5/11.
 * beforeExecute() - 任务执行前执行的方法
 afterExecute() -任务执行结束后执行的方法
 terminated() -线程池关闭后执行的方法
 */

public class CustomThreadPoolExecutor extends ThreadPoolExecutor {

    private static final String TAG = CustomThreadPoolExecutor.class.getSimpleName();

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }


    /**
     * 任务执行前 执行的方法
     * @param t
     * @param r
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        String threadName = t.getName();
        Log.d(TAG, "线程：" + threadName + "准备执行任务！");
    }

    /**
     * 任务执行后执行的方法
     * @param r
     * @param t
     */
    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        String threadName = Thread.currentThread().getName();
        Log.d(TAG, "线程：" + threadName + "准备执行任务！");
    }

    /**
     * 线程池关闭 执行方法
     */
    @Override
    protected void terminated() {
        super.terminated();
        Log.d(TAG, "terminated: 执行结束 线程池关闭");
    }
}
