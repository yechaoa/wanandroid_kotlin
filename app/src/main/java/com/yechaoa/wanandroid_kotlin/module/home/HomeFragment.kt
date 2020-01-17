package com.yechaoa.wanandroid_kotlin.module.home

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ArticleAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.ArticleDetail
import com.yechaoa.yutilskt.ToastUtilKt
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), IHomeView {

    lateinit var mHomePresenter: HomePresenter

    override fun createPresenter() {
        mHomePresenter = HomePresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(mContext)
    }

    override fun initData() {
        mHomePresenter.getArticleList(0)
    }

    override fun getArticleList(article: BaseBean<Article>) {
        val datas = article.data.datas
        val articleAdapter = ArticleAdapter(datas as MutableList<ArticleDetail>)
        articleAdapter.animationEnable = true
        articleAdapter.setOnItemClickListener { adapter, view, position ->
            ToastUtilKt.showCenterToast(datas[position].title + "---$position")
        }
        articleAdapter.setOnItemChildClickListener { adapter, view, position ->
            ToastUtilKt.showCenterToast("收藏$position")
        }
        recycler_view.adapter = articleAdapter
    }

    override fun getArticleError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
    }

}
