package com.hb.currencymanage.ui.fragment;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.DeviceEntity;
import com.hb.currencymanage.ui.activity.MineDeviceActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

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

public class DeviceListFragment extends BaseFragment
{
    
    @BindView(R.id.recycleView)
    RecyclerView recyclerView;
    
    private List<DeviceEntity> deviceEntityList;
    
    private CommonAdapter adapter = null;
    
    public static DeviceListFragment getInstance()
    {
        DeviceListFragment sf = new DeviceListFragment();
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
        return R.layout.fragment_device_list;
    }
    
    @Override
    protected void init()
    {
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new CommonAdapter(getActivity(), R.layout.device_list_item,
                deviceEntityList)
        {
            @Override
            protected void convert(ViewHolder holder, Object o, int position)
            {
                
            }
            
            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder,
                    int position)
            {
                
            }
        };
        recyclerView.setAdapter(adapter);
        
        adapter.setOnItemClickListener(
                new MultiItemTypeAdapter.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view,
                            RecyclerView.ViewHolder holder, int position)
                    {
                        
                    }
                    
                    @Override
                    public boolean onItemLongClick(View view,
                            RecyclerView.ViewHolder holder, int position)
                    {
                        return false;
                    }
                });
    }
    
    private void initData()
    {
        if (deviceEntityList == null)
        {
            deviceEntityList = new ArrayList<>();
        }
        for (int i = 0; i < 10; i++)
        {
            deviceEntityList.add(new DeviceEntity());
        }
    }
}
