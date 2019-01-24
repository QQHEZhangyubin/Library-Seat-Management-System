package com.example.a13608.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.view.KeyEvent.KEYCODE_BACK;

public class MainActivity extends AppCompatActivity {
    private WebView wb_introduce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wb_introduce=(WebView)findViewById(R.id.wb_introduce);
        wb_introduce.loadUrl("https://coh5.cn/p/0e33e4ee.html");
        WebSettings webSettings = wb_introduce.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wb_introduce.setWebViewClient(new WebViewClient());
        wb_introduce.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wb_introduce.canGoBack()) {
            wb_introduce.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        if (wb_introduce!=null){
            wb_introduce.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            wb_introduce.clearHistory();
            ((ViewGroup)wb_introduce.getParent()).removeView(wb_introduce);
            wb_introduce.destroy();
            wb_introduce=null;
        }
        super.onDestroy();
    }
}
