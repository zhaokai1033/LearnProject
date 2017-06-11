package com.zk.baselibrary.widget.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * ================================================
 * Created by zhaokai on 2017/5/17.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

class BaseHeaderFooterViewHolder<T> extends BaseViewHolder<T> {

    public BaseHeaderFooterViewHolder(View view) {
        super(view);
    }

    @Override
    public void setData(Object data, int position) {

    }

    public void addView(View view) {
        if (view != null) {
            ViewGroup viewParent = (ViewGroup) view.getParent();
            if (viewParent != null) {
                viewParent.removeView(view);
            }
            ((ViewGroup) itemView).removeAllViews();
            ((ViewGroup) itemView).addView(view);
        }
    }
}
