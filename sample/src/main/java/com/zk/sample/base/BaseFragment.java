package com.zk.sample.base;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.zk.baselibrary.skin.SkinBaseFragment;

import java.util.Map;

/**
 * ================================================
 * Describe：
 * 针对 当前APK 进行定制
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

public abstract class BaseFragment<VDB extends ViewDataBinding> extends SkinBaseFragment {

    @SuppressWarnings("unused")
    private static final String TAG = "BaseFragment";
    protected VDB binding;

    @Override
    protected Map<String, View> getStatesViews() {
        return null;
    }

    @Override
    protected boolean isAddToFrameLayout() {
        return false;
    }

    @Override
    protected View getTitleView() {
        return null;
    }

    @Override
    public boolean isShowTitle() {
        return false;
    }

    @Override
    public View onCreateView(ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        } catch (Exception e) {
            binding = null;
        }

        View v;
        if (binding != null && binding.getRoot() != null) {
            v = binding.getRoot();
        } else {
            v = inflater.inflate(getLayoutRes(), container, false);
        }
        return v;
    }
}
