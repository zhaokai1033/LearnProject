package com.zk.sample.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * ================================================
 * Created by zhaokai on 2017/4/18.
 * Email zhaokai1033@126.com
 * Describe : 配置文件
 * ================================================
 */
@SuppressWarnings("unused")
public class Settings {

    private SharedPreferences mSharedPreferences;

    public Settings(Context context) {
        Context mAppContext = context.getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void saveString(String key, String value) {
        mSharedPreferences.edit().putString(key, value).apply();
    }
}
