package com.yechaoa.wanandroid_kotlin.module.login

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.User
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import com.yechaoa.yutilskt.LogUtilKt
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class LoginPresenter(loginView: ILoginView) {

    private var mLoginView: ILoginView = loginView

    fun submit(username: String?, password: String?) {

        RetrofitService.getApiService()
            .login(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<User>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<User>) {
                    LogUtilKt.i("onNext")
                    if (-1 == t.errorCode) {
                        mLoginView.showLoginFailed("登录失败(°∀°)ﾉ" + t.errorMsg)
                    } else {
                        mLoginView.showLoginSuccess("登录成功（￣▽￣）")
                        mLoginView.doSuccess(t)
                    }
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mLoginView.showLoginFailed("登录失败(°∀°)ﾉ" + e.message)
                }

            })

    }

    fun unSubscribe() {

    }

}
