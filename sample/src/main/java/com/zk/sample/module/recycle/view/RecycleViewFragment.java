package com.zk.sample.module.recycle.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentRecycleBinding;
import com.zk.sample.module.recycle.holder.RecycleHolder;

/**
 * ================================================
 * Created by zhaokai on 2017/3/27.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class RecycleViewFragment extends BaseFragment<FragmentRecycleBinding> {


    public static RecycleViewFragment newInstance() {

        Bundle args = new Bundle();

        RecycleViewFragment fragment = new RecycleViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_recycle;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        RecycleHolderFace holder = RecycleHolder.getInstance();
        binding.setHolder(holder);
        binding.recycle.setAdapter(holder.getAdapter());
        binding.recycle.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public interface RecycleHolderFace {

        void onSearchClick(View v, String name);

        RecyclerView.Adapter getAdapter();
    }
}
