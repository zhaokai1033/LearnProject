package com.zk.sample.module.binding;

import android.view.View;
import android.widget.Button;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.data.DataManager;
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
}
