package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.hb.currencymanage.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/18.
 */

public class NewsDetailAcivity extends BaseActivity
{
    
    @BindView(R.id.wv)
    WebView mWebView;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
    }
    
    @OnClick(R.id.img_share)
    public void share(View view)
    {
        
    }
}
