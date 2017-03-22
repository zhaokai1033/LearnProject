package com.zk.sample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.zk.baselibrary.data.DataCache;
import com.zk.baselibrary.util.FragmentController;
import com.zk.sample.app.SampleApplication;
import com.zk.sample.base.activity.CustomActivity;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;

/**
 * ================================================
 * Created by zhaokai on 2017/3/20.
 * Email zhaokai1033@126.com
 * Describe :
 * APP 界面跳转控制类
 * ================================================
 */

@SuppressWarnings("unused")
public class UIControl {

    /**
     * 启动指定的Activity
     */
    public static void startActivity(Context context, Intent intent) {
        try {
            if (context != null && (context instanceof Activity && !((Activity) context).isFinishing())) {
                context.startActivity(intent);
            } else {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SampleApplication.getInstance().getApplicationContext().startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Log.e("UiControl", "开启Activity 失败" + e.getLocalizedMessage());
        }
    }

    /**
     * 展示Fragment 默认为CustomActivity 中
     */
    public static void showCustomFragment(BaseActivity activity, BaseFragment fragment) {
        //通过开启上下文判断 是否是在当前Activity显示
        if (activity != null && !activity.isFinishing() && activity.getClass().getSimpleName().equals("CustomActivity") && activity.getFragmentContentId() != 0) {
            FragmentController.changeFragment(
                    activity,
                    null,
                    fragment,
                    activity.getFragmentContentId(), false);
        } else {
            DataCache.setToCache(CustomActivity.DEFAULT_FRAGMENT, fragment);
            Intent intent = new Intent(SampleApplication.getInstance().getApplicationContext(), CustomActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activity, intent);
        }
    }
}
