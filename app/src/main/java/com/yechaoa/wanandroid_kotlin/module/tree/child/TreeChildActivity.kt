package com.yechaoa.wanandroid_kotlin.module.tree.child

import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.CommonViewPagerAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.bean.Children
import kotlinx.android.synthetic.main.activity_tree_child.*

class TreeChildActivity : BaseActivity() {

    companion object {
        var TITLE: String = "title"
        var CID: String = "cid"
        var POSITION: String = "position"
    }

    override fun createPresenter() {}

    override fun getLayoutId(): Int {
        return R.layout.activity_tree_child
    }

    override fun initView() {
        //初始化toolbar
        val title = intent.getStringExtra(TITLE)
        setMyTitle(title!!)
        setBackEnabled()

        //title集合
        val childList: ArrayList<Children> = intent.getSerializableExtra(CID) as ArrayList<Children>
        val titles = java.util.ArrayList<String>()
        for (i in childList.indices) {
            titles.add(childList[i].name)
        }

        //动态创建fragment
        val commonViewPagerAdapter = CommonViewPagerAdapter(supportFragmentManager, titles)
        for (i in titles.indices) {
            commonViewPagerAdapter.addFragment(TreeChildFragment.newInstance(childList[i].id))
        }

        view_pager.adapter = commonViewPagerAdapter

        //设置当前显示位置
        val index = intent.getIntExtra(POSITION, 0)
        view_pager.currentItem = index

        tab_layout.setupWithViewPager(view_pager)

    }
}
