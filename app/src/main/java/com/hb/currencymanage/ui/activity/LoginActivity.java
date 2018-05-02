package com.hb.currencymanage.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/23.
 */

public class LoginActivity extends BaseActivity
{
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.tv_login)
    public void login()
    {
        RetrofitUtils.getInstance(this).api.login("18705157954", "112")
                .compose(RxSchedulers.<ResultData<String>> compose())
                .subscribe(new BaseObserver<String>(this, true)
                {
                    @Override
                    public void onHandlerSuccess(ResultData<String> resultData)
                    {
                        
                        if (resultData.code == 200)
                        {
                            changeActivity(MainActivity.class);
                            finish();
                        }
                    }
                });
    }
    
    @OnClick(R.id.tv_reg)
    public void reg()
    {
        changeActivity(RegActivity.class);
    }
    
    @OnClick(R.id.tv_forget_pwd)
    public void forgetPwd()
    {
        changeActivity(ForgetPwdActivity.class);
    }
}
