package com.hang.study.gzinfo.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.hang.study.gzinfo.R;

/**
 * Created by hang on 16/7/28.
 */
public class NewsDetailActivity extends Activity implements View.OnClickListener{
    public ImageView back;
    public ImageView menu;
    public ImageView changeSize;
    public ImageView share;
    public LinearLayout ll_control;
    public WebView webView;
    public ProgressBar pb;
    public String url;
    public WebSettings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        initView();
        url=getIntent().getStringExtra("url");
        webView.loadUrl(url);
        settings=webView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public void initView() {
        back= (ImageView) findViewById(R.id.back);
        menu= (ImageView) findViewById(R.id.img_menu);
        changeSize= (ImageView) findViewById(R.id.changeSize);
        share= (ImageView) findViewById(R.id.share);
        ll_control= (LinearLayout) findViewById(R.id.ll_control);
        ll_control.setVisibility(View.VISIBLE);
        webView= (WebView) findViewById(R.id.webView);
        pb= (ProgressBar) findViewById(R.id.pb);
        back.setVisibility(View.VISIBLE);
        menu.setVisibility(View.GONE);
        changeSize.setOnClickListener(this);
        back.setOnClickListener(this);
        share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.changeSize:
                changeTextSize();
                break;
            case R.id.share:
                break;
        }
    }

    int textSize=2;
    private void changeTextSize() {

        String[] choice=new String[]{"超大号字体","大号字体","正常字体","小号字体","超小号字体"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("字体设置");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("which-->"+which+"   textSize-->"+textSize);
                switch (textSize) {
                    case 0:
                        settings.setTextSize(WebSettings.TextSize.LARGEST);
                        break;
                    case 1:
                        settings.setTextSize(WebSettings.TextSize.LARGER);
                        break;
                    case 2:
                        settings.setTextSize(WebSettings.TextSize.NORMAL);
                        break;
                    case 3:
                        settings.setTextSize(WebSettings.TextSize.SMALLER);
                        break;
                    case 4:
                        settings.setTextSize(WebSettings.TextSize.SMALLEST);
                        break;

                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setSingleChoiceItems(choice, textSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                textSize=which;
            }
        });
        builder.show();
    }
}
