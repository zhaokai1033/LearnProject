package com.zk.baselibrary.util.http;

import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * ================================================
 * Created by zhaokai on 2017/3/16.
 * Email zhaokai1033@126.com
 * Describe :
 * ================================================
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class RequestBodyBuilder {

    private MultipartBody.Builder multiBuilder;
    private FormBody.Builder formBuilder;

    private final List<String> names = new ArrayList<>();
    private final List<String> values = new ArrayList<>();

    public static RequestBodyBuilder newFormBody() {
        return new RequestBodyBuilder(false);
    }

    public static RequestBodyBuilder newMultipartBody() {
        return new RequestBodyBuilder(true);
    }

    private RequestBodyBuilder(boolean isMulti) {
        if (isMulti) {
            multiBuilder = new MultipartBody.Builder();
        } else {
            formBuilder = new FormBody.Builder();
        }
    }

    /**
     * 增加请求参数
     *
     * @return RequestBodyBuilder
     */
    public RequestBodyBuilder add(String name, String value) {
        if (TextUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name of params  is null ");
        }
        names.add(name);
        values.add(value);
        return this;
    }

    private RequestBodyBuilder addParams(String name, String value) {
        if (formBuilder != null) {
            formBuilder.add(name, value);
        } else if (multiBuilder != null) {
            multiBuilder.addFormDataPart(name, value);
        } else {
            throw new RuntimeException("formBuilder or multiBuilder is null ");
        }
        return this;
    }

    /**
     * 增加请求参数
     *
     * @param name  参数名
     * @param value 参数值
     */
    @Deprecated
    public RequestBodyBuilder addEncodedParams(String name, String value) {
        if (formBuilder != null) {
            formBuilder.addEncoded(name, value);
        } else {
            throw new RuntimeException("formBuilder is null ");
        }
        return this;
    }

    /**
     * 带进度回调的文件上传
     */
    public RequestBodyBuilder addFormDataPart(String name, String filename, RequestBody body) {
        if (multiBuilder != null) {
            multiBuilder.addFormDataPart(name, filename, body);
        } else {
            throw new RuntimeException("multiBuilder is null ");
        }
        return this;
    }

    /**
     * Add a part to the body.
     */
    public RequestBodyBuilder addPart(RequestBody body) {
        if (multiBuilder != null) {
            multiBuilder.addPart(body);
        } else {
            throw new RuntimeException("multiBuilder is null ");
        }
        return this;
    }

    /**
     * Add a part to the body.
     */
    public RequestBodyBuilder addPart(Headers headers, RequestBody body) {
        if (multiBuilder != null) {
            multiBuilder.addPart(headers, body);
        } else {
            throw new RuntimeException("multiBuilder is null ");
        }
        return this;
    }

    /**
     * return a request body for post.
     */
    public FormBody build() {
        if (formBuilder != null) {
            for (int i = 0; i < names.size(); i++) {
                formBuilder.add(names.get(i), values.get(i));
            }
            return formBuilder.build();
        } else {
            throw new RuntimeException("formBuilder is null ");
        }
    }

    public String buildGet() {
        StringBuilder builder = new StringBuilder();
        if (formBuilder != null) {
            for (int i = 0; i < names.size(); i++) {
                builder.append(names.get(i))
                        .append("=")
                        .append(values.get(i))
                        .append("&");
            }
            if (builder.length() > 0) {
                builder.deleteCharAt(builder.length() - 1).insert(0, "?");
            }
            return builder.toString();
        } else {
            throw new RuntimeException("formBuilder is null ");
        }
    }

    /**
     * Assemble the specified parts into a request body.
     */
    public MultipartBody buildMulti() {
        if (multiBuilder != null) {
            return multiBuilder.build();
        } else {
            throw new RuntimeException("multiBuilder is null ");
        }
    }

    /**
     * 文件上传流
     * RequestBody body = createUploadRequestBodyStream(MediaType.parse("application/octet-stream"), file, listener, length);
     * RequestBodyBuilder.newMultiInstance().addFormDataPart("img", filename, body);
     */
    public static RequestBody createUploadRequestBodyStream(final MediaType contentType, final ByteArrayInputStream file, final ProgressListener listener, final long length) {

        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return length;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    Long remaining = contentLength();
                    for (long readCount; (readCount = source.read(buf, 2048)) != -1; ) {
                        sink.write(buf, readCount);
                        listener.onProgress(contentLength(), remaining -= readCount, remaining == 0);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }

    /**
     * 是否是多样化表单请求
     */
    public boolean isMulti() {
        return multiBuilder != null;
    }

    public interface ProgressListener {
        void onProgress(long totalBytes, long remainingBytes, boolean done);
    }
}
