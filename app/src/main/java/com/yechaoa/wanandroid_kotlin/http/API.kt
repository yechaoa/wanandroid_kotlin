package com.yechaoa.wanandroid_kotlin.http

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.*
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
        fun login(
            @Field("username") username: String?,
            @Field("password") password: String?
        ): Observable<BaseBean<User>>

        //注册
        @FormUrlEncoded
        @POST("user/register")
        fun register(
            @Field("username") username: String?,
            @Field("password") password: String?,
            @Field("repassword") repassword: String?
        ): Observable<BaseBean<User>>


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


        //-----------------------【 导航 】----------------------

        //导航数据
        @GET("navi/json")
        fun getNavi(): Observable<BaseBean<MutableList<Navi>>>


        //-----------------------【 项目 】----------------------

        //项目分类
        @GET("project/tree/json")
        fun getProject(): Observable<BaseBean<MutableList<Project>>>

        //项目列表数据
        @GET("project/list/{page}/json?")
        fun getProjectChild(
            @Path("page") page: Int,
            @Query("cid") cid: Int
        ): Observable<BaseBean<ProjectChild>>


        //-----------------------【 搜索 】----------------------

        //搜索
        @FormUrlEncoded
        @POST("article/query/{page}/json?")
        fun getSearchList(
            @Path("page") page: Int,
            @Field("k") k: String
        ): Observable<BaseBean<Article>>

        //搜索热词
        @GET("hotkey/json")
        fun getHotkey(): Observable<BaseBean<MutableList<Hotkey>>>

        //-----------------------【 收藏 】----------------------

        //收藏文章列表
        @GET("lg/collect/list/{page}/json?")
        fun getCollectList(@Path("page") page: Int): Observable<BaseBean<Collect>>

        //收藏站内文章
        @POST("lg/collect/{id}/json")
        fun collect(@Path("id") id: Int): Observable<BaseBean<String>>

        //取消收藏（文章列表）
        @POST("lg/uncollect_originId/{id}/json")
        fun unCollect(@Path("id") id: Int): Observable<BaseBean<String>>

        //取消收藏（我的收藏页面）
        @FormUrlEncoded
        @POST("lg/uncollect/{id}/json")
        fun unCollect1(
            @Path("id") id: Int,
            @Field("originId") originId: Int
        ): Observable<BaseBean<String>>

    }

}