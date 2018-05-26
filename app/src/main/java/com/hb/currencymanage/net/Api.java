package com.hb.currencymanage.net;

import com.hb.currencymanage.bean.AccountBean;
import com.hb.currencymanage.bean.CurrencyBean;
import com.hb.currencymanage.bean.HostBean;
import com.hb.currencymanage.bean.HqViewBean;
import com.hb.currencymanage.bean.OrderBean;
import com.hb.currencymanage.bean.QuotesData;
import com.hb.currencymanage.bean.QuotesEntity;
import com.hb.currencymanage.bean.ResultData;
import com.hb.currencymanage.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
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
                                          @Query("forUserName") String forUserName,
                                          @Query("num") String num);
    
    @POST("clientMain/myCurrency")
    Observable<ResultData<CurrencyBean>> myCurrency(@Query("id") String id);

    @GET("index/hqView")
    Observable<ResultData<HqViewBean>> hqView();


    @POST("clientMain/getHosting")
    Observable<ResultData<List<HostBean>>> getHosting(@Query("userId") String userId );

    @POST("clientMain/getHostingOK")
    Observable<ResultData<List<HostBean>>> getHostingOK(@Query("userId") String userId );

    @POST("clientMain/getHostingCancel")
    Observable<ResultData<List<HostBean>>> getHostingCancel(@Query("userId") String userId );


    @POST("clientMain/cancelHosting")
    Observable<ResultData<List<HostBean>>> cancelHosting(@Query("hostingId") String hostingId);

    @POST("clientMain/getHostingAll")
    Observable<ResultData<List<HostBean>>> getHostingAll(@Query("userId") String userId);

    @POST("clientMain/recharge")
    Observable<ResultData<OrderBean>> recharge(@Query("userId") String userId,
                                               @Query("amount") String amount);

    @POST("clientMain/withdraw")
    Observable<ResultData<OrderBean>> withdraw(@Query("userId") String userId,
                                                     @Query("amount") String amount,
                                                     @Query("withdrawType") String withdrawType);

    @POST("clientMain/orderList")
    Observable<ResultData<List<OrderBean>>> orderList(@Query("userId") String userId);

    @POST("clientMain/zzVerificationSMS")
    Observable<ResultData<UserBean>> zzVerificationSMS(@Query("userId") String userId);


    @POST("clientMain/registerVerificationSMS")
    Observable<ResultData<UserBean>> registerVerificationSMS(@Query("phone") String phone);


    @POST("clientMain/rechargeCZ")
    Observable<ResultData<UserBean>> rechargeCZ(@Query("orderId") String orderId);

    @POST("clientMain/checkUserRecommendedCode")
    Observable<ResultData<UserBean>> checkUserRecommendedCode(@Query("recommendedcode") String recommendedcode);


    @POST("clientMain/getConsulting")
    Observable<ResultData<String>> getConsulting();


    @POST("pub/public/uploadImage")
    Observable<ResultData<UserBean>> uploadImage(@Query("userId ") String userId ,
                                                   @Body RequestBody body);





}