package com.yechaoa.wanandroid_kotlin.module.tree

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Tree
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
class TreePresenter(treeView: ITreeView) {

    private var mITreeView: ITreeView = treeView

    fun getTree() {
        RetrofitService.getApiService()
            .getTree()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Tree>>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<MutableList<Tree>>) {
                    LogUtilKt.i("onNext")
                    mITreeView.getTree(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mITreeView.getTreeError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

}
