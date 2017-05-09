package com.zk.sample.module.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.zk.baselibrary.util.LogUtil;

/**
 * ================================================
 * Created by zhaokai on 2017/5/1.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class MyLayout extends LinearLayout {
    private static final String TAG = "MyLayout";

    public MyLayout(@NonNull Context context) {
        super(context);
    }

    public MyLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.d(TAG, "ev:" + ev.toString());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.d(TAG, "ev:" + ev.toString());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.d(TAG, "ev:" + event.toString());
        return true;
    }
}
