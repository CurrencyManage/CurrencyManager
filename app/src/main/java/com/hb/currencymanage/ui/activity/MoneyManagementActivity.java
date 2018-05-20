package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hb.currencymanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoneyManagementActivity extends BaseActivity {



    @BindView(R.id.tv_cash)
    TextView tv_cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_money_management);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        String cash=bundle.getString("cash");
        tv_cash.setText(cash);



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

    @OnClick(R.id.order)
    void order()
    {

        changeActivity(MyOrderActivity.class);

    }


    @OnClick(R.id.back)
    void back()
    {

       finish();

    }





}
