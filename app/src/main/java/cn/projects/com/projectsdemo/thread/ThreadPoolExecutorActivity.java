package cn.projects.com.projectsdemo.thread;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 2017/5/11.
 */

public class ThreadPoolExecutorActivity extends AppCompatActivity {


    private static final int CORD_THREAD_SIZE = 4;
    private static final int MAX_THREAD_SIZE = 2;
    private static final long KEEP_ALIVE_TIME = 60 * 1000;
    private static final String TAG = ThreadPoolExecutorActivity.class.getSimpleName();

    @Bind(R.id.id_tv_thread_pause)
    TextView textView;

    @Bind(R.id.id_btn_thread_start)
    Button btn_start;

    @Bind(R.id.id_btn_thread_pause)
    Button btn_pause;

//    @Bind(R.id.id_btn_thread_stop)
//    Button btn_stop;
//    @Bind(R.id.id_btn_thread_stop_all)
//    Button btn_stp_all;

    private PausableThreadPoolExecutor pausableThreadPoolExecutor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);
        ButterKnife.bind(this);
        //ThreadPoolTest(2);
        //newCachedThreadPoolTest(2);
        //scheduledExecutorServiceTest(2);
        //ScheduledExecutorServiceTest();
        //customPoolExecutor();
    }

    private ExecutorService createThread(int i) {
        ExecutorService executorService = null;
        switch (i) {
            case 1:
                /**
                 * 作用：该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，即不会再创建新的线程，也不会销毁已经创建好的线程，自始自终都是那几个固定的线程在工作，所以该线程池可以控制线程的最大并发数。
                 * 栗子：假如有一个新任务提交时，线程池中如果有空闲的线程则立即使用空闲线程来处理任务，如果没有，则会把这个新任务存在一个任务队列中，一旦有线程空闲了，则按FIFO方式处理任务队列中的任务。
                 */
                executorService = Executors.newFixedThreadPool(MAX_THREAD_SIZE);
                break;
            case 2:
                /**
                 * 作用：该方法返回一个可以根据实际情况调整线程池中线程的数量的线程池。即该线程池中的线程数量不确定，是根据实际情况动态调整的。
                 * 栗子：假如该线程池中的所有线程都正在工作，而此时有新任务提交，那么将会创建新的线程去处理该任务，而此时假如之前有一些线程完成了任务，现在又有新任务提交，那么将不会创建新线程去处理，而是复用空闲的线程去处理新任务。
                 * 那么此时有人有疑问了，那这样来说该线程池的线程岂不是会越集越多？其实并不会，因为线程池中的线程都有一个“保持活动时间”的参数，通过配置它，如果线程池中的空闲线程的空闲时间超过该“保存活动时间”则立刻停止该线程，而该线程池默认的“保持活动时间”为60s。
                 */
                executorService = Executors.newCachedThreadPool();
                break;
            case 3:
                /**
                 *作用：该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，等待这一个线程空闲，当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。
                 */
                executorService = Executors.newSingleThreadExecutor();
                break;
            case 4:
                /**
                 * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
                 */
                executorService = Executors.newScheduledThreadPool(MAX_THREAD_SIZE);
                break;
            case 5:
                /**
                 * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。只不过和上面的区别是该线程池大小为1，而上面的可以指定线程池的大小。
                 */
                executorService = Executors.newSingleThreadScheduledExecutor();
                break;
        }

        /**
         * workQueue:
         * 1、newFixedThreadPool()—>LinkedBlockingQueue
         * 2、newSingleThreadExecutor()—>LinkedBlockingQueue
         * 3、newCachedThreadPool()—>SynchronousQueue
         * 4、newScheduledThreadPool()—>DelayedWorkQueue
         * 5、newSingleThreadScheduledExecutor()—>DelayedWorkQueue
         */

        /**
         * LinkedBlockingQueue：无界的队列
         * SynchronousQueue：直接提交的队列
         * DelayedWorkQueue：等待队列
         * 实现了BlockingQueue接口的队列还有：ArrayBlockingQueue（有界的队列）、PriorityBlockingQueue（优先级队列）。
         */
        return executorService;
    }

    private void threadPoolExecutor() {
        /**
         * 参数:
         * 1. corePoolSize: 线程池中的核心线程数量
         * 2. maximumPoolSize: 线程池中的最大线程数量
         * 3. keepAliveTime: 保持活动时间   就是当线程池中的线程数量超过了corePoolSize时，它表示多余的空闲线程的存活时间，即：多余的空闲线程在超过keepAliveTime时间内没有任务的话则被销毁。而这个主要应用在缓存线程池中
         * 4. unit: 时间单位
         * 5. workQueue：任务队列，主要用来存储已经提交但未被执行的任务，不同的线程池采用的排队策略不一样
         * 6. threadFactory：线程工厂，用来创建线程池中的线程，通常用默认的即可
         * 7. handler：通常叫做拒绝策略，1、在线程池已经关闭的情况下 2、任务太多导致最大线程数和任务队列已经饱和，无法再接收新的任务 。在上面两种情况下，只要满足其中一种时，在使用execute()来提交新的任务时将会拒绝，而默认的拒绝策略是抛一个RejectedExecutionException异常
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(CORD_THREAD_SIZE, MAX_THREAD_SIZE, KEEP_ALIVE_TIME, TimeUnit.MICROSECONDS, null, null, null);

    }


    private void ThreadPoolTest(int threadId) {
        ExecutorService service = createThread(threadId);
        for (int i = 0; i < 20; i++) {
            final int index = i;
            service.execute(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    Log.d(TAG, "线程：" + name + ",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        /**
         * 1. newFixedThreadPool:
         *
         * 我们创建了一个线程数为2的固定线程数量的线程池，
         * 同理该线程池支持的线程最大并发数也是2，而我模拟了20个任务让它处理，
         * 执行的情况则是首先执行前2个任务，后面18个则依次进入任务队列进行等待，
         * 执行完前2个任务后，再通过FIFO的方式从任务队列中取任务执行，直到最后任务都执行完毕
         *
         * 2.
         * 即依次一个一个的处理任务，而且都是复用一个线程
         * newSingleThreadExecutor
         *
         * 3.newCachedThreadPool
         * 这么些总是创建新的线程
         *
         * 4.newScheduledThreadPool
         * 创建一个可以定时或者周期性执行任务的线程池
         */
    }

    /**
     * 为了体现该线程池可以自动根据实现情况进行线程的重用，
     * 而不是一味的创建新的线程去处理任务，
     * 我设置了每隔1s去提交一个新任务，
     * 这个新任务执行的时间也是动态变化的
     *
     * @param threadId
     */
    private void newCachedThreadPoolTest(int threadId) {

        ExecutorService service = createThread(threadId);
        for (int i = 0; i < 20; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            service.execute(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    Log.d(TAG, "线程：" + name + ",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    private void scheduledExecutorServiceTest(int which) {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        switch (which) {
            case 1:
                //延迟5秒后执行该任务
                Log.d(TAG, "scheduledExecutorServiceTest: 开始执行");
                scheduledThreadPool.schedule(new Runnable() {
                    @Override
                    public void run() {
                        String name = Thread.currentThread().getName();
                        Log.d(TAG, "run: 已经执行 :" + name);
                    }
                }, 5, TimeUnit.SECONDS);
                break;
            case 2:
                //延迟5秒后，每隔3秒执行一次该任务
                Log.d(TAG, "scheduledExecutorServiceTest: 开始执行");
                scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        String name = Thread.currentThread().getName();
                        Log.d(TAG, "run: 已经执行" + name);
                    }
                }, 5, 3, TimeUnit.SECONDS);
                break;
        }
    }

    private void ScheduledExecutorServiceTest() {
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔2秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                Log.d(TAG, "线程：" + threadName + ",正在执行");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }


    private void customPoolExecutor() {
        ExecutorService priorityThreadPool = new CustomThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 1; i <= 10; i++) {
            final int priority = i;
            priorityThreadPool.execute(new CustomPriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    Log.v(TAG, "线程：" + threadName + ",正在执行优先级为：" + priority + "的任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void pausePoolExecutor() {
        pausableThreadPoolExecutor = new PausableThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 0; i < 100; i++) {
            final int index = i;
            pausableThreadPoolExecutor.execute(new CustomPriorityRunnable(index) {
                @Override
                public void doSth() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("priority : " + index + "  name : " + Thread.currentThread().getName());
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @OnClick(R.id.id_btn_thread_start)
    public void startThread() {
        pausePoolExecutor();
        getCPUSize();
    }

    /**
     * 通常核心线程数可以设为CPU数量+1，而最大线程数可以设为CPU的数量*2+1。
     * 三星s7 4个
     */
    private void getCPUSize() {
        int i = Runtime.getRuntime().availableProcessors();
        Log.d(TAG, "getCPUSize: " + i);
    }

    private boolean isPause = true;

    @OnClick(R.id.id_btn_thread_pause)
    public void pauseThread() {
        if (isPause) {
            pausableThreadPoolExecutor.pause();
            isPause = false;
        } else {
            pausableThreadPoolExecutor.resume();
            isPause = true;
        }
    }

    @OnClick(R.id.id_btn_thread_stop)
    public void threadStop() {
        if (pausableThreadPoolExecutor != null) {
            Log.d(TAG, "isShutDown: " + pausableThreadPoolExecutor.isShutdown());
            pausableThreadPoolExecutor.shutdown();
            Log.d(TAG, "isShutDown: " + pausableThreadPoolExecutor.isShutdown());
        }
    }

    @OnClick(R.id.id_btn_thread_stop_all)
    public void threadStopAll() {
        if (pausableThreadPoolExecutor != null) {
            Log.d(TAG, "isShutDown: " + pausableThreadPoolExecutor.isShutdown());
            pausableThreadPoolExecutor.shutdownNow();
            Log.d(TAG, "isShutDown: " + pausableThreadPoolExecutor.isShutdown());
        }
    }
}
