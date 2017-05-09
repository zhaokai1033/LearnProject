package com.zk.sample.module.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zk.baselibrary.util.LogUtil;

/**
 * ================================================
 * Created by zhaokai on 2017/5/1.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class MyView extends ViewPager {
    private static final String TAG = "MyView";

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, event.toString());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = super.onTouchEvent(event);
        LogUtil.d(TAG, "flag = " + flag + " " + event.toString());
        return flag;
    }
}
