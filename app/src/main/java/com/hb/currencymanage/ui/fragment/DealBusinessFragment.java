package com.hb.currencymanage.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.bean.QuotesData;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
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
import com.orhanobut.logger.Logger;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Created by 汪彬 on 2018/4/19.
 */

public class DealBusinessFragment extends BaseFragment
{
    public static final int TYPE_BUY = 1;
    
    public static final int TYPE_SALE = 2;
    
    public static final int TYPE_ASSIGN = 3;
    
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
    
    @BindView(R.id.et_assign_id)
    EditText mEtAssignId;
    
    @BindView(R.id.et_assign_phone)
    EditText mEtAssignPhone;
    
    @BindView(R.id.et_assign_name)
    EditText mEtAssignName;
    
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
    
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    
    @BindView(R.id.layout_ten_info)
    LinearLayout mLayoutTenInfo;
    
    @BindView(R.id.layout_chart)
    RelativeLayout mLayoutChart;
    
    @BindView(R.id.line_chart)
    MyLineChart mLineChart;
    
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
    
    Unbinder unbinder;
    
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
    
    private int mType = 0;
    
    private String mOwnCurrencyCount;
    
    private String mCash;
    
    private String mCode;
    
    private String mOwnCurrencyMoney;
    
    private int mDownNumPer = 0;
    
    private int mUpNumPer = 0;
    
    private float mDownPricePer = 0f;
    
    private float mUpPricePer = 0f;
    
    public static DealBusinessFragment getInstance(int type)
    {
        DealBusinessFragment f = new DealBusinessFragment();
        f.mType = type;
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
        initVariable();
        initView();
        initChart();
        getOffLineData();
    }
    
    private void initVariable()
    {
        mDownNumPer = Integer.parseInt(mTvNumDownPer.getText().toString());
        mUpNumPer = Integer.parseInt(mTvNumUpPer.getText().toString());
        mDownPricePer = Float.parseFloat(mTvPriceDownPer.getText().toString());
        mUpPricePer = Float.parseFloat(mTvPriceUpPer.getText().toString());
    }
    
    @OnClick(R.id.tv_ten_info)
    public void showTenInfo()
    {
        mTvTenInfo.setBackgroundColor(
                getResources().getColor(R.color.bg_color_gray));
        mTvMinutesInfo
                .setBackgroundColor(getResources().getColor(R.color.white));
        mLayoutChart.setVisibility(View.GONE);
        mLayoutTenInfo.setVisibility(View.VISIBLE);
    }
    
    @OnClick(R.id.layout_price_down)
    public void downPrice()
    {
        float price = Float.parseFloat(mEtPrice.getText().toString());
        price = price - mDownPricePer;
        mEtPrice.setText(price > 0 ? price + "" : 0 + "");
    }
    
    @OnClick(R.id.layout_price_up)
    public void upPrice()
    {
        float price = Float.parseFloat(mEtPrice.getText().toString());
        price = price + mUpPricePer;
        mEtPrice.setText(price + "");
    }
    
    @OnClick(R.id.layout_num_down)
    public void downNum()
    {
        String priceStr = mEtPrice.getText().toString();
        if (TextUtils.isEmpty(priceStr))
        {
            Toast.makeText(getContext(), "请先输入价格！", Toast.LENGTH_LONG).show();
            return;
        }
        int num = Integer.parseInt(mEtNum.getText().toString());
        num = num - mDownNumPer;
        mEtNum.setText(num > 0 ? num + "" : 0 + "");
    }
    
    @OnClick(R.id.layout_num_up)
    public void upNum()
    {
        String priceStr = mEtPrice.getText().toString();
        if (TextUtils.isEmpty(priceStr))
        {
            Toast.makeText(getContext(), "请先输入价格！", Toast.LENGTH_LONG).show();
            return;
        }
        float price = Float.parseFloat(priceStr);
        int max = (int) Math.floor(Float.parseFloat(mCash) / price);
        int num = Integer.parseInt(mEtNum.getText().toString());
        num = num + mUpNumPer;
        mEtNum.setText(num > max ? max + "" : num + "");
    }
    
