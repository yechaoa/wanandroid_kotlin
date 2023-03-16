package com.yechaoa.wanandroid_kotlin.module.project

import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Project
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
class ProjectPresenter(projectView: IProjectView) {

    private var mIProjectView: IProjectView = projectView

    fun getProject() {
        RetrofitService.getApiService()
            .getProject()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseBean<MutableList<Project>>> {
                override fun onComplete() {
                    LogUtil.i("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    LogUtil.i("onSubscribe")
                }

                override fun onNext(t: BaseBean<MutableList<Project>>) {
                    LogUtil.i("onNext")
                    mIProjectView.getProject(t)
                }

                override fun onError(e: Throwable) {
                    LogUtil.i("onError")
                    mIProjectView.getProjectError("获取失败(°∀°)ﾉ" + e.message)
                }
            })
    }

}
