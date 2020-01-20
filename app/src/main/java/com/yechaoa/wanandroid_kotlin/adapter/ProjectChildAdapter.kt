package com.yechaoa.wanandroid_kotlin.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.bean.DataX

/**
 * Created by yechao on 2020/1/19/019.
 * Describe :
 */
class ProjectChildAdapter(data: MutableList<DataX>) :
    BaseQuickAdapter<DataX, BaseViewHolder>(R.layout.item_project_child, data) {

    override fun convert(helper: BaseViewHolder, item: DataX?) {
        Glide.with(context).load(item?.envelopePic).into(helper.getView(R.id.iv_project_img))
        helper.setText(R.id.tv_project_title, item?.title)
        helper.setText(R.id.tv_project_desc, item?.desc)
        helper.setText(R.id.tv_project_date, item?.niceDate)
        helper.setText(R.id.tv_project_author, item?.author)
    }

}