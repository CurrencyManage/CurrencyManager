package com.hb.currencymanage.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hb.currencymanage.R;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 汪彬 on 2018/4/18.
 */

public class DealFragment extends BaseFragment
{
    @BindView(R.id.vpager)
    ViewPager mViewPager;
    
    @BindView(R.id.layout_tab)
    LinearLayout mLayoutTab;
    
    @BindView(R.id.layout_index)
    LinearLayout mLayoutIndex;
    
    private int mTabSize;
    
    private DealBusinessBuyFragment mDealBusinessBuyFragment;
    
    private DealBusinessSaleFragment mDealBusinessSaleFragment;
    
    private DealBusinessAssignFragment mDealBusinessAssignFragment;
    
    private Disposable mDisposable;
    
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    
    public static DealFragment getInstance()
    {
        DealFragment df = new DealFragment();
        return df;
    }
    
    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_deal;
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        if (null != mDisposable)
        {
            mDisposable.dispose();
        }
    }
    
    @Override
    protected void init()
    {
        initTabLayout();
    }
    
    public void setPage(int page)
    {
        mViewPager.setCurrentItem(page);
    }
    
    private void initTabLayout()
    {
        mDealBusinessBuyFragment = DealBusinessBuyFragment.getInstance();
        mDealBusinessSaleFragment = DealBusinessSaleFragment.getInstance();
        mDealBusinessAssignFragment = DealBusinessAssignFragment.getInstance();
        mTabSize = mLayoutTab.getChildCount();
        mFragments.add(mDealBusinessBuyFragment);
        mFragments.add(mDealBusinessSaleFragment);
        mFragments.add(MyDelegateFragment.getInstance());
        mFragments.add(OperationFragment.getInstance());
        mFragments.add(mDealBusinessAssignFragment);
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
        startTimer();
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
    
    private void startTimer()
    {
        Observable.interval(6, 3, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        mDisposable = d;
                    }
                    
                    @Override
                    public void onNext(Long value)
                    {
                        Log.e("timer : ", "onNext , value = " + value);
                        mDealBusinessBuyFragment.getTenInfo(false);
                        mDealBusinessSaleFragment.getTenInfo(false);
                        mDealBusinessAssignFragment.getTenInfo(false);
                    }
                    
                    @Override
                    public void onError(Throwable e)
                    {
                        
                    }
                    
                    @Override
                    public void onComplete()
                    {
                        
                    }
                });
    }
    
    private void switchTab(int position)
    {
        int size = mLayoutTab.getChildCount();
        TextView tvTab;
        for (int i = 0; i < size; i++)
        {
            tvTab = (TextView) mLayoutTab.getChildAt(i);
            tvTab.setTextColor(ContextCompat.getColor(getContext(),
                    i == position ? R.color.white
                            : R.color.tab_dunselect_color));
            // tvTab.setBackgroundColor(ContextCompat.getColor(getContext(),
            // i == position ? R.color.tab_blue_bg_color
            // : R.color.tab_blue_bg_color));
            mLayoutIndex.getChildAt(i).setVisibility(
                    i == position ? View.VISIBLE : View.INVISIBLE);
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
