package com.hb.currencymanage.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/4/21.
 */

public class ResultData<T> implements Serializable {


    public T data;

    public boolean ok;

    public String msg;

    public String result;

    public int code;


}