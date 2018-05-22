package com.hb.currencymanage;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hb.currencymanage.bean.TabEntity;
import com.hb.currencymanage.ui.activity.BaseActivity;
import com.hb.currencymanage.ui.fragment.DealFragment;
import com.hb.currencymanage.ui.fragment.MainFragment;
import com.hb.currencymanage.ui.fragment.MainNewFragment;
import com.hb.currencymanage.ui.fragment.PersonFragment;
import com.hb.currencymanage.ui.fragment.QuotesFragment;
import com.hb.currencymanage.ui.fragment.SimpleCardFragment;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
{
    @BindView(R.id.vp)
    ViewPager mViewPager;
    
    @BindView(R.id.tab_layout)
    CommonTabLayout mTabLayout;
    
    @BindView(R.id.status_view)
    View mStatusView;
    
    private String[] mTitles = { "首页", "行情", "交易", "个人" };
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    
    private int[] mIconUnSelectIds = { R.mipmap.icon_sy,
            R.mipmap.icon_hq_default, R.mipmap.icon_jy_default,
            R.mipmap.icon_gr_default };
    
    private int[] mIconSelectIds = { R.mipmap.icon_sy_selected,
            R.mipmap.icon_hq_selected, R.mipmap.icon_jy_selected,
            R.mipmap.icon_gr_selected };
    
    private DealFragment mDealFragment;
    
    private QuotesFragment mQuotesFragment;
    
    private PersonFragment mPersonFragment;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
    }
    
    public void swTab(int page)
    {
        mViewPager.setCurrentItem(2);
        mDealFragment.setPage(page);
    }
    
    private void initViewPager()
    {
        mDealFragment = DealFragment.getInstance();
        mQuotesFragment = QuotesFragment.getInstance();
        mPersonFragment = PersonFragment.getInstance();
        mFragments.add(MainNewFragment.getInstance());
        mFragments.add(mQuotesFragment);
        mFragments.add(mDealFragment);
        mFragments.add(mPersonFragment);
        for (int i = 0; i < mTitles.length; i++)
        {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i],
                    mIconUnSelectIds[i]));
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener()
        {
            @Override
            public void onTabSelect(int position)
            {
                
                mViewPager.setCurrentItem(position);
                
            }
            
            @Override
            public void onTabReselect(int position)
            {
                if (position == 0)
                {
                    // mTabLayout.showMsg(0, new Random().nextInt(100) + 1);
                }
            }
        });
        
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels)
            {
                
            }
            
            @Override
            public void onPageSelected(int position)
            {
                mTabLayout.setCurrentTab(position);
                if(position==0){
                    mStatusView.setVisibility(View.GONE);
                }else {
                    mStatusView.setVisibility(View.VISIBLE);
                }
                mStatusView.setBackgroundColor(position == 0
                        ? context.getResources()
                                .getColor(R.color.device_bar_color_50_alpha)
                        : position == 1
                                ? context.getResources()
                                        .getColor(R.color.color_head)
                                : context.getResources()
                                        .getColor(R.color.color_head));
                if(position==0){
                    mStatusView.setVisibility(View.GONE);
                }
                if (position == 1)
                {
                    if (null != mQuotesFragment)
                    {
                        mQuotesFragment.initNetWork();
                    }
                }
                if (position == mFragments.size() - 1)
                {
                    if (null != mPersonFragment)
                    {
                        mPersonFragment.initWorkNet();
                    }
                }
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mStatusView.setVisibility(View.GONE);
        mViewPager.setCurrentItem(0);
    }
    
    private class MyPagerAdapter extends FragmentPagerAdapter
    {
        public MyPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }
        
        @Override
        public int getCount()
        {
            return mFragments.size();
        }
        
        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTitles[position];
        }
        
        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }
    }
    
}
