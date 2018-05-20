package com.hb.currencymanage.ui.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.HostBean;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.customview.DividerItemDecoration;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.myview.SwipeItemLayout;
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

public class MyDelegateFragment extends BaseFragment
{
    @BindView(R.id.rv_operation)
    RecyclerView mRvOperation;
    
    private List<HostBean> mDatas ;

    private MyAdapter mAdapter;


    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    
    public static MyDelegateFragment getInstance()
    {

        MyDelegateFragment operationFragment=new MyDelegateFragment();
        return operationFragment;
    }
    
    @Override
    public int getLayoutResId()
    {
        return R.layout.my_fragment_deal_operation;
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
        mRvOperation.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getContext()));
        mAdapter=new MyAdapter(getContext());
        mRvOperation.setAdapter(mAdapter);
        mRvOperation.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
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
                .getHosting(new AccountDB(getActivity()).getUserId())
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

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

        private Context mContext;

        public MyAdapter(Context context) {
            mContext = context;

        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(mContext).inflate(R.layout.swipe_fragment_deal_item_operation_rv_item, parent, false);
            return new Holder(root);
        }

        @Override
        public void onBindViewHolder(Holder holder, final int position) {

            HostBean bean=mDatas.get(position);

            holder.tv_time.setText(bean.addtime);
            //type 委托类型 1、卖 2、买
            if(bean.type==1){
                holder.tv_type.setText("卖出");
            }else {
                holder.tv_type.setText("买入");
            }

            holder.tv_price.setText(bean.showPrice);
            holder.tv_num.setText(bean.showCountnum+"/"+bean.showCjNum);
            holder.tv_down.setText(bean.stateName);


            holder.btnDelegate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RetrofitUtils
                            .getInstance(getActivity())
                            .api
                            .cancelHosting(mDatas.get(position).id)
                            .compose(RxSchedulers.<ResultData<List<HostBean>>>compose())
                            .subscribe(new BaseObserver<List<HostBean>>(getActivity(),true) {
                                @Override
                                public void onHandlerSuccess(ResultData<List<HostBean>> resultData) {
                                    Toast.makeText(getContext(),resultData.message,Toast.LENGTH_SHORT).show();
                                    if (resultData.result == 200)
                                    {

                                        initData();

                                    }
                                }
                            });
                }
            });


        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        class Holder extends RecyclerView.ViewHolder{

            private TextView tv_type;

            private TextView tv_time;

            private TextView tv_price;

            private TextView tv_num;

            private TextView tv_down;

            private TextView btnDelegate;


            Holder(View itemView) {
                super(itemView);

                tv_type=itemView.findViewById(R.id.tv_type);
                tv_time=itemView.findViewById(R.id.tv_time);
                tv_price=itemView.findViewById(R.id.tv_price);
                tv_num=itemView.findViewById(R.id.tv_num);
                btnDelegate=itemView.findViewById(R.id.btnDelegate);
                tv_down=itemView.findViewById(R.id.tv_down);

            }



        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){

            initData();
        }
    }
}
