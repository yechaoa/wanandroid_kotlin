package com.yechaoa.wanandroid_kotlin.module.tree.child

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Article

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface ITreeChildView : BaseView {

    fun getTreeChild(treeChild: BaseBean<Article>)

    fun getTreeChildError(msg: String)

    fun getTreeMoreChild(treeChild: BaseBean<Article>)

    fun getTreeChildMoreError(msg: String)

    fun login(msg: String)

    fun collect(msg: String)

    fun unCollect(msg: String)

}