package com.yechaoa.wanandroid_kotlin.module.tree


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.TreeAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Tree
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.fragment_tree.*

/**
 * A simple [Fragment] subclass.
 */
class TreeFragment : BaseFragment(), ITreeView {

    private lateinit var mTreePresenter: TreePresenter

    override fun createPresenter() {
        mTreePresenter = TreePresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_tree
    }

    override fun initView() {
        initSwipeRefreshLayout()
        recycler_view.layoutManager = LinearLayoutManager(mContext)
    }

    private fun initSwipeRefreshLayout() {
        swipe_refresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        )
        swipe_refresh.setOnRefreshListener {
            swipe_refresh.postDelayed({
                mTreePresenter.getTree()
                swipe_refresh.isRefreshing = false
            }, 1500)
        }
    }

    override fun initData() {
        mTreePresenter.getTree()
    }

    override fun getTree(tree: BaseBean<MutableList<Tree>>) {
        val treeAdapter = TreeAdapter(tree.data)
        treeAdapter.setOnItemClickListener { _, _, position ->
            ToastUtilKt.showCenterToast(tree.data[position].name)
        }
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                mContext,
                LinearLayoutManager.VERTICAL
            )
        )
        recycler_view.adapter = treeAdapter
    }

    override fun getTreeError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

}
