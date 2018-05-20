package com.hb.currencymanage.ui.fragment;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.ui.activity.CapacityActivity;
import com.hb.currencymanage.ui.activity.MineCurrencyActivity;
import com.hb.currencymanage.ui.activity.MineDeviceActivity;
import com.hb.currencymanage.ui.activity.MoneyManagementActivity;
import com.hb.currencymanage.ui.activity.PersonalActivity;
import com.hb.currencymanage.ui.activity.SettingActivity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class PersonFragment extends BaseFragment
{
    
    @BindView(R.id.userId)
    TextView tv_userId;
    
    @BindView(R.id.currencyMoney)
    TextView tv_currencyMoney;
    
    @BindView(R.id.toDaySy)
    TextView tv_toDaySy;
    
    @BindView(R.id.toMothSy)
    TextView tv_toMothSy;
    
    @BindView(R.id.countMoney)
    TextView tv_ccountMoney;
    
    @BindView(R.id.cash)
    TextView tv_cash;
    
    public static PersonFragment getInstance()
    {
        PersonFragment sf = new PersonFragment();
        return sf;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        initWorkNet();
    }
    
    public void initWorkNet()
    {
        RetrofitUtils.getInstance(getActivity()).api.getAccountInfo(new AccountDB(getContext()).getUserId())
                .compose(RxSchedulers.<ResultData<AccountBean>> compose())
                .subscribe(new BaseObserver<AccountBean>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<AccountBean> resultData)
                    {
                        if (resultData.result == 200)
                        {
                            
                            AccountBean accountBean = resultData.data;
                            tv_cash.setText(accountBean.getCash());
                            tv_ccountMoney.setText(accountBean.getCountMoney());
                            tv_currencyMoney
                                    .setText(accountBean.getCurrencyMoney());
                            tv_toDaySy.setText(
                                    "今日收益：" + accountBean.getToDaySy());
                            tv_toMothSy.setText(
                                    "本月收益：" + accountBean.getToMonthSy());
                            tv_userId.setText(accountBean.getCode());
                            
                        }
                    }
                });
    }
    
    @OnClick(R.id.device_layoout)
    void device_layoout()
    {

        Toast.makeText(getActivity(),"功能暂未开放",Toast.LENGTH_SHORT).show();
        //changeActivity(MineDeviceActivity.class);
    }
    
    @OnClick(R.id.moneymanagement_layout)
    void moneymanagement_layout()
    {

        Bundle bundle=new Bundle();
        bundle.putString("cash",tv_cash.getText().toString());
        changeActivity(MoneyManagementActivity.class,bundle);

    }
    
    @OnClick(R.id.capacity_layout)
    void capacity_layout()
    {

        Toast.makeText(getActivity(),"功能暂未开放",Toast.LENGTH_SHORT).show();
        //changeActivity(CapacityActivity.class);
    }
    
    @OnClick(R.id.mineCurrency_layout)
    void mineCurrency_layout()
    {
        //Toast.makeText(getActivity(),"功能暂未开放",Toast.LENGTH_SHORT).show();
       changeActivity(MineCurrencyActivity.class);
    }
    
    @OnClick(R.id.person_layout)
    void person_layout()
    {
        changeActivity(PersonalActivity.class);
    }
    
    @OnClick(R.id.setting)
    void setting()
    {
        
        changeActivity(SettingActivity.class);
        
    }
    
    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_person;
    }
    
    @Override
    protected void init()
    {
        
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            
            initWorkNet();
            
        }
        
    }
}
