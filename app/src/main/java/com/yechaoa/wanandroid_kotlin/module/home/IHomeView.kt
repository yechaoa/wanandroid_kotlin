package com.yechaoa.wanandroid_kotlin.module.home

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.Banner

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface IHomeView : BaseView {

    fun getBanner(banners: BaseBean<MutableList<Banner>>)

    fun getBannerError(msg: String)

    fun getArticleList(article: BaseBean<Article>)

    fun getArticleError(msg: String)

}