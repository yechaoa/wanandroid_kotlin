package com.yechaoa.wanandroid_kotlin.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.bean.Tree
import java.util.*

/**
 * Created by yechao on 2020/1/19/019.
 * Describe :
 */
class TreeAdapter(data: MutableList<Tree>) :
    BaseQuickAdapter<Tree, BaseViewHolder>(R.layout.item_tree, data) {

    override fun convert(helper: BaseViewHolder, item: Tree?) {
        helper.setText(R.id.tv_tree_title, item?.name)
        val items: MutableList<String> = ArrayList()
        for (i in item!!.children.indices) {
            items.add(item.children[i].name)
        }
        helper.setText(R.id.tv_tree_items, items.toString())
    }

}