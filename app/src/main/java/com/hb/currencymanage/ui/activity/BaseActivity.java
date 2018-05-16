package com.hb.currencymanage.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class BaseActivity extends AppCompatActivity
{
    public static final String ACTION_EXIT = "ACTION_EXIT";
    
    public Context context;
    
    public ExitBroadcast mExitBroadcast;
    
    public class ExitBroadcast extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            if (intent.getAction().equals(ACTION_EXIT))
            {
                finish();
            }
        }
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mExitBroadcast = new ExitBroadcast();
        IntentFilter intentFilter = new IntentFilter(ACTION_EXIT);
        registerReceiver(mExitBroadcast, intentFilter);
        context = this;
    }
    
    /**
     * 跳转界面
     */
    public void changeActivity(Class<?> clz)
    {
        Intent intent = new Intent(context, clz);
        startActivity(intent);
        
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mExitBroadcast);
    }
}
