package com.yechaoa.wanandroid_kotlin.module.search

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.Hotkey
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import com.yechaoa.yutilskt.LogUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/3/4.
 * Describe :
 */
class SearchPresenter(searchView: ISearchView) {

    private var mSearchView: ISearchView = searchView

    fun getHotkey() {
        RetrofitService.getApiService()
            .getHotkey()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Hotkey>>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseBean<MutableList<Hotkey>>) {
                    mSearchView.getHotkey(t)
                }

                override fun onError(e: Throwable) {
                    mSearchView.getHotkeyError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getArticleList(page: Int, key: String) {

        RetrofitService.getApiService()
            .getSearchList(page, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseBean<Article>) {
                    mSearchView.getArticleList(t)
                }

                override fun onError(e: Throwable) {
                    mSearchView.getArticleError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getArticleMoreList(page: Int, key: String) {

        RetrofitService.getApiService()
            .getSearchList(page, key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseBean<Article>) {
                    mSearchView.getArticleMoreList(t)
                }

                override fun onError(e: Throwable) {
                    mSearchView.getArticleMoreError("获取失败(°∀°)ﾉ" + e.message)
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
                        mSearchView.login(t.errorMsg + "(°∀°)ﾉ")
                    } else {
                        mSearchView.collect("收藏成功 (°∀°)ﾉ")
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
                    mSearchView.unCollect("取消成功 (°∀°)ﾉ")
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                }
            })
    }


}
