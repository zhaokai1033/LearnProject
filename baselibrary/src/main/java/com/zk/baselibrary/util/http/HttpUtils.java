package com.zk.baselibrary.util.http;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
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

@SuppressWarnings({"unused", "WeakerAccess"})
public class HttpUtils {

    public final static int SUCCESS = 1000;
    public final static int ERROR_TIME_OUT = 1001;
    public final static int ERROR_IO = 1002;

    private static final String TAG = "HttpUtils";
    private static HttpUtils mInstance;
    private final OkHttpClient mOkHttpClient;
    private final CookiesManager cookieManager;
    private final RequestHeaderBuilder defaultRequestHeadBuilder;
    private final Gson mGson;
    private RequestHeaderBuilder requestHeaderBuilder;

    public static HttpUtils getInstance() {
        isInit();
        return mInstance;
    }

    public static HttpUtils newInstance(Context context, Config config) {
        return new HttpUtils(context, config);
    }

    private HttpUtils(Context context, Config config) {
        if (config == null) {
            config = new Config(10, 10, 30, false, "网络连接失败");
        }
        mGson = new Gson();
        cookieManager = CookiesManager.newInstance(context.getApplicationContext());
        mOkHttpClient = new OkHttpClient()
                .newBuilder()
                .cookieJar(cookieManager)
                .connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
                .readTimeout(config.readTimeout, TimeUnit.SECONDS)
                .build();
        defaultRequestHeadBuilder = RequestHeaderBuilder.newInstance();
    }

    public static HttpUtils init(Context context, Config config) {

        if (mInstance == null)
            synchronized (TAG) {
                if (mInstance == null) {
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
    public static String postResult(String url, RequestBodyBuilder builder) {
        isInit();
        RequestBody formBody = builder == null ? null : builder.build();
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("POST", formBody)
                .build();
        Response response = requestResponse(request);
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                return String.valueOf("code:" + response.code() + " message:" + e.getMessage());
            }
        }
        return String.valueOf("code:" + response.code() + " message:" + response.message());
    }

    /**
     * 同步post请求 并返回所需数据类型
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @param clazz   返回数据类型
     * @return 返回值 异常为null
     */
    public static <T> T postResult(String url, RequestBodyBuilder builder, Class<T> clazz) {
        isInit();
        RequestBody formBody = builder == null ? null : builder.build();
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("POST", formBody)
                .build();
        Response response = requestResponse(request);
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                return mInstance.mGson.fromJson(result, clazz);
            } catch (JsonSyntaxException j) {
                Log.e(TAG, "" + j.toString());
            } catch (IOException e) {
                Log.e(TAG, e.toString());
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
    public static String getResult(String url, RequestBodyBuilder builder) {
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
        Response response = requestResponse(request);
        if (response.isSuccessful()) {
            try {
                return response.body().string();
            } catch (IOException e) {
                return String.valueOf("code:" + response.code() + " message:" + e.getMessage());
            }
        }
        return String.valueOf("code:" + response.code() + " message:" + response.message());
    }

    /**
     * 同步get请求 并返回所需数据类型 只支持 FormBody
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @param clazz   返回数据类型
     * @return 返回值 异常为null
     */
    public static <T> T getResult(String url, RequestBodyBuilder builder, Class<T> clazz) {
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
        Response response = requestResponse(request);
        if (response.isSuccessful()) {
            try {
                String result = response.body().string();
                return mInstance.mGson.fromJson(result, clazz);
            } catch (JsonSyntaxException j) {
                Log.e(TAG, "" + j.toString());
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }
        return null;
    }

    /**
     * 同步网络请求
     */
    public static Response requestResponse(Request request) {
        isInit();
        Response response;
        try {
            Call call = mInstance.mOkHttpClient.newCall(request);
            response = call.execute();
        } catch (SecurityException s) {
            throw new RuntimeException("You need to declare that you want to open the network");
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
     * 异步get请求 并返回所需数据类型 只支持 FormBody
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @param clazz   返回数据类型
     */
    public static <T> void getResultAsync(String url, RequestBodyBuilder builder, final Class<T> clazz, final HttpCallBack<T> callBack) {
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
        requestAsync(request, new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.code(response.code());
                callBack.onResponse(call, response);
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        callBack.onSuccess(mInstance.mGson.fromJson(result, clazz));
                    } catch (Exception e) {
                        callBack.onFailure(call, e);
                    }
                } else {
                    callBack.onFailure(call, null);
                }
            }
        }, callBack);
    }

    /**
     * 异步post请求 并返回所需数据类型
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     * @param clazz   返回数据类型
     */
    public static <T> void postResultAsync(String url, RequestBodyBuilder builder, final Class<T> clazz, final HttpCallBack<T> callBack) {
        isInit();
        RequestBody formBody = builder == null ? null : builder.build();
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("POST", formBody)
                .build();

        requestAsync(request, new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.code(response.code());
                callBack.onResponse(call, response);
                if (response.isSuccessful()) {
                    try {
                        String result = response.body().string();
                        callBack.onSuccess(mInstance.mGson.fromJson(result, clazz));
                    } catch (Exception e) {
                        callBack.onFailure(call, e);
                    }
                } else {
                    callBack.onFailure(call, null);
                }
            }
        }, callBack);
    }

    /**
     * 异步get请求 并返回数据内容 只支持 FormBody
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     */
    public static void getResultAsync(String url, RequestBodyBuilder builder, final HttpCallBack<String> callBack) {
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
        requestAsync(request, new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.code(response.code());
                callBack.onResponse(call, response);
                if (response.isSuccessful()) {
                    try {
                        callBack.onSuccess(response.body().string());
                    } catch (Exception e) {
                        callBack.onFailure(call, e);
                    }
                } else {
                    callBack.onFailure(call, null);
                }
            }
        }, callBack);
    }

    /**
     * 异步post请求 并返回所需数据内容
     *
     * @param url     请求地址
     * @param builder 请求参数 RequestBodyBuilder.new
     */
    public static void postResultAsync(String url, RequestBodyBuilder builder, final HttpCallBack<String> callBack) {
        isInit();
        RequestBody formBody = builder == null ? null : builder.build();
        Request request = mInstance.getRequestHeaderBuilder()
                .url(url)
                .method("POST", formBody)
                .build();

        requestAsync(request, new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                callBack.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callBack.code(response.code());
                callBack.onResponse(call, response);
                if (response.isSuccessful()) {
                    try {
                        callBack.onSuccess(response.body().string());
                    } catch (Exception e) {
                        callBack.onFailure(call, e);
                    }
                } else {
                    callBack.onFailure(call, null);
                }
            }
        }, callBack);
    }


    /**
     * 异步网络请求
     */
    public static void requestAsync(Request request, Callback responseCallback, HttpCallBack callBack) {
        isInit();
        Call call = mInstance.mOkHttpClient.newCall(request);
        callBack.call(call);
        call.enqueue(responseCallback);
    }

    /**
     * 判断是否初始化过
     */
    private static void isInit() {
        if (mInstance == null || mInstance.defaultRequestHeadBuilder == null) {
            throw new RuntimeException("you need call HttpUtils.init() first");
        }
    }
}
