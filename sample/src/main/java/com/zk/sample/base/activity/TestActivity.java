package com.zk.sample.base.activity;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.zk.baselibrary.util.ClassUtil;
import com.zk.sample.R;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.module.card.view.CardFragment;
import com.zk.sample.module.permission.PermissionFragment;
import com.zk.sample.module.view.ViewFragment;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onHandleMessage(Message msg) {

    }

    @Override
    protected int getStateContentView() {
        return 0;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @Override
    protected void onCreated(Bundle savedInstanceState) {
        setSwipeBackEnable(true);
        findViewById(R.id.bt_next1).setOnClickListener(this);
        findViewById(R.id.bt_next2).setOnClickListener(this);
    }

    @Override
    public int getFragmentContentId() {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_next1:
                replaceFragment(ClassUtil.createInstance(PermissionFragment.class), R.id.content_main, false);
                break;
            case R.id.bt_next2:
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, ClassUtil.createInstance(CardFragment.class), CardFragment.class.getName());
                transaction.commitAllowingStateLoss();
//                replaceFragment(ClassUtil.createInstance(ViewFragment.class), R.id.content_main, false);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
