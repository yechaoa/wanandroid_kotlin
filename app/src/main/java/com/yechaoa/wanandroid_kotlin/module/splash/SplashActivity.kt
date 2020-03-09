package com.yechaoa.wanandroid_kotlin.module.splash

import android.content.Intent
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.common.MyConfig
import com.yechaoa.wanandroid_kotlin.module.MainActivity
import com.yechaoa.wanandroid_kotlin.module.login.LoginActivity
import com.yechaoa.yutilskt.SpUtilKt

class SplashActivity : BaseActivity() {

    override fun createPresenter() {}

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initView() {
        object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    if (SpUtilKt.getBoolean(MyConfig.IS_LOGIN)) {
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    } else {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                    finish()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

}
