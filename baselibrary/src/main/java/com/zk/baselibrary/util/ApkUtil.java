package com.zk.baselibrary.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * ================================================
 * Created by zhaokai on 2017/4/14.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class ApkUtil {


    public static String getAppName(Context context, String packageName) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        String name = "";
        try {
            ApplicationInfo info;
            info = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            name = pm.getApplicationLabel(info).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }
}
