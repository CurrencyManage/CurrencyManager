package com.hb.currencymanage.bean;

/**
 * Created by Administrator on 2018/4/17.
 */

public class QuotesEntity
{
    public float buy;
    
    public String stopPlate;
    
    public String currentPrice;
    
    public String disparity;

    public String disparityB;
    
    public String currentMix;
    
    public String currentMin;
    
    public String count;
    
    public String countPrice;
    
    public String buyPrice;
    
    public String buyUserId;
    
    public int buyNum;
    
    public String id;
    
    public String sellUserId;
    
    public String sellPrice;
    
    public int sellNum;

    public int countNum;

    public int sellCount;


    @Override
    public String toString()
    {
        return "QuotesEntity{" + "buy=" + buy + ", stopPlate='" + stopPlate
                + '\'' + ", currentPrice='" + currentPrice + '\''
                + ", Disparity='" + disparity + '\'' + ", disparityB='"
                + disparityB + '\'' + ", currentMix='" + currentMix + '\''
                + ", currentMin='" + currentMin + '\'' + ", count='" + count
                + '\'' + ", countPrice='" + countPrice + '\'' + ", buyPrice='"
                + buyPrice + '\'' + ", buyUserId='" + buyUserId + '\''
                + ", buyNum=" + buyNum + ", id='" + id + '\'' + ", sellUserId='"
                + sellUserId + '\'' + ", sellPrice='" + sellPrice + '\''
                + ", sellNum=" + sellNum + '}';
    }
    
    /*
     * "sellUserId": 1, "sellPrice": 55, "id": 14, "sellNum": 11
     */
}
