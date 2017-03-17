package com.zk.baselibrary.util;

import com.google.gson.Gson;

/**
 * ================================================
 * Created by zhaokai on 2017/3/16.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public class GsonUtil {

    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (gson == null) gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object obj) {
        if (gson == null) gson = new Gson();
        return gson.toJson(obj);
    }
}
