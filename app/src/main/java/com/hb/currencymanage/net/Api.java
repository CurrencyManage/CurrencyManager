package com.hb.currencymanage.net;

import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2018/4/21.
 */

public interface Api
{
    
    /*
     * @GET("sys/lg/sendlogincode") Observable<ResultData<MemberBean>>
     * sendlogincode(@Query("phone") String phone);
     * 
     * @POST("sys/lg/updateUserInfo") Observable<ResultData<MemberBean>>
     * updateUserInfo(@Query("sessiontoken") String sessiontoken,
     * 
     * @Body MemberBean memberBean);
     */
    
    @GET("index/getDisparity")
    Observable<ResultData<QuotesEntity>> getDisparity();
    
    @FormUrlEncoded
    @POST("clientMain/register")
    Observable<ResultData<String>> reg(@Field("phone") String phone,
            @Field("pass") String pass, @Field("name") String name,
            @Field("idcode") String idcode,
            @Field("recommendedcode") String recommendedcode,
            @Field("whereitis") String whereitis,
            @Field("bankcard") String bankcard, @Field("bank") String bank);
    
}
