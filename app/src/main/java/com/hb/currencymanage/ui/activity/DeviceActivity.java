package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.DeviceEntity;
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

public class DeviceActivity extends BaseActivity {


    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private List<DeviceEntity> deviceEntityList;

    private CommonAdapter adapter = null;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        ButterKnife.bind(this);
        userBean=new AccountDB(context).getAccount();
        init();

    }

    private void init() {

        if (deviceEntityList==null){
            deviceEntityList=new ArrayList<>();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new CommonAdapter<DeviceEntity>(context, R.layout.device_list_item,
                deviceEntityList)
        {

            @Override
            protected void convert(ViewHolder holder, DeviceEntity deviceEntity, int position) {
                holder.setText(R.id.tv_opentime,deviceEntity.opentime);
                if(deviceEntity.state==1){
                    holder.setText(R.id.tv_status,"正在运行");
                }else {
                    holder.setText(R.id.tv_status,"停止运行");
                }

                holder.setText(R.id.tv_production,"今日产值:"+deviceEntity.production);
                holder.setText(R.id.tv_code,"编号:"+deviceEntity.code);
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

                        Bundle bundle=new Bundle();
                        bundle.putString("code",deviceEntityList.get(position).code);
                        bundle.putString("cumulativeoutput",deviceEntityList.get(position).cumulativeoutput);
                        changeActivity(DeviceDetailLeaseActivity.class,bundle);


                    }

                    @Override
                    public boolean onItemLongClick(View view,
                                                   RecyclerView.ViewHolder holder, int position)
                    {
                        return false;
                    }
                });
    }


    private void initNetWork(){

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
                               deviceEntityList.addAll(resultData.data);
                               adapter.notifyDataSetChanged();

                           }
                        }else {

                            Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
                        }



                    }
                });
    }


    @Override
    protected void onResume() {
        super.onResume();

        initNetWork();

    }

    @OnClick(R.id.back)
    void back()
    {
        finish();
    }

    @OnClick(R.id.add)
    void add()
    {

        changeActivity(AddDeviceActivity.class);

    }



}
