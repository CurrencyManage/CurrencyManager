package com.hb.currencymanage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hb.currencymanage.bean.TabEntity;
import com.hb.currencymanage.ui.activity.BaseActivity;
import com.hb.currencymanage.ui.fragment.MainFragment;
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

    private String[] mTitles = { "首页", "行情", "交易", "个人" };
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    
    private int[] mIconUnSelectIds = { R.mipmap.icon_sy,
            R.mipmap.icon_hq_default, R.mipmap.icon_jy_default,
            R.mipmap.icon_gr_default };
    
    private int[] mIconSelectIds = { R.mipmap.icon_sy_selected,
            R.mipmap.icon_hq_selected, R.mipmap.icon_jy_selected,
            R.mipmap.icon_gr_selected };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViewPager();
    }
    
    private void initViewPager()
    {

        /*
        int index = 0;
        for (String title : mTitles)
        {
            if (index == 0)
            {
                mFragments.add(MainFragment.getInstance());
                index++;
                continue;
            }
            mFragments.add(SimpleCardFragment
                    .getInstance("Switch ViewPager " + title));
        }
        */

        mFragments.add(MainFragment.getInstance());
        mFragments.add(QuotesFragment.getInstance());
        mFragments.add(SimpleCardFragment.getInstance(""));
        mFragments.add(PersonFragment.getInstance());


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
//                    mTabLayout.showMsg(0, new Random().nextInt(100) + 1);
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
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
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
