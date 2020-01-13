package com.yechaoa.wanandroid_kotlin.module.login

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseObserver
import com.yechaoa.wanandroid_kotlin.base.BasePresenter
import com.yechaoa.wanandroid_kotlin.bean.User

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
//class LoginPresenter : BasePresenter<ILoginView>  {
//
//    constructor(baseView: ILoginView) : super(baseView)
//
//    fun submit(username: String?, password: String?) {
//
//
//        addDisposable(
//            apiServer.login(username, password),
//            object : BaseObserver<BaseBean<User?>?>(baseView) {
//                override fun onSuccess(bean: BaseBean<User?>?) {
//                    baseView?.showLoginSuccess("登录成功（￣▽￣）")
//                    baseView?.doSuccess(bean)
//                }
//
//                override fun onError(msg: String?) {
//                    baseView?.showLoginFailed("$msg(°∀°)ﾉ")
//                }
//
//            })
//    }
//
//}