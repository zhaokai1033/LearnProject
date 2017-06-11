package com.zk.baselibrary.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * ================================================
 * Created by zhaokai on 2017/5/9.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

abstract class BaseRecycleAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private List<T> mItemsData;

    BaseRecycleAdapter(List<T> mItemsData) {
        this.mItemsData = mItemsData;
    }

    @Override
    public int getItemCount() {
        return mItemsData == null ? 0 : mItemsData.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.setData(mItemsData.get(position), position);
        if (getOnItemClick() != null) {
            holder.setOnItemClickListener(getOnItemClick());
        }
    }

    /**
     * 条目点击事件
     */
    @SuppressWarnings("WeakerAccess")
    protected View.OnClickListener getOnItemClick() {
        return null;
    }

    /**
     * 替换全部数据
     */
    public void setItems(List<T> datas) {
        mItemsData = datas;
        notifyDataSetChanged();
    }

    /**
     * 添加数据到尾部
     */
    public void addItems(List<T> datas) {
        if (null == datas) return;
        mItemsData.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 获取列表指定位置的数据
     */
    @SuppressWarnings("unused")
    public T getItem(int position) {
        return mItemsData.get(position);
    }
}
