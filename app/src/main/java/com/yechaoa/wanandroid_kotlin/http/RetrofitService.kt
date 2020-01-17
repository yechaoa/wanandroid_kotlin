package com.yechaoa.wanandroid_kotlin.http

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by yechao on 2020/1/8/008.
 * Describe :
 */
object RetrofitService {

    private var apiServer: API.WAZApi

    fun getApiService(): API.WAZApi {
        return apiServer
    }

    //初始化retrofit
    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()

        //关联okhttp并加上rxjava和gson的配置和baseurl
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(API.BASE_URL)
            .build()

        apiServer = retrofit.create(API.WAZApi::class.java)
    }

}