package com.yechaoa.wanandroid_kotlin.module.register

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.User

/**
 * Created by yechao on 2020/3/4.
 * Describe :
 */
interface IRegisterView : BaseView {

    fun showRegisterSuccess(successMessage: String)

    fun showRegisterFailed(errorMessage: String)

    fun doSuccess(user: BaseBean<User>)

}