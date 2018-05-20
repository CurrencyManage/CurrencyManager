package com.hb.currencymanage.ui.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.DealOperationBean;
import com.hb.currencymanage.bean.HostBean;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.customview.DividerItemDecoration;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
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
    
    private CommonAdapter<HostBean> mAdapter;
    
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    
    private List<HostBean> mDatas ;

    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;


    public static OperationFragment getInstance()
    {

        OperationFragment operationFragment=new OperationFragment();
        return operationFragment;
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


        if(mDatas == null){
            mDatas = new ArrayList<>();
        }
        mRvOperation.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvOperation.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommonAdapter<HostBean>(getContext(),
                R.layout.fragment_deal_item_operation_rv_item, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, HostBean bean,
                    int position)
            {

                holder.setText(R.id.tv_time,bean.addtime);
                //type 委托类型 1、卖 2、买
                if(bean.type==1){
                    holder.setText(R.id.tv_type,"卖出");
                }else {
                    holder.setText(R.id.tv_type,"买入");
                }

                holder.setText(R.id.tv_price,bean.showPrice);
                //holder.setText(R.id.tv_num,bean.countnum+"/"+(bean.countnum-bean.num));
                holder.setText(R.id.tv_num,bean.showCountnum+"/"+bean.showCjNum);
                holder.setText(R.id.tv_down,bean.stateName);



            }
        };
        addHeadFootView();
        mRvOperation.setAdapter(mHeaderAndFooterWrapper);



        swipe.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorAccent,
                R.color.tv_bg_yellow);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });


        initData();

    }

    private void initData()
    {


        RetrofitUtils
                .getInstance(getActivity())
                .api
                .getHostingAll(new AccountDB(getActivity()).getUserId())
                .compose(RxSchedulers.<ResultData<List<HostBean>>>compose())
                .subscribe(new BaseObserver<List<HostBean>>(getActivity(),true) {
                    @Override
                    public void onHandlerSuccess(ResultData<List<HostBean>> resultData) {

                        swipe.setRefreshing(false);
                        if (resultData.result == 200)
                        {

                            mDatas.clear();
                            mDatas.addAll(resultData.data);
                            mAdapter.notifyDataSetChanged();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        swipe.setRefreshing(false);
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

            initData();

        }
    }
}
