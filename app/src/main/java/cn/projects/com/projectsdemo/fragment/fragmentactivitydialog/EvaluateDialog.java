package cn.projects.com.projectsdemo.fragment.fragmentactivitydialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by fengxing on 17/3/28.
 */
public class EvaluateDialog extends DialogFragment{
    public static final String RESPONSE_EVALUATE = "response_evaluate";
    private String[] mEvaluteVals = new String[] { "GOOD", "BAD", "NORMAL" };
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Evaluate :").setItems(mEvaluteVals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(which);
            }
        });

        return builder.create();
    }

    // 设置返回数据
    protected void setResult(int which)
    {
        // 判断是否设置了targetFragment
        if (getTargetFragment() == null)
            return;

        Intent intent = new Intent();
        intent.putExtra(RESPONSE_EVALUATE, mEvaluteVals[which]);
        getTargetFragment().onActivityResult(ContentFragment.REQUEST_EVALUATE,
                Activity.RESULT_OK, intent);

    }
}
