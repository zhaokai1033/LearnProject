package com.zk.sample.module.permission;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.zk.baselibrary.util.LogUtil;
import com.zk.baselibrary.util.SystemUtil;
import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.BR;
import com.zk.sample.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * ================================================
 * Created by zhaokai on 2017/4/14.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class PermissionHolder extends BaseObservable {
    private static final String TAG = "PermissionHolder";
    private final Activity mActivity;
    @Bindable
    private String content;
    private boolean isHaveStatePermission = false;
    private boolean isHaveOverPermission = false;

    PermissionHolder(Activity activity) {
        this.mActivity = activity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(BR.content);
    }

    public View.OnClickListener getOnClickListener() {
        return clickListener;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @SuppressLint("InlinedApi")
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_state:
                    showDialog(mActivity);
                    break;
                case R.id.bt_over:
                    ToastUtil.showToast(mActivity, "获取权限");
                    Intent intent;
                    try {
                        intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + mActivity.getPackageName()));
                        mActivity.startActivity(intent);
                    } catch (Exception e) {
                        LogUtil.d(TAG, e.getMessage());
                    }
                    break;
            }
        }
    };

    void showDialog(final Activity activity) {
        new AlertDialog
                .Builder(activity)
                .setTitle("是否开启应用权限查看")
                .setMessage("开启后将统计当前设备应用使用情况")
                .setNegativeButton("开启", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                            activity.startActivity(intent);
                        }
                    }
                })
                .setPositiveButton("查看", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                            Calendar calender = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
                            long end = calender.getTimeInMillis();
                            calender.add(Calendar.DATE, -1);
                            long start = calender.getTimeInMillis();
                            ArrayList<String> list = SystemUtil.getUsageState(activity, start, end);
                            if (list.size() > 1) {
                                setContent(list.get(list.size() - 1));
                            }
                        }
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        ToastUtil.showToast(activity, "Cancel");
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        ToastUtil.showToast(activity, "Dismiss");
                    }
                })
                .setNeutralButton("取消", null)
                .setCancelable(true)
                .create()
                .show();
    }

    void resume(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            isHaveStatePermission = isHaveStatePermission(context);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isHaveOverPermission = isHaveOverPermission(context);
        }

        setContent(""
                + "应用运行查看权限：" + (isHaveStatePermission ? "已获取" : "未获取") + "\n"
                + "悬浮在应用顶部：" + (isHaveOverPermission ? "已获取" : "未获取" + "-> 可调用对话框" + "\n")
        );
    }

    @SuppressLint("NewApi")
    private boolean isHaveOverPermission(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            AppOpsManager aom = ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE));
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), 0);
            return aom.checkOpNoThrow(AppOpsManager.OPSTR_SYSTEM_ALERT_WINDOW, info.uid, info.packageName) == AppOpsManager.MODE_ALLOWED;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean isHaveStatePermission(Context context) {
        try {
            PackageManager pm = context.getPackageManager();
            AppOpsManager aom = ((AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE));
            ApplicationInfo info = pm.getApplicationInfo(context.getPackageName(), 0);
            return aom.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, info.uid, info.packageName) == AppOpsManager.MODE_ALLOWED;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
