package com.hb.currencymanage;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class CMApplication extends Application
{


    private List<Activity> mList = new LinkedList<Activity>();

    private static CMApplication instance;


    @Override
    public void onCreate()
    {
        super.onCreate();
        initLog();
        Bugly.init(getApplicationContext(), "053ee28af3", false);
        //CrashReport.initCrashReport(getApplicationContext(), "053ee28af3", false);
    }
    
    private void initLog()
    {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }


    public synchronized static CMApplication getInstance(){
        if (null == instance) {
            instance = new CMApplication();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity:mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    //杀进程
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }


}
