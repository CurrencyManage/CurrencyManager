package com.hb.currencymanage.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.hb.currencymanage.R;
import com.hb.currencymanage.bean.OrderBean;
import com.hb.currencymanage.bean.UserBean;
import com.hb.currencymanage.db.AccountDB;

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






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);

        Bundle bundle=getIntent().getExtras();
        OrderBean orderBean= (OrderBean) bundle.getSerializable("bean");
        UserBean userBean=new AccountDB(context).getAccount();
        tv_code.setText(TextUtils.isEmpty(orderBean.code)?" ":orderBean.code);
        tv_skCard.setText(TextUtils.isEmpty(orderBean.skCard)?" ":orderBean.skCard);
        tv_skName.setText(TextUtils.isEmpty(orderBean.skName)?" ":orderBean.skName);
        if(orderBean.orderType.equals("recharge")){
            tv_price.setText(TextUtils.isEmpty(orderBean.amount)?" ":orderBean.amount);
            tv_skKHH.setText(TextUtils.isEmpty(orderBean.skKHH)?" ":orderBean.skKHH);
            tv_type.setText("充值");
        }else if (orderBean.orderType.equals("withdraw")){
            tv_price.setText(TextUtils.isEmpty(orderBean.withdrawalamount)?" ":orderBean.withdrawalamount);
            tv_skKHH.setText(userBean.getWhereitis());
            tv_skCard.setText(userBean.getBankcard());
            tv_skName.setText(TextUtils.isEmpty(userBean.getName())?" ":userBean.getName());
            tv_type.setText("提现");
        }



        switch (orderBean.state){
            case 1:
                tv_state.setText("已支付");
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
    void back()
    {
        finish();
    }







}
