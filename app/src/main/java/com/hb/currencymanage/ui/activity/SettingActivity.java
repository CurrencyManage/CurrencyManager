package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import com.hb.currencymanage.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
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




}
