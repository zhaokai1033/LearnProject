package com.zk.sample.base.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;

import com.zk.baselibrary.app.BaseFra;
import com.zk.baselibrary.data.DataCache;
import com.zk.baselibrary.util.LogUtil;
import com.zk.sample.R;
import com.zk.sample.UIControl;
import com.zk.sample.app.SampleApplication;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.ActivityCustomBinding;

/**
 * ================================================
 * Created by zhaokai on 2017/3/20.
 * Email zhaokai1033@126.com
 * Describe :
 * Fragment 的公共容器
 * ================================================
 */

public class CustomActivity extends BaseActivity<ActivityCustomBinding> {

    public final static String DEFAULT_FRAGMENT = "DEFAULT_FRAGMENT";
    public final static String CUSTOM_FRAGMENT = "CUSTOM_FRAGMENT";

    private static final String TAG = "CustomActivity";

    @Override
    protected void onHandleMessage(Message msg) {

    }

    @Override
    protected int getStateContentView() {
        return R.id.state_content;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_custom;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {

    }

    @Override
    public void onPostCreated(@Nullable Bundle savedInstanceState) {
        showStateView(State.LOADING);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideStateView();
            }
        }, 1000);
        setSupportActionBar(binding.toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //添加要改变的控件属性
        dynamicAddView(binding.toolbar, "background", R.color.colorPrimary);
        changeFragment(null, getDefaultFragment(), false, false);
        setSwipeBackEnable(true);
    }

    /**
     * 启动CustomActivity
     * 如果已存在并在首位 则直接加载指定的Fragment
     */
    @SuppressWarnings("unused")
    public static void startWithFragment(Context c, BaseFragment f) {
        DataCache.setToCache(DEFAULT_FRAGMENT, f);
        if (c == null) {
            c = SampleApplication.getInstance();
        }
        Intent intent = new Intent(c, CustomActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        UIControl.startActivity(c, intent);
    }

    /**
     * 当在栈顶时 已new Intent 传入参数
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //获取再次打开时 替换的 BaseFragment
        BaseFragment baseFragment = DataCache.getFromCache(DEFAULT_FRAGMENT, BaseFragment.class);
        changeFragment(null, baseFragment, false, true);
        //移除缓存
        DataCache.removeFromCache(CUSTOM_FRAGMENT);
    }

    /**
     * 获取默认显示页
     */
    private BaseFra getDefaultFragment() {
        //获取再次打开时 替换的 BaseFragment
        BaseFragment baseFragment = DataCache.getFromCache(DEFAULT_FRAGMENT, BaseFragment.class);
        //移除缓存
        DataCache.removeFromCache(CUSTOM_FRAGMENT);
        return baseFragment;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getFragmentContentId() {
        return R.id.frame_content;
    }
}
