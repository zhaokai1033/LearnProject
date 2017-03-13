package com.zk.sample.data;

import java.util.HashMap;

/**
 * ================================================
 *
 * @Describe :
 * Created by zhaokai on 2017/3/9.
 * @Email zhaokai1033@126.com
 * ================================================
 */

public class DataManager {

    private static final String TAG = "DataManager";
    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (TAG) {
                if (instance == null) {
                    instance = new DataManager();
                }
            }
        }
        return instance;
    }

    private DataManager() {
        initData();
    }

    public HashMap<String, SkinBean> skins = new HashMap<>();

    private void initData() {
        skins.put("black", new SkinBean("炫酷黑"));
        skins.put("blue", new SkinBean("默认蓝"));
        skins.put("brown", new SkinBean("高贵棕"));
    }

    public static SkinBean getSkin(String color) {
        return getInstance().skins.get(color);
    }
}
