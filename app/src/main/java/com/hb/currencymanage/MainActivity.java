package com.hb.currencymanage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity
{
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};

    @BindView(R.id.vp)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    CommonTabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
