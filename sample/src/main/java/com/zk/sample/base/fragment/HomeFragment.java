package com.zk.sample.base.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.sample.R;
import com.zk.sample.UIControl;
import com.zk.sample.databinding.FragmentHomeBinding;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.module.binding.view.DataBindingFragment;
import com.zk.sample.module.card.view.DemoCardFragment;
import com.zk.sample.module.recycle.view.RecycleViewFragment;
import com.zk.sample.module.system.view.SystemFragment;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        binding.observe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showCustomFragment(((BaseActivity) getActivity()), DataBindingFragment.newInstance());
            }
        });
        binding.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showCustomFragment(((BaseActivity) getActivity()), DemoCardFragment.newInstance());
            }
        });
        binding.recycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showCustomFragment(((BaseActivity) getActivity()), RecycleViewFragment.newInstance());
            }
        });
        binding.system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showCustomFragment(((BaseActivity) getActivity()), SystemFragment.newInstance());
            }
        });
    }

}
