package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.CapacityEntity;
import com.hb.currencymanage.bean.DeviceEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.StatusBarCompat;
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

    @BindView(R.id.tv_SumCurrency)
    TextView tv_SumCurrency;

    private List<DeviceEntity> deviceActivityList=null;

    private CommonAdapter adapter=null;

    private UserBean userBean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);
        ButterKnife.bind(this);
        userBean=new AccountDB(context).getAccount();
        initNetWork();
        recycleView.setLayoutManager(new LinearLayoutManager(context));
        adapter=new CommonAdapter<DeviceEntity>(context,R.layout.capacity_item,deviceActivityList) {
            @Override
            protected void convert(ViewHolder holder, DeviceEntity deviceEntity, int position) {

                holder.setText(R.id.tv_code,deviceEntity.code);
                holder.setText(R.id.tv_production,deviceEntity.production);

            }
        };

        recycleView.setAdapter(adapter);



    }




    private void initNetWork(){
        if(deviceActivityList==null){
            deviceActivityList=new ArrayList<>();
        }
        RetrofitUtils
                .getInstance(context)
                .api
                .equipmentList(userBean.getId())
                .compose(RxSchedulers.<ResultData<List<DeviceEntity>>>compose())
                .subscribe(new BaseObserver<List<DeviceEntity>>(context,false) {
                    @Override
                    public void onHandlerSuccess(ResultData<List<DeviceEntity>> resultData) {

                        if(resultData.result==200){
                            if(resultData.data!=null){
                                deviceActivityList.addAll(resultData.data);
                                adapter.notifyDataSetChanged();

                            }
                            tv_SumCurrency.setText(resultData.SumCurrency);
                        }else {

                            Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
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
