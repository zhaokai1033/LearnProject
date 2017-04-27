package com.zk.baselibrary.widget;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.zk.baselibrary.util.ToastUtil;
import com.zk.baselibrary.util.UIUtil;

/**
 * ================================================
 * Created by zhaokai on 2017/4/13.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class CustomEditText extends AppCompatEditText {

    private static final String TAG = "CustomEditText";

    public CustomEditText(Context context) {
        super(context);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
//        this.setLongClickable(false);
//        setCustomSelectionActionModeCallback(mActionModeCallback);
    }

    long lastTime;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (System.currentTimeMillis() - lastTime > 100 && MotionEvent.ACTION_DOWN == MotionEventCompat.getActionIndex(ev)) {
            lastTime = System.currentTimeMillis();
            super.onTouchEvent(ev);
            MotionEvent event = MotionEvent.obtain(ev);
            event.setAction(MotionEvent.ACTION_UP);
            event.offsetLocation(-UIUtil.dip2px(getContext(), 10), -UIUtil.dip2px(getContext(), 10));
            super.onTouchEvent(event);
        }
        return true;
    }

    @Override
    public boolean onTextContextMenuItem(int id) {

        if (id == android.R.id.paste) {
            ToastUtil.showToast(getContext(), "禁止粘贴");
            return true;
        } else if (id == android.R.id.copy || id == android.R.id.copyUrl) {
            ToastUtil.showToast(getContext(), "禁止复制");
            return true;
        }

        return super.onTextContextMenuItem(id);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu) {

//        menu.add(Menu.NONE, android.R.id.cut, 1,
//                "剪切")
//                .setAlphabeticShortcut('x');
//        menu.add(Menu.NONE, android.R.id.copy, 2,
//                "复制")
//                .setAlphabeticShortcut('c');
//        menu.add(Menu.NONE, android.R.id.paste, 3,
//                "粘贴")
//                .setAlphabeticShortcut('v');
//
//        LogUtil.d(TAG, "创建菜单" + menu.size());
        super.onCreateContextMenu(menu);
//        menu.clear();
//        LogUtil.d(TAG, "size:" + menu.size());
    }

    ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return true;//return false 隐藏actionMod菜单
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    };


}
