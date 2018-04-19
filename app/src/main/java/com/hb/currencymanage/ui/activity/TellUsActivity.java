package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import com.hb.currencymanage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TellUsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tell_us);

        ButterKnife.bind(this);
    }


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }





}
