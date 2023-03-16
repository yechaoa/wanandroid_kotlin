package com.yechaoa.wanandroid_kotlin.module.navi

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Navi
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import com.yechaoa.yutilskt.LogUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class NaviPresenter(naviView: INaviView) {

    private var mINaviView: INaviView = naviView

    fun getNavi() {
        RetrofitService.getApiService()
            .getNavi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Navi>>> {
                override fun onComplete() {
                    LogUtil.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtil.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<MutableList<Navi>>) {
                    LogUtil.i("onNext")
                    mINaviView.getNavi(t)
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                    mINaviView.getNaviError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

}
