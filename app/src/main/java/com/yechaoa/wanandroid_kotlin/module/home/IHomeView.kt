package com.yechaoa.wanandroid_kotlin.module.home

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.User

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface IHomeView : BaseView {

    fun getArticleList(article: BaseBean<Article>)

    fun getArticleError(msg:String)

}