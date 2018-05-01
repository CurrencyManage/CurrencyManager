package com.hb.currencymanage.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 汪彬 on 2018/5/1.
 */

public class CommonUtils
{
    
    public static void intent(Context context, Class<?> c, Bundle bundle)
    {
        Intent intent = new Intent(context, c);
        if (null != bundle)
        {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    
    public static void intent(Context context, Class<?> c)
    {
        intent(context, c, null);
    }
}
