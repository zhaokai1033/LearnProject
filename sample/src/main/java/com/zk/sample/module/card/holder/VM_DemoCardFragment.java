package com.zk.sample.module.card.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CompoundButton;

import com.zk.baselibrary.util.UIUtil;
import com.zk.sample.R;
import com.zk.sample.data.DataManager;
import com.zk.sample.databinding.FragmentCardDemoBinding;
import com.zk.sample.module.ViewHolderRefreshListener;
import com.zk.sample.module.card.adpater.CardFragmentAdapter;
import com.zk.sample.module.card.CardPageChangeListener;
import com.zk.sample.module.card.adpater.CardViewAdapter;
import com.zk.sample.module.card.view.DemoCardFragment;

/**
 * ================================================
 * Created by zhaokai on 2017/3/21.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class VM_DemoCardFragment implements DemoCardFragment.HolderFace {

    private final CardFragmentAdapter mFragmentAdapter;
    private final CardViewAdapter mCardAdapter;
    private final CardPageChangeListener mCardPageListener;
    private final ViewHolderRefreshListener refreshListener;
    private boolean mShowingFragments;
    private FragmentCardDemoBinding binding;

    public VM_DemoCardFragment(DemoCardFragment fragment, FragmentCardDemoBinding binding, ViewHolderRefreshListener refreshListener) {
        mShowingFragments = false;
        this.refreshListener = refreshListener;
        this.binding = binding;
        mCardPageListener = new CardPageChangeListener(binding.viewPager);
        mFragmentAdapter = new CardFragmentAdapter(fragment.getChildFragmentManager(), UIUtil.dip2px(fragment.getContext(), 2));
        mCardAdapter = new CardViewAdapter();
        mCardAdapter.addCardItems(DataManager.getCards());
    }

    private View.OnClickListener typeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!mShowingFragments) {
                binding.cardTypeBtn.setText(R.string.views);
            } else {
                binding.cardTypeBtn.setText(R.string.fragments);
            }
            mShowingFragments = !mShowingFragments;
            refreshListener.refresh(v);
        }
    };

    /**
     * 选择框监听事件
     */
    private CompoundButton.OnCheckedChangeListener checkListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mCardPageListener.enableScaling(isChecked);
        }
    };

    @Override
    public ViewPager.OnPageChangeListener getOnPageChangeListener() {
        return mCardPageListener;
    }

    @Override
    public PagerAdapter getAdapter() {
        return mShowingFragments ? mFragmentAdapter : mCardAdapter;
    }

    @Override
    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return checkListener;
    }

    @Override
    public View.OnClickListener getOnCardTypeBtnClickListener() {
        return typeClickListener;
    }
}
