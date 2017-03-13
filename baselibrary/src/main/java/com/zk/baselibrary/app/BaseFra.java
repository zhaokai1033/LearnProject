package com.zk.baselibrary.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

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

    protected View mainView;
    private HashMap<String, View> stateView = new HashMap<>();
    protected LayoutInflater inflater;
    public static final String NORMAL = "normal";

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //创建之前做一些准备工作
        readyBeforeCreate();
        this.inflater = inflater;

        if (isAddToFrameLayout() || isShowTitle()) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);

            if (isShowTitle() && getTitleView() != null) {
                linearLayout.addView(getTitleView());
            }
            if (isAddToFrameLayout()) {
                FrameLayout frameLayout = new FrameLayout(getContext());
                for (Map.Entry<String, View> entry : getStatesViews().entrySet()) {
                    frameLayout.addView(entry.getValue());
                    stateView.put(entry.getKey(), entry.getValue());
                }
                stateView.put(NORMAL, mainView);
                mainView = onCreateView(frameLayout, savedInstanceState);
                frameLayout.addView(mainView);
                linearLayout.addView(frameLayout);
            } else {
                mainView = onCreateView(linearLayout, savedInstanceState);
                linearLayout.addView(mainView);
            }
            return linearLayout;
        }
        mainView = onCreateView(container, savedInstanceState);
        return mainView;
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
}
