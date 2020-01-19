package com.yechaoa.wanandroid_kotlin.module.home

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.Banner
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
class HomePresenter(homeView: IHomeView) {

    private var mIHomeView: IHomeView = homeView

    fun getArticleList(page: Int) {

        RetrofitService.getApiService()
            .getArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<Article>) {
                    LogUtilKt.i("onNext")
                    mIHomeView.getArticleList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIHomeView.getArticleError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getBanner() {
        RetrofitService.getApiService()
            .getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<List<Banner>>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<List<Banner>>) {
                    LogUtilKt.i("onNext")
                    mIHomeView.getBanner(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIHomeView.getBannerError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

}
