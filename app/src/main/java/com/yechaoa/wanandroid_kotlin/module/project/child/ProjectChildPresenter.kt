package com.yechaoa.wanandroid_kotlin.module.project.child

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.ProjectChild
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
class ProjectChildPresenter(projectChildView: IProjectChildView) {

    private var mIProjectChildView: IProjectChildView = projectChildView

    fun getProjectChild(page: Int, cid: Int) {
        RetrofitService.getApiService()
            .getProjectChild(page, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<ProjectChild>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<ProjectChild>) {
                    LogUtilKt.i("onNext")
                    mIProjectChildView.getProjectChild(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIProjectChildView.getProjectChildError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }
    fun getProjectMoreChild(page: Int, cid: Int) {
        RetrofitService.getApiService()
            .getProjectChild(page, cid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<ProjectChild>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<ProjectChild>) {
                    LogUtilKt.i("onNext")
                    mIProjectChildView.getProjectMoreChild(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIProjectChildView.getProjectChildMoreError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

}
