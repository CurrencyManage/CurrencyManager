package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.customview.AutoHeightViewPager;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.CommonUtils;

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
        mEtPwd = stepTwo.findViewById(R.id.et_pwd);
        mEtPwdAgain = stepTwo.findViewById(R.id.et_pwd_again);
        mEtName = stepThree.findViewById(R.id.et_name);
        mEtCard = stepThree.findViewById(R.id.et_card);
        mEtRecId = stepThree.findViewById(R.id.et_rec_id);
        mEtBankAddress = stepFour.findViewById(R.id.et_bank_address);
        mEtBank = stepFour.findViewById(R.id.et_bank);
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
        if (check())
        {
            if (mCurPos < mViews.size() - 1)
            {
                mVpReg.setCurrentItem(++mCurPos);
            }
            else
            {
                RetrofitUtils.getInstance(this).api
                        .reg(mEtPhone.getText().toString(),
                                mEtPwd.getText().toString(),
                                mEtName.getText().toString(),
                                mEtCard.getText().toString().toUpperCase(),
                                mEtRecId.getText().toString(),
                                mEtBankAddress.getText().toString(),
                                mEtBank.getText().toString(),
                                mEtBank.getText().toString())
                        .compose(RxSchedulers.<ResultData<String>> compose())
                        .subscribe(new BaseObserver<String>(this, true)
                        {
                            @Override
                            public void onHandlerSuccess(
                                    ResultData<String> resultData)
                            {
                                if (resultData.result.equals("200"))
                                {
                                    // changeActivity(MainActivity.class);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(RegActivity.this,
                                            !TextUtils
                                                    .isEmpty(resultData.message)
                                                            ? resultData.message
                                                            : "注册失败",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        }
    }
    
    private boolean check()
    {
        if (mCurPos == 0)
        {
            if (!CommonUtils.isMobile(mEtPhone.getText().toString()))
            {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                return false;
            }
            if (TextUtils.isEmpty(mEtVerifyCode.getText().toString()))
            {
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else if (mCurPos == 1)
        {
            
            if (TextUtils.isEmpty(mEtPwd.getText().toString()))
            {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }
            else if (TextUtils.isEmpty(mEtPwdAgain.getText().toString()))
            {
                Toast.makeText(this, "请输入确认密码", Toast.LENGTH_LONG).show();
                return false;
            }
            else if (!mEtPwd.getText().toString().equals(
                    mEtPwdAgain.getText().toString()))
            {
                Toast.makeText(this, "前后密码不一致,请重新输入", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else if (mCurPos == 2)
        {
            if (TextUtils.isEmpty(mEtName.getText().toString()))
            {
                Toast.makeText(this, "请输入姓名", Toast.LENGTH_LONG).show();
                return false;
            }
            else if (!CommonUtils.isIdCard(mEtCard.getText().toString().toUpperCase()))
            {
                Toast.makeText(this, "请输入正确的身份证号码", Toast.LENGTH_LONG).show();
                return false;
            }
            // else if (TextUtils.isEmpty(mEtRecId.getText().toString()))
            // {
            // Toast.makeText(this, "请输入推荐人ID", Toast.LENGTH_LONG).show();
            // return false;
            // }
        }
        else if (mCurPos == 3)
        {
            if (TextUtils.isEmpty(mEtBankAddress.getText().toString()))
            {
                Toast.makeText(this, "请输入银行卡开户行", Toast.LENGTH_LONG).show();
                return false;
            }
            else if (!CommonUtils.isBankCard(mEtBank.getText().toString()))
            {
                Toast.makeText(this, "请输入正确的银行卡号", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
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
