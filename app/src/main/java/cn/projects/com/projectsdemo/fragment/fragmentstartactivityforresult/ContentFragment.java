package cn.projects.com.projectsdemo.fragment.fragmentstartactivityforresult;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by fengxing on 17/3/28.
 */
public class ContentFragment extends Fragment{
    public static final String ARGUMENT = "arguments";
    public static final String RESPONSE = "response";
    private String mArgument;

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
    public static ContentFragment newInstance(String argument){
        Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT,argument);
        ContentFragment contentFragment = new ContentFragment();
        contentFragment.setArguments(bundle);
        return contentFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Random random = new Random();
        TextView tv = new TextView(getActivity());
        tv.setText(mArgument);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundColor(Color.argb(random.nextInt(100),
                random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        return tv;
    }
}
