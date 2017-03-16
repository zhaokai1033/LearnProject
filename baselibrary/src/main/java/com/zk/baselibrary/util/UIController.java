package com.zk.baselibrary.util;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.zk.baselibrary.app.BaseFra;

import java.util.WeakHashMap;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

@SuppressWarnings({"unused"})
public class UIController {

    private static final String TAG = "UIController";

//    private static WeakHashMap<String, BaseFra> fragmentList = new WeakHashMap<>();

    /**
     * 替换Fragment
     *
     * @param activity baseAct
     * @param fragment baseFra
     */
    public static void replaceFragment(AppCompatActivity activity, int layoutRes, BaseFra fragment) {
        if (activity == null || layoutRes == 0 || fragment == null) {
            return;
        }
        LogUtil.e(TAG, fragment.getClass().getSimpleName());
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(layoutRes, fragment, fragment.getClass().getName());
        transaction.commitAllowingStateLoss();
    }

    /**
     * 切换Fragment
     *
     * @param current   当前显示的Fragment
     * @param target    需要显示的Fragment
     * @param layoutRes 位置
     * @param canBack   是否支持回退
     * @return 最终显示的Fragment
     */
    public static BaseFra changeFragment(AppCompatActivity act, BaseFra current, BaseFra target, int layoutRes, boolean canBack) {

        FragmentTransaction ta = act.getSupportFragmentManager().beginTransaction();

        if (current != null) {
            ta.hide(current);
        }
        if (target.isAdded()) {
            ta.show(target);
        } else {
            ta.add(layoutRes, target, target.getClass().getName());
        }

        if (canBack) {
            ta.addToBackStack(null);
        }
        ta.commitAllowingStateLoss();

        return target;
    }
}
