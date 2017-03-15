package com.zk.sample.ui.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zk.baselibrary.skin.SkinBaseActivity;
import com.zk.sample.ui.dialog.LoginDialogFragment;

/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/9.
 * @Email zhaokai1033@126.com
 * ================================================
 */

public abstract class BaseActivity<VDB extends ViewDataBinding> extends SkinBaseActivity implements LoginDialogFragment.LoginInputListener {

    protected VDB binding;

    enum DIALOG {
        DEMO
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, getLayoutResId());
    }

    @Override
    public void onLoginInputComplete(String username, String password) {
        Toast.makeText(this, "帐号：" + username + ",  密码 :" + password,
                Toast.LENGTH_SHORT).show();
    }

    public void showDialog() {
        LoginDialogFragment.newInstance(getSupportFragmentManager());
    }

}
