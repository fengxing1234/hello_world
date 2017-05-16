package cn.projects.com.projectsdemo.coordinatelayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by fengxing on 2017/4/6.
 */

public class TempView extends View {

    private int heightPixels;
    private int widthPixels;
    private int lastX;
    private int lastY;

    private static final String TAG = "TempView";
    
    public TempView(Context context) {
        this(context,null);
    }

    public TempView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
        Log.d(TAG, "screenWidth : " + widthPixels + "  screenHeight :" + heightPixels );

        int mHeight = getMeasuredHeight();
        int mWidth = getMeasuredWidth();
        Log.d(TAG, "mHeight : " + mHeight + "  mWidth :" + mWidth );



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int rawX = (int) event.getRawX();
        int rawY = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_MOVE:
                ViewGroup.MarginLayoutParams layoutParams = (CoordinatorLayout.MarginLayoutParams) getLayoutParams();
                int left = (int) (layoutParams.leftMargin + rawX - lastX);
                int top = (int) (layoutParams.topMargin + rawY - lastY);
                layoutParams.leftMargin = left;
                layoutParams.topMargin = top;
                setLayoutParams(layoutParams);
                requestLayout();
                break;
        }
        lastX = rawX;
        lastY = rawY;
        return true;
    }
}
