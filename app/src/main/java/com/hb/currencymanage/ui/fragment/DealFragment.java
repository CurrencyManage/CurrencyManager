package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.hb.currencymanage.ui.activity.MineDeviceActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/18.
 */

public class DealFragment extends BaseFragment
{
    @BindView(R.id.tab_layout)
    CommonTabLayout mTabLayout;
    
    @BindView(R.id.vp)
    ViewPager mViewPager;
    
    public static DealFragment getInstance()
    {
        DealFragment sf = new DealFragment();
        return sf;
    }
    
    private String[] mTitles = { "买入", "卖出", "撤单", "委托", "持仓" };
    
    private int[] mIconUnSelectIds = { R.mipmap.icon_sy,
            R.mipmap.icon_hq_default, R.mipmap.icon_sy, R.mipmap.icon_sy,
            R.mipmap.icon_sy };
    
    private int[] mIconSelectIds = { R.mipmap.icon_sy_selected,
            R.mipmap.icon_hq_selected, R.mipmap.icon_sy, R.mipmap.icon_sy,
            R.mipmap.icon_sy };
    
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_deal, null);
        ButterKnife.bind(this, v);
        initTabLayout();
        return v;
    }
    
    private void initTabLayout()
    {
        for (int i = 0; i < mTitles.length; i++)
        {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i],
                    mIconUnSelectIds[i]));
            mFragments.add(SimpleCardFragment.getInstance(mTitles[i]));
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
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
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
