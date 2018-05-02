package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.TabEntity;
import com.hb.currencymanage.customview.NoSlideViewPager;
import com.hb.currencymanage.ui.activity.MineDeviceActivity;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by 汪彬 on 2018/4/20.
 */

public class AuthorizeFragment extends BaseFragment
{
    @BindView(R.id.tab_layout_authorize)
    CommonTabLayout mTabLayout;
    
    @BindView(R.id.vp_authorize)
    ViewPager mVPager;
    
    private static final String[] titles = { "当日委托", "当日成交" };
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    
    private int[] mIconUnSelectIds = { R.mipmap.icon_sy,
            R.mipmap.icon_hq_default };
    
    private int[] mIconSelectIds = { R.mipmap.icon_sy_selected,
            R.mipmap.icon_hq_selected };
    
    public static AuthorizeFragment getInstance()
    {
        return new AuthorizeFragment();
    }
    
    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_deal_authorize;
    }
    
    @Override
    protected void init()
    {
        initTabLayout();
    }
    
    private void initTabLayout()
    {
        for (int i = 0; i < titles.length; i++)
        {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i],
                    mIconUnSelectIds[i]));
            mFragments.add(OperationFragment.getInstance());
        }
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener()
        {
            @Override
            public void onTabSelect(int position)
            {
                mVPager.setCurrentItem(position);
            }
            
            @Override
            public void onTabReselect(int position)
            {
            }
        });
        
        mVPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
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
        mVPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        mVPager.setCurrentItem(0);
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
            return titles[position];
        }
        
        @Override
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }
    }
}
