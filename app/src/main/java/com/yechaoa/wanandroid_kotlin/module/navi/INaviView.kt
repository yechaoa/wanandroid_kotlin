package com.yechaoa.wanandroid_kotlin.module.navi

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Navi
import com.yechaoa.wanandroid_kotlin.bean.Tree

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface INaviView : BaseView {

    fun getNavi(navi: BaseBean<MutableList<Navi>>)

    fun getNaviError(msg: String)

}