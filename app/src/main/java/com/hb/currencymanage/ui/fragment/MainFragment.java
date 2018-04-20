package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.NewsBean;
import com.hb.currencymanage.customview.DividerItemDecoration;
import com.hb.currencymanage.imgloader.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 汪彬 on 2018/4/16.
 */

public class MainFragment extends BaseFragment implements OnBannerListener
{
    @BindView(R.id.banner)
    Banner mBanner;
    
    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    
    private CommonAdapter<NewsBean> mAdapter;
    
    private List<NewsBean> mDatas = new ArrayList<>();
    
    public static MainFragment getInstance()
    {
        MainFragment sf = new MainFragment();
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
        return R.layout.fragment_main;
    }
    
    @Override
    protected void init()
    {
        initBanner();
        initRv();
    }
    
    private void initRv()
    {
        mDatas.add(new NewsBean("四个点十个人凡人歌个人而无法各位哥哥服务二哥哥如果个反而更反而更而广泛而个股", "彬三分",
                "15:39", ""));
        mDatas.add(new NewsBean("四个点十个人凡人歌个人而无法各位哥哥服务二哥哥如果个反而更反而更而广泛而个股", "彬三分",
                "15:39", ""));
        mDatas.add(new NewsBean("四个点十个人凡人歌个人而无法各位哥哥服务二哥哥如果个反而更反而更而广泛而个股", "彬三分",
                "15:39", ""));
        mDatas.add(new NewsBean("四个点十个人凡人歌个人而无法各位哥哥服务二哥哥如果个反而更反而更而广泛而个股", "彬三分",
                "15:39", ""));
        mDatas.add(new NewsBean("四个点十个人凡人歌个人而无法各位哥哥服务二哥哥如果个反而更反而更而广泛而个股", "彬三分",
                "15:39", ""));
        mRvContent.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvContent.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new CommonAdapter<NewsBean>(getContext(),
                R.layout.main_news_item, mDatas)
        {
            @Override
            protected void convert(ViewHolder holder, NewsBean news,
                    int position)
            {
                // holder.setText(R.id.tv_title,
                // s + " : " + holder.getAdapterPosition() + " , "
                // + holder.getLayoutPosition());
                holder.setText(R.id.tv_title, news.getTitle());
                holder.setText(R.id.tv_publisher,
                        news.getPublisher() + "    " + news.getPublishTime());
            }
        };
        mRvContent.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder,
                    int position)
            {
                Toast.makeText(getContext(),
                        "pos = " + position,
                        Toast.LENGTH_SHORT).show();
            }
            
            @Override
            public boolean onItemLongClick(View view,
                    RecyclerView.ViewHolder holder, int position)
            {
                return false;
            }
        });
    }
    
    private void initBanner()
    {
        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        ArrayList images = new ArrayList(Arrays.asList(urls));
        ArrayList titles = new ArrayList(Arrays.asList(tips));
        mBanner.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .start();
    }
    
    @Override
    public void onStart()
    {
        super.onStart();
        // 开始轮播
        mBanner.startAutoPlay();
    }
    
    @Override
    public void onStop()
    {
        super.onStop();
        // 结束轮播
        mBanner.stopAutoPlay();
    }
    
    @Override
    public void OnBannerClick(int position)
    {
        Toast.makeText(getActivity().getApplicationContext(),
                "你点击了：" + position,
                Toast.LENGTH_SHORT).show();
    }
}
