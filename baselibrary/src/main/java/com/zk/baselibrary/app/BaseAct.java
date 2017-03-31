package com.zk.baselibrary.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zk.baselibrary.util.ColorUtil;
import com.zk.baselibrary.util.FragmentController;
import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.StatusBarUtil;
import com.zk.baselibrary.widget.SwipeBackLayout;
import com.zk.baselibrary.widget.Utils;
import com.zk.baselibrary.widget.statusbar.StatusBarHelper;

/**
 * ================================================
 *
 * @Describe Activity 基类，包含一下功能
 * 1、退出全部页面
 * 2、替换 加载 指定的Fragment
 * 3、支持侧滑返回 {@link BaseAct#setSwipeBackEnable(boolean)}
 * Created by zhaokai on 2017/3/3.
 * Email zhaokai1033@126.com
 * ================================================
 */
@SuppressWarnings("unused")
public abstract class BaseAct extends AppCompatActivity implements SwipeBackActivityBase {

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
    protected SwipeBackActivityHelper mHelper;
    protected StatusBarHelper mStatusBarHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        LocalBroadcastManager.getInstance(this).registerReceiver(closeReceiver, closeFilter);
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        onTintStatusBar();
    }

    /**
     * 确定状态栏样式
     */
    protected void onTintStatusBar() {
        if (mStatusBarHelper == null) {
            mStatusBarHelper = new StatusBarHelper(this, StatusBarHelper.LEVEL_19_TRANSLUCENT,
                    StatusBarHelper.LEVEL_21_NORMAL);
        }
    }

    /**
     * 设置状态栏颜色
     * 如果界面使用的DrawerLayout 则主界面需要矫正状态栏高度
     * {@link #adjustStatusBarHeight(View)}
     * 覆写{@link #getMainView()}
     *
     * @param color {@link android.graphics.Color#BLUE}
     */
    public void setStateBarColor(@ColorInt int color) {
        color = ColorUtil.changeAlpha(color, 180);
        if (mStatusBarHelper != null) {
            mStatusBarHelper.setColor(color);
        }
        //调整 DrawerLayout 主界面的状态栏
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            adjustStatusBarHeight(getMainView());
        }
    }

    /**
     * 获取主界面的view 或者 DrawerLayout中的主页面
     */
    protected View getMainView() {
        View v = null;
        ViewGroup contentLayout = ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        ViewGroup drawerLayout = ((ViewGroup) contentLayout.getChildAt(0));
        if ("DrawerLayout".equals(drawerLayout.getClass().getSimpleName())) {
            v = drawerLayout.getChildAt(0);
        }
        return v;
    }

    /**
     * 给主界面view 增加padding 值
     */
    protected void adjustStatusBarHeight(View view) {

        if (view != null)
            view.setPadding(
                    getMainView().getPaddingLeft()
                    , StatusBarUtil.getStatusBarHeight(this)
                    , getMainView().getPaddingRight()
                    , getMainView().getPaddingBottom());
    }


    /**
     * 创建完成时调用
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }


    /**
     * 获取布局资源Id
     */
    protected abstract int getLayoutResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy");
        LocalBroadcastManager.getInstance(this).unregisterReceiver(closeReceiver);
    }

    /**
     * 是否全屏
     *
     * @param isFull true/false
     */
    protected void setFullscreen(boolean isFull) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        if (isFull) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    /**
     * 是否开启侧滑关闭
     *
     * @param enable true/false
     */
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    /**
     * 侧滑关闭Activity
     */
    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }


    /**
     * 关闭所有的Activity
     * 通过发送应用内广播关闭退出所有的Activity
     */
    @CallSuper
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
        FragmentController.replaceFragment(this, layoutRes, fragment);
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
        FragmentController.changeFragment(this, current, target, layoutRes, canBack);
        if (isNeedRefresh)
            target.refresh();
        return target;
    }
}
