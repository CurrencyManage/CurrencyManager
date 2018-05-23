package com.hb.currencymanage.net;

import android.content.Context;

import com.hb.currencymanage.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2018/4/21.
 */

public class RetrofitUtils {


    //public static final String BASE="http://39.104.50.236:8088/currency/";

    public static final String BASE="http://39.104.50.236/currency/";

    //public static final String BASE_URL = BASE+"sys/";

    public static Retrofit retrofit;

    public static OkHttpClient client;

    public static OkHttpClient.Builder builder;

    private static Context context;

    public static Api api;

    private static RetrofitUtils mInstance;

    public RetrofitUtils(){
        initOkhttpClient();
        retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public  static RetrofitUtils getInstance(Context mcontext) {

        if (mInstance == null){
            synchronized (RetrofitUtils.class){
                context=mcontext.getApplicationContext();
                mInstance = new RetrofitUtils();
            }
        }

        api= retrofit.create(Api.class);
        return mInstance;

    }


    private void initOkhttpClient()
    {
        builder = new OkHttpClient().newBuilder();
        //cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        builder
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5,TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(interceptor);
        }

        client=builder.build();

    }


}
