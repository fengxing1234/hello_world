package cn.projects.com.projectsdemo.retrofit;

import android.content.Context;

import rx.Subscriber;

/**
 * Created by fengxing on 2017/4/24.
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{


    private Context mContext;
    private SimpleLoadDialog simpleLoadDialog;
    private boolean isShowDialog = true;

    public ProgressSubscriber(Context context){
        this.mContext = context;
        simpleLoadDialog = new SimpleLoadDialog(context,this,true);
    }

    public void setShowDialog(boolean isShowDialog){
        this.isShowDialog = isShowDialog;
    }

    @Override
    public void onCompleted() {
        disMissDialog();
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (simpleLoadDialog != null && isShowDialog) {
            simpleLoadDialog.obtainMessage(SimpleLoadDialog.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void disMissDialog(){
        if(simpleLoadDialog != null){
            simpleLoadDialog.obtainMessage(SimpleLoadDialog.DISMISS_PROGRESS_DIALOG).sendToTarget();
            simpleLoadDialog = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
//        if (false) { //这里自行替换判断网络的代码
//            _onError("网络不可用");
//        } else if (e instanceof ApiException) {
//            _onError(e.getMessage());
//        } else {
//            _onError("请求失败，请稍后再试...");
//        }
        onDoError(e);
        disMissDialog();
    }


    @Override
    public void onNext(T t) {
        onDoNext(t);
    }

    @Override
    public void onCancelProgress() {
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }


    protected abstract void onDoError(Throwable e);
    protected abstract void onDoNext(T t);

}
