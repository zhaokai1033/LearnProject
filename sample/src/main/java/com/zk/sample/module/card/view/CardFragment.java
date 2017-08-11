package com.zk.sample.module.card.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.module.card.adpater.CardAdapter;


public class CardFragment extends BaseFragment {

    private CardView mCardView;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_card;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mCardView = (CardView) view.findViewById(R.id.cardView);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
    }

    public CardView getCardView() {
        return mCardView;
    }
}
