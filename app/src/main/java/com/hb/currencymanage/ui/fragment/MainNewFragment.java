package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.HomeBean;
import com.hb.currencymanage.bean.NewsBean;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.customview.DividerItemDecoration;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.imgloader.GlideImageLoader;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class MainNewFragment extends BaseFragment
{


    @BindView(R.id.img_head)
    ImageView img_head;

    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    private List<HomeBean> homeBeanList;

    private CommonAdapter adapter;
    
    public static MainNewFragment getInstance()
    {
        MainNewFragment sf = new MainNewFragment();
        return sf;
    }
    

    
    @Override
    public int getLayoutResId()
    {
        return R.layout.fragment_new_main;
    }

    @Override
    protected void init() {

        /*
        Glide
                .with(getContext())
                .load("http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg")
                .fitCenter()
                .into(img_head);
                */


        if(homeBeanList==null){
            homeBeanList=new ArrayList<>();
        }


        initNetwork();


        adapter = new CommonAdapter<HomeBean>(getActivity(),
                R.layout.home_item, homeBeanList)
        {
            @Override
            protected void convert(ViewHolder holder, HomeBean bean,
                                   int position)
            {


            }
        };

        recycleView.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleView.setAdapter(adapter);





    }

    private void initNetwork()
    {

//        RetrofitUtils
//                .getInstance(getActivity())
//                .api
//                .getConsulting()
//                .compose(RxSchedulers.<ResultData<UserBean>>compose())
//                .subscribe(new BaseObserver<UserBean>(context,false) {
//                    @Override
//                    public void onHandlerSuccess(ResultData<UserBean> resultData) {
//
//                        if(resultData.result==200){
//
//                        }
//                    }
//                });

    }


}
