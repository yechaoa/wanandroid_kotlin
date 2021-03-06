package com.yechaoa.wanandroid_kotlin.module.tree

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Tree

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface ITreeView : BaseView {

    fun getTree(tree: BaseBean<MutableList<Tree>>)

    fun getTreeError(msg: String)

}