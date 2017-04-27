package com.zk.sample.module.home;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zk.sample.BuildConfig;
import com.zk.sample.R;
import com.zk.sample.base.BaseFragment;
import com.zk.sample.databinding.FragmentWebBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
            url = "https://github.com/zhaokai1033";
        }
        initImp(binding.web);
        setClient(binding.web);
        binding.web.loadUrl(url);
        setSwipeBackEnable(false);
    }

    private final Set<String> offlineResources = new HashSet<>();

    private void fetchOfflineResources() {
        AssetManager am = getActivity().getAssets();
        try {
            String[] res = am.list("offline_res");
            if (res != null) {
                Collections.addAll(offlineResources, res);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setClient(WebView web) {
        fetchOfflineResources();
        web.setWebViewClient(new WebViewClient() {

            @SuppressWarnings("deprecation")
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d("WebViewActivity", "shouldInterceptRequest thread id: " + Thread.currentThread().getId());
                int lastSlash = url.lastIndexOf("/");
                if (lastSlash != -1) {
                    String suffix = url.substring(lastSlash + 1);
                    if (offlineResources.contains(suffix)) {
                        String mimeType;
                        if (suffix.endsWith(".js")) {
                            mimeType = "application/x-javascript";
                        } else if (suffix.endsWith(".css")) {
                            mimeType = "text/css";
                        } else {
                            mimeType = "text/html";
                        }
                        try {
                            InputStream is = getActivity().getAssets().open("offline_res/" + suffix);
                            Log.i("shouldInterceptRequest", "use offline resource for: " + url);
                            return new WebResourceResponse(mimeType, "UTF-8", is);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Log.i("shouldInterceptRequest", "load resource from internet, url: " + url);
                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                WebResourceResponse response;
                response = super.shouldInterceptRequest(view, request);
                if (url.contains("123icon.png")) {
                    try {
                        response = new WebResourceResponse("image/png", "UTF-8", getActivity().getAssets().open("icon.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return response;
            }
        });
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
