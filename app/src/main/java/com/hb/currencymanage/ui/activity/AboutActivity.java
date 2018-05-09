package com.hb.currencymanage.ui.activity;

import android.os.Bundle;

import com.hb.currencymanage.R;
import com.hb.currencymanage.util.StatusBarCompat;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}
