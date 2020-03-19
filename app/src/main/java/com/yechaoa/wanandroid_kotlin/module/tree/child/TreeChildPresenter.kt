package com.yechaoa.wanandroid_kotlin.module.tree.child

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.http.RetrofitService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class TreeChildPresenter(treeChildView: ITreeChildView) {

    private var mITreeChildView: ITreeChildView = treeChildView

    fun getTreeChild(page: Int, cid: Int) {
        RetrofitService.getApiService()
            .getTreeChild(page, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<Article>) {
                    mITreeChildView.getTreeChild(t)
                }

                override fun onError(e: Throwable) {
                    mITreeChildView.getTreeChildError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

    fun getTreeMoreChild(page: Int, cid: Int) {
        RetrofitService.getApiService()
            .getTreeChild(page, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<Article>> {
                override fun onComplete() {}

                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: BaseBean<Article>) {
                    mITreeChildView.getTreeMoreChild(t)
                }

                override fun onError(e: Throwable) {
                    mITreeChildView.getTreeChildMoreError("获取失败(°∀°)ﾉ" + e.message)
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
                        mITreeChildView.login(t.errorMsg + "(°∀°)ﾉ")
                    } else {
                        mITreeChildView.collect("收藏成功 (°∀°)ﾉ")
                    }
                }

                override fun onError(e: Throwable) {}
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
                    mITreeChildView.unCollect("取消成功 (°∀°)ﾉ")
                }

                override fun onError(e: Throwable) {}
            })
    }

}
