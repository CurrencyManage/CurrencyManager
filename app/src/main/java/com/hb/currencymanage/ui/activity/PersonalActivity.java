package com.hb.currencymanage.ui.activity;

import android.os.Bundle;

import com.hb.currencymanage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ButterKnife.bind(this);


    }


    @OnClick(R.id.bank_layout)
    void bank_layout()
    {
        changeActivity(BankInformationActivity.class);

    }


    @OnClick(R.id.recommend_layout)
    void recommend_layout()
    {

        changeActivity(RecommendActivity.class);

    }

    @OnClick(R.id.back)
    void back()
    {
        finish();
    }







}
