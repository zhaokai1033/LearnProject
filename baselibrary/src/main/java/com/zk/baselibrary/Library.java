package com.zk.baselibrary;

import android.content.Context;

import com.zk.baselibrary.util.LogUtil;

/**
 * ================================================
 * <p>
 * Created by zhaokai on 2017/3/3.
 * Email zhaokai1033@126.com
 * <p>
 * ================================================
 */

public abstract class Library {

    public static void init(Context context) {

        //LogUtil 初始化
        LogUtil.initLog(true, false, "");
    }
}
