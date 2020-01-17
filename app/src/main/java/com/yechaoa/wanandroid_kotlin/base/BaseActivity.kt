package com.yechaoa.wanandroid_kotlin.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.yechaoa.yutilskt.YUtilsKt

/**
 * Created by yechao on 2020/1/3/003.
 * Describe :
 */
abstract class BaseActivity: AppCompatActivity(), BaseView {

//    protected var presenter: P? = null
    protected abstract fun createPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(LayoutInflater.from(this).inflate(getLayoutId(), null))

//        presenter = createPresenter()
//        presenter?.attachView(this)

        createPresenter()

        initView()
    }

    protected fun setMyTitle(title: String) {
        supportActionBar?.title = title
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

    override fun showLoading() {
        YUtilsKt.showLoading(this,"加载中")
    }

    override fun hideLoading() {
        YUtilsKt.hideLoading()
    }

    override fun onErrorCode(bean: BaseBean<Any>) {

    }

    override fun onDestroy() {
        super.onDestroy()
//        presenter?.detachView()
    }

}
