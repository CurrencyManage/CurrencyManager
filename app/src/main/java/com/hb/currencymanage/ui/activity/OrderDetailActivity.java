package com.hb.currencymanage.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.OrderBean;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;
import com.hb.currencymanage.net.BaseObserver;
import com.hb.currencymanage.net.RetrofitUtils;
import com.hb.currencymanage.net.RxSchedulers;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity {


    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.tv_skName)
    TextView tv_skName;

    @BindView(R.id.tv_skCard)
    TextView tv_skCard;

    @BindView(R.id.tv_code)
    TextView tv_code;

    @BindView(R.id.tv_state)
    TextView tv_state;

    @BindView(R.id.tv_skKHH)
    TextView tv_skKHH;

    @BindView(R.id.tv_type)
    TextView tv_type;

    @BindView(R.id.type)
    TextView type;

    @BindView(R.id.tv_bank)
    TextView tvBink;

    @BindView(R.id.btn)
    Button btn;

    @BindView(R.id.ll_recharge)
    LinearLayout llRecharge;

    @BindView(R.id.tv_withdraw_hint)
    TextView tvWithDrawHint;

    @BindView(R.id.tv_urgent_withdraw_hint)
    TextView tvUrgentWithDrawHintl;

    @BindView(R.id.tv_withdraw_type)
    TextView tvWithDrawType;

    OrderBean orderBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        orderBean = (OrderBean) bundle.getSerializable("bean");
        UserBean userBean = new AccountDB(context).getAccount();
        tv_code.setText(TextUtils.isEmpty(orderBean.code) ? " " : orderBean.code);

        tv_skCard.setText(TextUtils.isEmpty(orderBean.skCard) ? " " : orderBean.skCard);
        tv_skName.setText(TextUtils.isEmpty(orderBean.skName) ? " " : orderBean.skName);
        if (orderBean.orderType.equals("recharge")) {
            tv_price.setText(TextUtils.isEmpty(orderBean.amount) ? " " : orderBean.amount);
            tv_skKHH.setText(TextUtils.isEmpty(orderBean.skKHH) ? " " : orderBean.skKHH);
            tv_type.setText("充值");
            type.setText("订单类型:充值订单");
            tvBink.setText(TextUtils.isEmpty(orderBean.skBank) ? "" : orderBean.skBank);
            llRecharge.setVisibility(View.VISIBLE);
            tvWithDrawHint.setVisibility(View.GONE);
            tvUrgentWithDrawHintl.setVisibility(View.GONE);
        } else if (orderBean.orderType.equals("withdraw")) {
            tv_price.setText(TextUtils.isEmpty(orderBean.withdrawalamount) ? " " : orderBean.withdrawalamount);
            tv_skKHH.setText(userBean.getWhereitis());
            tv_skCard.setText(userBean.getBankcard());
            tv_skName.setText(TextUtils.isEmpty(userBean.getName()) ? " " : userBean.getName());
            tv_type.setText("提现");
            tvBink.setText(TextUtils.isEmpty(userBean.getBank()) ? "" : userBean.getBank());
            //tvBink.setText(TextUtils.isEmpty(orderBean.bank) ? "" : orderBean.skBank);
            type.setText("订单类型:提现订单");
            tvWithDrawType.setText("提现金额");
            llRecharge.setVisibility(View.GONE);
            tvWithDrawHint.setVisibility(View.VISIBLE);
            tvUrgentWithDrawHintl.setVisibility(View.GONE);
        } else if (orderBean.type.equals("2")) {
            llRecharge.setVisibility(View.GONE);
            tvWithDrawHint.setVisibility(View.GONE);
            tvUrgentWithDrawHintl.setVisibility(View.VISIBLE);
        }


        switch (orderBean.state) {
            case 1:
                tv_state.setText("已支付");
                btn.setVisibility(View.GONE);
                break;

            case 0:
                tv_state.setText("未支付");
                break;
            case 2:
                tv_state.setText("已完成");
                break;

            case 3:
                tv_state.setText("充值成功");
                break;


        }


    }

    @OnClick(R.id.back)
    void back() {
        finish();
    }

    @OnClick(R.id.btn)
    void btn() {

        RetrofitUtils.getInstance(this).api.rechargeCZ(orderBean.id)
                .compose(RxSchedulers.<ResultData<UserBean>>compose())
                .subscribe(new BaseObserver<UserBean>(this, true) {
                    @Override
                    public void onHandlerSuccess(
                            ResultData<UserBean> resultData) {
                        Toast.makeText(context,
                                !TextUtils.isEmpty(resultData.message)
                                        ? resultData.message
                                        : "失败",
                                Toast.LENGTH_LONG).show();
                    }
                });

    }


}
