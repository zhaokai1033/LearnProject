package com.zk.sample.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.zk.baselibrary.app.BaseFra;
import com.zk.baselibrary.skin.SkinBaseActivity;
import com.zk.sample.module.binding.view.LoginDialogFragment;

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

    /**
     * 获取当前页可替换的FrameLayout 资源
     */
    @SuppressWarnings("unused")
    public abstract int getFragmentContentId();

    public BaseFra changeFragment(BaseFra current, BaseFra target, boolean isNeedRefresh, boolean canBack) {
        if (getFragmentContentId() == 0) {
            throw new IllegalArgumentException("FragmentContentId cannot be 0 ");
        }
        return changeFragment(current, target, getFragmentContentId(), isNeedRefresh, canBack);
    }
}
