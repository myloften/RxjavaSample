// (c)2016 Flipboard Inc, All Rights Reserved.

package com.loften.rxjavasample.network;


import com.loften.rxjavasample.network.api.FakeApi;
import com.loften.rxjavasample.network.api.GankApi;
import com.loften.rxjavasample.network.api.ZhuangbiApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static ZhuangbiApi zhuangbiApi;
    private static GankApi gankApi;
    private static FakeApi fakeApi;
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    private static OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.addInterceptor(logging);
        return okHttpClient.build();
    }

    public static ZhuangbiApi getZhuangbiApi() {
        if (zhuangbiApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl("http://zhuangbi.info/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            zhuangbiApi = retrofit.create(ZhuangbiApi.class);
        }
        return zhuangbiApi;
    }

    public static GankApi getGankApi() {
        if (gankApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl("http://gank.io/api/")
                    .addConverterFactory(gsonConverterFactory)
                    .addCallAdapterFactory(rxJavaCallAdapterFactory)
                    .build();
            gankApi = retrofit.create(GankApi.class);
        }
        return gankApi;
    }

    public static FakeApi getFakeApi() {
        if (fakeApi == null) {
            fakeApi = new FakeApi();
        }
        return fakeApi;
    }
}
