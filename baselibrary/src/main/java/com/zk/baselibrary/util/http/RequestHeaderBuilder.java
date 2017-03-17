package com.zk.baselibrary.util.http;

import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * ================================================
 * Created by zhaokai on 2017/3/17.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */

@SuppressWarnings({"unused", "WeakerAccess"})
public class RequestHeaderBuilder {

    private final Request.Builder builder;

    public static RequestHeaderBuilder newInstance() {
        return new RequestHeaderBuilder();
    }

    private RequestHeaderBuilder() {
        builder = new Request.Builder();
    }

    /**
     * Sets the URL target of this request.
     *
     * @throws IllegalArgumentException if {@code url} is not a valid HTTP or HTTPS URL. Avoid this
     *                                  exception by calling {@link HttpUrl#parse}; it returns null for invalid URLs.
     */
    public RequestHeaderBuilder url(String url) {
        builder.url(url);
        return this;
    }

    /**
     * Sets the header named {@code name} to {@code value}. If this request already has any headers
     * with that name, they are all replaced.
     */
    public RequestHeaderBuilder header(String name, String value) {
        builder.header(name, value);
        return this;
    }

    /**
     * Adds a header with {@code name} and {@code value}. Prefer this method for multiply-valued
     * headers like "Cookie".
     * <p>
     * <p>Note that for some headers including {@code Content-Length} and {@code Content-Encoding},
     * OkHttp may replace {@code value} with a header derived from the request body.
     */
    public RequestHeaderBuilder addHeader(String name, String value) {
        builder.addHeader(name, value);
        return this;
    }

    /**
     * 确定请求方式
     */
    public RequestHeaderBuilder method(String method, RequestBody body) {
        builder.method(method, body);
        return this;
    }

    public Request build() {
        return builder.build();
    }

    @Override
    public String toString() {
        return "RequestHeaderBuilder{" +
                "method=" + builder +
                '}';
    }
}
