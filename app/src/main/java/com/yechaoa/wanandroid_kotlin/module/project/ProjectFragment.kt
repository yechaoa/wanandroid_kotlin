package com.yechaoa.wanandroid_kotlin.module.project


import androidx.fragment.app.Fragment
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.CommonViewPagerAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Project
import com.yechaoa.wanandroid_kotlin.module.project.child.ProjectChildFragment
import com.yechaoa.yutilskt.ToastUtil
import kotlinx.android.synthetic.main.fragment_project.*

/**
 * A simple [Fragment] subclass.
 */
class ProjectFragment : BaseFragment(), IProjectView {

    private lateinit var mProjectPresenter: ProjectPresenter

    override fun createPresenter() {
        mProjectPresenter = ProjectPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_project
    }

    override fun initView() {
        project_tab_layout.setupWithViewPager(project_view_pager)
    }

    override fun initData() {
        mProjectPresenter.getProject()
    }

    override fun getProject(project: BaseBean<MutableList<Project>>) {
        //得到标题集合
        val titles: MutableList<String> = ArrayList()
        for (i in project.data.indices) {
            titles.add(project.data[i].name)
        }

        //创建Fragment集合 并设置为ViewPager
        val commonViewPagerAdapter = CommonViewPagerAdapter(childFragmentManager, titles)
        for (i in titles.indices) {
            commonViewPagerAdapter.addFragment(ProjectChildFragment.newInstance(project.data[i].id))
        }
        project_view_pager.adapter = commonViewPagerAdapter
        project_view_pager.currentItem = 0
    }

    override fun getProjectError(msg: String) {
        ToastUtil.showCenter(msg)
    }

}
