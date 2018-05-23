package com.hb.currencymanage.net;

/**
 * Created by Administrator on 2018/4/21.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import com.hb.currencymanage.bean.ResultData;
import org.apache.http.conn.ConnectTimeoutException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;



public abstract class BaseObserver<T> implements Observer<ResultData<T>> {


    private Context mContext;

 //   private static ProgressDialog pd;

    public BaseObserver(Context context, boolean flag){
        this.mContext=context.getApplicationContext();
//        if(flag){
//            //进度框
//            pd=ProgressDialog.show(context,"","正在加载");
//            pd.setCanceledOnTouchOutside(true);
//
//
//        }

    }


    @Override
    public void onSubscribe(Disposable d) {

    }



    @Override
    public void onNext(ResultData<T> resultData) {

       // pd.dismiss();
        try{
            onHandlerSuccess(resultData);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public void onError(Throwable e) {
        //Log.e("BaseObserver", "error:" + e.toString());

        try {
            //pd.dismiss();
            if (e instanceof SocketTimeoutException) {

                Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();

            } else if (e instanceof ConnectException || e instanceof ConnectTimeoutException) {

                Toast.makeText(mContext, "连接超时", Toast.LENGTH_SHORT).show();

            } else {

                //Toast.makeText(mContext, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }

        }catch (Exception e1){


            e1.printStackTrace();

        }


    }







    @Override
    public void onComplete() {

    }


    public abstract void onHandlerSuccess(ResultData<T> resultData);




}