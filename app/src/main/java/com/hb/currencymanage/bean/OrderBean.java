package com.hb.currencymanage.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import retrofit2.http.PUT;

/**
 * Created by gaodesong on 18/5/16.
 */

public class OrderBean implements Serializable {


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

    public String bank = "";

    public String type;// 提现订单类型 1、普通提现 2、紧急提现

    public String addtime;// 提现订单提交时间

    public Order order;

    public String khh;

    public String card;

    public String name;

    public String skKHH;

    public String successTime;

    public String skName;

    public Order withdrawalorder;

    public String withdrawalamount;

    public String skBank;



    public static class Order implements Serializable{

        public int skCardId;

        public int amount;

        public String orderTime;

        public String successTime;

        public String skCard;

        public String toaccount;

        public String code;

        public String addtime;

        public String toaccountamount;

        public String id;

        public int state;

        public String type;

        public String userid;

        public String withdrawalamount;


    }


}
