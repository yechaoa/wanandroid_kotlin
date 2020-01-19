package com.yechaoa.wanandroid_kotlin.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.bean.ArticleX

/**
 * Created by yechao on 2020/1/19/019.
 * Describe :
 */
class NaviAdapter(data: MutableList<ArticleX>) :
    BaseQuickAdapter<ArticleX, BaseViewHolder>(R.layout.item_navi, data) {

    override fun convert(helper: BaseViewHolder, item: ArticleX?) {
        helper.setText(R.id.chip, item?.title)
    }

}