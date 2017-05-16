package cn.projects.com.projectsdemo.fragment.fragmentactivitydialog;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by fengxing on 17/3/28.
 */

public class ContentFragment extends Fragment {
    private String mArgument;
    public static final String ARGUMENT = "argument";
    public static final String RESPONSE = "response";
    public static final String EVALUATE_DIALOG = "evaluate_dialog";
    public static final int REQUEST_EVALUATE = 0X110;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            mArgument = bundle.getString(ARGUMENT);
            Intent intent = new Intent();
            intent.putExtra(RESPONSE, "接受到传递的信息");
            getActivity().setResult(ListTitleFragment.REQUEST_DETAIL,intent);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Random random = new Random();
        TextView tv = new TextView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setText(mArgument);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.argb(random.nextInt(100),
                random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EvaluateDialog dialog = new EvaluateDialog();
                //注意setTargetFragment
                //这个方法，一般就是用于当前fragment由别的fragment启动
                dialog.setTargetFragment(ContentFragment.this, REQUEST_EVALUATE);
                dialog.show(getFragmentManager(), EVALUATE_DIALOG);
            }
        });
        return tv;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EVALUATE)
        {
            String evaluate = data
                    .getStringExtra(EvaluateDialog.RESPONSE_EVALUATE);
            Toast.makeText(getActivity(), evaluate, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra(RESPONSE, evaluate);
            getActivity().setResult(Activity.RESULT_OK, intent);
        }


    }
}
