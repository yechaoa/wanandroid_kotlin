package com.yechaoa.wanandroid_kotlin.module.project.child

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.ProjectChild

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface IProjectChildView : BaseView {

    fun getProjectChild(projectChild: BaseBean<ProjectChild>)

    fun getProjectChildError(msg: String)

}