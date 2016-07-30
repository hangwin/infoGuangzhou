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

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
                showShare();
                break;
        }
    }

    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
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
