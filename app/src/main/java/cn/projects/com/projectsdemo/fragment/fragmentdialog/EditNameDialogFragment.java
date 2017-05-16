package cn.projects.com.projectsdemo.fragment.fragmentdialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/28.
 */

public class EditNameDialogFragment extends DialogFragment {
    //重写onCreateView创建Dialog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_edit_name, container, false);
        return view;
    }
}
