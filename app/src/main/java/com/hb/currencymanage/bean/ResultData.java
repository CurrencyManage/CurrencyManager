package com.hb.currencymanage.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/21.
 */

public class ResultData<T> implements Serializable {


    public T data;

    public boolean ok;

    public String msg;

    public int result;

    //public int code;

    public String message;

    public List<HomeBean> consultingArray;

    public HomeBean consultingTop;

    public String title;

    public String msgData;

}