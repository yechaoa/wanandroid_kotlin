package com.yechaoa.wanandroid_kotlin.http

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.User
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by yechao on 2020/1/8/008.
 * Describe :
 */
class API {

    companion object{
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    interface WAZApi {

        //-----------------------【登录注册】----------------------

        //登录
        @FormUrlEncoded
        @POST("user/login")
        fun login(@Field("username") username: String?, @Field("password") password: String?): Observable<BaseBean<User>>

        //注册
        @FormUrlEncoded
        @POST("user/register")
        fun register(
            @Field("username") username: String?,
            @Field("password") password: String?,
            @Field("repassword") repassword: String?
        ): Observable<BaseBean<String>>


    }

}