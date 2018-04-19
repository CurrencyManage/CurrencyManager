package com.hb.currencymanage.ui.fragment;

import com.hb.currencymanage.R;
import com.hb.currencymanage.ui.activity.MineDeviceActivity;
import com.hb.currencymanage.ui.activity.MoneyManagementActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class PersonFragment extends BaseFragment
{
    public static PersonFragment getInstance()
    {
        PersonFragment sf = new PersonFragment();
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
        View v = inflater.inflate(R.layout.fragment_person, null);
        ButterKnife.bind(this, v);
        return v;
    }


    @OnClick(R.id.device_layoout)
    void device_layoout(){

        changeActivity(MineDeviceActivity.class);
    }


    @OnClick(R.id.moneymanagement_layout)
    void moneymanagement_layout(){

        changeActivity(MoneyManagementActivity.class);
    }




}
