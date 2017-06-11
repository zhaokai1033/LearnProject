package com.zk.baselibrary.widget.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Created by zhaokai on 2017/5/17.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public abstract class BaseHeaderAndFooterAdapter<T, VH extends BaseViewHolder<T>> extends BaseRecycleAdapter<T, BaseViewHolder<T>> {

    private static final int TYPE_HEADER = 7898;
    private static final int TYPE_FOOTER = 7899;
    private List<View> mHeaders = new ArrayList<>();
    private List<View> mFooters = new ArrayList<>();

    public BaseHeaderAndFooterAdapter(List<T> mItemsData) {
        super(mItemsData);
    }

    @Override
    public final BaseViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER || viewType == TYPE_FOOTER) {
            FrameLayout view = new FrameLayout(parent.getContext());
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new BaseHeaderFooterViewHolder<>(view);
        } else {
            return onCreateRealViewHolder(parent, viewType);
        }
    }

    public abstract VH onCreateRealViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(BaseViewHolder<T> holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_HEADER || type == TYPE_FOOTER) {
            onBindHeaderFooter(((BaseHeaderFooterViewHolder) holder), position);
        } else {
            super.onBindViewHolder(holder, getRealPosition(position));
        }
    }

    private void onBindHeaderFooter(BaseHeaderFooterViewHolder holder, int position) {
        if (position < mHeaders.size()) {
            holder.addView(mHeaders.get(position));
        } else {
            int total = mHeaders.size() + super.getItemCount();
            holder.addView(mFooters.get(position - total));
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (position < mHeaders.size()) {
            return TYPE_HEADER;
        } else if (position >= (mHeaders.size() + super.getItemCount())) {
            return TYPE_FOOTER;
        } else {
            return getItemType(getRealPosition(position));
        }
    }

    private int getRealPosition(int position) {
        return position - mHeaders.size();
    }

    @Override
    public final int getItemCount() {
        return super.getItemCount() + mFooters.size() + mHeaders.size();
    }

    public void addHeader(View header) {
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            // animate
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    // remove a header from the adapter
    public void removeHeader(View header) {
        if (mHeaders.contains(header)) {
            // animate
            notifyItemRemoved(mHeaders.indexOf(header));
            mHeaders.remove(header);
        }
    }

    // add a footer to the adapter
    public void addFooter(View footer) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            // animate
            notifyItemInserted(mHeaders.size() + super.getItemCount() + mFooters.size() - 1);
        }
    }

    // remove a footer from the adapter
    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            // animate
            notifyItemRemoved(mHeaders.size() + super.getItemCount() + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    /**
     * 获取实际类型
     */
    @SuppressWarnings("WeakerAccess")
    public int getItemType(int position) {
        return super.getItemViewType(position);
    }
}
