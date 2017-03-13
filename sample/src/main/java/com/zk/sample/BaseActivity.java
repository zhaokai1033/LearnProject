package com.zk.sample;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zk.baselibrary.skin.SkinBaseActivity;

/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/9.
 * @Email zhaokai1033@126.com
 * ================================================
 */

public abstract class BaseActivity<VDB extends ViewDataBinding> extends SkinBaseActivity {

    protected VDB binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResId());
    }
}
