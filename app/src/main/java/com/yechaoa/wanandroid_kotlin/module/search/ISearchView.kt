package com.yechaoa.wanandroid_kotlin.module.search

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.User

/**
 * Created by yechao on 2020/3/4.
 * Describe :
 */
interface ISearchView : BaseView {

    fun getArticleList(article: BaseBean<Article>)

    fun getArticleError(msg: String)

    fun getArticleMoreList(article: BaseBean<Article>)

    fun getArticleMoreError(msg: String)

}