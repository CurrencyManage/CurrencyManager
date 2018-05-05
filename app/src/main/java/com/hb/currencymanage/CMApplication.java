package com.hb.currencymanage;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class CMApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        initLog();
    }
    
    private void initLog()
    {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }
}
