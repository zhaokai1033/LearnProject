package com.zk.baselibrary.app;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/6.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class BaseApplication extends Application {

    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return super.getApplicationInfo();
    }

    public static BaseApplication getInstance() {
        return instance;
    }
}
