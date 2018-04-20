package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hb.currencymanage.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 汪彬 on 2018/4/20.
 */

public class OperationFragment extends BaseFragment
{
    @BindView(R.id.rv_operation)
    RecyclerView mRvOperation;
    
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
        
    }
}
