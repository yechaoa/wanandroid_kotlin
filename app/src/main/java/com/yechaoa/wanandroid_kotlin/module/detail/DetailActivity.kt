package com.yechaoa.wanandroid_kotlin.module.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.KeyEvent
import android.webkit.WebSettings
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentweb.AgentWeb
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    companion object {
        const val WEB_URL: String = "web_url"
        const val WEB_TITLE: String = "web_title"
    }

    private lateinit var mAgentWeb: AgentWeb

    override fun createPresenter() {}

    override fun getLayoutId(): Int {
        return R.layout.activity_detail
    }

    override fun initView() {
        setMyTitle(intent.getStringExtra(WEB_TITLE)!!)
        setBackEnabled()

        initAgentWeb()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initAgentWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(web_content, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(getLoadUrl())

        val webView = mAgentWeb.webCreator.webView
        val settings = webView.settings
        //获取手势焦点
        webView.requestFocusFromTouch()
        //支持JS
        settings.javaScriptEnabled = true
        //是否支持缩放
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        //是否显示缩放按钮
        settings.displayZoomControls = false
        //自适应屏幕
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        //设置布局方式,支持内容重新布局
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN

        addBGChild(mAgentWeb.webCreator.webParentLayout as FrameLayout) // 得到 AgentWeb 最底层的控件
    }

    private fun addBGChild(frameLayout: FrameLayout) {
        val title="技术由 AgentWeb 提供"
        val mTextView = TextView(frameLayout.context)
        mTextView.text = title
        mTextView.textSize = 16f
        mTextView.setTextColor(Color.parseColor("#727779"))
        frameLayout.setBackgroundColor(Color.parseColor("#272b2d"))
        val mFlp = FrameLayout.LayoutParams(-2, -2)
        mFlp.gravity = Gravity.CENTER_HORIZONTAL
        val scale: Float = frameLayout.context.resources.displayMetrics.density
        mFlp.topMargin = (15 * scale + 0.5f).toInt()
        frameLayout.addView(mTextView, 0, mFlp)
    }

    private fun getLoadUrl(): String? {
        return intent.getStringExtra(WEB_URL)
    }

    /**
     * 事件处理
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    /**
     * 跟随 Activity Or Fragment 生命周期 ， 释放 CPU 更省电 。
     */
    override fun onPause() {
        mAgentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        mAgentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

}
