package com.hb.currencymanage.ui.fragment;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.dialog.QutoesDialogFragment;
import com.zhy.adapter.recyclerview.CommonAdapter;
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

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class QuotesFragment extends BaseFragment
{


    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private List<QuotesEntity> quotesEntityList;

    public static QuotesFragment getInstance()
    {
        QuotesFragment sf = new QuotesFragment();
        return sf;
    }
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        View v = inflater.inflate(R.layout.fragment_quotes, null);
        ButterKnife.bind(this, v);
        initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new CommonAdapter(getActivity(),R.layout.quotes_item,quotesEntityList) {
            @Override
            protected void convert(ViewHolder holder, Object o, int position) {

               holder.setText(R.id.sale_num, "12");
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            }
        });


        QutoesDialogFragment qutoesDialogFragment=new QutoesDialogFragment();
        qutoesDialogFragment.show(getFragmentManager(),"qutoesDialogFragment");

        return v;
    }

    private void initData() {
        if(quotesEntityList == null){
            quotesEntityList=new ArrayList<>();
        }


        for(int i=0;i<10;i++){

            quotesEntityList.add(new QuotesEntity());
        }
    }
}
