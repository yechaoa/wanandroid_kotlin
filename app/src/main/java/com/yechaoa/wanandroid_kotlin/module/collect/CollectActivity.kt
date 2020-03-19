package com.yechaoa.wanandroid_kotlin.module.collect

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.CollectAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.Collect
import com.yechaoa.wanandroid_kotlin.bean.CollectDetail
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.wanandroid_kotlin.module.login.LoginActivity
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.activity_collect.*

class CollectActivity : BaseActivity(), ICollectView, OnItemClickListener, OnLoadMoreListener,
    SwipeRefreshLayout.OnRefreshListener, OnItemChildClickListener {

    private lateinit var mCollectPresenter: CollectPresenter
    private lateinit var mCollectAdapter: CollectAdapter
    private lateinit var mDataList: MutableList<CollectDetail>
    private var mPosition: Int = 0

    companion object {
        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数
    }

    override fun createPresenter() {
        mCollectPresenter = CollectPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun initView() {
        setMyTitle("收藏列表")
        setBackEnabled()
        initSwipeRefreshLayout()
        mCollectPresenter.getCollectList(CURRENT_PAGE)
    }

    /**
     * 初始化SwipeRefreshLayout
     */
    private fun initSwipeRefreshLayout() {
        swipe_refresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )
        swipe_refresh.setOnRefreshListener(this)
    }

    override fun getCollectList(collect: BaseBean<Collect>) {
        CURRENT_SIZE = collect.data.datas.size
        mDataList = collect.data.datas
        recycler_view.layoutManager = LinearLayoutManager(this)
        mCollectAdapter = CollectAdapter(collect.data.datas)
        //开启加载动画
        mCollectAdapter.animationEnable = true
        //item点击
        mCollectAdapter.setOnItemClickListener(this)
        //item子view点击
        mCollectAdapter.setOnItemChildClickListener(this)
        //加载更多
        mCollectAdapter.loadMoreModule?.setOnLoadMoreListener(this)
        recycler_view.adapter = mCollectAdapter
    }

    override fun getCollectError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun login(msg: String) {
        showLoginDialog(msg)
    }

    private fun showLoginDialog(msg: String) {
        val builder = AlertDialog.Builder(this@CollectActivity)
        builder.setTitle("提示")
        builder.setMessage(msg)
        builder.setPositiveButton("确定") { _, _ ->
            startActivity(Intent(this, LoginActivity::class.java))
        }
        builder.setNegativeButton("取消") { _, _ ->
            finish()
        }
        builder.create().show()
    }

    override fun getCollectMoreList(collect: BaseBean<Collect>) {
        CURRENT_SIZE = collect.data.datas.size
        mDataList.addAll(collect.data.datas)
        mCollectAdapter.addData(collect.data.datas)
        mCollectAdapter.loadMoreModule?.loadMoreComplete()
    }

    override fun getCollectMoreError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun unCollect(msg: String) {
        ToastUtilKt.showCenterToast(msg)
        mCollectAdapter.remove(mPosition)
//        mCollectAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.WEB_URL, mDataList[position].link)
        intent.putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
        startActivity(intent)
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {
        if (CURRENT_SIZE < TOTAL_COUNTER) {
            mCollectAdapter.loadMoreModule?.loadMoreEnd(true)
        } else {
            CURRENT_PAGE++
            mCollectPresenter.getCollectMoreList(CURRENT_PAGE)
        }
    }

    /**
     * 下拉刷新
     */
    override fun onRefresh() {
        swipe_refresh.postDelayed({
            CURRENT_PAGE = 0
            mCollectPresenter.getCollectList(CURRENT_PAGE)
            swipe_refresh.isRefreshing = false
        }, 1500)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mPosition = position

        val oid: Int = if (-1 < mDataList[position].originId)
            mDataList[position].originId
        else
            -1

        mCollectPresenter.unCollect(mDataList[position].id, oid)
    }

}
