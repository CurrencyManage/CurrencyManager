package com.hb.currencymanage.ui.fragment;


import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.QuotesData;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.dialog.QutoesDialogFragment;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class QuotesFragment extends BaseFragment
{
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
    

    private List<QuotesEntity> saleQuotesEntityList;
    
    private CommonAdapter saleAdapter;

    private List<QuotesEntity> buyQuotesEntityList;

    private CommonAdapter buyAdapter;
    
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
                        saleQuotesEntityList.get(position).sellNum + "");
                holder.setText(R.id.tv_No, "卖" + (position + 1));
                holder.setText(R.id.tv_Price,
                        saleQuotesEntityList.get(position).sellPrice);
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
    
    private void initNetWork()
    {
        
        RetrofitUtils.getInstance(getActivity()).api.getDisparity()
                .compose(RxSchedulers.<ResultData<QuotesEntity>> compose())
                .subscribe(new BaseObserver<QuotesEntity>(getActivity(), true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<QuotesEntity> resultData)
                    {
                        
                        if (resultData.code == 200)
                        {
                            tv_count.setText(resultData.data.count);
                            tv_countPrice.setText(resultData.data.countPrice);
                            tv_currentMin.setText(resultData.data.currentMin);
                            tv_currentMix.setText(resultData.data.currentMix);
                            tv_currentPrice
                                    .setText(resultData.data.currentPrice);
                            tv_Disparity.setText(resultData.data.Disparity);
                            tv_disparityB.setText(resultData.data.disparityB);
                            
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
                        if (resultData.code == 200)
                        {
                            saleQuotesEntityList.addAll(resultData.data.sell);
                            buyQuotesEntityList.addAll(resultData.data.buy);
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
        QutoesDialogFragment qutoesDialogFragment = new QutoesDialogFragment();
        qutoesDialogFragment.show(getFragmentManager(), "qutoesDialogFragment");
        
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

        
    }
    
    // 设置数据
    private void initData()
    {

        
    }
    
}
