package cn.projects.com.projectsdemo.thread;

/**
 * Created by fengxing on 2017/5/11.
 */

public abstract class CustomPriorityRunnable implements Runnable, Comparable<CustomPriorityRunnable> {
    private int priority;

    public CustomPriorityRunnable(int priority) {
        if (priority < 0)
            throw new IllegalArgumentException();
        this.priority = priority;
    }

    @Override
    public int compareTo(CustomPriorityRunnable another) {
        int my = this.getPriority();
        int other = another.getPriority();
        return my < other ? 1 : my > other ? -1 : 0;
    }

    @Override
    public void run() {
        doSth();
    }

    public abstract void doSth();

    public int getPriority() {
        return priority;
    }
}
