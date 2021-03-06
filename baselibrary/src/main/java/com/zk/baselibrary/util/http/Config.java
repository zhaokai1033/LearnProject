package com.zk.baselibrary.util.http;

/**
 * ================================================
 * Created by zhaokai on 2017/3/17.
 * Email zhaokai1033@126.com
 * Describe :
 * 网络请求参数
 * ================================================
 */
@SuppressWarnings("WeakerAccess")
public class Config {
    public String msg;
    public boolean isShowHint;
    public int readTimeout = 10;
    public int writeTimeout = 10;
    public int connectTimeout = 10;

    @SuppressWarnings("unused")
    private Config() {
    }

    /**
     * 网络请求参数  单位 秒
     *
     * @param connectTimeout 连接超时
     * @param writeTimeout   写超时
     * @param readTimeout    读取超时
     * @param isShowHint     网络断开时是否显示Toast提示
     * @param msg            网络断开提示语
     */
    public Config(int connectTimeout, int writeTimeout, int readTimeout, boolean isShowHint, String msg) {
        this.connectTimeout = connectTimeout;
        this.writeTimeout = writeTimeout;
        this.readTimeout = readTimeout;
        this.isShowHint = isShowHint;
        this.msg = msg;
    }
}
