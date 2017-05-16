package cn.projects.com.projectsdemo.fragment.fragmentdialog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.projects.com.projectsdemo.R;
import cn.projects.com.projectsdemo.fragment.fragmentactivitydialog.ListTitleActivity;

/**
 * Created by fengxing on 17/3/28.
 */

public class FragmentDialogActivity extends Activity implements LoginDialogFragment.LoginInputListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_dialog);
        findViewById(R.id.id_fragment_dialog_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditNameDialogFragment editNameDialogFragment = new EditNameDialogFragment();
                editNameDialogFragment.show(getFragmentManager(),"editNameDialogFragment");
            }
        });
        findViewById(R.id.id_fragment_dialog_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDialogFragment loginDialogFragment = new LoginDialogFragment();
                loginDialogFragment.show(getFragmentManager(),"loginDialogFragment");
            }
        });
        findViewById(R.id.id_fragment_dialog_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FragmentDialogActivity.this,ListTitleActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(this, username+":"+password, Toast.LENGTH_SHORT).show();
    }
}
