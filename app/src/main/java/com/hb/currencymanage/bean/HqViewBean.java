package com.hb.currencymanage.bean;

import java.util.List;

/**
 * Created by gaodesong on 18/5/6.
 */

public class HqViewBean {


    public String relativeCurrentMix;

    public String currentMinB;

    public String relativeCurrentMixB;

    public String currentMin;

    public List<LineBean> data;




    public static class LineBean{

        public int rowno;

        public int price;

        public String time;


    }






}
