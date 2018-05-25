package com.hb.currencymanage.ui.activity;

import android.content.DialogInterface;
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

public class MoneyRechargeActivity extends BaseActivity {


    @BindView(R.id.tv_whereitis)
    TextView tv_whereitis;

    @BindView(R.id.tv_bankcard)
    TextView tv_bankcard;

    @BindView(R.id.et_money)
    EditText et_money;

    private UserBean userBean = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_recharge);

        ButterKnife.bind(this);

        userBean = new AccountDB(context).getAccount();
        tv_whereitis.setText(userBean.getWhereitis());
        tv_bankcard.setText(userBean.getBankcard());


    }

    @OnClick(R.id.tv_recharge)
    void tv_recharge() {


        String str_money = et_money.getText().toString();
        if (!TextUtils.isEmpty(str_money) && Integer.parseInt(str_money) >= 100) {
            RetrofitUtils
                    .getInstance(context)
                    .api
                    .recharge(new AccountDB(context).getUserId(), et_money.getText().toString())
                    .compose(RxSchedulers.<ResultData<UserBean>>compose())
                    .subscribe(new BaseObserver<UserBean>(context, false) {
                        @Override
                        public void onHandlerSuccess(final ResultData<UserBean> resultData) {

                            if (resultData.result == 200) {
                                showAlert(resultData);
                                finish();

                            } else {
                                if (resultData.result==500){
                                    showAlert(resultData);
                                }

                            }
                        }
                    });
        } else {
            Toast.makeText(context, "充值最小为100", Toast.LENGTH_SHORT).show();
        }


    }

    private void showAlert(final ResultData<UserBean> resultData) {
        showSystemV7Dialog("", resultData.message, "确定", "取消", true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        //跳转订单详情界面
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("bean",resultData);
                        changeActivity(OrderDetailActivity.class, bundle);
                        break;
                }
            }
        });
    }


    @OnClick(R.id.back)
    void back() {
        finish();
    }


}
