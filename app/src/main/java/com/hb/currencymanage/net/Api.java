package com.hb.currencymanage.net;

import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.bean.QuotesData;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
    
    @GET("index/transaction")
    Observable<ResultData<QuotesData>> transaction();
    
    @POST("clientMain/login")
    Observable<ResultData<UserBean>> login(@Query("phone") String phone,
            @Query("pass") String pass);
    
    @POST("clientMain/getAssets")
    Observable<ResultData<AccountBean>> getAccountInfo(@Query("id") String id);
    
    @POST("clientMain/transaction")
    Observable<ResultData<String>> sale(@Query("selluserid") String sellUserId,
            @Query("num") String num, @Query("price") String price);
    
    @POST("clientMain/transaction")
    Observable<ResultData<String>> buy(@Query("buyuserid") String buyUserId,
            @Query("num") String num, @Query("price") String price);
    
    @POST("clientMain/transferThePossessionOf")
    Observable<ResultData<String>> assign(@Query("toUserId") String toUserId,
            @Query("forUserCode") String forUserCode,
            @Query("forUserPhone") String forUserPhone,
            @Query("forUserName") String forUserName, @Query("num") String num);
    
    @POST("clientMain/myCurrency")
    Observable<ResultData<String>> myCurrency(@Query("id") String id);
    
}