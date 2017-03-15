package com.zk.sample.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.sample.R;
import com.zk.sample.databinding.FragmentHomeBinding;
import com.zk.sample.ui.base.BaseActivity;
import com.zk.sample.ui.base.BaseFragment;

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
        binding.tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).showDialog();
            }
        });
    }

}
