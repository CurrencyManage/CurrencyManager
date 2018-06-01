package com.hb.currencymanage.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.hb.currencymanage.CMApplication;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.util.CommonUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity
{
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }
    
    @OnClick(R.id.back)
    void back()
    {
        finish();
    }
    
    @OnClick(R.id.question_layout)
    void question_layout()
    {
        changeActivity(TellUsActivity.class);
    }
    
    @OnClick(R.id.about_layout)
    void about_layout()
    {
        changeActivity(AboutActivity.class);
    }
    
    @OnClick(R.id.tv_login_out)
    void loginOut()
    {
//        Intent intent = new Intent(BaseActivity.ACTION_EXIT);
//        sendBroadcast(intent);
//        CommonUtils.intent(this, LoginActivity.class);

        new AccountDB(context).clear();
        changeActivity(LoginActivity.class);
        CMApplication.getInstance().exit();


    }
}
