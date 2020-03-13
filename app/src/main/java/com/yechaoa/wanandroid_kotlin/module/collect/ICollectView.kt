package com.yechaoa.wanandroid_kotlin.module.collect

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Collect

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface ICollectView : BaseView {

    fun getCollectList(collect: BaseBean<Collect>)

    fun getCollectError(msg: String)

    fun login(msg: String)

    fun getCollectMoreList(collect: BaseBean<Collect>)

    fun getCollectMoreError(msg: String)

    fun unCollect(msg: String)
}