package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.NewsBean;
import com.hb.currencymanage.bean.QuotesEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/19.
 */

public class DealBusinessFragment extends BaseFragment
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
    
    @BindView(R.id.tv_ten_info)
    TextView mTvTenInfo;
    
    @BindView(R.id.tv_minutes_info)
    TextView mTvMinutesInfo;
    
    @BindView(R.id.rv_info)
    RecyclerView mRVInfo;
    
    private List<QuotesEntity> mDatas = new ArrayList<>();
    
    private CommonAdapter<QuotesEntity> mAdapter;
    
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    
    public static DealBusinessFragment getInstance()
    {
        DealBusinessFragment f = new DealBusinessFragment();
        return f;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_deal_item_business, null);
        ButterKnife.bind(this, v);
        initRV();
        return v;
    }
    
    private void addFootView()
    {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        View footView = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_deal_business_foot, mRVInfo, false);
        mHeaderAndFooterWrapper.addFootView(footView);
    }
    
    private void initRV()
    {
        mRVInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        int i = 0;
        while (i < 10)
        {
            mDatas.add(new QuotesEntity());
            i++;
        }
        mAdapter = new CommonAdapter<QuotesEntity>(getContext(),
                R.layout.quotes_item, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, QuotesEntity entity,
                    int position)
            {
            }
        };
        addFootView();
        mRVInfo.setAdapter(mHeaderAndFooterWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder,
                    int position)
            {
                Toast.makeText(getContext(),
                        "pos = " + position,
                        Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public boolean onItemLongClick(View view,
                    RecyclerView.ViewHolder holder, int position)
            {
                return false;
            }
        });
    }
}
