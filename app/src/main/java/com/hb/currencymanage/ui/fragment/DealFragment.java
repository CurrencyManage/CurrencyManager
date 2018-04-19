package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    @BindView(R.id.vpager)
    ViewPager mViewPager;
    
    @BindView(R.id.layout_tab)
    LinearLayout mLayoutTab;
    
    private int mTabSize;
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    public static DealFragment getInstance()
    {
        DealFragment df = new DealFragment();
        return df;
    }
    
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
        mTabSize = mLayoutTab.getChildCount();
        mFragments.add(SimpleCardFragment.getInstance("第一页"));
        mFragments.add(SimpleCardFragment.getInstance("第二页"));
        mFragments.add(SimpleCardFragment.getInstance("第三页"));
        mFragments.add(SimpleCardFragment.getInstance("第四页"));
        mFragments.add(SimpleCardFragment.getInstance("第五页"));
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
                switchTab(position);
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
            }
        });
        bindTabClick();
        switchTab(0);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        mViewPager.setCurrentItem(0);
    }
    
    private void bindTabClick()
    {
        for (int i = 0; i < mTabSize; i++)
        {
            final int index = i;
            mLayoutTab.getChildAt(i)
                    .setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            mViewPager.setCurrentItem(index);
                        }
                    });
        }
    }
    
    private void switchTab(int position)
    {
        int size = mLayoutTab.getChildCount();
        TextView tvTab;
        for (int i = 0; i < size; i++)
        {
            tvTab = (TextView) mLayoutTab.getChildAt(i);
            tvTab.setTextColor(ContextCompat.getColor(getContext(),
                    i == position ? R.color.tab_blue_bg_color : R.color.white));
            tvTab.setBackgroundColor(ContextCompat.getColor(getContext(),
                    i == position ? R.color.white : R.color.tab_blue_bg_color));
        }
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
        public Fragment getItem(int position)
        {
            return mFragments.get(position);
        }
    }
    
}