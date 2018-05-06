package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.CurrencyBean;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineCurrencyActivity extends BaseActivity
{
    
    @BindView(R.id.tv_income)
    TextView mTvIncome;
    
    @BindView(R.id.tv_income_percent)
    TextView mTvIncomePercent;
    
    @BindView(R.id.tv_total_money)
    TextView mTvTotalMoney;
    
    @BindView(R.id.tv_total_profit_loss)
    TextView mTvTotalProfitLoss;
    
    @BindView(R.id.tv_currency_total_profit_loss)
    TextView mTvCurrencyTotalProfitLoss;
    
    @BindView(R.id.tv_currency_market_price)
    TextView mTvCurrencyMarketPrice;
    
    @BindView(R.id.tv_currency_hold_count)
    TextView mTvCurrencyHoldCount;
    
    @BindView(R.id.tv_currency_cost)
    TextView mTvCurrencyCost;
    
    @BindView(R.id.tv_currency_available_count)
    TextView mTvCurrencyAvailableCount;
    
    @BindView(R.id.tv_currency_buy_avg)
    TextView mTvCurrencyBuyAvg;
    
    @BindView(R.id.tv_currency_market_value)
    TextView mTvCurrencyMarketValue;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_currency);
        ButterKnife.bind(this);
        getData();
    }
    
    private void getData()
    {

        RetrofitUtils.getInstance(this).api
                .myCurrency(new AccountDB(this).getUserId())
                .compose(RxSchedulers.<ResultData<CurrencyBean>> compose())
                .subscribe(new BaseObserver<CurrencyBean>(this, true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<CurrencyBean> resultData)
                    {
                        if (resultData.result.equals("200"))
                        {
                            CurrencyBean bean = resultData.data;
                            mTvIncome.setText(bean.mySy);
                            mTvIncomePercent.setText(
                                    "我的&#160;&#160;&#160;" + bean.mySyB);
                            mTvTotalMoney.setText(bean.countMoney);
                            mTvTotalProfitLoss.setText(bean.profitAndLoss);
                            mTvCurrencyTotalProfitLoss
                                    .setText("总盈亏      " + bean.profitAndLoss1);
                            mTvCurrencyMarketPrice.setText(bean.marketPrice);
                            mTvCurrencyHoldCount.setText(bean.holdThePosition);
                            mTvCurrencyCost.setText(bean.cost);
                            mTvCurrencyAvailableCount
                                    .setText(bean.warehouseAvailability);
                            mTvCurrencyBuyAvg.setText(bean.priceAVG);
                            mTvCurrencyMarketValue.setText(bean.marketValue);
                        }
                    }
                });
    }
    
    @OnClick(R.id.back)
    void back()
    {
        finish();
    }
}
