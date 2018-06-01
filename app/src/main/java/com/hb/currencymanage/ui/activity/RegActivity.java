package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.hb.currencymanage.bean.HostBean;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.customview.AutoHeightViewPager;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.CommonUtils;
import com.hb.currencymanage.util.IdcardValidator;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 汪彬 on 2018/4/23.
 */

public class RegActivity extends BaseActivity {
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

    private EditText et_mname;

    private EditText et_mbank;

    private List<View> mViews = new ArrayList<>();

    private int mCurPos = 0;

    private String msgData;

    private TextView tv_getcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View stepOne = inflater.inflate(R.layout.activity_reg_step_one, null);
        View stepTwo = inflater.inflate(R.layout.activity_reg_step_two, null);
        View stepThree = inflater.inflate(R.layout.activity_reg_step_three,
                null);
        View stepFour = inflater.inflate(R.layout.activity_reg_step_four, null);


        tv_getcode =stepOne.findViewById(R.id.tv_getcode);
        tv_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitUtils.getInstance(RegActivity.this).api
                        .registerVerificationSMS(mEtPhone.getText().toString())
                        .compose(RxSchedulers.<ResultData<UserBean>>compose())
                        .subscribe(new BaseObserver<UserBean>(context,true) {
                            @Override
                            public void onHandlerSuccess(ResultData<UserBean> resultData) {

                                if (resultData.result==200){
                                    handler.sendEmptyMessage(0);
                                    msgData=resultData.msgData;
                                    Toast.makeText(RegActivity.this,"验证码获取成功",Toast.LENGTH_SHORT).show();
                                }else if(resultData.result==400){
                                    NormalAlertDialog dialog = new NormalAlertDialog.Builder(context)
                                            .setHeight(0.23f)  //屏幕高度*0.23
                                            .setWidth(0.65f)  //屏幕宽度*0.65
                                            .setTitleVisible(true)
                                            .setTitleText("温馨提示")
                                            .setTitleTextColor(R.color.colorPrimary)
                                            .setContentText(resultData.message)
                                            .setContentTextColor(R.color.colorPrimaryDark)
                                            .setSingleMode(true)
                                            .setSingleButtonText("关闭")
                                            .setSingleButtonTextColor(R.color.colorAccent)
                                            .setCanceledOnTouchOutside(true)
                                            .setSingleListener(new DialogInterface.OnSingleClickListener<NormalAlertDialog>() {
                                                @Override
                                                public void clickSingleButton(NormalAlertDialog dialog, View view) {
                                                    dialog.dismiss();
                                                }
                                            })
                                            .build();

                                    dialog.show();



                                }
                                else {
                                    tv_getcode.setEnabled(true);
                                    Toast.makeText(RegActivity.this,"验证码获取失败",Toast.LENGTH_SHORT).show();
                                }
                            }


                        });
            }
        });


        mEtPhone = stepOne.findViewById(R.id.et_phone);
        mEtVerifyCode = stepOne.findViewById(R.id.et_verify_code);
        mEtPwd = stepTwo.findViewById(R.id.et_pwd);
        mEtPwdAgain = stepTwo.findViewById(R.id.et_pwd_again);
        mEtName = stepThree.findViewById(R.id.et_name);
        mEtCard = stepThree.findViewById(R.id.et_card);
        mEtRecId = stepThree.findViewById(R.id.et_rec_id);
        mEtBankAddress = stepFour.findViewById(R.id.et_bank_address);
        et_mname = stepFour.findViewById(R.id.et_mname);
        et_mbank = stepFour.findViewById(R.id.et_mbank);

        mEtBank = stepFour.findViewById(R.id.et_bank);
        mViews.add(stepOne);
        mViews.add(stepTwo);
        mViews.add(stepThree);
        mViews.add(stepFour);
        mVpReg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mVpReg.requestLayout();
                mTvRegStep
                        .setText(position == mViews.size() - 1 ? "注册" : "下一步");
                mCurPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpReg.setScanScroll(false);
        mVpReg.setOffscreenPageLimit(mViews.size() - 1);
        mVpReg.setAdapter(new MyPagerAdapter());
        mVpReg.setCurrentItem(0);
    }

    @OnClick(R.id.iv_left)
    public void back() {
        if (mCurPos > 0 && mCurPos <= mViews.size() - 1) {
            mVpReg.setCurrentItem(--mCurPos);
        } else if (mCurPos == 0) {
            finish();
        }
    }



    @OnClick(R.id.tv_reg_step)
    public void reg() {
        if (check()) {
            if (mCurPos < mViews.size() - 1) {

                //checkRecommdId();

                if(mCurPos==0){

                    if(!TextUtils.isEmpty(msgData) && msgData.equals(mEtVerifyCode.getText().toString())){
                        mVpReg.setCurrentItem(++mCurPos);
                    }else {
                        Toast.makeText(RegActivity.this,"验证码输入不正确",Toast.LENGTH_LONG).show();
                    }

                }else if (mCurPos==2){
                    checkRecommdId();

                }else {
                    mVpReg.setCurrentItem(++mCurPos);
                }



            } else {

                NormalAlertDialog  dialog = new NormalAlertDialog.Builder(context)
                        .setHeight(0.23f)  //屏幕高度*0.23
                        .setWidth(0.65f)  //屏幕宽度*0.65
                        .setTitleVisible(true)
                        .setTitleText("温馨提示")
                        .setTitleTextColor(R.color.black_light)
                        .setContentText("注册信息一旦确认，无法更改，确认注册?")
                        .setContentTextColor(R.color.black_light)
                        .setLeftButtonText("取消")
                        .setLeftButtonTextColor(R.color.gray)
                        .setRightButtonText("确定")
                        .setRightButtonTextColor(R.color.black_light)
                        .setOnclickListener(new DialogInterface.OnLeftAndRightClickListener<NormalAlertDialog>() {
                            @Override
                            public void clickLeftButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                            }

                            @Override
                            public void clickRightButton(NormalAlertDialog dialog, View view) {
                                dialog.dismiss();
                                RetrofitUtils.getInstance(context).api
                                        .reg(mEtPhone.getText().toString(),
                                                mEtPwd.getText().toString(),
                                                et_mname.getText().toString(),
                                                mEtCard.getText().toString().toUpperCase(),
                                                mEtRecId.getText().toString(),
                                                mEtBankAddress.getText().toString(),
                                                mEtBank.getText().toString(),
                                                et_mbank.getText().toString())
                                        .compose(RxSchedulers.<ResultData<String>>compose())
                                        .subscribe(new BaseObserver<String>(context, true) {
                                            @Override
                                            public void onHandlerSuccess(
                                                    ResultData<String> resultData) {
                                                if (resultData.result == 200) {
                                                    // changeActivity(MainActivity.class);
                                                    finish();
                                                } else {
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
                        })
                        .build();


                dialog.show();



            }
        }
    }

    private int count=60;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_getcode.setText(count+"s");
            --count;
            if(count==0){
                tv_getcode.setText("获取验证码");
                count=60;
            }else {
                handler.sendEmptyMessageDelayed(0,1000);
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(0);
        handler=null;
    }

    private boolean check() {
        if (mCurPos == 0) {
            if (!CommonUtils.isMobile(mEtPhone.getText().toString())) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
                return false;
            }
            if (TextUtils.isEmpty(mEtVerifyCode.getText().toString())) {
                Toast.makeText(this, "验证码不能为空", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (mCurPos == 1) {

            if (TextUtils.isEmpty(mEtPwd.getText().toString())) {
                Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
                return false;
            } else if (TextUtils.isEmpty(mEtPwdAgain.getText().toString())) {
                Toast.makeText(this, "请输入确认密码", Toast.LENGTH_LONG).show();
                return false;
            } else if (!mEtPwd.getText().toString().equals(
                    mEtPwdAgain.getText().toString())) {
                Toast.makeText(this, "前后密码不一致,请重新输入", Toast.LENGTH_LONG).show();
                return false;
            }
        } else if (mCurPos == 2) {
            if (TextUtils.isEmpty(mEtName.getText().toString())) {
                Toast.makeText(this, "请输入姓名", Toast.LENGTH_LONG).show();
                return false;
            } else if (!CommonUtils.isIdCard(mEtCard.getText().toString().toUpperCase()))//(!IdcardValidator.validator.isValidatedAllIdcard(mEtCard.getText().toString()))//))
            {
                Toast.makeText(this, "请输入正确的身份证号码", Toast.LENGTH_LONG).show();
                return false;
            }
            // else if (TextUtils.isEmpty(mEtRecId.getText().toString()))
            // {
            // Toast.makeText(this, "请输入推荐人ID", Toast.LENGTH_LONG).show();
            // return false;
            // }
        } else if (mCurPos == 3) {
            if (TextUtils.isEmpty(mEtBankAddress.getText().toString())) {
                Toast.makeText(this, "请输入银行卡开户行", Toast.LENGTH_LONG).show();
                return false;
            } else if (!CommonUtils.isBankCard(mEtBank.getText().toString())) {
                Toast.makeText(this, "请输入正确的银行卡号", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }


    public void checkRecommdId()
    {

        if(TextUtils.isEmpty(mEtRecId.getText().toString())){
            Toast.makeText(context,"推荐人id不能为空",Toast.LENGTH_SHORT).show();
        }else {
            RetrofitUtils
                    .getInstance(context)
                    .api
                    .checkUserRecommendedCode(mEtRecId.getText().toString())
                    .compose(RxSchedulers.<ResultData<UserBean>>compose())
                    .subscribe(new BaseObserver<UserBean>(context,true) {
                        @Override
                        public void onHandlerSuccess(ResultData<UserBean> resultData) {
                            if (resultData.result == 200)
                            {
                                mVpReg.setCurrentItem(++mCurPos);

                            }else {
                                Toast.makeText(context,resultData.message,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }



    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mViews.get(position);
            container.addView(v);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(mViews.get(position));
        }
    }
}
