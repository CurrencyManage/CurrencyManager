package com.hb.currencymanage.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

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
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_main, null);
        ButterKnife.bind(this, v);
        String[] urls = getResources().getStringArray(R.array.url);
        String[] tips = getResources().getStringArray(R.array.title);
        ArrayList images = new ArrayList(Arrays.asList(urls));
        ArrayList titles = new ArrayList(Arrays.asList(tips));
        mBanner.setImages(images)
                .setBannerTitles(titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setImageLoader(new GlideImageLoader())
                .start();
        return v;
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
