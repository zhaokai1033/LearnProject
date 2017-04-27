package com.zk.baselibrary.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * ================================================
 * Created by zhaokai on 2017/4/13.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class CustomViewGroup extends FrameLayout {
    private GestureDetector mGestureDetector;

    public CustomViewGroup(Context context) {
        super(context);
        init();
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (MotionEvent.ACTION_DOWN == MotionEventCompat.getActionIndex(ev)) {
//        if (mGestureDetector.onTouchEvent(ev)) {
            super.dispatchTouchEvent(ev);
            ev.setAction(MotionEvent.ACTION_UP);
            MotionEvent.obtain(ev);
            super.dispatchTouchEvent(ev);
        }
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }
}
