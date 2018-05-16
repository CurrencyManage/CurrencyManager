package com.hb.currencymanage.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.OrderBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyOrderActivity extends BaseActivity {



    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private CommonAdapter adapter = null;

    private List<OrderBean> orderBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        initData();
        initView();



    }

    private void initView()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

    }

    private void initData()
    {


        if (orderBeanList == null)
        {
            orderBeanList=new ArrayList<>();
        }

        for(int i=0;i<3;i++)
        {

            orderBeanList.add(new OrderBean());

        }

        adapter = new CommonAdapter(context, R.layout.order_item,
                orderBeanList)
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


    }


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}
