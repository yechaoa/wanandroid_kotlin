package com.yechaoa.wanandroid_kotlin.module.tree


import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.TreeAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Tree
import com.yechaoa.yutilskt.ToastUtilKt
import com.zhy.view.flowlayout.FlowLayout
import kotlinx.android.synthetic.main.fragment_tree.*

/**
 * A simple [Fragment] subclass.
 */
class TreeFragment : BaseFragment(), ITreeView, TreeAdapter.OnItemTagClickListener {

    private lateinit var mTreePresenter: TreePresenter
    private lateinit var mTreeList: MutableList<Tree>
    private var mPosition: Int = 0

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
        mTreeList = tree.data

        val treeAdapter = TreeAdapter(tree.data)

        //点击事件
        treeAdapter.setOnItemClickListener { _, _, position ->
            mPosition = position

            //先重置再赋值，实现类似单选的效果
            if (tree.data[position].isShow) {
                for (i in tree.data.indices) {
                    tree.data[i].isShow = false
                }
            } else {
                for (i in tree.data.indices) {
                    tree.data[i].isShow = false
                }
                tree.data[position].isShow = true
            }
            treeAdapter.notifyDataSetChanged()
        }

        //分割线
        recycler_view.addItemDecoration(
            DividerItemDecoration(
                mContext,
                LinearLayoutManager.VERTICAL
            )
        )

        //子view标签点击事件
        treeAdapter.setOnItemTagClickListener(this)

        recycler_view.adapter = treeAdapter
    }

    override fun getTreeError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    /**
     * 标签点击事件
     */
    override fun onItemTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        ToastUtilKt.showCenterToast(mTreeList[mPosition].children[position].name)
        return true
    }

}
