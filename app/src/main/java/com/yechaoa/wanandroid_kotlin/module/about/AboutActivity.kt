package com.yechaoa.wanandroid_kotlin.module.about

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.yutilskt.YUtilsKt
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.content_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        initView()
    }

    fun initView() {
        //透明状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            val option: Int = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }

        //设置返回键可用
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        tv_app_info.text =
            resources.getString(R.string.app_name) + "  V" + YUtilsKt.getVersionName()

        tv_github.paint.flags = Paint.UNDERLINE_TEXT_FLAG

        tv_github.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.WEB_URL, "https://github.com/yechaoa/wanandroid_kotlin")
            intent.putExtra(DetailActivity.WEB_TITLE, "yechaoa/wanandroid_kotlin")
            startActivity(intent)
        }

        fab.setOnClickListener {
            Snackbar.make(it, "分享", Snackbar.LENGTH_SHORT).show()
        }

    }

    /**
     * 返回键
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}
