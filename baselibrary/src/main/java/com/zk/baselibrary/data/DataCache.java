package com.zk.baselibrary.data;

import android.util.Log;
import android.util.LruCache;

import com.zk.baselibrary.app.BaseApplication;
import com.zk.baselibrary.util.FileUtil;
import com.zk.baselibrary.util.LogUtil;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by zhaokai on 2016-06-06
 * Email zhaokai1033@126.com
 * <p/>
 * 数据储存
 * 用于页面跳转大数据 或者复杂数据的 转接
 * 用于缓存数据到本地
 * 用于存储程序运行时常用数据
 * <p/>
 * 存储
 * DataCache.setToCache("123",456);
 * BeanUser user = new BeanUser();
 * user.setName("haha");
 * DataCache.setToCache("ev",user);
 * <p/>
 * 获取
 * Integer i = DataCache.getFromCache(123+"",Integer.class);
 * BeanUser user = (DataCache.getFromCache("ev",BeanUser.class));
 */
@SuppressWarnings({"unused", "unchecked", "ResultOfMethodCallIgnored"})
public class DataCache {

    /**
     * App 运行长久缓存
     */
    private static HashMap<String, Object> cacheLong = new HashMap<>();
    private File cacheFile;


    /**
     * App运行临时缓存
     */
    private final LruCache<String, Object> cache;

    private DataCache() {
        cache = new LruCache<>(50);
        cacheFile = FileUtil.getRootDir(BaseApplication.getInstance());
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
    }

    private static DataCache dataCache;

    private static DataCache getInstance() {
        if (dataCache == null) {
            dataCache = new DataCache();
        }
//        if(cacheLong == null){
//            cacheLong = new HashMap<>();
//        }
        return dataCache;
    }

    /**
     * 从数据缓存中获取 此数据不会自动清空
     *
     * @param key   数据索引
     * @param clazz 类文件
     * @return 需要的类
     */
    public static <T> T getFromStore(String key, Class<T> clazz) {
        return getInstance().getFromStoreIml(key, clazz);
    }

    private <T> T getFromStoreIml(String key, Class<T> clazz) {

        Object obj = null;
        if (cacheLong != null) {
            obj = cacheLong.get(key);
        }

//        LogUtil.e("DataCache",obj.getClass().getSimpleName());
//        LogUtil.e("DataCache",clazz.getSimpleName());

        if (obj != null && clazz.getSimpleName().equals(obj.getClass().getSimpleName())) {
            return (T) obj;
        }
        return null;
    }

    /**
     * App 运行期间缓存数据
     *
     * @param key   数据索引
     * @param value 数据值
     * @return 同一key 值的其他索引
     */
    public static <V> V setToStore(String key, V value) {
        return getInstance().setToStoreIml(key, value);
    }

    private <V> V setToStoreIml(String key, V value) {
        Object obj = null;
        if (cacheLong != null) {
            obj = cacheLong.put(key, value);
        }
        return obj == null || !obj.getClass().getSimpleName().equals(value.getClass().getSimpleName()) ? null : ((V) obj);
    }

    /**
     * 从长久缓存中移除 数据
     *
     * @param key 数据的key 值
     * @return 是否存在并移除
     */
    public static boolean removeFromStore(String key) {
        Object obj = cacheLong.remove(key);
        return obj == null;
    }

    /**
     * 清空长久缓存
     */
    public static void clearLongCache() {
        getInstance().clearLongCacheIml();
    }

    private void clearLongCacheIml() {
        cacheLong.clear();
    }

    /**
     * 清空短期缓存
     */
    public static void clearCache() {
        getInstance().clearCacheIml();
    }

    private void clearCacheIml() {
        cache.evictAll();
    }


    /**
     * 从缓存中获取数据
     *
     * @param key 要获取数据的key值
     * @return 返回数据
     */
    public static <T> T getFromCache(String key, Class<T> clazz) {

        return getInstance().getFromCacheIml(key, clazz);
    }

    public static String getString(String key) {
        return getInstance().getFromCacheIml(key, String.class);
    }

    private <T> T getFromCacheIml(String key, Class<T> T) {

        T obj;
        if (key != null) {
            try {
                obj = ((T) cache.get(key));
                return obj;
            } catch (Exception e) {
                Log.e("DataCache", e.toString());
                return null;
            }
        }
        return null;
    }

