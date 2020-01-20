package com.yechaoa.wanandroid_kotlin.module.project.child


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ProjectChildAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.ProjectChild
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.fragment_project_child.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectChildFragment : BaseFragment(), IProjectChildView {

    companion object {

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

    lateinit var mProjectChildPresenter: ProjectChildPresenter

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
        val cid = arguments?.getInt(CID)
        mProjectChildPresenter.getProjectChild(0, cid!!)
    }

    override fun getProjectChild(projectChild: BaseBean<ProjectChild>) {
        val projectChildAdapter = ProjectChildAdapter(projectChild.data.datas)
        projectChildAdapter.setOnItemClickListener { adapter, view, pos ->
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra(DetailActivity.WEB_URL, projectChild.data.datas[pos].link)
            intent.putExtra(DetailActivity.WEB_TITLE, projectChild.data.datas[pos].title)
            startActivity(intent)
        }
        recycler_view.adapter = projectChildAdapter
    }

    override fun getProjectChildError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

}
