package com.zk.sample.module.recycle.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.zk.sample.module.recycle.holder.UserHolder;
import com.zk.sample.module.recycle.model.GitUser;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * Created by zhaokai on 2017/3/27.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public class RecycleAdapter extends RecyclerView.Adapter<UserHolder> {

    private List<GitUser> mItems = new ArrayList<>();

    public RecycleAdapter() {

    }

    public void addItems(List<GitUser> users) {
        int last = mItems.size();
        mItems.addAll(users);
        notifyItemRangeChanged(last, users.size());
    }

    public void setItems(List<GitUser> users) {
        mItems.clear();
        mItems.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserHolder.create(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(UserHolder holder, int position) {
        holder.bindTo(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
