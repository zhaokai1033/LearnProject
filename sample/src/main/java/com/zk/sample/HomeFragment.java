package com.zk.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class HomeFragment extends BaseFragment {


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

}
