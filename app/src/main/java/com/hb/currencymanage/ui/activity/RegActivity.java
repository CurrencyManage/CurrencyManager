package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.customview.AutoHeightViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/23.
 */

public class RegActivity extends BaseActivity
{
    @BindView(R.id.vp_reg)
    AutoHeightViewPager mVpReg;
    
    @BindView(R.id.tv_reg_step)
    TextView mTvRegStep;
    
    private List<View> mViews = new ArrayList<>();
    
    private int mCurPos = 0;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        initView();
    }
    
    private void initView()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View stepOne = inflater.inflate(R.layout.activity_reg_step_one, null);
        View stepTwo = inflater.inflate(R.layout.activity_reg_step_two, null);
        View stepThree = inflater.inflate(R.layout.activity_reg_step_three,
                null);
        View stepFour = inflater.inflate(R.layout.activity_reg_step_four, null);
        mViews.add(stepOne);
        mViews.add(stepTwo);
        mViews.add(stepThree);
        mViews.add(stepFour);
        mVpReg.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels)
            {
                
            }
            
            @Override
            public void onPageSelected(int position)
            {
                mVpReg.requestLayout();
                mTvRegStep
                        .setText(position == mViews.size() - 1 ? "注册" : "下一步");
                mCurPos = position;
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
        mVpReg.setScanScroll(false);
        mVpReg.setOffscreenPageLimit(mViews.size() - 1);
        mVpReg.setAdapter(new MyPagerAdapter());
        mVpReg.setCurrentItem(0);
    }
    
    @OnClick(R.id.tv_reg_step)
    public void reg()
    {
        if (mCurPos < mViews.size() - 1)
        {
            mVpReg.setCurrentItem(mCurPos + 1);
        }
        else
        {
            changeActivity(MainActivity.class);
        }
    }
    
    private class MyPagerAdapter extends PagerAdapter
    {
        
        @Override
        public int getCount()
        {
            return mViews.size();
        }
        
        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View v = mViews.get(position);
            container.addView(v);
            return v;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position,
                Object object)
        {
            container.removeView(mViews.get(position));
        }
    }
}
