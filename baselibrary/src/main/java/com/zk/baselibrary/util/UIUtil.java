package com.zk.baselibrary.util;

import android.content.Context;

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
}
