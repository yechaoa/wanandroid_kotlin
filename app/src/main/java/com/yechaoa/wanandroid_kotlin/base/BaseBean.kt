package com.yechaoa.wanandroid_kotlin.base

/**
 * Created by yechao on 2020/1/8/008.
 * Describe :
 */

data class BaseBean<T>(
    val errorMsg: String,
    val errorCode: Int,
    val data: T
)
