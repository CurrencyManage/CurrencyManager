package com.hb.currencymanage.ui.activity;

import com.hb.currencymanage.R;

import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/23.
 */

public class ForgetPwdActivity extends BaseActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
    }
}
