package com.hb.currencymanage.ui.fragment;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.bean.HqViewBean;
import com.hb.currencymanage.bean.QuotesData;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.customview.CustomDialog;
import com.hb.currencymanage.customview.TriangleView;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.mpchart.DataParse;
import com.hb.currencymanage.mpchart.MinutesBean;
import com.hb.currencymanage.mpchart.MyBottomMarkerView;
import com.hb.currencymanage.mpchart.MyLeftMarkerView;
import com.hb.currencymanage.mpchart.MyLineChart;
import com.hb.currencymanage.mpchart.MyRightMarkerView;
import com.hb.currencymanage.mpchart.MyXAxis;
import com.hb.currencymanage.mpchart.MyYAxis;
import com.hb.currencymanage.myview.ProgressView;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.MyUtils;
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by 汪彬 on 2018/4/19.
 */

public class DealBusinessSaleFragment extends BaseFragment
{
    @BindView(R.id.tv_price_down)
    TextView mTvPriceDown;
    
    @BindView(R.id.et_price)
    EditText mEtPrice;
    
    @BindView(R.id.tv_price_up)
    TextView mTvPriceUp;
    
    @BindView(R.id.tv_num_down)
    TextView mTvNumDown;
    
    @BindView(R.id.et_num)
    EditText mEtNum;
    
    @BindView(R.id.tv_num_up)
    TextView mTvNumUp;
    
    @BindView(R.id.layout_enter_num)
    LinearLayout mLayoutEnterNum;
    
    @BindView(R.id.layout_assign)
    LinearLayout mLayoutAssign;
    
    @BindView(R.id.tv_cur_rate)
    TextView mTvCurRate;
    
    @BindView(R.id.tv_increase_rate)
    TextView mTvIncreaseRate;
    
    @BindView(R.id.tv_business_num)
    TextView mTvBusinessNum;
    
    @BindView(R.id.tv_business)
    TextView mTvBusiness;
    
    @BindView(R.id.tv_store)
    TextView mTvStore;
    
    @BindView(R.id.tv_refresh)
    TextView mTvRefresh;
    
    @BindView(R.id.saleRecycleView)
    RecyclerView mRvSale;
    
    @BindView(R.id.buyRecycleView)
    RecyclerView mRvBuy;
    
    @BindView(R.id.layout_ten_info)
    LinearLayout mLayoutTenInfo;
    
    @BindView(R.id.layout_chart)
    RelativeLayout mLayoutChart;
    
    @BindView(R.id.line_chart)
    MyLineChart lineChart;
    
    @BindView(R.id.tv_sale_total)
    TextView mTvSaleTotal;
    
    @BindView(R.id.tv_buy_total)
    TextView mTvBuyTotal;
    
    @BindView(R.id.tv_ten_info)
    TextView mTvTenInfo;
    
    @BindView(R.id.tv_minutes_info)
    TextView mTvMinutesInfo;
    
    @BindView(R.id.layout_enter_price)
    LinearLayout mLayoutEnterPrice;
    
    @BindView(R.id.tv_price_down_per)
    TextView mTvPriceDownPer;
    
    @BindView(R.id.tv_price_up_per)
    TextView mTvPriceUpPer;
    
    @BindView(R.id.tv_num_down_per)
    TextView mTvNumDownPer;
    
    @BindView(R.id.tv_num_up_per)
    TextView mTvNumUpPer;
    
    @BindView(R.id.tv_verify_code)
    TextView mTvVerifyCode;
    
    @BindView(R.id.layout_assign_info)
    LinearLayout mLayoutAssignInfo;
    
    @BindView(R.id.triangle_ten)
    TriangleView mTriangleTen;
    
    @BindView(R.id.triangle_hour)
    TriangleView mTriangleHour;
    
    @BindView(R.id.et_verify_code)
    EditText mEtVerifyCode;
    
    private MyXAxis xAxisLine;
    
    private MyYAxis axisRightLine;
    
    private MyYAxis axisLeftLine;
    
    private BarDataSet barDataSet;
    
    private LineDataSet d1, d2;
    
    private SparseArray<String> stringSparseArray;
    
