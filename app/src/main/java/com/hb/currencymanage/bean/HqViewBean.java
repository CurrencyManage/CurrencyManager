package com.hb.currencymanage.bean;

import java.util.List;

/**
 * Created by gaodesong on 18/5/6.
 */

public class HqViewBean {


    public float relativeCurrentMix;

    public String currentMinB;

    public String relativeCurrentMixB;

    public float currentMin;

    public List<LineBean> data;




    public static class LineBean{

        public int rowno;

        public float price;

        public String time;


    }






}
