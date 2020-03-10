package com.yechaoa.wanandroid_kotlin.module.collect

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Collect
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class CollectPresenter(collectView: ICollectView) {

    private var mICollectView: ICollectView = collectView

    fun getCollectList(page: Int) {
        RetrofitService.getApiService()
            .getCollectList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Collect>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<Collect>) {
                    if (-1001 == t.errorCode) {
                        mICollectView.login(t.errorMsg + "(°∀°)ﾉ")
                    } else {
                        mICollectView.getCollectList(t)
                    }
                }

                override fun onError(e: Throwable) {
                    mICollectView.getCollectError(e.message + "(°∀°)ﾉ")
                }
            })
    }

    fun getCollectMoreList(page: Int) {
        RetrofitService.getApiService()
            .getCollectList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Collect>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<Collect>) {
                    mICollectView.getCollectMoreList(t)
                }

                override fun onError(e: Throwable) {
                    mICollectView.getCollectMoreError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }


}
