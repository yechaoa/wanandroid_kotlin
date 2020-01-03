package com.yechaoa.wanandroid_kotlin.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by yechao on 2020/1/3/003.
 * Describe :
 */
class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        //竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//        setContentView(LayoutInflater.from(this).inflate(getLayoutId(), null))

    }

}