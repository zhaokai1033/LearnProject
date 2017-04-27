package com.zk.sample.module.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.zk.baselibrary.util.SystemUtil;
import com.zk.baselibrary.util.ToastUtil;

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

public class PermissionHolder {

    private View.OnClickListener checkListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };
    private TextView showText;

    public View.OnClickListener getCheckListener() {
        return checkListener;
    }

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
                            if (showText != null && list.size() > 1) {
                                showText.setText(list.get(list.size() - 1));
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

    public void setShowText(TextView showText) {
        this.showText = showText;
    }
}
