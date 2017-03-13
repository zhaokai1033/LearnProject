package com.zk.baselibrary.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.UIController;

/**
 * ================================================
 *
 * @Describe Activity 基类，包含一下功能
 * 1、退出全部页面
 * 2、替换 加载 指定的Fragment
 * Created by zhaokai on 2017/3/3.
 * Email zhaokai1033@126.com
 * ================================================
 */
@SuppressWarnings("unused")
public abstract class BaseAct extends AppCompatActivity {

    private static final String TAG = "BaseAct";
    public static final String CLOSE_ACTION = "CLOSE_ACTIVITY";
    //注册广播
    IntentFilter closeFilter = new IntentFilter(CLOSE_ACTION);
    //广播接受者
    BroadcastReceiver closeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.d(CLOSE_ACTION, "收到 广播");
            finish();
        }
    };

//    protected VDB binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  VDB binding = DataBindingUtil.setContentView(this, getLayoutResId());
        LocalBroadcastManager.getInstance(this).registerReceiver(closeReceiver, closeFilter);
    }

    /**
     * 获取布局资源Id
     */
    protected abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(closeReceiver);
    }

    /**
     * 关闭所有的Activity
     */
    public void exitApp() {
        LogUtil.d(CLOSE_ACTION, "发送 广播");
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(CLOSE_ACTION));
    }

    /**
     * 替换Fragment
     *
     * @param fragment      指定的Fragment 资源
     * @param layoutRes     需要替换的Fragment 位置
     * @param isNeedRefresh 是否需要刷新 You must override {@link BaseFra#refresh()} to refresh
     */
    public void replaceFragment(BaseFra fragment, int layoutRes, boolean isNeedRefresh) {
        if (fragment == null) return;
        UIController.replaceFragment(this, layoutRes, fragment);
        if (isNeedRefresh) {
            fragment.refresh();
        }
    }

    /**
     * 切换Fragment
     *
     * @param current       当前显示的Fragment
     * @param target        需要显示的Fragment
     * @param layoutRes     位置
     * @param isNeedRefresh 切换后是否需要刷新 You must override {@link BaseFra#refresh()} to refresh
     * @param canBack       是否支持回退
     * @return 最终显示的Fragment
     */
    public BaseFra changeFragment(BaseFra current, BaseFra target, int layoutRes, boolean isNeedRefresh, boolean canBack) {
//        if (current == null) {
//            UIController.replaceFragment(this, layoutRes, target);
//        } else {
            UIController.changeFragment(this, current, target, layoutRes,canBack);
            target.refresh();
//        }
        return target;
    }

}
