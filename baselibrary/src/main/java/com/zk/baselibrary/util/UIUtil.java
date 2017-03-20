package com.zk.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * ================================================
 * <p>
 * Created by zhaokai on 2017/3/3.
 * Email zhaokai1033@126.com
 * <p>
 * ================================================
 * 视图工具类
 */

@SuppressWarnings("unused")
public class UIUtil {

    private UIUtil() {
    }

    /**
     * get the height of the StatusBar
     *
     * @param context the context
     * @return the height
     */
    public static int getStatusBarHeight(Context context) {
        int result = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    /**
     * 获取屏幕的宽度
     *
     * @param context context
     * @return width of screen
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context context
     * @return height of screen
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    /**
     * 像素转化
     */
    public static int px2dip(Context context, float pxValue) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5f);
    }


    public static int dip2px(Context context, float dipValue) {
        return (int) (dipValue * context.getResources().getDisplayMetrics().density + 0.5f);
    }


    public static int px2sp(Context context, float pxValue) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }


    public static int sp2px(Context context, float spValue) {
        return (int) (spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f);
    }


    /**
     * 动态设置全屏
     */
    public static void setFullScreen(Activity activity) {
        if (activity == null) return;
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 动态取消全屏
     */
    public static void quitFullScreen(Activity activity) {
        if (activity == null) return;
        final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 是否是全屏
     */
    public static boolean isFullScreen(Activity activity) {
        // 全屏 66816 - 非全屏 65792
        final WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        return attrs.flags == 66816;//非全屏
    }

    /**
     * 显示软键盘
     */
    public static void showKeyboard(Activity activity, View view) {
        if (activity != null) {
            if (view != null) {
                view.requestFocus();
            }
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null && activity.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                activity.getCurrentFocus().clearFocus();
            }
        }
    }

    /**
     * 隐藏键盘
     */
    public static void hideKeyboard(Activity activity, View view) {
        if (activity != null) {
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } else {
                hideKeyboard(activity);
            }
        }
    }
}