    @OnTextChanged(R.id.et_price)
    public void priceChange()
    {
        String priceStr = mEtPrice.getText().toString();
        if (!TextUtils.isEmpty(priceStr))
        {
            float price = Float.parseFloat(priceStr);
            int max = (int) Math.floor(Float.parseFloat(mCash) / price);
            mTvBusinessNum.setText(max + "");
            String curNumStr = mEtNum.getText().toString();
            if (!TextUtils.isEmpty(curNumStr))
            {
                int curNum = Integer.parseInt(curNumStr);
                mEtNum.setText(curNum > max ? max + "" : curNum + "");
            }
        }
    }
    
    @OnClick(R.id.tv_business)
    public void deal()
    {
        if (mType == TYPE_BUY)
        {
            doDeal(mCode,
                    "",
                    mEtNum.getText().toString(),
                    mEtPrice.getText().toString());
        }
        else if (mType == TYPE_SALE)
        {
            doDeal("",
                    mCode,
                    mEtNum.getText().toString(),
                    mEtPrice.getText().toString());
        }
        else if (mType == TYPE_ASSIGN)
        {
            
        }
    }
    
    private void doDeal(String buyUserId, String sellUserId, String num,
            String price)
    {
        RetrofitUtils.getInstance(getActivity()).api
                .deal(buyUserId, sellUserId, num, price)
                .compose(RxSchedulers.<ResultData<String>> compose())
                .subscribe(new BaseObserver<String>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(ResultData<String> resultData)
                    {
                        Logger.d("doDeal onHandlerSuccess" + resultData.data);
                        if (resultData.code == 200)
                        {
                        }
                    }
                });
    }
    
    private void assign(String toUserId, String forUserCode,
            String forUserPhone, String forUserName, String num)
    {
        RetrofitUtils.getInstance(getActivity()).api
                .assign(toUserId, forUserCode, forUserPhone, forUserName, num)
                .compose(RxSchedulers.<ResultData<String>> compose())
                .subscribe(new BaseObserver<String>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(ResultData<String> resultData)
                    {
                        Logger.d("getDisparity onHandlerSuccess"
                                + resultData.data);
                        if (resultData.code == 200)
                        {
                        }
                    }
                });
    }
    
    @OnTextChanged(R.id.et_num)
    public void countChange()
    {
        String price = mEtPrice.getText().toString();
        if (TextUtils.isEmpty(price))
        {
            Toast.makeText(getContext(), "请先输入价格！", Toast.LENGTH_LONG).show();
            mEtNum.setText("");
        }
        else
        {
            int max = (int) Math
                    .floor(Float.parseFloat(mCash) / Float.parseFloat(price));
            String countStr = mEtNum.getText().toString();
            if (TextUtils.isEmpty(countStr))
            {
                countStr = "0";
            }
            int count = Integer.parseInt(countStr);
            if (count > max)
            {
                Toast.makeText(getContext(), "数量不可大于可买数量！", Toast.LENGTH_LONG)
                        .show();
                mEtNum.setText(max + "");
                return;
            }
        }
    }
    
    @OnClick(R.id.tv_minutes_info)
    public void showChartInfo()
    {
        mTvTenInfo.setBackgroundColor(getResources().getColor(R.color.white));
        mTvMinutesInfo.setBackgroundColor(
                getResources().getColor(R.color.bg_color_gray));
        mLayoutTenInfo.setVisibility(View.GONE);
        mLayoutChart.setVisibility(View.VISIBLE);
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
        }, 600);
        // getDisparity();
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
    
    private void getTenInfo()
    {
        RetrofitUtils.getInstance(getActivity()).api.transaction()
                .compose(RxSchedulers.<ResultData<QuotesData>> compose())
                .subscribe(new BaseObserver<QuotesData>(getActivity(), false)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<QuotesData> resultData)
                    {
                        if (resultData.code == 200)
                        {
                            mDataBuy.clear();
                            mDataSale.clear();
                            mDataBuy.addAll(resultData.data.buy);
                            mDataSale.addAll(resultData.data.sell);
                            getActivity().runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    mBuyAdapter.notifyDataSetChanged();
                                    mSaleAdapter.notifyDataSetChanged();
                                }
                            });
                            getDisparity();
                        }
                    }
                });
    }
    
    private void getAccountInfo()
    {
        RetrofitUtils.getInstance(getActivity()).api.getAccountInfo("1")
                .compose(RxSchedulers.<ResultData<AccountBean>> compose())
                .subscribe(new BaseObserver<AccountBean>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<AccountBean> resultData)
                    {
                        if (resultData.result.equals("200"))
                        {
                            mCode = resultData.data.getCode();
                            mCash = resultData.data.getCash();
                            mOwnCurrencyCount = resultData.data.getCurrency();
                            mOwnCurrencyMoney = resultData.data
                                    .getCurrencyMoney();
                            mTvBusinessNum.setText(mType == TYPE_BUY ? "可买:"
                                    : mType == TYPE_SALE
                                            ? "可卖:" + mOwnCurrencyCount
                                            : "持仓:" + mOwnCurrencyMoney);
                            
                            getTenInfo();
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
                        if (resultData.code == 200)
                        {
                            if (null != resultData.data)
                            {
                                mTvCurRate
                                        .setText(resultData.data.currentPrice);
                                mTvIncreaseRate
                                        .setText(resultData.data.disparityB);
                            }
                        }
                    }
                });
    }
    
    private void initChart()
    {
        stringSparseArray = setXLabels();
        mLineChart.setScaleEnabled(false);
        mLineChart.setDrawBorders(true);
        mLineChart.setBorderWidth(1);
        mLineChart.setBorderColor(
                getResources().getColor(R.color.minute_grayLine));
        mLineChart.setDescription("");
        Legend lineChartLegend = mLineChart.getLegend();
        lineChartLegend.setEnabled(false);
        
        // x轴
        xAxisLine = mLineChart.getXAxis();
        xAxisLine.setDrawLabels(true);
        xAxisLine.setPosition(XAxis.XAxisPosition.BOTTOM);
        // xAxisLine.setLabelsToSkip(59);
        
        // 左边y
        axisLeftLine = mLineChart.getAxisLeft();
        /* 折线图y轴左没有basevalue，调用系统的 */
        axisLeftLine.setLabelCount(5, true);
        axisLeftLine.setDrawLabels(true);
        axisLeftLine.setDrawGridLines(false);
        /* 轴不显示 避免和border冲突 */
        axisLeftLine.setDrawAxisLine(false);
        
        // 右边y
        axisRightLine = mLineChart.getAxisRight();
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
    }
    
    private void getOffLineData()
    {
        mData = new DataParse();
        JSONObject object = null;
        try
        {
            object = new JSONObject(ConstantTest.MINUTESURL);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        mData.parseMinutes(object);
        setChartData(mData);
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
        mLineChart.setMarker(leftMarkerView,
                rightMarkerView,
                bottomMarkerView,
                mData);
    }
    
    private void setChartData(DataParse mData)
    {
        setMarkerView(mData);
        setShowLabels(stringSparseArray);
        Log.e("###", mData.getDatas().size() + "ee");
        if (mData.getDatas().size() == 0)
        {
            mLineChart.setNoDataText("暂无数据");
            return;
        }
        // 设置y左右两轴最大最小值
        axisLeftLine.setAxisMinValue(mData.getMin());
        axisLeftLine.setAxisMaxValue(mData.getMax());
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
        for (int i = 0; i < 38; i++)
        {
            MinutesBean t = new MinutesBean();
            t.avprice = (float) 10.02;
            t.cjprice = (float) 10.29;
            t.per = (float) 0.03343;
            t.cjnum = 1203;
            t.time = "10:" + (21 + i);
            mData.getDatas().add(t);
        }
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
        mLineChart.setData(cd);
        setOffset();
        mLineChart.invalidate();// 刷新图
    }
    
    /**
     * 设置量表对齐
     */
    private void setOffset()
    {
        
    }
    
    private void initView()
    {
        mTvBusiness.setText(
                mType == TYPE_BUY ? "买入" : mType == TYPE_SALE ? "卖出" : "转让");
        mTvStore.setText(mType == TYPE_BUY ? "全仓" : "清仓");
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
                        mDataBuy.get(position).buyNum + "");
                holder.setText(R.id.tv_No, "买" + (position + 1));
                holder.setText(R.id.tv_Price, mDataBuy.get(position).buyPrice);
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
                        mDataSale.get(position).sellPrice);
            }
        };
        mRvBuy.setAdapter(mBuyAdapter);
        mRvSale.setAdapter(mSaleAdapter);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater,
                container,
                savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
