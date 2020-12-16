package com.yechaoa.wanandroid_kotlin.module.tree.child


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ArticleAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.ArticleDetail
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.wanandroid_kotlin.module.login.LoginActivity
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.fragment_project_child.*

/**
 * A simple [Fragment] subclass.
 */
class TreeChildFragment : BaseFragment(), ITreeChildView, OnLoadMoreListener, OnItemClickListener,
    OnItemChildClickListener {

    companion object {

        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 0//当前加载页数

        const val CID: String = "cid"

        /**
         * 创建fragment
         */
        fun newInstance(cid: Int): TreeChildFragment {
            val projectChildFragment = TreeChildFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            projectChildFragment.arguments = bundle
            return projectChildFragment
        }
    }

    private var mCid: Int = 0
    private lateinit var mTreeChildPresenter: TreeChildPresenter
    private lateinit var mDataList: MutableList<ArticleDetail>
    private lateinit var mArticleAdapter: ArticleAdapter
    private var mPosition: Int = 0

    override fun createPresenter() {
        mTreeChildPresenter = TreeChildPresenter(this)
    }

    //layout可以复用
    override fun getLayoutId(): Int {
        return R.layout.fragment_project_child
    }

    override fun initView() {
    }

    override fun initData() {
        mCid = arguments?.getInt(CID)!!
        mTreeChildPresenter.getTreeChild(CURRENT_PAGE, mCid)
    }

    override fun getTreeChild(treeChild: BaseBean<Article>) {
        CURRENT_SIZE = treeChild.data.datas.size
        mDataList = treeChild.data.datas
        mArticleAdapter = ArticleAdapter().apply {
            setOnItemClickListener(this@TreeChildFragment)
            setOnItemChildClickListener(this@TreeChildFragment)
            loadMoreModule.setOnLoadMoreListener(this@TreeChildFragment)
        }
        recycler_view.adapter = mArticleAdapter
        mArticleAdapter.setList(treeChild.data.datas)
    }

    override fun getTreeChildError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun getTreeMoreChild(treeChild: BaseBean<Article>) {
        CURRENT_SIZE = treeChild.data.datas.size
        mDataList.addAll(treeChild.data.datas)
        mArticleAdapter.addData(treeChild.data.datas)
        mArticleAdapter.loadMoreModule.loadMoreComplete()
    }

    override fun getTreeChildMoreError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun login(msg: String) {
        showLoginDialog(msg)
    }

    private fun showLoginDialog(msg: String) {
        val builder = AlertDialog.Builder(mContext).apply {
            setTitle("提示")
            setMessage(msg)
            setPositiveButton("确定") { _, _ ->
                startActivity(Intent(mContext, LoginActivity::class.java))
            }
            setNegativeButton("取消", null)
        }
        builder.create().show()
    }

    override fun collect(msg: String) {
        ToastUtilKt.showCenterToast(msg)
        mDataList[mPosition].collect = true
        mArticleAdapter.notifyDataSetChanged()
    }

    override fun unCollect(msg: String) {
        ToastUtilKt.showCenterToast(msg)
        mDataList[mPosition].collect = false
        mArticleAdapter.notifyDataSetChanged()
    }

    override fun onLoadMore() {
        if (CURRENT_SIZE < TOTAL_COUNTER) {
            mArticleAdapter.loadMoreModule.loadMoreEnd(true)
        } else {
            CURRENT_PAGE++
            mTreeChildPresenter.getTreeMoreChild(CURRENT_PAGE, mCid)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val intent = Intent(mContext, DetailActivity::class.java).apply {
            putExtra(DetailActivity.WEB_URL, mDataList[position].link)
            putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
        }
        startActivity(intent)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        mPosition = position
        if (mDataList[position].collect) {
            mTreeChildPresenter.unCollect(mDataList[position].id)
        } else {
            mTreeChildPresenter.collect(mDataList[position].id)
        }
    }

}
