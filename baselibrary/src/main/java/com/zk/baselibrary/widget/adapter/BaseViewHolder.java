package com.zk.baselibrary.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    @SuppressWarnings("WeakerAccess")
    protected Context mContext;
    private View.OnClickListener onItemClickListener;

    public BaseViewHolder(final View itemView) {
        super(itemView);
        this.mContext = itemView.getContext();
        View.OnClickListener listener = getOnItemViewClick();
        if (listener != null) {
            itemView.setOnClickListener(listener);
        }
    }

    /**
     * 根据mItems给view赋值
     */
    public abstract void setData(final T data, int position);

    /**
     * 封装好的onclick操作，如果需要点击事件，重写该方法即可
     * 数据获取可以通过一下两个方法
     * {@link #getLayoutPosition()}
     * {@link #getAdapterPosition()}
     */
    @SuppressWarnings("WeakerAccess")
    public View.OnClickListener getOnItemViewClick() {
        return onItemClickListener;
    }

    /**
     * 直接由适配器 添加的itemClick 类似list
     */
    @SuppressWarnings("WeakerAccess")
    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        itemView.setOnClickListener(onItemClickListener);
    }
}
