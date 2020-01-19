package com.yechaoa.wanandroid_kotlin.http

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.Banner
import com.yechaoa.wanandroid_kotlin.bean.Tree
import com.yechaoa.wanandroid_kotlin.bean.User
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Created by yechao on 2020/1/8/008.
 * Describe :
 */
class API {

    companion object {
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


        //-----------------------【首页相关】----------------------

        //首页文章列表
        @GET("article/list/{page}/json")
        fun getArticleList(@Path("page") page: Int): Observable<BaseBean<Article>>

        //首页banner
        @GET("banner/json")
        fun getBanner(): Observable<BaseBean<MutableList<Banner>>>


        //-----------------------【 体系 】----------------------

        //体系数据
        @GET("tree/json")
        fun getTree(): Observable<BaseBean<MutableList<Tree>>>

    }

}