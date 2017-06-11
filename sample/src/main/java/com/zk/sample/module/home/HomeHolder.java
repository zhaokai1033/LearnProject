package com.zk.sample.module.home;

import android.view.View;
import android.widget.TextView;

import com.zk.baselibrary.widget.adapter.BaseViewHolder;

/**
 * ========================================
 * Created by zhaokai on 2017/6/11.
 * Email zhaokai1033@126.com
 * des:
 * ========================================
 */

class HomeHolder extends BaseViewHolder<String> {

    HomeHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(String data, int position) {
        ((TextView) itemView).setText(data);
    }
}
