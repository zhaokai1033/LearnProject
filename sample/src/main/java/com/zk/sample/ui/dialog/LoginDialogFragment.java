package com.zk.sample.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import com.zk.sample.R;

/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/15.
 * @Email zhaokai1033@126.com
 * ================================================
 */

public class LoginDialogFragment extends DialogFragment {

    public static LoginDialogFragment newInstance(FragmentManager manager) {
        LoginDialogFragment editNameDialog = new LoginDialogFragment();
        editNameDialog.show(manager, "LoginDialogFragment");
        return editNameDialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        return inflater.inflate(R.layout.dialog_demo, container);
    }

    private EditText mUsername;
    private EditText mPassword;

    public interface LoginInputListener {
        void onLoginInputComplete(String username, String password);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_demo, null);
        mUsername = (EditText) view.findViewById(R.id.id_txt_username);
        mPassword = (EditText) view.findViewById(R.id.id_txt_password);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("Sign in",
                        new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int id)
                            {
                                LoginInputListener listener = (LoginInputListener) getActivity();
                                listener.onLoginInputComplete(mUsername
                                        .getText().toString(), mPassword
                                        .getText().toString());
                            }
                        }).setNegativeButton("Cancel", null);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        // onAttach()是合适的早期阶段进行检查Activity是否真的实现了接口。
        // 采用接口的方式，dialog无需详细了解Activity，只需了解其所需的接口函数，这是真正项目中应采用的方式。
        if (!(activity instanceof LoginInputListener)) {
            throw new IllegalStateException("fragment所在的Activity必须实现LoginInputListener接口");
        }
        super.onAttach(activity);
    }

}
