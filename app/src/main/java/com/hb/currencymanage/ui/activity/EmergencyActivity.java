package com.hb.currencymanage.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmergencyActivity extends BaseActivity {



    @BindView(R.id.tv_whereitis)
    TextView tv_whereitis;

    @BindView(R.id.tv_bankcard)
    TextView tv_bankcard;

    @BindView(R.id.et_money)
    EditText et_money;

    private UserBean userBean=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ButterKnife.bind(this);


        userBean=new AccountDB(context).getAccount();
        tv_whereitis.setText(userBean.getWhereitis());
        tv_bankcard.setText(userBean.getBankcard());

    }


    @OnClick(R.id.withDraw)
    void withDraw(){

        String str_money=et_money.getText().toString();
        if(!TextUtils.isEmpty(str_money) && Integer.parseInt(str_money)>=100){
            RetrofitUtils
                    .getInstance(context)
                    .api
                    .withdraw(new AccountDB(context).getUserId(),et_money.getText().toString(),2+"")
                    .compose(RxSchedulers.<ResultData<UserBean>>compose())
                    .subscribe(new BaseObserver<UserBean>(context,false) {
                        @Override
                        public void onHandlerSuccess(ResultData<UserBean> resultData) {

                            if(resultData.result==200){
                                Toast.makeText(context, "提现成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            Toast.makeText(context, "紧急提现最小为1000",Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}



