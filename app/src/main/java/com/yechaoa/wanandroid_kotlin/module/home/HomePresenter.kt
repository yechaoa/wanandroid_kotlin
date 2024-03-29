package com.yechaoa.wanandroid_kotlin.module.home

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.Banner
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
class HomePresenter(homeView: IHomeView) {

    private var mIHomeView: IHomeView = homeView

    fun getArticleList(page: Int) {

        RetrofitService.getApiService()
            .getArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                    LogUtil.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtil.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<Article>) {
                    LogUtil.i("onNext")
                    mIHomeView.getArticleList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                    mIHomeView.getArticleError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getArticleMoreList(page: Int) {

        RetrofitService.getApiService()
            .getArticleList(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                    LogUtil.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtil.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<Article>) {
                    LogUtil.i("onNext")
                    mIHomeView.getArticleMoreList(t)
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                    mIHomeView.getArticleMoreError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getBanner() {
        RetrofitService.getApiService()
            .getBanner()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Banner>>> {
                override fun onComplete() {
                    LogUtil.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtil.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<MutableList<Banner>>) {
                    LogUtil.i("onNext")
                    mIHomeView.getBanner(t)
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                    mIHomeView.getBannerError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun collect(id: Int) {
        RetrofitService.getApiService()
            .collect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<String>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<String>) {
                    if (-1001 == t.errorCode) {
                        mIHomeView.login(t.errorMsg + "(°∀°)ﾉ")
                    } else {
                        mIHomeView.collect("收藏成功 (°∀°)ﾉ")
                    }
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                }
            })
    }

    fun unCollect(id: Int) {
        RetrofitService.getApiService()
            .unCollect(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<String>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<String>) {
                    mIHomeView.unCollect("取消成功 (°∀°)ﾉ")
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                }
            })
    }

}
