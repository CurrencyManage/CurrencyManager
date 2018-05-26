package com.hb.currencymanage.ui.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
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

public class EmergencyActivity extends BaseActivity {



    @BindView(R.id.tv_whereitis)
    TextView tv_whereitis;

    @BindView(R.id.tv_bankcard)
    TextView tv_bankcard;

    @BindView(R.id.et_money)
    EditText et_money;

    private UserBean userBean=null;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_bank)
    TextView tv_bank;

    @BindView(R.id.tv_skKHH)
    TextView tv_skKHH;

    @BindView(R.id.tv_skCard)
    TextView tv_skCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        ButterKnife.bind(this);


        userBean=new AccountDB(context).getAccount();
        tv_whereitis.setText(userBean.getWhereitis());
        tv_bankcard.setText(userBean.getBankcard());


        tv_name.setText(userBean.getName());
        tv_bank.setText(userBean.getBank());
        tv_skKHH.setText(userBean.getWhereitis());
        tv_skCard.setText(userBean.getBankcard());

    }


    @OnClick(R.id.withDraw)
    void withDraw(){

//        String str_money=et_money.getText().toString();
//        if(!TextUtils.isEmpty(str_money) && Integer.parseInt(str_money)>=10000){
//            RetrofitUtils
//                    .getInstance(context)
//                    .api
//                    .withdraw(new AccountDB(context).getUserId(),et_money.getText().toString(),2+"")
//                    .compose(RxSchedulers.<ResultData<OrderBean>>compose())
//                    .subscribe(new BaseObserver<OrderBean>(context,false) {
//                        @Override
//                        public void onHandlerSuccess(ResultData<OrderBean> resultData) {
//
//                            if(resultData.result==200){
//                                Toast.makeText(context, "提现成功",Toast.LENGTH_SHORT).show();
//                                finish();
//                            }else {
//                                Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
//        }else {
//            Toast.makeText(context, "紧急提现最小为1000",Toast.LENGTH_SHORT).show();
//        }


        String str_money=et_money.getText().toString();
        if(!TextUtils.isEmpty(str_money) && Integer.parseInt(str_money)>=10000){
            RetrofitUtils
                    .getInstance(context)
                    .api
                    .withdraw(new AccountDB(context).getUserId(),et_money.getText().toString(),2+"")
                    .compose(RxSchedulers.<ResultData<OrderBean>>compose())
                    .subscribe(new BaseObserver<OrderBean>(context,false) {
                        @Override
                        public void onHandlerSuccess(ResultData<OrderBean> resultData) {

                            if(resultData.result==200 || resultData.result==500){
                                resultData.data.orderType="withdraw";
                                resultData.data.code=resultData.data.withdrawalorder.code;
                                resultData.data.toaccountamount=resultData.data.withdrawalorder.toaccountamount;
                                resultData.data.state=resultData.data.withdrawalorder.state;
                                resultData.data.type=resultData.data.withdrawalorder.type;

                                showAlert(resultData);

                            }else {
                                Toast.makeText(context,resultData.message+"",Toast.LENGTH_SHORT).show();
                            }



                        }
                    });
        }else {
            Toast.makeText(context, "提现最小为100",Toast.LENGTH_SHORT).show();
        }

    }


    private void showAlert(final ResultData<OrderBean> resultData) {
        showSystemV7Dialog(resultData.title, resultData.message, "确定", "取消", true, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        //跳转订单详情界面
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("bean",resultData.data);
                        changeActivity(OrderDetailActivity.class, bundle);
                        finish();
                        break;
                }
            }
        });
    }

    @OnClick(R.id.back)
    void back()
    {
        finish();
    }




}



