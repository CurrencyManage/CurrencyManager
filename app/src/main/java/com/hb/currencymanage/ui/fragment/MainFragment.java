package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hb.currencymanage.R;

import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class MainFragment extends BaseFragment
{
    public static MainFragment getInstance()
    {
        MainFragment sf = new MainFragment();
        return sf;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, v);
        return v;
    }
}
