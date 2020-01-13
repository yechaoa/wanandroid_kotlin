package com.yechaoa.wanandroid_kotlin.base

import com.yechaoa.wanandroid_kotlin.bean.User
import com.yechaoa.wanandroid_kotlin.http.API
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
open class BasePresenter<V : BaseView?>(baseView: V) {

}