package cn.projects.com.projectsdemo.fragment.fragmentdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import cn.projects.com.projectsdemo.R;

/**
 * Created by fengxing on 17/3/28.
 */

public class LoginDialogFragment extends DialogFragment {

    private EditText mUsername;
    private EditText mPassword;

    public interface LoginInputListener
    {
        void onLoginInputComplete(String username, String password);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_login_dialog, null);
        mUsername = (EditText) view.findViewById(R.id.id_txt_username);
        mPassword = (EditText) view.findViewById(R.id.id_txt_password);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginInputListener listener = (LoginInputListener) getActivity();
                        listener.onLoginInputComplete(mUsername.getText().toString(), mPassword.getText().toString());
                    }
                })
                .setNegativeButton("cancel", null);
        return builder.create();
    }
}
