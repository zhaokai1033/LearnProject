package com.zk.sample.module.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zk.baselibrary.widget.adapter.BaseHeaderAndFooterAdapter;
import com.zk.sample.R;

import java.util.List;

/**
 * ========================================
 * Created by zhaokai on 2017/6/11.
 * Email zhaokai1033@126.com
 * des:
 * ========================================
 */

class HomeAdapter extends BaseHeaderAndFooterAdapter<String, HomeHolder> {

    HomeAdapter(List<String> mItemsData) {
        super(mItemsData);
    }

    @Override
    public HomeHolder onCreateRealViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        return new HomeHolder(view);
    }
}
