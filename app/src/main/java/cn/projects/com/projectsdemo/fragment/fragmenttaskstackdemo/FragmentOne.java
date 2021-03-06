package cn.projects.com.projectsdemo.fragment.fragmenttaskstackdemo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
    public static final String STRING_NAME = "stringName";

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
                FragmentTwo fTwo = new FragmentTwo();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction tx = fm.beginTransaction();
                tx.replace(R.id.id_back_stack_fragment_content, fTwo, "TWO");
                tx.addToBackStack(null);
                tx.commit();
            }
        });
        return view;
    }

    public static FragmentOne newInstance(){
        Bundle bundle = new Bundle();
        bundle.putString(STRING_NAME,"冯星");
        FragmentOne fragmentOne = new FragmentOne();
        fragmentOne.setArguments(bundle);
        return fragmentOne;
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
        Bundle bundle = getArguments();
        if(bundle!=null){
            String string = bundle.getString(STRING_NAME);
            Log.d(TAG, "string: "+string);
        }
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
