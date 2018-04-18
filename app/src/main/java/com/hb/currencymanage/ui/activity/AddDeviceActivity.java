package com.hb.currencymanage.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hb.currencymanage.R;

import butterknife.OnClick;

public class AddDeviceActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);
    }






    @OnClick(R.id.back)
    void back()
    {
        finish();
    }



}
