package com.hb.currencymanage.ui.activity;

import android.os.Bundle;

import com.hb.currencymanage.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyManagementActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_money_management);
        ButterKnife.bind(this);



    }


    @OnClick(R.id.moneyrechargelayout)
    void moneyrechargelayout()
    {

        changeActivity(MoneyRechargeActivity.class);

    }

    @OnClick(R.id.widthdrawlayout)
    void widthdrawlayout()
    {

        changeActivity(WithDrawActivity.class);

    }

    @OnClick(R.id.emerygencylayout)
    void emerygencylayout()
    {

        changeActivity(EmergencyActivity.class);

    }


    @OnClick(R.id.back)
    void back()
    {

       finish();

    }





}
