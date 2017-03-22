package com.zk.sample.module.cardView.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;

import com.zk.baselibrary.util.UIUtil;
import com.zk.sample.R;
import com.zk.sample.databinding.FragmentCardDemoBinding;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.module.ViewHolderRefreshListener;
import com.zk.sample.module.cardView.viewModel.VM_DemoCardFragment;

/**
 * ================================================
 * Created by zhaokai on 2017/3/20.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class DemoCardFragment extends BaseFragment<FragmentCardDemoBinding> implements ViewHolderRefreshListener {

    private HolderFace model;

    public static DemoCardFragment newInstance() {

        Bundle args = new Bundle();

        DemoCardFragment fragment = new DemoCardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_card_demo;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        model = new VM_DemoCardFragment(this, binding, this);
        binding.setViewModel(model);
        binding.viewPager.addOnPageChangeListener(model.getOnPageChangeListener());
        binding.viewPager.setAdapter(model.getAdapter());
        binding.viewPager.setPageMargin(UIUtil.dip2px(getContext(), 5));
        binding.viewPager.setOffscreenPageLimit(3);
    }

    /**
     * ViewHolder 刷新回调
     */
    @Override
    public void refresh(View view) {
        binding.viewPager.setAdapter(model.getAdapter());
    }

    /**
     * 此Fragment 所用的ViewModel 必须要此接口
     * 在res 文件中 所用的参数都由此接口提供
     */
    public interface HolderFace {

        /**
         * 切换动画处理
         */
        ViewPager.OnPageChangeListener getOnPageChangeListener();

        /**
         * ViewPager 适配器
         */
        PagerAdapter getAdapter();

        /**
         * Scale 选择事件
         */
        CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener();

        /**
         * 样式点击事件
         */
        View.OnClickListener getOnCardTypeBtnClickListener();
    }
}
