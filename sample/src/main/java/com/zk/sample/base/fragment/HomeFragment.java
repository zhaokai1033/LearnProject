package com.zk.sample.base.fragment;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

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

    private static final String TAG = "HomeFragment";

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
        binding.video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int orientation = SystemUtil.getDisplayRotation(getActivity());
                LogUtil.d(TAG, "ScreenOrientation:" + orientation);
                if (orientation == 0 || orientation == 180) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                    } else {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                    } else {
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    }
                }
            }
        });
    }

}
