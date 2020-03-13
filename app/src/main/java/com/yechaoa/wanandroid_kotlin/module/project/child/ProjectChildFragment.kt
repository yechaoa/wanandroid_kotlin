package com.yechaoa.wanandroid_kotlin.module.project.child


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnLoadMoreListener
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ProjectChildAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.DataX
import com.yechaoa.wanandroid_kotlin.bean.ProjectChild
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.fragment_project_child.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectChildFragment : BaseFragment(), IProjectChildView, OnLoadMoreListener,
    OnItemClickListener {

    companion object {

        private const val TOTAL_COUNTER = 20//每次加载数量
        private var CURRENT_SIZE = 0//当前加载数量
        private var CURRENT_PAGE = 1//当前加载页数

        const val CID: String = "cid"

        /**
         * 创建fragment
         */
        fun newInstance(cid: Int): ProjectChildFragment {
            val projectChildFragment = ProjectChildFragment()
            val bundle = Bundle()
            bundle.putInt(CID, cid)
            projectChildFragment.arguments = bundle
            return projectChildFragment
        }
    }

    private var mCid: Int = 0
    private lateinit var mProjectChildPresenter: ProjectChildPresenter
    private lateinit var mDataList: MutableList<DataX>
    private lateinit var mProjectChildAdapter: ProjectChildAdapter

    override fun createPresenter() {
        mProjectChildPresenter = ProjectChildPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project_child
    }

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(mContext)
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                mContext,
                LinearLayoutManager.VERTICAL
            )
        )
    }

    override fun initData() {
        mCid = arguments?.getInt(CID)!!
        mProjectChildPresenter.getProjectChild(CURRENT_PAGE, mCid)
    }

    override fun getProjectChild(projectChild: BaseBean<ProjectChild>) {
        CURRENT_SIZE = projectChild.data.datas.size
        mDataList = projectChild.data.datas
        mProjectChildAdapter = ProjectChildAdapter(projectChild.data.datas)
        mProjectChildAdapter.setOnItemClickListener(this)
        mProjectChildAdapter.loadMoreModule?.setOnLoadMoreListener(this)
        recycler_view.adapter = mProjectChildAdapter
    }

    override fun getProjectChildError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun getProjectMoreChild(projectChild: BaseBean<ProjectChild>) {
        CURRENT_SIZE = projectChild.data.datas.size
        mDataList.addAll(projectChild.data.datas)
        mProjectChildAdapter.addData(projectChild.data.datas)
        mProjectChildAdapter.loadMoreModule?.loadMoreComplete()
    }

    override fun getProjectChildMoreError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    override fun onLoadMore() {
        if (CURRENT_SIZE < TOTAL_COUNTER) {
            mProjectChildAdapter.loadMoreModule?.loadMoreEnd(true)
        } else {
            CURRENT_PAGE++
            mProjectChildPresenter.getProjectMoreChild(CURRENT_PAGE, mCid)
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val intent = Intent(mContext, DetailActivity::class.java)
        intent.putExtra(DetailActivity.WEB_URL, mDataList[position].link)
        intent.putExtra(DetailActivity.WEB_TITLE, mDataList[position].title)
        startActivity(intent)
    }

}
