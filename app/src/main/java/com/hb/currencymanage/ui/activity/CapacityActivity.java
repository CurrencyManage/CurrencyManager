package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.CapacityEntity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CapacityActivity extends BaseActivity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private List<CapacityEntity> capacityEntityList=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);

        ButterKnife.bind(this);
        initData();
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        recycleView.setAdapter(new CommonAdapter(context,R.layout.capacity_item,capacityEntityList) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {

            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        });


    }

    private void initData() {
        if(capacityEntityList==null){
            capacityEntityList=new ArrayList<>();
        }

        for(int i=0;i<10;i++){
            capacityEntityList.add(new CapacityEntity());
        }
    }


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}
