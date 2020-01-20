package com.yechaoa.wanandroid_kotlin.module.project

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Navi
import com.yechaoa.wanandroid_kotlin.bean.Project
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
class ProjectPresenter(projectView: IProjectView) {

    private var mIProjectView: IProjectView = projectView

    fun getProject() {
        RetrofitService.getApiService()
            .getProject()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Project>>> {
                override fun onComplete() {
                    LogUtilKt.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtilKt.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<MutableList<Project>>) {
                    LogUtilKt.i("onNext")
                    mIProjectView.getProject(t)
                }

                override fun onError(e: Throwable) {
                    LogUtilKt.i("onError")
                    mIProjectView.getProjectError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

}
