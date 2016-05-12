package com.hx.template.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hx.template.R;

/**
 * Created by huangx on 2016/5/12.
 */
public class BaseWebFragment extends BaseFragment {
    private WebView mWebView;

    protected int getLayoutId() {
        return R.layout.fragment_base_web;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), null);
        mWebView = (WebView) view.findViewById(R.id.base_web);
        if (mWebView == null) {
            throw new IllegalArgumentException("layout中缺少id为R.id.base_web的WebView");
        }
        initWebSetting();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                boolean result = TextUtils.isEmpty(url) || !(url.startsWith("http") || (url.startsWith("file") && url.endsWith(".html")));
                if (result) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                return result;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initWebSetting() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setDefaultTextEncodingName("utf-8");

        webSettings.setLoadWithOverviewMode(true);// 全屏
        webSettings.setUseWideViewPort(true);// 全屏
        webSettings.setDomStorageEnabled(true);
    }
    
}
