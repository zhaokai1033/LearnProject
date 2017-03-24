package com.zk.sample.module.card.adpater;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.sample.R;
import com.zk.sample.databinding.ViewCardBinding;
import com.zk.sample.module.card.holder.CardHolder;
import com.zk.sample.module.card.model.CardItem;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Created by zhaokai on 2017/3/21.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public class CardViewAdapter extends PagerAdapter implements CardAdapter {

    private List<CardView> mViews;
    private List<CardItem> mData;
    private float mBaseElevation;

    public CardViewAdapter() {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
    }

    public void addCardItem(CardItem item) {
        mViews.add(null);
        mData.add(item);
    }

    public void addCardItems(List<CardItem> items) {
        for (int i = 0; i < items.size(); i++) {
            mViews.add(null);
        }
        mData.addAll(items);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mViews.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        ViewCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), R.layout.view_card, container, true);

        binding.setCard(mData.get(position));
        binding.setCardHolder(new CardHolder());

        if (mBaseElevation == 0) {
            mBaseElevation = binding.cardView.getCardElevation();
        }
        binding.cardView.setMaxCardElevation(mBaseElevation * MAX_ELEVATION_FACTOR);
        mViews.set(position, binding.cardView);
        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        mViews.set(position, null);
    }
}
