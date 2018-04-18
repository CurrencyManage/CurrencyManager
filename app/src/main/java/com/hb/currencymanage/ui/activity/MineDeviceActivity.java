package com.hb.currencymanage.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;


import com.hb.currencymanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineDeviceActivity extends BaseActivity {



    String[] titles={"我的","租赁"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_device);

        ButterKnife.bind(this);


        for(String title:titles){

        }
    }
}
