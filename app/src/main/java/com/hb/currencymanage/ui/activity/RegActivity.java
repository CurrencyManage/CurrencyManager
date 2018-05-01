package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.customview.AutoHeightViewPager;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/23.
 */

public class RegActivity extends BaseActivity
{
    @BindView(R.id.vp_reg)
    AutoHeightViewPager mVpReg;
    
    @BindView(R.id.tv_reg_step)
    TextView mTvRegStep;
    
    private EditText mEtName;
    
    private EditText mEtCard;
    
    private EditText mEtRecId;
    
    private EditText mEtBankAddress;
    
    private EditText mEtBank;
    
    private EditText mEtPwd;
    
    private EditText mEtPwdAgain;
    
    private EditText mEtPhone;
    
    private EditText mEtVerifyCode;
    
    private List<View> mViews = new ArrayList<>();
    
    private int mCurPos = 0;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        initView();
    }
    
    private void initView()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View stepOne = inflater.inflate(R.layout.activity_reg_step_one, null);
        View stepTwo = inflater.inflate(R.layout.activity_reg_step_two, null);
        View stepThree = inflater.inflate(R.layout.activity_reg_step_three,
                null);
        View stepFour = inflater.inflate(R.layout.activity_reg_step_four, null);
        mEtPhone = stepOne.findViewById(R.id.et_phone);
        mEtVerifyCode = stepOne.findViewById(R.id.et_verify_code);
        mEtPwd = stepOne.findViewById(R.id.et_pwd);
        mEtPwdAgain = stepOne.findViewById(R.id.et_pwd_again);
        mEtName = stepOne.findViewById(R.id.et_name);
        mEtCard = stepOne.findViewById(R.id.et_card);
        mEtRecId = stepOne.findViewById(R.id.et_rec_id);
        mEtBankAddress = stepOne.findViewById(R.id.et_bank_address);
        mEtBank = stepOne.findViewById(R.id.et_bank);
        mViews.add(stepOne);
        mViews.add(stepTwo);
        mViews.add(stepThree);
        mViews.add(stepFour);
        mVpReg.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels)
            {
                
            }
            
            @Override
            public void onPageSelected(int position)
            {
                mVpReg.requestLayout();
                mTvRegStep
                        .setText(position == mViews.size() - 1 ? "注册" : "下一步");
                mCurPos = position;
            }
            
            @Override
            public void onPageScrollStateChanged(int state)
            {
                
            }
        });
        mVpReg.setScanScroll(false);
        mVpReg.setOffscreenPageLimit(mViews.size() - 1);
        mVpReg.setAdapter(new MyPagerAdapter());
        mVpReg.setCurrentItem(0);
    }
    
    @OnClick(R.id.iv_left)
    public void back()
    {
        if (mCurPos > 0 && mCurPos <= mViews.size() - 1)
        {
            mVpReg.setCurrentItem(--mCurPos);
        }
        else if (mCurPos == 0)
        {
            finish();
        }
    }
    
    @OnClick(R.id.tv_reg_step)
    public void reg()
    {
        if (mCurPos < mViews.size() - 1)
        {
            mVpReg.setCurrentItem(mCurPos + 1);
        }
        else
        {
            try
            {
                RetrofitUtils.getInstance(this).api.reg("18705157954",
                        "112",
                        "1sda",
                        "2332231",
                        "2324",
                        "efwef",
                        "234342",
                        "231423442")
                        .compose(RxSchedulers.<ResultData<String>> compose())
                        .subscribe(new BaseObserver<String>(this, true)
                        {
                            @Override
                            public void onHandlerSuccess(
                                    ResultData<String> resultData)
                            {
                                
                                if (resultData.code == 200)
                                {
                                    Log.d("wangbin", "result = " + resultData);
                                    changeActivity(MainActivity.class);
                                    finish();
                                }
                            }
                        });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    private class MyPagerAdapter extends PagerAdapter
    {
        
        @Override
        public int getCount()
        {
            return mViews.size();
        }
        
        @Override
        public boolean isViewFromObject(View view, Object object)
        {
            return view == object;
        }
        
        @Override
        public Object instantiateItem(ViewGroup container, int position)
        {
            View v = mViews.get(position);
            container.addView(v);
            return v;
        }
        
        @Override
        public void destroyItem(ViewGroup container, int position,
                Object object)
        {
            container.removeView(mViews.get(position));
        }
    }
}
