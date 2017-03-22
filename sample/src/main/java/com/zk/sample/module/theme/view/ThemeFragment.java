package com.zk.sample.module.theme.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.data.DataManager;
import com.zk.sample.databinding.FragmentThemeBinding;
import com.zk.sample.module.theme.model.SkinBean;
import com.zk.sample.module.theme.viewModel.ThemeHolder;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/7.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class ThemeFragment extends BaseFragment<FragmentThemeBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_theme;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ThemeHolder holder = new ThemeHolder(this);
        binding.setViewModel(holder);
        binding.setSkinBlack(holder.getSkinBlack());
        binding.setSkinBlue(holder.getSkinBlue());
        binding.setSkinBrown(holder.getSkinBrown());
    }

    public interface DataBindingFace {
        SkinBean getSkinBlack();

        SkinBean getSkinBrown();

        SkinBean getSkinBlue();

        View.OnClickListener getOnClickListener();
    }
}
