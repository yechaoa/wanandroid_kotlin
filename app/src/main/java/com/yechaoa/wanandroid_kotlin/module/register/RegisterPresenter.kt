package com.yechaoa.wanandroid_kotlin.module.register

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.User
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/3/4.
 * Describe :
 */
class RegisterPresenter(registerView: IRegisterView) {

    private var mRegisterView: IRegisterView = registerView

    fun submit(username: String?, password: String?, repassword: String?) {

        RetrofitService.getApiService()
            .register(username, password, repassword)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<User>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseBean<User>) {
                    if (-1 == t.errorCode) {
                        mRegisterView.showRegisterFailed(t.errorMsg)
                    } else {
                        mRegisterView.showRegisterSuccess("注册成功（￣▽￣）")
                        mRegisterView.doSuccess(t)
                    }
                }

                override fun onError(e: Throwable) {
                    mRegisterView.showRegisterFailed("注册失败(°∀°)ﾉ" + e.message)
                }

            })

    }

    fun unSubscribe() {

    }

}
