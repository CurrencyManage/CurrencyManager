package com.hb.currencymanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by gaodesong on 18/5/16.
 */

public class OrderBean implements Serializable{



    public String orderType;

    public String amount;//充值订单金额用这个

    public String toaccountamount;//提现订单金额用这个

    public String code;

    public String orderTime;

    public String skCard;  //收款账号

    public String toaccount;

    public String id;

    public String orderWz;

    public int state;//state 1代表 已支付 2已完成 后面要加0未支付 和3充值部分成功

    public String skKHH;

    public String successTime;

    public String skName;

    public String withdrawalamount;





}
