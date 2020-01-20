package com.yechaoa.wanandroid_kotlin.adapter

import android.text.Html
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.bean.ArticleDetail

/**
 * Created by yechao on 2020/1/17/017.
 * Describe :
 */
class ArticleAdapter(data: MutableList<ArticleDetail>) : BaseQuickAdapter<ArticleDetail, BaseViewHolder>(R.layout.item_article, data) {

    override fun convert(helper: BaseViewHolder, item: ArticleDetail?) {
        //fromHtml，因为搜索结果中的title中含有html标签
        helper.setText(R.id.article_title, Html.fromHtml(item!!.title))
        helper.setText(R.id.article_chapter, item.chapterName)
        //helper.setText(R.id.article_author, item.author)
        helper.setText(R.id.article_date, item.niceDate)

        addChildClickViewIds(R.id.article_favorite)
    }
}