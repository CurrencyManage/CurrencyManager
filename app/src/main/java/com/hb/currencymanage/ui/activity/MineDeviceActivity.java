package com.hb.currencymanage.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.TabEntity;
import com.hb.currencymanage.ui.fragment.DeviceListFragment;
import com.hb.currencymanage.ui.fragment.SimpleCardFragment;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineDeviceActivity extends BaseActivity
{
    
    @BindView(R.id.tab_layout)
    CommonTabLayout mTabLayout;
    
    @BindView(R.id.vp)
    ViewPager mViewPager;
    
    private static final String[] titles = { "我的", "租赁" };
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    
    private int[] mIconUnSelectIds = { R.mipmap.icon_sy,
            R.mipmap.icon_hq_default };
    
    private int[] mIconSelectIds = { R.mipmap.icon_sy_selected,
            R.mipmap.icon_hq_selected };

    private DeviceListFragment deviceListFragment1,deviceListFragment2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_device);
        ButterKnife.bind(this);
        initTabLayout();
    }


    @OnClick(R.id.add)
    void add()
    {

        changeActivity(AddDeviceActivity.class);

    }
    
    private void initTabLayout()
    {
        for (int i = 0; i < titles.length; i++)
        {
            mTabEntities.add(new TabEntity(titles[i], mIconSelectIds[i],
                    mIconUnSelectIds[i]));
           // mFragments.add(SimpleCardFragment.getInstance(titles[i]));
            if(i==0){
                if(deviceListFragment1==null){
                    deviceListFragment1=DeviceListFragment.getInstance("我的");
                }
                mFragments.add(deviceListFragment1);
            }

            if(i==1){
                if(deviceListFragment2==null){
                    deviceListFragment2=DeviceListFragment.getInstance("租赁");
                }
                mFragments.add(deviceListFragment2);
            }
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
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(1);
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


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }



}
