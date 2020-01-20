package com.yechaoa.wanandroid_kotlin.module.project

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.Navi
import com.yechaoa.wanandroid_kotlin.bean.Project
import com.yechaoa.wanandroid_kotlin.bean.Tree

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface IProjectView : BaseView {

    fun getProject(project: BaseBean<MutableList<Project>>)

    fun getProjectError(msg: String)

}