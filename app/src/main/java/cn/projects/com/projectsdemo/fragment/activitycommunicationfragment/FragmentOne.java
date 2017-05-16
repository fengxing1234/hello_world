package cn.projects.com.projectsdemo.fragment.activitycommunicationfragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/26.
 *
 * 在开启FragmentOne之后再开启FragmenTwo 之后调用的生命周期
 D/FragmentTwo: onAttach:
 D/FragmentTwo: onCreate:
 D/FragmentOne: onPause:
 D/FragmentOne: onStop:
 D/FragmentOne: onDestroyView:
 D/FragmentTwo: onActivityCreated:
 D/FragmentTwo: onStart:
 D/FragmentTwo:onResume:
 */
public class FragmentOne extends Fragment {

    private static final String TAG = "FragmentOne";

    public interface FragmentOneClickListener{
        void onOneClick();
    }

    private FragmentOneClickListener f;
    public void setFragmentOneClickListener(FragmentOneClickListener f){
        this.f = f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 不加最后一个参数会报错 必须加上FALSE
        //Caused by: java.lang.IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_back_stack_one, container,false);
        view.findViewById(R.id.id_button_fragment_back_stack_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(f!=null){
                    f.onOneClick();
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }
}
