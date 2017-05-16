package cn.projects.com.projectsdemo.coordinatelayout;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

/**
 * Created by fengxing on 2017/4/6.
 */

public class MyCoordinateLayout extends CoordinatorLayout.Behavior<Button> {


    private final int width;

    public MyCoordinateLayout(Context context, AttributeSet attrs){
        super(context,attrs);
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        width = display.widthPixels;

    }
    /**
     * 判断child的布局是否依赖dependency
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
        return dependency instanceof  TempView;
    }

    /**
     * 当dependency发生变化时位置、宽高等），执行这个函数
     * 返回true表示child的位置或者是宽高要发生改变，否则就返回false
     * @param parent
     * @param child
     * @param dependency
     * @return
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
        //child要执行的具体动作
        int top = dependency.getTop();
        int left = dependency.getLeft();

        int x = width - left - child.getWidth();
        int y = top;

        setPosition(child, x, y);
        return true;
    }

    private void setPosition(Button child, int x, int y) {
        CoordinatorLayout.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) child.getLayoutParams();
        layoutParams.leftMargin = x;
        layoutParams.topMargin = y;
        child.setLayoutParams(layoutParams);
    }
}
