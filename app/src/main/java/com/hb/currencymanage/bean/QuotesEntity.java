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

    public int countNum;
    
    public String id;
    
    public String sellUserId;
    
    public String sellPrice;

    public int sellCount;

    public int sellNum;

    public String showSellNum;

    public String showBuyNum;

    public String showBuyPrice;

    public String showSellPrice;





    /*
     "currentMixColour": "#00E300",
        "countPrice": "4709",
        "stopPlate": "1",
        "disparityB": "-100.0%",
        "count": "11",
        "currentPrice": 0,
        "disparityColour": "-100.0%",
        "currentMin": "0",
        "currentPriceColour": "#00E300",
        "currentMix": "0",
        "currentMinColour": "#00E300",
        "disparity": "-1",
        "countPriceColour": "#C5CED5",
        "countColour": "#C5CED5"
     */

    public String currentMixColour;

    public String currentPriceColour;

    public String currentMinColour;

    public String countPriceColour;

    public String countColour;

    public String showColor;

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
