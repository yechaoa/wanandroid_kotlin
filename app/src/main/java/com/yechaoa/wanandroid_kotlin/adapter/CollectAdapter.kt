package com.yechaoa.wanandroid_kotlin.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.bean.CollectDetail

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class CollectAdapter(data: MutableList<CollectDetail>) :
    BaseQuickAdapter<CollectDetail, BaseViewHolder>(R.layout.item_article, data), LoadMoreModule {

    override fun convert(helper: BaseViewHolder, item: CollectDetail?) {
        //fromHtml，因为搜索结果中的title中含有html标签
        helper.setText(R.id.article_title, Html.fromHtml(item!!.title))
        helper.setText(R.id.article_chapter, item.chapterName)
        helper.setText(R.id.article_date, item.niceDate)
        Glide.with(context).load(R.drawable.ic_like_checked)
            .into(helper.getView(R.id.article_favorite))
        addChildClickViewIds(R.id.article_favorite)
    }
}