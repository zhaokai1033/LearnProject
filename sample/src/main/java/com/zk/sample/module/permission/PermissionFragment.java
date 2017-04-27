package com.zk.sample.module.permission;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentPermissionBinding;

/**
 * ================================================
 * Created by zhaokai on 2017/4/14.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class PermissionFragment extends BaseFragment<FragmentPermissionBinding> {

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_permission;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final PermissionHolder holder = new PermissionHolder();
        binding.setHolder(holder);
        holder.setShowText(binding.tvDes);
        binding.btState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.showDialog(getActivity());
            }
        });
    }
}
