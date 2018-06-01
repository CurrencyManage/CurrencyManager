package com.hb.currencymanage.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.hb.currencymanage.CMApplication;
import com.hb.currencymanage.R;

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
        CMApplication.getInstance().addActivity(this);

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


    /**
     * 弹出V7版本系统弹窗
     *
     * @param title
     * @param message
     * @param positiveText
     * @param negativeText
     * @param onClickListener
     */
    public void showSystemV7Dialog(@Nullable String title, @Nullable String message, @Nullable String positiveText, @Nullable String negativeText,
                                   @Nullable Boolean isCancel, @Nullable DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveText, onClickListener)
                .setNegativeButton(negativeText, onClickListener)
                .create();
        alertDialog.show();
        alertDialog.setCancelable(isCancel);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.color_red));
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(context.getResources().getColor(R.color.color_2121));

    }

    public void changeActivity(Class<?> clz, Bundle bundle)
    {
        Intent intent = new Intent(context, clz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        unregisterReceiver(mExitBroadcast);

    }
}
