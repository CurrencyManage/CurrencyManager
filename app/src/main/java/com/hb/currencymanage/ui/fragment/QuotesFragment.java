package com.hb.currencymanage.ui.fragment;

import com.hb.currencymanage.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class QuotesFragment extends BaseFragment
{
    public static QuotesFragment getInstance()
    {
        QuotesFragment sf = new QuotesFragment();
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
        View v = inflater.inflate(R.layout.fragment_quotes, null);
        ButterKnife.bind(this, v);
        return v;
    }
}
