package com.zk.baselibrary.widget.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * 使用5.x自带的方法设置status bar颜色。适用于大部分的情况。
 * Created by naturs on 2016/2/21.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class StatusBarHelperImpl21Normal extends StatusBarHelperImplBase {

    public StatusBarHelperImpl21Normal(Activity activity) {
        super(activity);
    }

    @Override
    protected void setColor(int color) {
//        mActivity.getWindow().setBackgroundDrawable(new ColorDrawable(color));
        mActivity.getWindow().setStatusBarColor(color);
        setActivityRootLayoutFitSystemWindowsInternal();
//        mActivity.getWindow().getDecorView().getRootView().set
    }

    @Override
    protected void setDrawable(Drawable drawable) {
        // not support
    }

    @Override
    protected void destroy() {
        // don't need
    }
}
