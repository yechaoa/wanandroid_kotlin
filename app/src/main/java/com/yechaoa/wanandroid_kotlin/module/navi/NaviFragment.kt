package com.yechaoa.wanandroid_kotlin.module.navi


import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.NaviAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Navi
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.fragment_navi.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView.*
import q.rorbin.verticaltablayout.widget.TabView


/**
 * A simple [Fragment] subclass.
 */
class NaviFragment : BaseFragment(), INaviView, VerticalTabLayout.OnTabSelectedListener {

    lateinit var mNaviPresenter: NaviPresenter
    lateinit var mNaviList: MutableList<Navi>

    override fun createPresenter() {
        mNaviPresenter = NaviPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navi
    }

    override fun initView() {
        recycler_view.layoutManager = GridLayoutManager(mContext, 2)
    }

    override fun initData() {
        mNaviPresenter.getNavi()
    }

    override fun getNavi(navi: BaseBean<MutableList<Navi>>) {
        mNaviList = navi.data
        /**
         * 填充数据
         */
        vertical_tab_layout.setTabAdapter(object : TabAdapter {
            override fun getCount(): Int {
                return navi.data.size
            }

            override fun getBadge(position: Int): TabBadge? {
                return null
            }

            override fun getIcon(position: Int): TabIcon? {
                return null
            }

            override fun getTitle(position: Int): TabTitle {
                return TabTitle.Builder()
                    .setContent(navi.data[position].name)
                    .setTextColor(-0xde690d, -0x8a8a8b)
                    .setTextSize(16)
                    .build()
            }

            override fun getBackground(position: Int): Int {
                return 0
            }
        })


        /**
         * 设置点击事件
         */
        vertical_tab_layout.addOnTabSelectedListener(this)

        /**
         * 默认选中第一个
         */
        val naviAdapter = NaviAdapter(mNaviList[0].articles)
        naviAdapter.setOnItemClickListener { adapter, view, pos ->
            ToastUtilKt.showCenterToast(mNaviList[0].articles[pos].title)
        }
        recycler_view.adapter = naviAdapter
    }

    override fun getNaviError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    /**
     * Tab选中
     */
    override fun onTabSelected(tab: TabView?, position: Int) {
        ToastUtilKt.showCenterToast(mNaviList[position].name)

        val naviAdapter = NaviAdapter(mNaviList[position].articles)
        naviAdapter.setOnItemClickListener { adapter, view, pos ->
            ToastUtilKt.showCenterToast(mNaviList[position].articles[pos].title)
        }
        recycler_view.adapter = naviAdapter
    }

    /**
     * Tab选中Tab再次选中
     */
    override fun onTabReselected(tab: TabView?, position: Int) {

    }

}