    private DataParse mData;
    
    private List<QuotesEntity> mDataBuy = new ArrayList<>();
    
    private List<QuotesEntity> mDataSale = new ArrayList<>();
    
    private CommonAdapter<QuotesEntity> mBuyAdapter;
    
    private CommonAdapter<QuotesEntity> mSaleAdapter;
    
    private int mOwnCurrencyCount;
    
    private double mCash = 0.0;
    
    private String mUserId;
    
    private double mOwnCurrencyMoney = 0.0;
    
    private int mDownNumPer = 0;
    
    private int mUpNumPer = 0;
    
    private double mDownPricePer = 0f;
    
    private double mUpPricePer = 0f;
    
    private boolean mIsOpen = false;
    
    private MainActivity mainActivity;
    
    private boolean mIsVisibleToUser;
    
    private TimerTask autoRefreshTimerTask;
    
    private Timer autoRefreshTimer;
    
    public static DealBusinessSaleFragment getInstance()
    {
        DealBusinessSaleFragment f = new DealBusinessSaleFragment();
        return f;
    }
    
    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_deal_item_business;
    }
    
    @Override
    protected void init()
    {
        mainActivity = (MainActivity) getActivity();
        initVariable();
        initView();
        initChart();
        initData();
        // getOffLineData();
        getNetLineData();
    }
    
    private void initVariable()
    {
        mDownNumPer = Integer.valueOf(mTvNumDownPer.getText().toString());
        mUpNumPer = Integer.valueOf(mTvNumUpPer.getText().toString());
        mDownPricePer = Double.valueOf(mTvPriceDownPer.getText().toString());
        mUpPricePer = Double.valueOf(mTvPriceUpPer.getText().toString());
    }
    
    @OnClick(R.id.tv_ten_info)
    public void showTenInfo()
    {
        mLayoutChart.setVisibility(View.GONE);
        mLayoutTenInfo.setVisibility(View.VISIBLE);
        mTriangleTen.setVisibility(View.VISIBLE);
        mTriangleHour.setVisibility(View.INVISIBLE);
    }
    
    @OnClick(R.id.layout_price_down)
    public void downPrice()
    {
        try
        {
            BigDecimal price = new BigDecimal(mEtPrice.getText().toString());
            BigDecimal num = new BigDecimal("0.01");
            mEtPrice.setText(price.subtract(num) + "");
        }
        catch (NumberFormatException e)
        {
            mEtPrice.setText("");
        }
    }
    
    @OnClick(R.id.layout_price_up)
    public void upPrice()
    {
        try
        {
            BigDecimal price = new BigDecimal(mEtPrice.getText().toString());
            BigDecimal num = new BigDecimal("0.01");
            mEtPrice.setText(price.add(num) + "");
        }
        catch (NumberFormatException e)
        {
            mEtPrice.setText("");
        }
    }
    
    @OnClick(R.id.layout_num_down)
    public void downNum()
    {
        try
        {
            int num = Integer.parseInt(mEtNum.getText().toString());
            num = num - mDownNumPer;
            mEtNum.setText(num > 0 ? num + "" : 0 + "");
        }
        catch (NumberFormatException e)
        {
            mEtNum.setText("");
        }
    }
    
    @OnClick(R.id.layout_num_up)
    public void upNum()
    {
        try
        {
            String countStr = mEtNum.getText().toString();
            if (TextUtils.isEmpty(countStr))
            {
                countStr = "0";
            }
            int count = Integer.parseInt(countStr);
            if (count + mUpNumPer > mOwnCurrencyCount)
            {
                Toast.makeText(getContext(), "数量不可大于持有货币数量！", Toast.LENGTH_LONG)
                        .show();
                mEtNum.setText(String.valueOf(mOwnCurrencyCount));
            }
            else
            {
                mEtNum.setText(String.valueOf(count + mUpNumPer));
            }
        }
        catch (NumberFormatException e)
        {
            mEtNum.setText("");
        }
    }
    
    @OnClick(R.id.tv_store)
    public void store()
    {
        mEtNum.setText(String.valueOf(mOwnCurrencyCount));
    }
    
    @OnClick(R.id.tv_business)
    public void deal()
    {
        String price = mEtPrice.getText().toString();
        String num = mEtNum.getText().toString();
        if (TextUtils.isEmpty(price) || TextUtils.isEmpty(num))
        {
            Toast.makeText(getContext(), "请先输入价格和数量!", Toast.LENGTH_LONG)
                    .show();
            return;
        }
        String type = "卖出";
        String title = String.format(getContext().getResources()
                .getString(R.string.dialog_deal_title), type);
        String firstContent = type + "价格：" + price;
        String secondContent = type + "数量：" + num;
        new CustomDialog.Builder(getContext()).setTitle(title)
                .setContentFirstVisibility(true)
                .setContentSecondVisibility(true)
                .setContentFirst(firstContent)
                .setContentSecond(secondContent)
                .setConfirmListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialogConfirm();
                    }
                })
                .build()
                .show();
        
    }
    
    @OnTextChanged(R.id.et_num)
    public void countChange()
    {
        try
        {
            String countStr = mEtNum.getText().toString();
            if (TextUtils.isEmpty(countStr))
            {
                countStr = "0";
            }
            int count = (int) Math.floor(Double.valueOf(countStr));
            if (count > (int) Math.floor(Double.valueOf(mOwnCurrencyCount)))
            {
                Toast.makeText(getContext(), "数量不可大于持有货币数量！", Toast.LENGTH_LONG)
                        .show();
                mEtNum.setText(String.valueOf(mOwnCurrencyCount));
            }
        }
        catch (NumberFormatException e)
        {
            mEtNum.setText("");
        }
    }
    
    @OnClick(R.id.tv_minutes_info)
    public void showChartInfo()
    {
        // mTvTenInfo.setBackgroundColor(getResources().getColor(R.color.white));
        // mTvMinutesInfo.setBackgroundColor(
        // getResources().getColor(R.color.bg_color_gray));
        mLayoutTenInfo.setVisibility(View.GONE);
        mLayoutChart.setVisibility(View.VISIBLE);
        mTriangleTen.setVisibility(View.INVISIBLE);
        mTriangleHour.setVisibility(View.VISIBLE);
    }
    
    @OnClick(R.id.tv_refresh)
    public void refresh()
    {
        requestData();
    }
    
    @Override
    protected void requestData()
    {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                getAccountInfo();
            }
        }, 500);
    }
    
    private void dialogConfirm()
    {
        doDeal(mUserId,
                mEtNum.getText().toString(),
                mEtPrice.getText().toString());
    }
    
    private void doDeal(String userId, String num, String price)
    {
        RetrofitUtils.getInstance(getActivity()).api.sale(userId, num, price)
                // .sale("1", "1", "222")
                .compose(RxSchedulers.<ResultData<String>> compose())
                .subscribe(new BaseObserver<String>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(ResultData<String> resultData)
                    {
                        Logger.d("doDeal onHandlerSuccess" + resultData.data);
                        if (resultData.result == 200)
                        {
                            getAccountInfo();
                            
                        }
                        else
                        {
                            if (!TextUtils.isEmpty(resultData.message))
                            {
                                Toast.makeText(getContext(),
                                        resultData.message,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }
    
    private SparseArray<String> setXLabels()
    {
        SparseArray<String> xLabels = new SparseArray<>();
        xLabels.put(0, "09:30");
        xLabels.put(60, "10:30");
        xLabels.put(121, "11:30");
        xLabels.put(182, "12:30");
        xLabels.put(241, "13:30");
        return xLabels;
    }
    
    public void getTenInfo(final boolean isNeedReqOther)
    {
        RetrofitUtils.getInstance(getActivity()).api.transaction()
                .compose(RxSchedulers.<ResultData<QuotesData>> compose())
                .subscribe(new BaseObserver<QuotesData>(getActivity(), false)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<QuotesData> resultData)
                    {
                        Log.e("<<<sale ==== ", "sale , getTenInfo");
                        if (resultData.result == 200)
                        {
                            mDataBuy.clear();
                            mDataSale.clear();
                            mDataBuy.addAll(resultData.data.buy);
                            mDataSale.addAll(resultData.data.sell);
                            int buyTotal = 0;
                            int sellTotal = 0;
                            for (QuotesEntity bean : resultData.data.buy)
                            {
                                buyTotal += bean.buyNum;
                            }
                            for (QuotesEntity bean : resultData.data.sell)
                            {
                                sellTotal += bean.sellNum;
                            }
                            mTvBuyTotal.setText(String.valueOf(buyTotal));
                            mTvSaleTotal.setText(String.valueOf(sellTotal));
                            mBuyAdapter.notifyDataSetChanged();
                            mSaleAdapter.notifyDataSetChanged();
                            if (isNeedReqOther)
                            {
                                getDisparity();
                            }
                        }
                    }
                });
    }
    
    private void getAccountInfo()
    {
        AccountDB db = new AccountDB(getContext());
        RetrofitUtils.getInstance(getActivity()).api
                .getAccountInfo(db.getUserId())
                .compose(RxSchedulers.<ResultData<AccountBean>> compose())
                .subscribe(new BaseObserver<AccountBean>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<AccountBean> resultData)
                    {
                        if (resultData.result == 200)
                        {
                            try
                            {
                                
                                mUserId = resultData.data.getUserid();
                                mCash = Double
                                        .valueOf(resultData.data.getKyCash());
                                Logger.d("cash = " + mCash);
                                mOwnCurrencyCount = (int) Math
                                        .floor(Double.valueOf(resultData.data
                                                .getKyCurrency()));
                                mOwnCurrencyMoney = Double.valueOf(
                                        resultData.data.getCurrencyMoney());
                                mTvBusinessNum
                                        .setText("可卖：" + mOwnCurrencyCount);
                                
                                getTenInfo(true);
                                
                            }
                            catch (NumberFormatException e)
                            {
                                Logger.e(e, "NumberFormatException");
                            }
                        }
                        else
                        {
                            mCash = 0.0;
                            mOwnCurrencyCount = 0;
                            mOwnCurrencyMoney = 0.0;
                        }
                    }
                });
    }
    
    private void getDisparity()
    {
        Logger.d("getDisparity");
        RetrofitUtils.getInstance(getActivity()).api.getDisparity()
                .compose(RxSchedulers.<ResultData<QuotesEntity>> compose())
                .subscribe(new BaseObserver<QuotesEntity>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<QuotesEntity> resultData)
                    {
                        Logger.d("getDisparity onHandlerSuccess"
                                + resultData.data);
                        if (resultData.result == 200)
                        {
                            if (null != resultData.data)
                            {
                                mTvCurRate
                                        .setText(resultData.data.currentPrice);
                                setTextColor(mTvCurRate,
                                        resultData.data.currentPriceColour);
                                mTvIncreaseRate
                                        .setText(resultData.data.disparityB);
                                setTextColor(mTvIncreaseRate,
                                        resultData.data.currentPriceColour);
                            }
                        }
                    }
                });
    }
    
    // 设置chart基本属性
    private void initChart()
    {
        stringSparseArray = setXLabels();
        lineChart.setOnChartValueSelectedListener(
                new OnChartValueSelectedListener()
                {
                    @Override
                    public void onValueSelected(Entry e, int dataSetIndex,
                            Highlight h)
                    {
                        // barChart.setHighlightValue(new
                        // Highlight(h.getXIndex(), 0));
                        
                        // lineChart.setHighlightValue(h);
                    }
                    
                    @Override
                    public void onNothingSelected()
                    {
                    }
                });
        
    }
    
    // 设置数据
    private void initData()
    {
        
        lineChart.setScaleEnabled(false);
        lineChart.setDrawBorders(true);
        lineChart.setBorderWidth(1);
        lineChart.setBorderColor(
                getResources().getColor(R.color.minute_grayLine));
        lineChart.setDescription("");
        Legend lineChartLegend = lineChart.getLegend();
        lineChartLegend.setEnabled(false);
        
        // x轴
        xAxisLine = lineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxisLine.setLabelsToSkip(59);
        
        // 左边y
        axisLeftLine = lineChart.getAxisLeft();
        /* 折线图y轴左没有basevalue，调用系统的 */
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /* 轴不显示 避免和border冲突 */
        axisLeftLine.setDrawAxisLine(true);
        
        // 右边y
        axisRightLine = lineChart.getAxisRight();
        axisRightLine.setLabelCount(2, true);
        axisRightLine.setDrawLabels(true);
        axisRightLine.setValueFormatter(new YAxisValueFormatter()
        {
            @Override
            public String getFormattedValue(float value, YAxis yAxis)
            {
                DecimalFormat mFormat = new DecimalFormat("#0.00%");
                return mFormat.format(value);
            }
        });
        
        axisRightLine.setStartAtZero(false);
        axisRightLine.setDrawGridLines(false);
        axisRightLine.setDrawAxisLine(false);
        // 背景线
        xAxisLine
                .setGridColor(getResources().getColor(R.color.minute_grayLine));
        xAxisLine.enableGridDashedLine(10f, 5f, 0f);
        xAxisLine.setAxisLineColor(
                getResources().getColor(R.color.minute_grayLine));
        xAxisLine.setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisLeftLine
                .setGridColor(getResources().getColor(R.color.minute_grayLine));
        axisLeftLine
                .setTextColor(getResources().getColor(R.color.minute_zhoutv));
        axisRightLine.setAxisLineColor(
                getResources().getColor(R.color.minute_grayLine));
        axisRightLine
                .setTextColor(getResources().getColor(R.color.minute_zhoutv));
        
        // xAxisBar.setPosition(XAxis.XAxisPosition.BOTTOM);
        
    }
    
    public void setShowLabels(SparseArray<String> labels)
    {
        xAxisLine.setXLabels(labels);
    }
    
    public String[] getMinutesCount()
    {
        return new String[242];
    }
    
    private void setMarkerView(DataParse mData)
    {
        MyLeftMarkerView leftMarkerView = new MyLeftMarkerView(getActivity(),
                R.layout.mymarkerview);
        MyRightMarkerView rightMarkerView = new MyRightMarkerView(getActivity(),
                R.layout.mymarkerview);
        MyBottomMarkerView bottomMarkerView = new MyBottomMarkerView(
                getActivity(), R.layout.mymarkerview);
        lineChart.setMarker(leftMarkerView,
                rightMarkerView,
                bottomMarkerView,
                mData);
        
    }
    
    private void setData(DataParse mData)
    {
        setMarkerView(mData);
        setShowLabels(stringSparseArray);
        Log.e("###", mData.getDatas().size() + "ee");
        if (mData.getDatas().size() == 0)
        {
            lineChart.setNoDataText("暂无数据");
            return;
        }
        // 设置y左右两轴最大最小值
        // axisLeftLine.setAxisMinValue(mData.getMin());
        // axisLeftLine.setAxisMaxValue(mData.getMax());
        // axisRightLine.setAxisMinValue(mData.getPercentMin());
        // axisRightLine.setAxisMaxValue(mData.getPercentMax());
        
        axisLeftLine.setAxisMinValue(mData.min);
        axisLeftLine.setAxisMaxValue(mData.max);
        axisRightLine.setAxisMinValue(mData.getPercentMin());
        axisRightLine.setAxisMaxValue(mData.getPercentMax());
        
        /* 单位 */
        String unit = MyUtils.getVolUnit(mData.getVolmax());
        int u = 1;
        if ("万手".equals(unit))
        {
            u = 4;
        }
        else if ("亿手".equals(unit))
        {
            u = 8;
        }
        /* 次方 */
        
        // axisRightBar.setAxisMinValue(mData.getVolmin);//即使最小是不是0，也无碍
        // axisRightBar.setShowOnlyMinMax(true);
        
        // 基准线
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
         * for(int i=0;i<60;i++){ MinutesBean t=new MinutesBean(); t.avprice=
         * (float) 10.02; t.cjprice= (float) 10.29; t.per= (float) 0.03343;
         * t.cjnum=1203; //t.time="10:"+(21+i); mData.getDatas().add(t); }
         */
        for (int i = 0, j = 0; i < mData.getDatas().size(); i++, j++)
        {
            /*
             * //避免数据重复，skip也能正常显示 if
             * (mData.getDatas().get(i).time.equals("13:30")) { continue; }
             */
            MinutesBean t = mData.getDatas().get(j);
            
            if (t == null)
            {
                lineCJEntries.add(new Entry(Float.NaN, i));
                lineJJEntries.add(new Entry(Float.NaN, i));
                barEntries.add(new BarEntry(Float.NaN, i));
                continue;
            }
            if (!TextUtils.isEmpty(stringSparseArray.get(i))
                    && stringSparseArray.get(i).contains("/"))
            {
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
        
        // 谁为基准
        d1.setAxisDependency(YAxis.AxisDependency.LEFT);
        // d2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        sets.add(d2);
        /* 注老版本LineData参数可以为空，最新版本会报错，修改进入ChartData加入if判断 */
        LineData cd = new LineData(getMinutesCount(), sets);
        lineChart.setData(cd);
        
        setOffset();
        
        lineChart.invalidate();// 刷新图
        
    }
    
    /* 设置量表对齐 */
    private void setOffset()
    {
        
    }
    
    private void getNetLineData()
    {
        
        RetrofitUtils.getInstance(getActivity()).api.hqView()
                .compose(RxSchedulers.<ResultData<HqViewBean>> compose())
                .subscribe(new BaseObserver<HqViewBean>(getActivity(), false)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<HqViewBean> resultData)
                    {
                        
                        if (resultData.result == 200)
                        {
                            mData = new DataParse();
                            HqViewBean hqViewBean = resultData.data;
                            mData.parseNetMinutes(hqViewBean);
                            setData(mData);
                        }
                        
                    }
                });
        
    }
    
    private void initView()
    {
        mEtPrice.setHint("卖出价格");
        mEtNum.setHint("卖出数量");
        mTvBusiness.setText("卖出");
        setBgColor(mTvBusiness, "#2C5FA0");
        mTvStore.setText("清仓");
        mLayoutEnterPrice.setVisibility(View.VISIBLE);
        mLayoutAssign.setVisibility(View.GONE);
        mRvBuy.setLayoutManager(new LinearLayoutManager(getContext())
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        });
        mRvSale.setLayoutManager(new LinearLayoutManager(getContext())
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        });
        mBuyAdapter = new CommonAdapter<QuotesEntity>(getContext(),
                R.layout.quotes_item, mDataBuy)
        {
            @Override
            protected void convert(ViewHolder holder, QuotesEntity entity,
                    int position)
            {
                holder.setText(R.id.sale_num,
                        mDataBuy.get(position).showBuyNum);
                holder.setText(R.id.tv_No, "买" + (position + 1));
                holder.setText(R.id.tv_Price,
                        mDataBuy.get(position).showBuyPrice);
                // holder.setProgress(R.id.progress_sale,
                // entity.buyNum == 0 ? 0
                // : 100 * entity.buyNum / entity.countNum);
                ProgressView pg = holder.getView(R.id.progress);
                pg.setColorAndProgress(entity.showColor,
                        entity.buyNum == 0 ? 0
                                : 100 * entity.buyNum / entity.countNum);
                holder.setTextColor(R.id.tv_Price,
                        Color.parseColor(entity.showColor));
            }
        };
        mSaleAdapter = new CommonAdapter<QuotesEntity>(getContext(),
                R.layout.quotes_item, mDataSale)
        {
            @Override
            protected void convert(ViewHolder holder, QuotesEntity entity,
                    int position)
            {
                holder.setText(R.id.sale_num,
                        mDataSale.get(position).sellNum + "");
                holder.setText(R.id.tv_No, "卖" + (position + 1));
                holder.setText(R.id.tv_Price,
                        mDataSale.get(position).showSellPrice);
                // holder.setProgress(R.id.progress_sale,
                // entity.sellNum == 0 ? 0
                // : 100 * entity.sellNum / entity.sellCount);
                ProgressView pg = holder.getView(R.id.progress);
                pg.setColorAndProgress(entity.showColor,
                        entity.sellNum == 0 ? 0
                                : 100 * entity.sellNum / entity.sellCount);
                holder.setTextColor(R.id.tv_Price,
                        Color.parseColor(entity.showColor));
            }
        };
        mRvBuy.setAdapter(mBuyAdapter);
        mRvSale.setAdapter(mSaleAdapter);
        /*
         * if (mType == TYPE_BUY) { mSaleAdapter.setOnItemClickListener( new
         * CommonAdapter.OnItemClickListener() {
         * 
         * @Override public void onItemClick(View view, RecyclerView.ViewHolder
         * holder, int position) {
         * mEtPrice.setText(mDataSale.get(position).sellPrice);
         * mEtNum.setText(mDataSale.get(position).sellNum+""); }
         * 
         * @Override public boolean onItemLongClick(View view,
         * RecyclerView.ViewHolder holder, int position) { return false; } }); }
         * else if (mType == TYPE_SALE) { mBuyAdapter.setOnItemClickListener(
         * new CommonAdapter.OnItemClickListener() {
         * 
         * @Override public void onItemClick(View view, RecyclerView.ViewHolder
         * holder, int position) {
         * mEtPrice.setText(mDataBuy.get(position).buyPrice);
         * mEtNum.setText(mDataBuy.get(position).buyNum+""); }
         * 
         * @Override public boolean onItemLongClick(View view,
         * RecyclerView.ViewHolder holder, int position) { return false; } }); }
         */
        
        mSaleAdapter
                .setOnItemClickListener(new CommonAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view,
                            RecyclerView.ViewHolder holder, int position)
                    {
                        mEtPrice.setText(mDataSale.get(position).sellPrice);
                        mEtNum.setText((mDataSale
                                .get(position).sellNum > mOwnCurrencyCount
                                        ? mOwnCurrencyCount
                                        : mDataSale.get(position).sellNum)
                                + "");
                    }
                    
                    @Override
                    public boolean onItemLongClick(View view,
                            RecyclerView.ViewHolder holder, int position)
                    {
                        return false;
                    }
                });
        
        mBuyAdapter
                .setOnItemClickListener(new CommonAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view,
                            RecyclerView.ViewHolder holder, int position)
                    {
                        mEtPrice.setText(mDataBuy.get(position).buyPrice);
                        mEtNum.setText(mDataBuy.get(position).buyNum + "");
                        mEtNum.setText((mDataBuy
                                .get(position).buyNum > mOwnCurrencyCount
                                        ? mOwnCurrencyCount
                                        : mDataBuy.get(position).buyNum)
                                + "");
                    }
                    
                    @Override
                    public boolean onItemLongClick(View view,
                            RecyclerView.ViewHolder holder, int position)
                    {
                        return false;
                    }
                });
    }
    
    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(300);
                    mIsVisibleToUser = isVisibleToUser;
                    Log.i("3秒刷新vis   ", "" + mIsVisibleToUser);
                    startTimer();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    
    private void startTimer()
    {
        if (null == autoRefreshTimer)
        {
            autoRefreshTimer = new Timer();
        }
        if (null == autoRefreshTimerTask)
        {
            autoRefreshTimerTask = new TimerTask()
            {
                @Override
                public void run()
                {
                    int currentItem = mainActivity.mViewPager.getCurrentItem();
                    if (mIsVisibleToUser && currentItem == 2)
                    {
                        // requestData();
                        getAccountInfo();
                    }
                    else
                    {
                    }
                }
            };
            
            if (null != autoRefreshTimer && null != autoRefreshTimerTask)
            {
                autoRefreshTimer.schedule(autoRefreshTimerTask, 1000, 3000);
            }
        }
    }
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        stopTimer();
    }
    
    private void stopTimer()
    {
        if (null != autoRefreshTimer)
        {
            autoRefreshTimer.cancel();
            autoRefreshTimer.purge();
            autoRefreshTimer = null;
            autoRefreshTimerTask.cancel();
            autoRefreshTimerTask = null;
        }
    }
    
}
