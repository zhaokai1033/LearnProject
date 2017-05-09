package com.zk.baselibrary.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.widget.SwipeCloseLayout;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

@SuppressWarnings("unused")
public abstract class BaseFra extends Fragment {
    private static final String TAG = "BaseFra";
    protected View mainView;
    private HashMap<String, View> stateView = new HashMap<>();
    protected LayoutInflater mInflater;
    public static final String NORMAL = "normal";
    private SwipeCloseLayout mSwipeClose;

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建之前做一些准备工作
        readyBeforeCreate();
        this.mInflater = inflater == null ? LayoutInflater.from(getContext()) : inflater;

        if (isAddToFrameLayout() || isShowTitle()) {
            FrameLayout frameLayout = null;
            LinearLayout linearLayout = null;
            if (isAddToFrameLayout()) {
                frameLayout = new FrameLayout(getContext());
                for (Map.Entry<String, View> entry : getStatesViews().entrySet()) {
                    frameLayout.addView(entry.getValue());
                    stateView.put(entry.getKey(), entry.getValue());
                }
                stateView.put(NORMAL, mainView);
                frameLayout.addView(onCreateView(frameLayout, savedInstanceState));
            }

            if (isShowTitle() && getTitleView() != null) {
                linearLayout = new LinearLayout(getContext());
                linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(getTitleView());
                if (frameLayout != null) {
                    linearLayout.addView(frameLayout);
                } else {
                    linearLayout.addView(onCreateView(linearLayout, savedInstanceState));
                }
            }
            if (linearLayout != null && frameLayout != null) {
                mainView = linearLayout;
            } else if (frameLayout != null) {
                mainView = frameLayout;
            }
        } else {
            mainView = onCreateView(container, savedInstanceState);
        }
        if (mainView == null) {
            throw new IllegalArgumentException("mainView Can not be null");
        }
        mSwipeClose = new SwipeCloseLayout(getActivity(), this);
        mSwipeClose.injectFragmentWindow(mainView);
        return mSwipeClose;
    }

    /**
     * 指定状态页到前端
     */
    public void bringStateToFront(String state) {
        if (stateView.get(state) != null) {
            stateView.get(state).bringToFront();
        }
    }

    /**
     * 状态页  建议静态页面
     * string : 对应页面所属状态
     */
    protected abstract Map<String, View> getStatesViews();

    /**
     * 是否分状态页
     */
    protected abstract boolean isAddToFrameLayout();

    /**
     * 自定义的标题栏
     */
    protected abstract View getTitleView();

    /**
     * 是否显示标题栏
     */
    public abstract boolean isShowTitle();

    /**
     * 获取布局资源
     *
     * @return 布局资源的ID
     */
    protected abstract int getLayoutRes();

    /**
     * 主显示页创建
     */
    public abstract View onCreateView(ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 页面被创建完成时调用   可用于对页面的初始化操作
     *
     * @param view               根视图
     * @param savedInstanceState 保存状态
     */
    @Override
    public abstract void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    /**
     * 刷新整个页面
     */
    public void refresh() {
    }


    /**
     * 初始化View的数据
     */
    protected void readyBeforeCreate() {
    }

    /**
     * 屏蔽以下操作
     * 所属Activity 创建 此时应尽量不做操作
     */
    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 脱离所属Activity时调用，此时fragment 以不在使用
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 在onDestroy 之前调用 此时Fragment 状态已经保存
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否开启侧滑关闭
     *
     * @param enable true/false
     */
//    @Override
    public void setSwipeBackEnable(boolean enable) {
        if (mSwipeClose != null) {
            mSwipeClose.setSwipeEnabled(enable);
        }
    }

    /**
     * 增加特殊View 防止滑动冲突
     */
    public void addSwipeSpecialView(View view) {
        if (mSwipeClose != null) {
            LogUtil.d(TAG,"addSwipeSpecialView");
            mSwipeClose.addSpecialView(view);
            ((BaseAct) getActivity()).addSwipeSpecialView(view);
        }
    }
}
