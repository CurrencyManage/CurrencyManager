package com.hb.currencymanage.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hb.currencymanage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmergencyActivity extends BaseActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}



