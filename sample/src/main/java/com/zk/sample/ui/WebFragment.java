package com.zk.sample.ui;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.zk.sample.BuildConfig;
import com.zk.sample.R;
import com.zk.sample.ui.base.BaseFragment;
import com.zk.sample.databinding.FragmentWebBinding;

/**
 * ================================================
 * Describe：
 * Created by zhaokai on 2017/3/7.
 * Email zhaokai1033@126.com
 * ================================================
 */

public class WebFragment extends BaseFragment<FragmentWebBinding> {

    private static final String URL = "URL";
    private String url;

    public static WebFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(URL, url);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_web;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
        if (TextUtils.isEmpty(url)) {
            url = "https://www.baidu.com";
        }
        initImp(binding.web);
        binding.web.loadUrl(url);
    }


    private void initImp(WebView mWebView) {
        Context mContext = mWebView.getContext();
        mWebView.setFocusable(true);
        mWebView.requestFocusFromTouch();//支持触摸获取焦点
        WebSettings webSettings = mWebView.getSettings();
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCachePath(mContext.getCacheDir().getAbsolutePath() + "/webViewCache");
        webSettings.setAllowFileAccess(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setJavaScriptEnabled(true);// 设置使用够执行JS脚本
        webSettings.setBuiltInZoomControls(false);// 设置使支持缩放
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        String ua = webSettings.getUserAgentString();
        if (!ua.contains("ylyqAndroid"))
            webSettings.setUserAgentString(ua + " ylyqAndroid/" + BuildConfig.VERSION_NAME);
    }
}
