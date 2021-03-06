package com.hb.currencymanage.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hb.currencymanage.R;
import com.hb.currencymanage.net.RxSchedulers;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public abstract class BaseFragment extends Fragment
{
    private Context context;
    
    private Unbinder mBinder;
    
    protected boolean mIsVisible;
    
    private boolean mIsPrepared;
    
    // private boolean mIsFirst = true;
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(getLayoutResId(), null);
        mBinder = ButterKnife.bind(this, v);
        init();
        return v;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        // super.onViewCreated(view, savedInstanceState);
        context = getActivity();
    }
    
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        lazyLoad();
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        mIsVisible = getUserVisibleHint();
        if (getUserVisibleHint())
        {
            lazyLoad();
        }
    }
    
    protected void lazyLoad()
    {
        // if (!mIsPrepared || !mIsVisible || !mIsFirst)
        if (!mIsPrepared || !mIsVisible)
        {
            return;
        }
        requestData();
        // mIsFirst = false;
    }
    
    protected void requestData()
    {
        
    }
    
    /**
     * 跳转界面
     */
    public void changeActivity(Class<?> clz)
    {
        Intent intent = new Intent(context, clz);
        startActivity(intent);
    }
    
    public void changeActivity(Class<?> clz, Bundle bundle)
    {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        RxSchedulers.clear();
    }
    
    @Override
    public void onDestroyView()
    {
        RxSchedulers.clear();
        super.onDestroyView();
        mBinder.unbind();
    }

    public void setTextColor(TextView tv, String color)
    {

        try {
            if(!TextUtils.isEmpty(color)){
                tv.setTextColor(Color.parseColor(color));
            }
        }catch (Exception e){

        }


    }


    public void setBgColor(TextView tv, String color)
    {

        try {
            if(!TextUtils.isEmpty(color)){
                tv.setBackgroundColor(Color.parseColor(color));
            }
        }catch (Exception e){

        }


    }
    
    public abstract int getLayoutResId();
    
    protected abstract void init();
}
