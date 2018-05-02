package com.hb.currencymanage.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.DealOperationBean;
import com.hb.currencymanage.customview.DividerItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 汪彬 on 2018/4/20.
 */

public class OperationFragment extends BaseFragment
{
    @BindView(R.id.rv_operation)
    RecyclerView mRvOperation;
    
    private CommonAdapter<DealOperationBean> mAdapter;
    
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    
    private List<DealOperationBean> mDatas = new ArrayList<>();
    
    public static OperationFragment getInstance()
    {
        return new OperationFragment();
    }
    
    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_deal_operation;
    }
    
    @Override
    protected void init()
    {
        initRv();
    }
    
    private void initRv()
    {
        mDatas.add(new DealOperationBean());
        mDatas.add(new DealOperationBean());
        mDatas.add(new DealOperationBean());
        mDatas.add(new DealOperationBean());
        mDatas.add(new DealOperationBean());
        mDatas.add(new DealOperationBean());
        mDatas.add(new DealOperationBean());
        mRvOperation.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOperation.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommonAdapter<DealOperationBean>(getContext(),
                R.layout.fragment_deal_item_operation_rv_item, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, DealOperationBean bean,
                    int position)
            {
            }
        };
        addHeadFootView();
        mRvOperation.setAdapter(mHeaderAndFooterWrapper);
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
    
    private void addHeadFootView()
    {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        View footView = LayoutInflater.from(getContext()).inflate(
                R.layout.fragment_deal_item_operation_header,
                mRvOperation,
                false);
        mHeaderAndFooterWrapper.addHeaderView(footView);
    }
}
