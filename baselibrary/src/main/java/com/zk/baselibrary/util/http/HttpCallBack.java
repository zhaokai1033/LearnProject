package com.zk.baselibrary.util.http;

import okhttp3.Call;
import okhttp3.Response;

/**
 * ================================================
 * Created by zhaokai on 2017/3/17.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public interface HttpCallBack<T> {

    void code(int code);

    void call(Call call);

    void onSuccess(T t);

    void onFailure(Call call, Exception e);

    void onResponse(Call call, Response response);

}
