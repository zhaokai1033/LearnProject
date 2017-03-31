package com.zk.sample.module.system.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.SystemUtil;
import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentSystemBinding;

/**
 * ================================================
 * Created by zhaokai on 2017/3/30.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class SystemFragment extends BaseFragment<FragmentSystemBinding> {

    private static final String TAG = "SystemFragment";

    public static SystemFragment newInstance() {

        Bundle args = new Bundle();

        SystemFragment fragment = new SystemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_system;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        binding.setFragment(this);
        rejust();
    }

    private void rejust() {
        ViewGroup rootView = ((ViewGroup) getActivity().getWindow().getDecorView().getRootView());

        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }

    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_full:
                    SystemUtil.setFullScreen(getActivity(), isChecked);
                    break;
                case R.id.cb_status:
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        //系统方法 fitSystem 存在兼容性问题
                        SystemUtil.setTranslucentStatus(getActivity(), isChecked);
                    }
                    break;
            }
        }
    };

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return onCheckedChangeListener;
    }
}
