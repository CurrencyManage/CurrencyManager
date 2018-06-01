package com.hb.currencymanage.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.MainActivity;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.hb.currencymanage.util.CommonUtils;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.bugly.beta.Beta;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by 汪彬 on 2018/4/23.
 */

public class LoginActivity extends BaseActivity
{
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        RxPermissions rxPermissions=new RxPermissions(LoginActivity.this);
//        rxPermissions
//                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                .subscribe(new Consumer<Boolean>() {
//                    @Override
//                    public void accept(Boolean aBoolean) throws Exception {
//                        if (aBoolean){
//
//                        }else{
//                            finish();
//                        }
//                    }
//                });

        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                        if (permission.name.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            //当ACCESS_FINE_LOCATION权限获取成功时，permission.granted=true
                            Logger.i("permissions", Manifest.permission.WRITE_EXTERNAL_STORAGE + "：" + permission.granted);

                            if(!permission.granted){
                                finish();
                            }

                        }


                    }
                });

        //Beta.checkUpgrade();

        Beta.checkUpgrade(false,false);
        UserBean userBean = new AccountDB(this).getAccount();
        if (null != userBean)
        {
//            mEtPhone.setText(userBean.getPhone());
//            mEtPwd.setText(userBean.getPss());
            changeActivity(MainActivity.class);
            finish();
        }
    }
    
    @OnClick(R.id.tv_login)
    public void login()
    {
        String phone = mEtPhone.getText().toString();
        String pwd = mEtPwd.getText().toString();
        if (!CommonUtils.isMobile(phone))
        {
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show();
        }
        RetrofitUtils.getInstance(this).api.login(phone, pwd)
                .compose(RxSchedulers.<ResultData<UserBean>> compose())
                .subscribe(new BaseObserver<UserBean>(this, true)
                {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<UserBean> resultData)
                    {
                        Logger.d("res = " + resultData.data + " , "
                                + resultData.msg + " , " + resultData.result);
                        if (resultData.result==200)
                        {
                            new AccountDB(context).saveAccount(resultData.data);
                            changeActivity(MainActivity.class);
                            finish();
                        }else
                        {
                            Toast.makeText(LoginActivity.this,
                                    !TextUtils.isEmpty(resultData.message)
                                            ? resultData.message
                                            : "登陆失败，手机号或密码错误",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    
    @OnClick(R.id.tv_reg)
    public void reg()
    {
        changeActivity(RegActivity.class);
    }
    
    @OnClick(R.id.tv_forget_pwd)
    public void forgetPwd()
    {
        changeActivity(ForgetPwdActivity.class);
    }
}
