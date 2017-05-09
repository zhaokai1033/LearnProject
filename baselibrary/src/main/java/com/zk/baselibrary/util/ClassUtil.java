package com.zk.baselibrary.util;

/**
 * ================================================
 * Created by zhaokai on 2017/3/20.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

public class ClassUtil {

    /**
     * 根据Class创建实例
     *
     * @param clazz the Fragment of create
     * @return 实例
     */
    @SuppressWarnings("TryWithIdenticalCatches")
    public static <T> T createInstance(Class<T> clazz) {
        T Instance = null;
        try {
            Instance = clazz.newInstance();
        } catch (InstantiationException w) {
            w.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return Instance;
    }
}
