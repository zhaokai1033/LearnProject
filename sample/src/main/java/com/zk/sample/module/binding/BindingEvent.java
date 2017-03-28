package com.zk.sample.module.binding;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.view.View;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.data.DataManager;
import com.zk.sample.databinding.FragmentDataBindingBinding;
import com.zk.sample.module.binding.model.UserImg;

/**
 * ================================================
 * Created by zhaokai on 2017/3/24.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class BindingEvent {
    private static final String TAG = "BindingEvent";

    /**
     * 点击传参
     */
    public void getDataOnClick(View view, String url) {
        ToastUtil.showToast(view.getContext(), "网址：" + url);
    }

    /**
     * 点击切换显示的图片
     */
    public void setParamsOnClick(UserImg img) {
        String url = DataManager.getRandomUrl();
        img.setUrl(url);
        String t = url.substring(url.length() - 10, url.length());
        LogUtil.d(TAG, "原标题:" + img.title + "\n现标题:" + t);
        img.setTitle(t);
    }

    public View.OnClickListener getTestOnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d(TAG, "getTestOnClick");
                ((FragmentDataBindingBinding) DataBindingUtil.findBinding(v)).setListener(
                        new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

                            }
                        });
            }
        };
    }


    @BindingAdapter("testUrl")
    public static void testName(View view, String oldValue, String newValue) {
        LogUtil.d(TAG, "load image ->url：" + newValue + " old:" + oldValue);
        if (view != null) {
            LogUtil.d(TAG, "des = " + view.getContentDescription());
        }
    }

    @BindingAdapter("android:onLayoutChange")
    public static void setOnLayoutChangeListener(View view,
                                                 View.OnLayoutChangeListener oldValue,
                                                 View.OnLayoutChangeListener newValue) {
        LogUtil.d(TAG, "load image ->url：" + newValue + " old:" + oldValue);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (oldValue != null) {
                view.removeOnLayoutChangeListener(oldValue);
            }
            if (newValue != null) {
                view.addOnLayoutChangeListener(newValue);
            }
        }
    }
}
