package com.zk.sample;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;

import com.zk.baselibrary.app.BaseApplication;
import com.zk.baselibrary.data.DataCache;
import com.zk.baselibrary.util.FragmentController;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.app.SampleApplication;
import com.zk.sample.base.BaseActivity;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.base.activity.CustomActivity;

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
                com.zk.sample.app.SampleApplication.getInstance().getApplicationContext().startActivity(intent);
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
            Intent intent = new Intent(com.zk.sample.app.SampleApplication.getInstance().getApplicationContext(), CustomActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(activity, intent);
        }
    }

    /**
     * 显示Alert 弹出对话框
     */
    @RequiresPermission(Manifest.permission.SYSTEM_ALERT_WINDOW)
    public static void showDialogType1(String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SampleApplication.getInstance().getApplicationContext(), R.style.AppTheme_Dialog);
        AlertDialog dialog = builder.setTitle(title)
                .setMessage(content)
                .setNeutralButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ToastUtil.showToast(SampleApplication.getInstance(), "确认了");
                    }
                }).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }

    /**
     * 利用Intent 弹出对话框
     */
    public static void showDialogType2(String title, String content, Class<?> clazz) {
        Intent intent = new Intent(BaseApplication.getInstance().getApplicationContext(), clazz);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(SampleApplication.getInstance().getApplicationContext(), intent);
    }
}
