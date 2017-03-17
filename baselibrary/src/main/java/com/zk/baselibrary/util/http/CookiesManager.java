package com.zk.baselibrary.util.http;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * ================================================
 * Created by zhaokai on 2017/3/16.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class CookiesManager implements CookieJar {

    private static final String TAG = "CookiesManager";
    private PersistentCookieStore cookieStore;

    private CookiesManager(Context context) {
        Context mContext = context.getApplicationContext();
        cookieStore = new PersistentCookieStore(mContext);
    }

    public static CookiesManager newInstance(Context context) {
        return new CookiesManager(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return cookieStore.get(url);
    }

    /**
     * 移出指定Cookie
     * 判断标注 url & cookie.name
     */
    public boolean removeCookie(HttpUrl url, Cookie cookie) {
        return cookieStore.remove(url, cookie);
    }

    /**
     * 移出所有的cookie
     */
    public boolean removeAll() {
        return cookieStore.removeAll();
    }

    /**
     * 获取指定网址的cookie
     */
    public List<Cookie> getCookies(HttpUrl url) {
        return cookieStore.get(url);
    }

    /**
     * 获取所有的Cookie
     */
    public List<Cookie> getAllCookies() {
        return cookieStore.getCookies();
    }

    /**
     * 添加Cookie
     */
    public void addCookie(HttpUrl url, Cookie cookie) {
        cookieStore.add(url, cookie);
    }

    /**
     * 获取内存中Cookie 的key值
     */
    public String getCookieName(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    /**
     * 获取store
     */
    public PersistentCookieStore getCookieStore() {
        return cookieStore;
    }


}
