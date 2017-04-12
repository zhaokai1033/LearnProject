package com.zk.baselibrary.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

/**
 * ================================================
 * <p>
 * Created by zhaokai on 2017/3/3.
 * Email zhaokai1033@126.com
 * <p>
 * ================================================
 *
 * 提示框工具类
 */

@SuppressWarnings("unused")
public class ToastUtil {
    private static ToastUtil instance;
    private final ToastSingle toastSingle;

    private ToastUtil(Context applicationContext) {
        this.toastSingle = new ToastSingle(applicationContext);
    }

    private static ToastUtil getInstance(Context context) {
        if (instance == null) {
            if (context != null) {
                instance = new ToastUtil(context.getApplicationContext());
            } else {
                throw new RuntimeException("can't tip without context");
            }
        }
        return instance;
    }

    /**
     * 短提示
     */
    @SuppressWarnings("unused")
    public static void showShortToast(Context context, String text) {
        getInstance(context).toastSingle.implShowShortToast(text);
    }

    /**
     * 长提示
     */
    @SuppressWarnings("unused")
    public static void showLongToast(Context context, String text) {
        getInstance(context).toastSingle.implShowLongToast(text);
    }

    private static class ToastSingle {

        private final Context mApplicationContext;
        private Handler mUiHandler = new Handler(Looper.getMainLooper());
        private static final Object mLock = new Object();

        private Toast mToast;
        private Toast mLongToast;

        ToastSingle(Context applicationContext) {
            this.mApplicationContext = applicationContext;
        }

        private void implShowShortToast(final String text) {
            mUiHandler.post(new Runnable() {

                @Override
                public void run() {
                    synchronized (mLock) {
                        if (mToast == null) {
                            mToast = Toast.makeText(mApplicationContext, text,
                                    Toast.LENGTH_SHORT);
//                            int xOffset = mToast.getXOffset();
//                            int yOffset = mToast.getYOffset() + Y_OFFSET;
//                            mToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
//                                    xOffset, yOffset);
                        } else {
                            mToast.setText(text);
                        }
                        if (mLongToast != null)
                            mLongToast.cancel();

                        mToast.show();
                    }
                }
            });
        }

        private void implShowLongToast(final String text) {
            mUiHandler.post(new Runnable() {

                @Override
                public void run() {
                    synchronized (mLock) {
                        if (mLongToast == null) {
                            mLongToast = Toast.makeText(mApplicationContext, text,
                                    Toast.LENGTH_LONG);
//                            int xOffset = mLongToast.getXOffset();
//                            int yOffset = mLongToast.getYOffset() + Y_OFFSET;
//                            mLongToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM,
//                                    xOffset, yOffset);
                        } else {
                            // mLongToast.cancel();
                            mLongToast.setText(text);
                        }
                        if (mToast != null)
                            mToast.cancel();
                        mLongToast.show();
                    }
                }
            });
        }
    }
}
