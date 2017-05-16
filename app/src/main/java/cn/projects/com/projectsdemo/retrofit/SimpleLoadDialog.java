package cn.projects.com.projectsdemo.retrofit;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import java.lang.ref.WeakReference;

import cn.projects.com.projectsdemo.R;


/**
 * Created by fengxing on 2017/4/24.
 */

public class SimpleLoadDialog extends Handler {

    private static final String TAG = SimpleLoadDialog.class.getSimpleName();
    private ProgressCancelListener mListener;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    public static final int SHOW_PROGRESS_DIALOG = 1;
    private boolean mCancelable;
    private Dialog mDialog;
    private WeakReference<Context> reference;
    private Context context;

    public SimpleLoadDialog(Context context, ProgressCancelListener listener, boolean cancelable) {
        super();
        this.mListener = listener;
        this.mCancelable = cancelable;

        this.reference = new WeakReference<Context>(context);
    }


    private void create() {
        if (mDialog == null) {
            context = reference.get();
            mDialog = new Dialog(context, R.style.retrofitDialogStyle);
            View view = LayoutInflater
                    .from(context)
                    .inflate(R.layout.custom_sload_layout, null);
            //触摸窗体边界是否会取消对话框
            mDialog.setCanceledOnTouchOutside(false);
            //设置对话框点击回退键能否被取消掉
            mDialog.setCancelable(mCancelable);
            mDialog.setContentView(view);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mListener != null) {
                        mListener.onCancelProgress();
                    }
                }
            });
            Window window = mDialog.getWindow();
            window.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        }
        if (context != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }


    public void show() {
        create();
    }

    public void disMiss() {
        context = reference.get();
        if (mDialog != null && mDialog.isShowing() && !((Activity) context).isFinishing()) {
            String name = Thread.currentThread().getName();
            Log.d(TAG, "显示对话框的线程 name: " + name);
            mDialog.dismiss();
            mDialog = null;
        }
    }


    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                create();
                break;

            case DISMISS_PROGRESS_DIALOG:
                disMiss();
                break;
        }
    }
}
