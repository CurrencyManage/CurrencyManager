package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hb.currencymanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class SimpleCardFragment extends Fragment
{
    @BindView(R.id.card_title_tv)
    TextView mTxt;
    
    private String mTitle;
    
    public static SimpleCardFragment getInstance(String title)
    {
        SimpleCardFragment sf = new SimpleCardFragment();
        sf.mTitle = title;
        return sf;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fr_simple_card, null);
        ButterKnife.bind(this, v);
        mTxt.setText(mTitle);
        return v;
    }
}
