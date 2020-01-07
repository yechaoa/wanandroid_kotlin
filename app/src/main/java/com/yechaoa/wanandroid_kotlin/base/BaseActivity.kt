package com.yechaoa.wanandroid_kotlin.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by yechao on 2020/1/3/003.
 * Describe :
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        setContentView(LayoutInflater.from(this).inflate(getLayoutId(), null))

        initView()
    }

    protected abstract fun getLayoutId(): Int

    protected abstract fun initView()

}
