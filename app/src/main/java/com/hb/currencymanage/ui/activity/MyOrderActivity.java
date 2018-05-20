package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.OrderBean;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
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

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        ButterKnife.bind(this);
        userBean=new AccountDB(context).getAccount();
        initData();
        initView();



    }

    private void initView()
    {


        if (orderBeanList == null)
        {
            orderBeanList=new ArrayList<>();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonAdapter<OrderBean>(context, R.layout.order_item,
                orderBeanList)
        {
            @Override
            protected void convert(ViewHolder holder, OrderBean bean, int position)
            {

                switch (bean.state){
                    case 1:
                        holder.setText(R.id.tv_state,"已支付");
                        break;

                    case 0:
                        holder.setText(R.id.tv_state,"未支付");
                        break;
                    case 2:
                        holder.setText(R.id.tv_state,"已完成");
                        break;

                    case 3:
                        holder.setText(R.id.tv_state,"充值成功");
                        break;


                }
                holder.setText(R.id.tv_skCard,bean.skCard);
                if(bean.orderType.equals("recharge")){
                    holder.setText(R.id.tv_money,bean.amount);
                    holder.setText(R.id.tv_khh,bean.skKHH);
                    holder.setText(R.id.tv_type,"充值");
                }else if (bean.orderType.equals("withdraw")){
                    holder.setText(R.id.tv_money,bean.withdrawalamount);
                    holder.setText(R.id.tv_skCard,userBean.getBankcard());
                    holder.setText(R.id.tv_khh,userBean.getWhereitis());
                    holder.setText(R.id.tv_type,"提现");
                }

                holder.setText(R.id.tv_time,bean.orderTime);


            }


        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                Bundle bundle=new Bundle();
                bundle.putSerializable("bean",orderBeanList.get(position));
                changeActivity(OrderDetailActivity.class,bundle);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(adapter);

    }

    private void initData()
    {


        RetrofitUtils
                .getInstance(context)
                .api
                .orderList(new AccountDB(context).getUserId())
                .compose(RxSchedulers.<ResultData<List<OrderBean>>>compose())
                .subscribe(new BaseObserver<List<OrderBean>>(context,false) {

                    @Override
                    public void onHandlerSuccess(ResultData<List<OrderBean>> resultData) {
                        if(resultData.result==200){

                            orderBeanList.clear();
                            orderBeanList.addAll(resultData.data);
                            adapter.notifyDataSetChanged();
                        }
                    }
                });




    }


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}