    /**
     * 存储键值对到缓存
     *
     * @param key   获取数据的key值
     * @param value 存储的值
     * @return 之前存放的值
     */
    public static <V> V setToCache(String key, V value) {
        return getInstance().setToCacheIml(key, value);
    }

    public static String setString(String key, String value) {
        return getInstance().setToCacheIml(key, value);
    }

    private <V> V setToCacheIml(String key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException("key == null || value == null");
        }
        try {
            Object obj = cache.put(key, value);
            return obj == null || !obj.getClass().getSimpleName().equals(value.getClass().getSimpleName()) ? null : ((V) obj);
        } catch (Exception e) {
            Log.e("DataCache", e.toString());
        }
        return null;
    }


    /**
     * 保存JSON 到本地
     *
     * @param key  文件索引
     * @param json JSON数据
     */
    public static void saveToLocal(String key, String json) {
        try {
            getInstance().saveToLocalImp(key, json, dataCache.cacheFile);
        } catch (Exception e) {
            LogUtil.e("Data", "写数据报错：" + e.getMessage());
        }
    }

    /**
     * 保存JSON 到本地
     *
     * @param key       文件索引
     * @param json      JSON数据
     * @param cacheFile 缓存目录
     */
    public static void saveToLocal(String key, String json, File cacheFile) {
        getInstance().saveToLocalImp(key, json, cacheFile);
    }

    private void saveToLocalImp(String key, String json, File cacheFile) {

        ACache aCache = getACache(cacheFile);
        if (aCache == null) {
            return;
        }
        aCache.put(key, json);
    }

    private ACache getACache(File cacheFile) {

        ACache aCache = null;
        try {
            aCache = ACache.get(cacheFile);
        } catch (Exception e) {
            LogUtil.e("ACache 创建报错", " error" + e.getLocalizedMessage());
        }
        return aCache;
    }

    /**
     * 从本地获取缓存数据
     *
     * @param key       文件索引
     * @param cacheFile 缓存目录
     * @return 缓存数据
     */
    public static String getFromLocal(String key, File cacheFile) {
        return getInstance().getFromLocalImp(key, cacheFile);
    }

    /**
     * 从本地获取缓存数据
     *
     * @param key 文件索引
     * @return 缓存数据
     */
    public static String getFromLocal(String key) {
        return getInstance().getFromLocalImp(key, dataCache.cacheFile);
    }

    private String getFromLocalImp(String key, File cacheFile) {
        ACache aCache = getACache(cacheFile);
        if (aCache == null) {
            return "";
        }
        return aCache.getAsString(key);
    }


    /**
     * 缓存其他类型的数据到本地
     *
     * @param key   索引
     * @param value 继承自Serializabe的module
     */
    public static <V extends Serializable> void saveToLocal(String key, V value) {
        getInstance().saveToLocalImp(key, value);
    }

    private <V extends Serializable> void saveToLocalImp(String key, V value) {
        ACache aCache = getACache(cacheFile);
        if (aCache == null) {
            return;
        }
        aCache.put(key, value);
    }

    /**
     * 缓存其他类型的数据到本地
     *
     * @param key 索引
     */
    public static <T extends Serializable> T getFromLocal(String key, Class<T> clazz) {
        return getInstance().getFromLocalImp(key, clazz);
    }

    private <T extends Serializable> T getFromLocalImp(String key, Class<T> clazz) {
        ACache aCache = getACache(cacheFile);
        if (aCache == null) {
            return null;
        }
        Object obj;
        obj = aCache.getAsObject(key);
        Log.e("DataCache", "clazz = " + clazz.getSimpleName());
        if (obj != null && clazz.getSimpleName().equals(obj.getClass().getSimpleName())) {
            try {
                return ((T) obj);
            } catch (Exception e) {
                Log.e("DataCache", "Exception = " + e.toString());
            }
        }
        return null;
    }


    /**
     * 移除临时缓存
     *
     * @param key 移除临时缓存
     */
    public static void removeFromCache(String key) {
        getInstance().removeFromCacheImp(key);
    }

    private void removeFromCacheImp(String key) {
        cache.remove(key);
    }

    /**
     * 获取 Key 对应的Object
     *
     * @param key 存储用的key
     * @return 返回Object
     */
    public static Object getObject(String key) {
        return getInstance().getObjectImp(key);
    }

    private Object getObjectImp(String key) {
        return cache.get(key);
    }


}
