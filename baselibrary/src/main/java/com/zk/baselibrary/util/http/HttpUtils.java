package com.zk.baselibrary.util.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.JsonSyntaxException;
import com.zk.baselibrary.util.GsonUtil;
import com.zk.baselibrary.util.LogUtil;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * ================================================
 * Created by zhaokai on 2017/3/16.
 * <p>
 * Email zhaokai1033@126.com
 * Describe :
 * 网络请求工具
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
public class HttpUtils {

    public final static int SUCCESS = 1000;
    public final static int ERROR_TIME_OUT = 1001;
    public final static int ERROR_IO = 1002;
    public final static int ERROR_JSON = 1003;

    private static final String TAG = "HttpUtils";
    private static HttpUtils mInstance;
    private final Handler mHandler;
    private final OkHttpClient mOkHttpClient;
    private final CookiesManager cookieManager;
    private RequestHeaderBuilder requestHeaderBuilder;
    private RequestHeaderBuilder defaultRequestHeadBuilder;

    private HttpUtils(Context context, Config config) {
        mHandler = new Handler(Looper.getMainLooper());
        cookieManager = CookiesManager.newInstance(context);
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .cookieJar(cookieManager)
                .connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
                .readTimeout(config.readTimeout, TimeUnit.SECONDS)
                .build();
        defaultRequestHeadBuilder = RequestHeaderBuilder.newInstance();
    }

    private void initClient() {
    }

    public static HttpUtils init(Context context, Config config) {

        if (mInstance == null)
            synchronized (TAG) {
                if (mInstance == null) {
                    if (config == null) {
                        config = new Config(10, 10, 30);
                    }
                    mInstance = new HttpUtils(context, config);
                }
            }
        return mInstance;
    }

    /**
     * 获取指定 地址的Cookie;
     *
     * @param url 请求地址
     */
    public static List<Cookie> getCookie(String url) {
        isInit();
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (httpUrl == null) throw new IllegalArgumentException("unexpected url: " + url);
        return mInstance.cookieManager.getCookies(httpUrl);
    }

    /**
     * 获取CookieStore
     */
    public static PersistentCookieStore getCookieStore() {
        isInit();
        return mInstance.cookieManager.getCookieStore();
    }

    /**
     * 添加Cookie
     */
    public void addCookie(String url, Cookie cookie) {
        isInit();
        HttpUrl httpUrl = HttpUrl.parse(url);
        mInstance.cookieManager.addCookie(httpUrl, cookie);
    }

    /**
     * 设置默认请求头
     *
     * @param builder RequestHeaderBuilder.new
     */
    public void setHeaderBuilder(RequestHeaderBuilder builder) {
        this.requestHeaderBuilder = builder;
    }

    /**
     * 获取默认请求头
     */
    public RequestHeaderBuilder getRequestHeaderBuilder() {
        if (requestHeaderBuilder == null) {
            return defaultRequestHeadBuilder;
        }
        return requestHeaderBuilder;
    }

    /**
     * 同步post请求 并返回
     * 此方法将整个响应体加载到内存中。如果响应体是非常大的可能引发 {@link OutOfMemoryError}.
     * 建议直接获取Response
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @return 主体内容，异常返回空或异常信息
     */
    public static String getPostResult(String url, RequestBodyBuilder builder) {
        isInit();
        RequestBody formBody = builder == null ? null : builder.build();
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("POST", formBody)
                .build();
        Response response = getResponse(request);
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return String.valueOf("code:" + response.code() + " message:" + response.message());
    }

    /**
     * 同步post请求 并返回所需数据类型
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @param T       返回数据类型
     * @return 返回值 异常为null
     */
    public static <T> T getPostResult(String url, RequestBodyBuilder builder, Class<T> T) {
        isInit();
        RequestBody formBody = builder == null ? null : builder.build();
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("POST", formBody)
                .build();
        Response response = getResponse(request);
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                return GsonUtil.fromJson(result, T);
            } catch (JsonSyntaxException j) {
                LogUtil.e(TAG, "" + j.toString());
            } catch (IOException e) {
                LogUtil.e(TAG, e.toString());
            }
        }
        return null;
    }


    /**
     * 同步get请求 并返回所需数据类型 只支持 FormBody
     * 此方法将整个响应体加载到内存中。如果响应体是非常大的可能引发 {@link OutOfMemoryError}.
     * 建议直接获取Response
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @return 主体内容，异常返回空或异常信息
     */
    public static String getGetResult(String url, RequestBodyBuilder builder) {
        isInit();
        if (builder != null) {
            if (builder.isMulti()) {
                throw new IllegalArgumentException("MultipartBody is not supported");
            } else {
                url += builder.buildGet();
            }
        }
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = getResponse(request);
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        return String.valueOf("code:" + response.code() + " message:" + response.message());
    }

    /**
     * 同步get请求 并返回所需数据类型 只支持 FormBody
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @param T       返回数据类型
     * @return 返回值 异常为null
     */
    public static <T> T getGetResult(String url, RequestBodyBuilder builder, Class<T> T) {
        isInit();
        if (builder != null) {
            if (builder.isMulti()) {
                throw new IllegalArgumentException("MultipartBody is not supported");
            } else {
                url += builder.buildGet();
            }
        }
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = getResponse(request);
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                return GsonUtil.fromJson(result, T);
            } catch (JsonSyntaxException j) {
                LogUtil.e(TAG, "" + j.toString());
            } catch (IOException e) {
                LogUtil.e(TAG, e.toString());
            }
        }
        return null;
    }

    /**
     * 同步网络请求
     */
    public static Response getResponse(Request request) {
        Response response;
        try {
            response = mInstance.mOkHttpClient.newCall(request).execute();
        } catch (SocketTimeoutException w) {
            response = new Response.Builder()
                    .message("网络连接超时:" + w.getMessage())
                    .code(ERROR_TIME_OUT)
                    .build();
        } catch (IOException e) {
            response = new Response.Builder()
                    .message("IO 异常:" + e.getMessage())
                    .code(ERROR_IO)
                    .build();
        }
        return response;
    }

    /**
     * 判断是否初始化过
     */
    private static void isInit() {
        if (mInstance == null) {
            throw new RuntimeException("you need call HttpUtils.init() first");
        }
    }

}
