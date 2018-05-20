package com.hb.currencymanage.mpchart;

import android.util.SparseArray;

import com.hb.currencymanage.bean.HqViewBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataParse {
    private ArrayList<MinutesBean> datas = new ArrayList<>();
    private ArrayList<KLineBean> kDatas = new ArrayList<>();
    private float baseValue;
    private float permaxmin;
    private float volmax;
    private SparseArray<String> dayLabels;
    private String code = "sz002081";
    private int decreasingColor;
    private int increasingColor;
    private String stockExchange;
    private SparseArray<String> xValuesLabel=new SparseArray<>();
    private int firstDay = 10;

    public float min;
    public float max;
    public float percentmin;
    public float percentmax;

    public void parseMinutes(JSONObject object) {
        JSONArray jsonArray = object.optJSONObject("data").optJSONObject(code).optJSONObject("data").optJSONArray("data");
        String date = object.optJSONObject("data").optJSONObject(code).optJSONObject("data").optString("date");
        if (date.length() == 0) {
            return;
        }
/*数据解析依照自己需求来定，如果服务器直接返回百分比数据，则不需要客户端进行计算*/
        baseValue = (float) object.optJSONObject("data").optJSONObject(code).optJSONObject("qt").optJSONArray(code).optDouble(4);
        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            String[] t = jsonArray.optString(i).split(" ");/*  "0930 9.50 4707",*/
            MinutesBean minutesData = new MinutesBean();
            minutesData.time = t[0].substring(0, 2) + ":" + t[0].substring(2);
            minutesData.cjprice = Float.parseFloat(t[1]);
            if (i != 0) {
                String[] pre_t = jsonArray.optString(i - 1).split(" ");
                minutesData.cjnum = Integer.parseInt(t[2]) - Integer.parseInt(pre_t[2]);
                minutesData.total = minutesData.cjnum * minutesData.cjprice + datas.get(i - 1).total;
                minutesData.avprice = (minutesData.total) / Integer.parseInt(t[2]);
            } else {
                minutesData.cjnum = Integer.parseInt(t[2]);
                minutesData.avprice = minutesData.cjprice;
                minutesData.total = minutesData.cjnum * minutesData.cjprice;
            }
            minutesData.cha = minutesData.cjprice - baseValue;
            minutesData.per = (minutesData.cha / baseValue);
            double cha = minutesData.cjprice - baseValue;
            if (Math.abs(cha) > permaxmin) {
                permaxmin = (float) Math.abs(cha);
            }
            volmax = Math.max(minutesData.cjnum, volmax);
            datas.add(minutesData);
        }

        if (permaxmin == 0) {
            permaxmin = baseValue * 0.02f;
        }


    }


    public void parseNetMinutes(HqViewBean hqViewBean) {


//        baseValue= (float) 10.04;
//        permaxmin= (float) 9.85;


        permaxmin=hqViewBean.currentMin;

        min=hqViewBean.currentMin;
        max=hqViewBean.relativeCurrentMix;

        volmax=200;
        double cPercentmin=Double.parseDouble(hqViewBean.currentMinB.substring(0, hqViewBean.currentMinB.length()-1))*0.01;
       double cPercentMax=Double.parseDouble(hqViewBean.relativeCurrentMixB.substring(0, hqViewBean.relativeCurrentMixB.length()-1))*0.01;
//
        baseValue= (float) (permaxmin/cPercentMax);
//        baseValue=hqViewBean.relativeCurrentMix;



        if (hqViewBean.data!=null && hqViewBean.data.size()>0){
            for(int i=0;i<hqViewBean.data.size();i++){
                MinutesBean minutesData=new MinutesBean();
                minutesData.cjprice= hqViewBean.data.get(i).price<=max?hqViewBean.data.get(i).price:max;
                datas.add(minutesData);

            }
        }else {
            for(int i=0;i<4*60-1;i++){
                MinutesBean minutesData=new MinutesBean();
                minutesData.cjprice= 0;
                datas.add(minutesData);

            }
        }



    }

    public void parseKLine(JSONObject obj) {
        ArrayList<KLineBean> kLineBeans = new ArrayList<>();
        JSONObject data = obj.optJSONObject("data").optJSONObject(code);
        JSONArray list = data.optJSONArray("day");
        if (list != null) {
            int count = list.length();
            for (int i = 0; i < count; i++) {
                JSONArray dayData = list.optJSONArray(i);
                KLineBean kLineData = new KLineBean();
                kLineBeans.add(kLineData);
                kLineData.date = dayData.optString(0);
                kLineData.open = (float) dayData.optDouble(1);
                kLineData.close = (float) dayData.optDouble(2);
                kLineData.high = (float) dayData.optDouble(3);
                kLineData.low = (float) dayData.optDouble(4);
                kLineData.vol = (float) dayData.optDouble(5);
                volmax = Math.max(kLineData.vol, volmax);
                xValuesLabel.put(i, kLineData.date);
            }
        }
        kDatas.addAll(kLineBeans);
    }

    public float getMin() {
        return baseValue - permaxmin;
    }

    public float getMax() {
        return baseValue + permaxmin;
    }

    public float getPercentMax() {
        return permaxmin / baseValue;
    }

    public float getPercentMin() {
        return -getPercentMax();
    }


    public float getVolmax() {
        return volmax;
    }


    public ArrayList<MinutesBean> getDatas() {
        return datas;
    }

    public ArrayList<KLineBean> getKLineDatas() {
        return kDatas;
    }
    public SparseArray<String> getXValuesLabel() {
        return xValuesLabel;
    }
}
