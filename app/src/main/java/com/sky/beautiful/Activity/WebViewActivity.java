package com.sky.beautiful.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.sky.beautiful.BaseActivity.BaseActivity;
import com.sky.beautiful.ImmersionBar.ImmersionBar;
import com.sky.beautiful.R;

/**
 * @Time : 2018/1/3 no 下午12:25
 * @USER : vvguoliang
 * @File : WebViewActivity.java
 * @Software: Android Studio
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 * ***┏┓   ┏ ┓
 * **┏┛┻━━━┛ ┻┓
 * **┃   ☃   ┃
 * **┃ ┳┛  ┗┳ ┃
 * **┃    ┻   ┃
 * **┗━┓    ┏━┛
 * ****┃    ┗━━━┓
 * ****┃ 神兽保佑 ┣┓
 * ****┃ 永无BUG！┏┛
 * ****┗┓┓┏━┳┓┏┛┏┛
 * ******┃┫┫  ┃┫┫
 * ******┗┻┛  ┗┻┛
 */

public class WebViewActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    private String url = "";

    private WebView webview;

    private LinearLayout banner_linear;

    private ProgressBar banner_progressBar;

    private ImageView image;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_search_image2:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            finish();
        }
    }

    @Override
    public void initData() {
        findView(R.id.title_search_image2).setVisibility(View.VISIBLE);
        findView(R.id.title_search_image2).setOnClickListener(this);
        webview.setWebViewClient(webViewClient);
        webview.setWebChromeClient(webChromeClient);
        if (!TextUtils.isEmpty(url) && !"null".equals(url)) {
            banner_linear.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            webview.loadUrl(url);
//            webview.loadUrl(url);
        } else {
            title.setText("");
            banner_linear.setVisibility(View.GONE);
            image.setVisibility(View.VISIBLE);
        }
        getSettings();
    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra("url");
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            mImmersionBar.statusBarDarkFont(true).init();
        } else {
            findViewById(R.id.tab).setBackgroundColor(Color.rgb(231, 231, 231));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            findViewById(R.id.tab).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.tab).setVisibility(View.GONE);
        }
        title = findView(R.id.title);
        webview = findView(R.id.banner_webView);
        banner_linear = findView(R.id.banner_linear);
        banner_progressBar = findView(R.id.banner_progressBar);
        image = findView(R.id.image);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.act_webview;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void getSettings() {
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null); //渲染加速器
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH); //提高渲染的优先级
        webview.removeJavascriptInterface("searchBoxJavaBridge_"); //防止360
        WebSettings settings = webview.getSettings();

        settings.setBlockNetworkImage(false);
        settings.setSaveFormData(false);
        settings.setAllowContentAccess(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        settings.setJavaScriptEnabled(true);  //支持js
        settings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        settings.setSupportZoom(false);  //支持缩放
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        settings.supportMultipleWindows();  //多窗口
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //关闭webview中缓存
        settings.setAllowFileAccess(true);  //设置可以访问文件
        settings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        settings.setBuiltInZoomControls(true); //设置支持缩放
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        settings.setDatabaseEnabled(false);//开启数据库形式存储
        settings.setDomStorageEnabled(false);//开启DOM形式存储
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private WebViewClient webViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            boolean isIntercepted = false;
            if (url.startsWith("weixin://wap/pay?")) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            } else {
                /**
                 * 推荐采用的新的二合一接口(payInterceptorWithUrl),只需调用一次
                 */
                final PayTask task = new PayTask(WebViewActivity.this);
                isIntercepted = task.payInterceptorWithUrl(url, true, result -> {
                    final String url1 = result.getReturnUrl();
                    if (!TextUtils.isEmpty(url1)) {
                        WebViewActivity.this.runOnUiThread(() -> view.loadUrl(url1));
                    }
                });
            }

            if (!isIntercepted)
                view.loadUrl(url);
            return true;
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            String[] stringurl = url.split("\\?");
            if (stringurl[0].contains("returnURL.do") || stringurl[0].contains("wxReturnUrl.do") || stringurl[0].contains("JsyPay/wx/wxReturnUrl")) {
                finish();
            }
            return super.shouldInterceptRequest(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (TextUtils.isEmpty(view.getTitle()) || view.getTitle().contains("404") || view.getTitle().contains("找不到") ||
                    view.getTitle().contains("about:")) {
                title.setText("");
                banner_linear.setVisibility(View.GONE);
                image.setVisibility(View.VISIBLE);
            } else {
                title.setText(view.getTitle());
                banner_linear.setVisibility(View.VISIBLE);
                image.setVisibility(View.GONE);
            }
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            banner_progressBar.setProgress(newProgress);
            if (newProgress == 100) {
                banner_progressBar.setVisibility(View.GONE);
            } else {
                banner_progressBar.setVisibility(View.VISIBLE);
            }
        }
    };
}
