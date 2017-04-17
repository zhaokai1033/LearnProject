package com.zk.sample.module.home;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zk.baselibrary.util.ClassUtil;
import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.SystemUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.R;
import com.zk.sample.UIControl;
import com.zk.sample.databinding.FragmentHomeBinding;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.module.binding.view.DataBindingFragment;
import com.zk.sample.module.card.view.DemoCardFragment;
import com.zk.sample.module.demo.ViewFragment;
import com.zk.sample.module.permission.PermissionFragment;
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
        binding.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showCustomFragment(((BaseActivity) getActivity()), ClassUtil.createInstance(ViewFragment.class));
            }
        });
        binding.tvPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIControl.showCustomFragment(((BaseActivity) getActivity()), ClassUtil.createInstance(PermissionFragment.class));
            }
        });
        binding.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtil.changeDirection(getActivity());
            }
        });
        binding.tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shaderText((TextView) v);
                getActivity().setTitle("测试标题");
            }
        });

    }

    private void shaderText(TextView v) {
        Shader shader = v.getPaint().getShader();
        if (shader == null) {
            shader = new LinearGradient(0, 0, 0, 50, Color.parseColor("#a35726"), Color.parseColor("#d88f2d"), Shader.TileMode.CLAMP);
            v.getPaint().setShader(shader);
        } else {
            v.getPaint().setShader(null);
            v.invalidate();
        }
    }

}
