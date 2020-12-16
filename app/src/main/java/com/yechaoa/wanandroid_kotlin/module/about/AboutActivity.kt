package com.yechaoa.wanandroid_kotlin.module.about

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    @SuppressLint("SetTextI18n")
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

        tv_app_info.text = "玩安卓  V${YUtilsKt.getVersionName()}"

        //添加下划线
        tv_github.paint.flags = Paint.UNDERLINE_TEXT_FLAG

        tv_github.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.WEB_URL, "https://github.com/yechaoa/wanandroid_kotlin")
            intent.putExtra(DetailActivity.WEB_TITLE, "yechaoa/wanandroid_kotlin")
            startActivity(intent)
        }

        fab.setOnClickListener {
            shareProject()
        }

        setTypeface()
    }

    /**
     * 调用系统的分享功能
     */
    private fun shareProject() {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, "玩安卓")
            putExtra(Intent.EXTRA_TEXT, "https://github.com/yechaoa/wanandroid_kotlin")
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(Intent.createChooser(intent, "玩安卓"))
    }

    /**
     * 设置字体
     */
    private fun setTypeface() {
        //获取AssetManager
        val assets = assets as AssetManager
        //根据路径得到字体
        val typeface = Typeface.createFromAsset(assets, "fonts/mononoki-Regular.ttf")
        //设置给TextView
        tv_library.typeface = typeface
        tv_github.typeface = typeface
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
