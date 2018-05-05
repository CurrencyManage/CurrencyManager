package com.hb.currencymanage.db;

import android.content.Context;

import com.hb.currencymanage.bean.UserBean;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * Created by 汪彬 on 2018/5/5.
 */

public class AccountDB
{
    private Context context;
    
    private final static String DB_NAME = "USER_INFO";
    
    public AccountDB(Context context)
    {
        this.context = context;
    }
    
    /**
     * 获取用户id
     * 
     * @return
     */
    public String getUserId()
    {
        String code = null;
        try
        {
            DB db = DBFactory.open(context, DB_NAME);
            code = db.get("id");
            db.close();
        }
        catch (SnappydbException e)
        {
            e.printStackTrace();
        }
        return code;
    }
    
    /**
     * 保存当前登录帐号信息
     * 
     * @param bean
     */
    public void saveAccount(UserBean bean)
    {
        try
        {
            DB db = DBFactory.open(context, DB_NAME);
            db.put("account", bean);
            db.put("id", bean.getId());
            db.close();
        }
        catch (SnappydbException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取当前登录帐号信息
     * 
     * @return
     */
    public UserBean getAccount()
    {
        UserBean bean = null;
        try
        {
            DB db = DBFactory.open(context, DB_NAME);
            bean = db.getObject("account", UserBean.class);
            db.close();
        }
        catch (SnappydbException e)
        {
            e.printStackTrace();
        }
        return bean;
    }
    
    /**
     * 清空当前登录帐号信息
     */
    public void clear()
    {
        try
        {
            DB db = DBFactory.open(context, DB_NAME);
            db.del("account");
            db.close();
        }
        catch (SnappydbException e)
        {
            e.printStackTrace();
        }
    }
}
