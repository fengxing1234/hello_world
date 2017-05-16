package cn.projects.com.projectsdemo.screenchangedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/30.
 */

public class ScreenChangListActivity extends AppCompatActivity{

    private Activity mActivity = ScreenChangListActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_change);

        findViewById(R.id.id_screen_change_save_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity,SavedInstanceStateUsingActivity.class);
                startActivity(intent);
            }
        });
    }

}
