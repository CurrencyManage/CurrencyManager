package com.hb.currencymanage.ui.fragment;

import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.dialog.QutoesDialogFragment;
import com.hb.currencymanage.mpchart.MyLineChart;
import com.hb.currencymanage.mpchart.MyXAxisRenderer;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class QuotesFragment extends BaseFragment
{
    
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    
    @BindView(R.id.lineChart)
    MyLineChart mLineChart;
    
    private ArrayList<Entry> pointValues;
    
    private List<QuotesEntity> quotesEntityList;
    
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
        initChart();
        initData();
        
        if (quotesEntityList == null)
        {
            quotesEntityList = new ArrayList<>();
        }
        
        for (int i = 0; i < 10; i++)
        {
            
            quotesEntityList.add(new QuotesEntity());
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())
        {
            @Override
            public boolean canScrollVertically()
            {
                return false;
            }
        });
        recyclerView.setAdapter(new CommonAdapter(getActivity(),
                R.layout.quotes_item, quotesEntityList)
        {
            @Override
            protected void convert(ViewHolder holder, Object o, int position)
            {
                
                holder.setText(R.id.sale_num, "12");
            }
            
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder,
                    int position)
            {
                
            }
        });
    }
    
    @OnClick(R.id.tv_buy)
    public void tvbuy()
    {
        QutoesDialogFragment qutoesDialogFragment = new QutoesDialogFragment();
        qutoesDialogFragment.show(getFragmentManager(), "qutoesDialogFragment");
        
    }
    
    @OnClick(R.id.one)
    public void one()
    {
        
        String[] x = { "11", "22", "33", "44", "55" };
        MyXAxisRenderer.setData(x);
        mLineChart.invalidate();
        
    }
    
    // 设置chart基本属性
    private void initChart()
    {
        // 描述信息
        Description description = new Description();
        description.setText("");
        // 设置描述信息
        mLineChart.setDescription(description);
        // 设置没有数据时显示的文本
        mLineChart.setNoDataText("没有数据喔~~");
        // 设置是否绘制chart边框的线
        mLineChart.setDrawBorders(true);
        // 设置chart边框线颜色
        mLineChart.setBorderColor(Color.GRAY);
        // 设置chart边框线宽度
        mLineChart.setBorderWidth(1f);
        // 设置chart是否可以触摸
        mLineChart.setTouchEnabled(true);
        // 设置是否可以拖拽
        mLineChart.setDragEnabled(true);
        // 设置是否可以缩放 x和y，默认true
        mLineChart.setScaleEnabled(false);
        // 设置是否可以通过双击屏幕放大图表。默认是true
        mLineChart.setDoubleTapToZoomEnabled(false);
        // 设置chart动画
        mLineChart.animateXY(1000, 1000);
        
        // =========================设置图例=========================
        // 像"□ xxx"就是图例
        Legend legend = mLineChart.getLegend();
        // 设置图例显示在chart那个位置 setPosition建议放弃使用了
        // 设置垂直方向上还是下或中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        // 设置水平方向是左边还是右边或中
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        // 设置所有图例位置排序方向
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        // 设置图例的形状 有圆形、正方形、线
        legend.setForm(Legend.LegendForm.CIRCLE);
        // 是否支持自动换行 目前只支持BelowChartLeft, BelowChartRight, BelowChartCenter
        legend.setWordWrapEnabled(true);
        
        // =======================设置X轴显示效果==================
        XAxis xAxis = mLineChart.getXAxis();
        // 是否启用X轴
        xAxis.setEnabled(true);
        // 是否绘制X轴线
        xAxis.setDrawAxisLine(true);
        // 设置X轴上每个竖线是否显示
        xAxis.setDrawGridLines(true);
        // 设置是否绘制X轴上的对应值(标签)
        xAxis.setDrawLabels(true);
        // 设置X轴显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 设置竖线为虚线样式
        // xAxis.enableGridDashedLine(10f, 10f, 0f);
        // 设置x轴标签数
        xAxis.setLabelCount(5, true);
        // 图表第一个和最后一个label数据不超出左边和右边的Y轴
        // xAxis.setAvoidFirstLastClipping(true);
        
        /*
         * //设置限制线 12代表某个该轴某个值，也就是要画到该轴某个值上 LimitLine limitLine = new
         * LimitLine(12); //设置限制线的宽 limitLine.setLineWidth(1f); //设置限制线的颜色
         * limitLine.setLineColor(Color.RED); //设置基线的位置
         * limitLine.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
         * limitLine.setLabel("马丹我是基线，也可以叫我水位线"); //设置限制线为虚线
         * limitLine.enableDashedLine(10f, 10f, 0f); //左边Y轴添加限制线
         * xAxis.addLimitLine(limitLine);
         */
        
        // =================设置左边Y轴===============
        YAxis axisLeft = mLineChart.getAxisLeft();
        // 是否启用左边Y轴
        axisLeft.setEnabled(true);
        // 设置最小值（这里就按demo里固死的写）
        axisLeft.setAxisMinimum(0);
        // 设置最大值（这里就按demo里固死的写了）
        axisLeft.setAxisMaximum(20);
        axisLeft.setGranularity(10f);
        // 设置横向的线为虚线
        axisLeft.enableGridDashedLine(10f, 10f, 0f);
        // axisLeft.setDrawLimitLinesBehindData(true);
        
        // ====================设置右边的Y轴===============
        YAxis axisRight = mLineChart.getAxisRight();
        // 是否启用右边Y轴
        axisRight.setEnabled(true);
        // 设置最小值（这里按demo里的数据固死写了）
        axisRight.setAxisMinimum(0);
        // 设置最大值（这里按demo里的数据固死写了）
        axisRight.setAxisMaximum(20);
        axisRight.setGranularity(10f);
        // 设置横向的线为虚线
        axisRight.enableGridDashedLine(10f, 10f, 0f);
        
    }
    
    // 设置数据
    private void initData()
    {
        // 每个点的坐标,自己随便搞点（x,y）坐标就可以了
        pointValues = new ArrayList<>();
        for (int i = 1; i < 50; i++)
        {
            int y = (int) (Math.random() * 20);
            pointValues.add(new Entry(i, y));
            
        }
        
        // 点构成的某条线
        LineDataSet lineDataSet = new LineDataSet(pointValues, "走势");
        
        // 设置该线的颜色
        lineDataSet
                .setColor(getResources().getColor(R.color.tab_unselect_color));
        // 设置每个点的颜色
        // lineDataSet.setCircleColor(Color.YELLOW);
        // 设置该线的宽度
        lineDataSet.setLineWidth(1f);
        // 设置每个坐标点的圆大小
        // lineDataSet.setCircleRadius(1f);
        // 设置是否画圆
        lineDataSet.setDrawCircles(false);
        // 设置平滑曲线模式
        // lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        // 设置线一面部分是否填充颜色
        lineDataSet.setDrawFilled(true);
        // 设置填充的颜色
        lineDataSet.setFillColor(Color.BLUE);
        // 设置是否显示点的坐标值
        lineDataSet.setDrawValues(false);
        
        // 线的集合（可单条或多条线）
        List<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        // 把要画的所有线(线的集合)添加到LineData里
        LineData lineData = new LineData(dataSets);
        // 把最终的数据setData
        mLineChart.setData(lineData);
        
        if (mLineChart.getData() != null)
        {
            mLineChart.getData().setHighlightEnabled(
                    !mLineChart.getData().isHighlightEnabled());
            mLineChart.invalidate();
        }
        
    }
    
}
