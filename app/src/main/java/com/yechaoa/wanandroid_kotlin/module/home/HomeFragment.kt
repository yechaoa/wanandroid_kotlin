package com.yechaoa.wanandroid_kotlin.module.home

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.adapter.ArticleAdapter
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.base.BaseFragment
import com.yechaoa.wanandroid_kotlin.bean.Article
import com.yechaoa.wanandroid_kotlin.bean.ArticleDetail
import com.yechaoa.wanandroid_kotlin.bean.Banner
import com.yechaoa.wanandroid_kotlin.utils.GlideImageLoader
import com.yechaoa.yutilskt.ToastUtilKt
import com.yechaoa.yutilskt.YUtilsKt
import com.youth.banner.BannerConfig
import com.youth.banner.listener.OnBannerListener
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), IHomeView, OnBannerListener {

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
        mHomePresenter.getBanner()
        mHomePresenter.getArticleList(0)
    }

    override fun getBanner(banners: BaseBean<List<Banner>>) {
        val images: MutableList<String> = ArrayList()
        val titles: MutableList<String> = ArrayList()
        for (i in banners.data.indices) {
            images.add(banners.data[i].imagePath)
            titles.add(banners.data[i].title)
        }

        //动态设置高度
        val layoutParams = banner.layoutParams
        layoutParams.height = (YUtilsKt.getScreenWidth() / 1.8).roundToInt()

        banner
            .setImages(images)
            .setBannerTitles(titles)
            .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            .setImageLoader(GlideImageLoader())
            .start()

        banner.setOnBannerListener(this)
    }

    override fun getBannerError(msg: String) {
        ToastUtilKt.showCenterToast(msg)
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

    override fun onDestroyView() {
        super.onDestroyView()
        //结束轮播
        banner.stopAutoPlay()
    }

    override fun OnBannerClick(position: Int) {
        ToastUtilKt.showToast("$position")
    }

}

