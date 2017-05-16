package cn.projects.com.projectsdemo.fragment.staticfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/25.
 */

public class ShiShangFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.static_fragment_shishang,container,false);
    }
}
