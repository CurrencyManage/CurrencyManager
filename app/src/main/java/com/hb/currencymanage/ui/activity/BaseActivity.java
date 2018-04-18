package com.hb.currencymanage.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class BaseActivity extends AppCompatActivity
{

    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
    }


    /**
     * 跳转界面
     */
    public void changeActivity(Class<?> clz) {
        Intent intent = new Intent(context, clz);
        startActivity(intent);

    }


}
