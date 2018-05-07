package com.hb.currencymanage.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

/**
 * Created by 汪彬 on 2018/5/1.
 */

public class CommonUtils
{
    private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
            5, 8, 4, 2, 1 };
    
    private static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
    
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
    
    /**
     * 验证手机格式
     */
    public static boolean isMobile(String mobiles)
    {
        String telRegex = "[1][34578]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }
    
    /**
     * 校验银行卡卡号
     * 
     * @param cardId
     * @return
     */
    public static boolean isBankCard(String cardId)
    {
        char bit = getBankCardCheckCode(
                cardId.substring(0, cardId.length() - 1));
        if (bit == 'N')
        {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * 
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId)
    {
        if (nonCheckCodeCardId == null
                || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+"))
        {
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++)
        {
            int k = chs[i] - '0';
            if (j % 2 == 0)
            {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');
    }
    
    public static boolean isIdCard(String idCard)
    {
        if (idCard.length() != 18)
        {
            return false;
        }
        String verify = idCard.substring(17, 18);
        if (verify.equals(getVerifyId(idCard)))
        {
            return true;
        }
        return false;
    }
    
    private static String getVerifyId(String idCard)
    {
        int remaining = 0;
        int[] ai = new int[18];
        if (idCard.length() == 18)
        {
            idCard = idCard.substring(0, 17);
        }
        if (idCard.length() == 17)
        {
            int sum = 0;
            for (int i = 0; i < 17; i++)
            {
                String k = idCard.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }
            for (int i = 0; i < 17; i++)
            {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }
        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }
}
