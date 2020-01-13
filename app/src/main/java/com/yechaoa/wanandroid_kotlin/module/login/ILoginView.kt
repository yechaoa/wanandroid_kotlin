package com.yechaoa.wanandroid_kotlin.module.login

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseView
import com.yechaoa.wanandroid_kotlin.bean.User

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
interface ILoginView : BaseView {

    fun showLoginSuccess(successMessage: String?)

    fun showLoginFailed(errorMessage: String?)

    fun doSuccess(user: BaseBean<User?>?)

}