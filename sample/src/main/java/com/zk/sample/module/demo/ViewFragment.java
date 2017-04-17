package com.zk.sample.module.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentViewDemoBinding;

/**
 * ================================================
 * Created by zhaokai on 2017/4/13.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class ViewFragment extends BaseFragment<FragmentViewDemoBinding> {

    private ViewDemoHolder holder;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_view_demo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        holder = ViewDemoHolder.newInstance();
//        holder.setName("测试");
        holder.setNumber("123");
        binding.setHolder(holder);
        binding.bt1.setBadgeText("123");
        binding.bt2.setBadgeText("223aqwe");
    }
}
