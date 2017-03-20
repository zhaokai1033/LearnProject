package com.zk.baselibrary.util.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ================================================
 * Created by zhaokai on 2017/3/17.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings("unused")
public interface HttpCallBack<T> {

    void code(int code);

    void call(Call call);

    void onSuccess(T t);

    void onFailure(Call call, Exception e);

    void onResponse(Call call, Response response);

}
