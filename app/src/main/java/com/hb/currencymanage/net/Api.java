package com.hb.currencymanage.net;

import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2018/4/21.
 */

public interface Api {


    /*
    @GET("sys/lg/sendlogincode")
    Observable<ResultData<MemberBean>> sendlogincode(@Query("phone") String phone);
     @POST("sys/lg/updateUserInfo")
    Observable<ResultData<MemberBean>> updateUserInfo(@Query("sessiontoken") String sessiontoken,
                                                      @Body MemberBean memberBean);
     */


    @GET("index/getDisparity")
    Observable<ResultData<QuotesEntity>> getDisparity();








}
