package com.hb.currencymanage.ui.fragment;


import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Utils;
import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.HqViewBean;
import com.hb.currencymanage.bean.QuotesData;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.dialog.QutoesDialogFragment;
import com.hb.currencymanage.mpchart.DataParse;
import com.hb.currencymanage.mpchart.MinutesBean;
import com.hb.currencymanage.mpchart.MyBottomMarkerView;
import com.hb.currencymanage.mpchart.MyLeftMarkerView;
import com.hb.currencymanage.mpchart.MyLineChart;
import com.hb.currencymanage.mpchart.MyRightMarkerView;
import com.hb.currencymanage.mpchart.MyXAxis;
import com.hb.currencymanage.mpchart.MyYAxis;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.ConstantTest;
import com.hb.currencymanage.util.MyUtils;
import com.hb.currencymanage.util.VolFormatter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.reactivestreams.Subscription;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class QuotesFragment extends BaseFragment
{
    public static final int SWITCH_BUY = 0;

    public static final int SWITCH_SALE = 1;

    public static final int SWITCH_WITHDRAW = 2;

    @BindView(R.id.saleRecycleView)
    RecyclerView saleRecycleView;

    @BindView(R.id.buyRecycleView)
    RecyclerView buyRecycleView;


    @BindView(R.id.tv_currentPrice)
    TextView tv_currentPrice;

    @BindView(R.id.tv_Disparity)
    TextView tv_Disparity;

    @BindView(R.id.tv_disparityB)
    TextView tv_disparityB;

    @BindView(R.id.tv_currentMix)
    TextView tv_currentMix;

    @BindView(R.id.tv_currentMin)
    TextView tv_currentMin;

    @BindView(R.id.tv_count)
    TextView tv_count;

    @BindView(R.id.tv_countPrice)
    TextView tv_countPrice;

    @BindView(R.id.line_chart)
    MyLineChart lineChart;

    @BindView(R.id.tv_buyTotalNum)
    TextView tv_buyTotalNum;

    @BindView(R.id.tv_sellTotalNum)
    TextView tv_sellTotalNum;

    private Subscription subscriptionMinute;
    private LineDataSet d1, d2;
    MyXAxis xAxisLine;
    MyYAxis axisRightLine;
    MyYAxis axisLeftLine;
    BarDataSet barDataSet;

    MyXAxis xAxisBar;
    MyYAxis axisLeftBar;
    MyYAxis axisRightBar;
    SparseArray<String> stringSparseArray;
    private DataParse mData;
    Integer sum = 0;
    List<Integer> listA, listB;


    private List<QuotesEntity> saleQuotesEntityList;

    private CommonAdapter saleAdapter;

    private List<QuotesEntity> buyQuotesEntityList;

    private CommonAdapter buyAdapter;

    private int sellTotalNum;

    private int buyTotalNum;

    public static QuotesFragment getInstance()
    {
        QuotesFragment sf = new QuotesFragment();
        return sf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_quotes;
    }

    @Override
    protected void init()
    {


        stringSparseArray = setXLabels();
        initChart();
        initData();
        //getOffLineData();
        getNetLineData();

        if (saleQuotesEntityList == null)
        {
            saleQuotesEntityList = new ArrayList<>();
        }

        saleAdapter = new CommonAdapter<QuotesEntity>(getActivity(),
                R.layout.quotes_item, saleQuotesEntityList)
        {
            @Override
            protected void convert(ViewHolder holder, QuotesEntity entity,
                    int position)
            {


                holder.setText(R.id.sale_num,
                        saleQuotesEntityList.get(position).showSellNum);
                holder.setText(R.id.tv_No, "卖" + (position + 1));
                holder.setText(R.id.tv_Price,
                        saleQuotesEntityList.get(position).showSellPrice);

                //progress_sale

                holder.setProgress(R.id.progress_sale,entity.sellNum==0?0:100*entity.sellNum/entity.sellCount);
            }
        };

        saleRecycleView.setLayoutManager(new LinearLayoutManager(getActivity())
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        });
        saleRecycleView.setAdapter(saleAdapter);


        if (buyQuotesEntityList == null)
        {
            buyQuotesEntityList = new ArrayList<>();
        }

        buyAdapter = new CommonAdapter<QuotesEntity>(getActivity(),
                R.layout.quotes_item, buyQuotesEntityList)
        {
            @Override
            protected void convert(ViewHolder holder, QuotesEntity entity,
                                   int position)
            {
                holder.setText(R.id.sale_num,
                        buyQuotesEntityList.get(position).buyNum + "");
                holder.setText(R.id.tv_No, "买" + (position + 1));
                holder.setText(R.id.tv_Price,
                        buyQuotesEntityList.get(position).buyPrice);
                holder.setProgress(R.id.progress_sale,entity.buyNum==0?0:100*entity.buyNum/entity.countNum);
            }
        };

        buyRecycleView.setLayoutManager(new LinearLayoutManager(getActivity())
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        });
        buyRecycleView.setAdapter(buyAdapter);

        initNetWork();
    }

    public void initNetWork()
    {

        RetrofitUtils.getInstance(getActivity()).api.getDisparity()
                .compose(RxSchedulers.<ResultData<QuotesEntity>> compose())
                .subscribe(new BaseObserver<QuotesEntity>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<QuotesEntity> resultData)
                    {
                        QuotesEntity quotesEntity=resultData.data;

                        if (resultData.result == 200)
                        {
                            tv_count.setText(quotesEntity.count);
                            tv_countPrice.setText(quotesEntity.countPrice);
                            tv_currentMin.setText(quotesEntity.currentMin);
                            tv_currentMix.setText(quotesEntity.currentMix);
                            tv_currentPrice
                                    .setText(quotesEntity.currentPrice);
                            tv_Disparity.setText(quotesEntity.disparity);
                            tv_disparityB.setText(quotesEntity.disparityB);
                            setTextColor(tv_currentMix,quotesEntity.currentMixColour);
                            setTextColor(tv_currentPrice,quotesEntity.currentPriceColour);
                            setTextColor(tv_currentMin,quotesEntity.currentMinColour);
                            setTextColor(tv_countPrice,quotesEntity.countPriceColour);
                            setTextColor(tv_count,quotesEntity.countColour);


                        }
                    }
                });

        RetrofitUtils.getInstance(getActivity()).api.transaction()
                .compose(RxSchedulers.<ResultData<QuotesData>> compose())
                .subscribe(new BaseObserver<QuotesData>(getActivity(), false)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<QuotesData> resultData)
                    {
                        if (resultData.result == 200)
                        {

                            saleQuotesEntityList.clear();
                            buyQuotesEntityList.clear();

                            saleQuotesEntityList.addAll(resultData.data.sell);
                            buyQuotesEntityList.addAll(resultData.data.buy);
                            sellTotalNum=0;
                            buyTotalNum=0;
                            for(QuotesEntity entity:saleQuotesEntityList){

                                sellTotalNum+=entity.sellNum;
                            }
                            for(QuotesEntity entity:buyQuotesEntityList){

                                buyTotalNum+=entity.buyNum;
                            }
                            tv_buyTotalNum.setText(buyTotalNum+"");
                            tv_sellTotalNum.setText(sellTotalNum+"");

                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    saleAdapter.notifyDataSetChanged();
                                    buyAdapter.notifyDataSetChanged();
                                }
                            });

                        }
                    }
                });

    }





    @OnClick(R.id.tv_buy)
    public void tvbuy()
    {

        /*
        QutoesDialogFragment qutoesDialogFragment = new QutoesDialogFragment();
        qutoesDialogFragment.show(getFragmentManager(), "qutoesDialogFragment");
        */

        MainActivity activity= (MainActivity) getActivity();
        activity.swTab(SWITCH_BUY);
    }

    @OnClick(R.id.tv_sell)
    public void tv_sell()
    {
        MainActivity activity= (MainActivity) getActivity();
        activity.swTab(SWITCH_SALE);
    }


    @OnClick(R.id.tv_undo)
    public void tv_undo()
    {
        MainActivity activity= (MainActivity) getActivity();
        activity.swTab(SWITCH_WITHDRAW);
    }

    /*
     * @OnClick(R.id.one) public void one() {
     *
     * String[] x = { "11", "22", "33", "44", "55" };
     * MyXAxisRenderer.setData(x); mLineChart.invalidate();
     *
     * }
     */

    // 设置chart基本属性
    private void initChart()
    {

        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//                barChart.setHighlightValue(new Highlight(h.getXIndex(), 0));


                // lineChart.setHighlightValue(h);
            }

            @Override
            public void onNothingSelected() {
            }
        });


    }

    // 设置数据
    private void initData()
    {

        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(getResources().getColor(R.color.minute_grayLine));
        lineChart.setDescription("");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);


        //x轴
        xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxisLine.setLabelsToSkip(59);


        //左边y
        axisLeftLine = lineChart.getAxisLeft();
        /*折线图y轴左没有basevalue，调用系统的*/
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /*轴不显示 避免和border冲突*/
        axisLeftLine.setDrawAxisLine(true);


        //右边y
        axisRightLine = lineChart.getAxisRight();
        axisRightLine.setLabelCount(2, true);
        axisRightLine.setDrawLabels(true);
        axisRightLine.setValueFormatter(new YAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, YAxis yAxis) {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });

        axisRightLine.setStartAtZero(false);
        axisRightLine.setDrawGridLines(false);
        axisRightLine.setDrawAxisLine(false);
        //背景线
        xAxisLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.enableGridDashedLine(10f,5f,0f);
        xAxisLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine.setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisRightLine.setAxisLineColor(getResources().getColor(R.color.minute_grayLine));
        axisRightLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));


        // xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);





    }



    private void setData(DataParse mData) {
        setMarkerView(mData);
        setShowLabels(stringSparseArray);
        Log.e("###", mData.getDatas().size() + "ee");
        if (mData.getDatas().size() == 0) {
            lineChart.setNoDataText("暂无数据");
            return;
        }
        //设置y左右两轴最大最小值
//        axisLeftLine.setAxisMinValue(mData.getMin());
//        axisLeftLine.setAxisMaxValue(mData.getMax());
//        axisRightLine.setAxisMinValue(mData.getPercentMin());
//        axisRightLine.setAxisMaxValue(mData.getPercentMax());

        axisLeftLine.setAxisMinValue(mData.min);
        axisLeftLine.setAxisMaxValue(mData.max);
        axisRightLine.setAxisMinValue(mData.getPercentMin());
        axisRightLine.setAxisMaxValue(mData.getPercentMax());

        /*单位*/
        String unit = MyUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit)) {
            u = 4;
        } else if ("亿手".equals(unit)) {
            u = 8;
        }
        /*次方*/

        //   axisRightBar.setAxisMinValue(mData.getVolmin);//即使最小是不是0，也无碍
        //axisRightBar.setShowOnlyMinMax(true);

        //基准线
        LimitLine ll = new LimitLine(0);
        ll.setLineWidth(1f);
        ll.setLineColor(getResources().getColor(R.color.minute_jizhun));
        ll.enableDashedLine(10f, 10f, 0f);
        ll.setLineWidth(1);
        axisRightLine.addLimitLine(ll);
        axisRightLine.setBaseValue(0);

        ArrayList<Entry> lineCJEntries = new ArrayList<>();
        ArrayList<Entry> lineJJEntries = new ArrayList<>();
        ArrayList<String> dateList = new ArrayList<>();
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<String> xVals = new ArrayList<>();
        Log.e("##", Integer.toString(xVals.size()));
        /*
        for(int i=0;i<60;i++){
            MinutesBean t=new MinutesBean();
            t.avprice= (float) 10.02;
            t.cjprice= (float) 10.29;
            t.per= (float) 0.03343;
            t.cjnum=1203;
            //t.time="10:"+(21+i);
            mData.getDatas().add(t);
        }
        */
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++) {
           /* //避免数据重复，skip也能正常显示
            if (mData.getDatas().get(i).time.equals("13:30")) {
                continue;
            }*/
            MinutesBean t = mData.getDatas().get(j);

            if (t == null) {
                lineCJEntries.add(new Entry(Float.NaN, i));
                lineJJEntries.add(new Entry(Float.NaN, i));
                barEntries.add(new BarEntry(Float.NaN, i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i)) &&
                    stringSparseArray.get(i).contains("/")) {
                i++;
            }
            lineCJEntries.add(new Entry(mData.getDatas().get(i).cjprice, i));
            lineJJEntries.add(new Entry(mData.getDatas().get(i).avprice, i));
            barEntries.add(new BarEntry(mData.getDatas().get(i).cjnum, i));
            // dateList.add(mData.getDatas().get(i).time);
        }
        d1 = new LineDataSet(lineCJEntries, "成交价");
        d2 = new LineDataSet(lineJJEntries, "均价");
        d1.setFillColor(Color.rgb(192, 202, 216));
        d1.setDrawValues(false);
        d2.setDrawValues(false);
        barDataSet = new BarDataSet(barEntries, "成交量");

        d1.setCircleRadius(0);
        d2.setCircleRadius(0);
        d1.setColor(getResources().getColor(R.color.minute_blue));
        d2.setColor(getResources().getColor(R.color.minute_yellow));
        d1.setHighLightColor(Color.WHITE);
        d2.setHighlightEnabled(false);
        d1.setDrawFilled(true);


        //谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
        // d2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        /*注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断*/
        LineData cd = new LineData(getMinutesCount(), sets);
        lineChart.setData(cd);


        setOffset();


        lineChart.invalidate();//刷新图



    }


    private void getOffLineData() {
        mData = new DataParse();
        JSONObject object = null;
        try {
            object = new JSONObject(ConstantTest.MINUTESURL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mData.parseMinutes(object);
        setData(mData);
    }

    private void getNetLineData() {


        RetrofitUtils.getInstance(getActivity()).api.hqView()
                .compose(RxSchedulers.<ResultData<HqViewBean>>compose())
                .subscribe(new BaseObserver<HqViewBean>(getActivity(),false) {
                    @Override
                    public void onHandlerSuccess(ResultData<HqViewBean> resultData) {

                        if(resultData.result==200){
                            mData = new DataParse();
                            HqViewBean hqViewBean=resultData.data;
                            mData.parseNetMinutes(hqViewBean);

                            setData(mData);
                        }

                    }
                });


    }

    private SparseArray<String> setXLabels() {
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, "12:00");
        xLabels.put(60, "13:00");
        xLabels.put(121, "14:00");
        xLabels.put(182, "15:00");
        xLabels.put(241, "16:00");

        return xLabels;
    }


    /*设置量表对齐*/
    private void setOffset() {

    }

    public void setShowLabels(SparseArray<String> labels) {
        xAxisLine.setXLabels(labels);
    }

    public String[] getMinutesCount() {
        return new String[242];
    }

    private void setMarkerView(DataParse mData) {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(), R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(), R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(getActivity(), R.layout.mymarkerview);
        lineChart.setMarker(leftMarkerView, rightMarkerView,bottomMarkerView, mData);

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            getNetLineData();
        }
    }
}
