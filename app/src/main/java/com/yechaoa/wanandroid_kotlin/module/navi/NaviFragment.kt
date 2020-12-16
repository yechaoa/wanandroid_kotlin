package com.yechaoa.wanandroid_kotlin.module.navi

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.ArticleX
import com.yechaoa.wanandroid_kotlin.bean.Navi
import com.yechaoa.wanandroid_kotlin.module.detail.DetailActivity
import com.yechaoa.yutilskt.ToastUtilKt
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import kotlinx.android.synthetic.main.fragment_navi.*
import q.rorbin.verticaltablayout.VerticalTabLayout
import q.rorbin.verticaltablayout.adapter.TabAdapter
import q.rorbin.verticaltablayout.widget.ITabView.*
import q.rorbin.verticaltablayout.widget.TabView

/**
 * A simple [Fragment] subclass.
 */
class NaviFragment : BaseFragment(), INaviView, VerticalTabLayout.OnTabSelectedListener, TagFlowLayout.OnTagClickListener {

    private lateinit var mNaviPresenter: NaviPresenter
    private lateinit var mNaviList: MutableList<Navi>

    private lateinit var mArticles: MutableList<ArticleX>

    override fun createPresenter() {
        mNaviPresenter = NaviPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_navi
    }

    override fun initView() {

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
        mArticles = mNaviList[0].articles
        setFlowLayout(mArticles)
    }

    /**
     * 填充FlowLayout数据
     */
    private fun setFlowLayout(articles: MutableList<ArticleX>) {
        flow_layout.adapter = object : TagAdapter<ArticleX>(articles) {
            override fun getView(parent: FlowLayout, position: Int, s: ArticleX): View {
                val tvTag = LayoutInflater.from(activity).inflate(
                    R.layout.item_navi,
                    flow_layout, false
                ) as TextView
                tvTag.text = s.title
                return tvTag
            }
        }
        //设置点击事件
        flow_layout.setOnTagClickListener(this)
    }

    override fun getNaviError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

    /**
     * Tab选中
     */
    override fun onTabSelected(tab: TabView?, position: Int) {
        mArticles = mNaviList[position].articles
        setFlowLayout(mArticles)
    }

    /**
     * Tab选中Tab再次选中
     */
    override fun onTabReselected(tab: TabView?, position: Int) {

    }

    /**
     * 标签点击事件
     */
    override fun onTagClick(view: View?, position: Int, parent: FlowLayout?): Boolean {
        val intent = Intent(mContext, DetailActivity::class.java).apply {
            putExtra(DetailActivity.WEB_URL, mArticles[position].link)
            putExtra(DetailActivity.WEB_TITLE, mArticles[position].title)
        }
        startActivity(intent)
        return true
    }

}
