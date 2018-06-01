package com.hb.currencymanage.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import com.wevey.selector.dialog.DialogInterface;
import com.wevey.selector.dialog.NormalAlertDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceDetailLeaseActivity extends BaseActivity {



    @BindView(R.id.tv_code)
    TextView tv_code;


    @BindView(R.id.tv_total)
    TextView tv_total;

    @BindView(R.id.et_ecode)
    EditText et_ecode;


    @BindView(R.id.tv_unbind)
    TextView tv_unbind;

    @BindView(R.id.tv_getcode)
    TextView tv_getcode;

    private String code;

    private UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_detail_lease);
        ButterKnife.bind(this);

        userBean=new AccountDB(context).getAccount();
        Bundle bundle=getIntent().getExtras();
        tv_code.setText("编号:"+bundle.getString("code"));
        tv_total.setText(bundle.getString("cumulativeoutput"));
        code=bundle.getString("code");
    }


    @OnClick(R.id.back)
    void back()
    {
        finish();
    }


    //tv_bind
    @OnClick(R.id.tv_unbind)
    void tv_unbind()
    {

        RetrofitUtils
                .getInstance(context)
                .api
                .unbindEquipment(et_ecode.getText().toString(),code,userBean.getPhone(),userBean.getId())
                .compose(RxSchedulers.<ResultData<UserBean>>compose())
                .subscribe(new BaseObserver<UserBean>(context,false) {
                    @Override
                    public void onHandlerSuccess(ResultData<UserBean> resultData) {

                        if(resultData.result==200){
                            //handler.sendEmptyMessage(0);
                            finish();
                        }else {
                            //tv_getcode.setEnabled(true);
                            //Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
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


                    }
                });


    }


    //tv_getcode
    @OnClick(R.id.tv_getcode)
    void tv_getcode()
    {

        tv_getcode.setEnabled(false);
        RetrofitUtils
                .getInstance(context)
                .api
                .unbindSendCode(userBean.getPhone())
                .compose(RxSchedulers.<ResultData<UserBean>>compose())
                .subscribe(new BaseObserver<UserBean>(context,false) {
                    @Override
                    public void onHandlerSuccess(ResultData<UserBean> resultData) {

                        if(resultData.result==200){
                            handler.sendEmptyMessage(0);
                        }else {
                            tv_getcode.setEnabled(true);
                            Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
                        }



                    }
                });


    }

    private int count=300;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            --count;
            if(count==0){
                tv_getcode.setEnabled(true);
                tv_getcode.setText("获取验证码");

            }else{
                tv_getcode.setText(count+"");
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
}
