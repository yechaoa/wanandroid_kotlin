package com.yechaoa.wanandroid_kotlin.base

import java.io.IOException

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class BaseException : IOException() {

    companion object {
        /**
         * 解析数据失败
         */
        val PARSE_ERROR_MSG = "解析数据失败"
        /**
         * 网络问题
         */
        val BAD_NETWORK_MSG = "网络问题"
        /**
         * 连接错误
         */
        val CONNECT_ERROR_MSG = "连接错误"
        /**
         * 连接超时
         */
        val CONNECT_TIMEOUT_MSG = "连接超时"
        /**
         * 未知错误
         */
        val OTHER_MSG = "未知错误"
    }

    private var errorMsg: String? = null
    private var errorCode = 0

    fun getErrorMsg(): String? {
        return errorMsg
    }

    fun getErrorCode(): Int {
        return errorCode
    }

    fun BaseException(message: String?) {
        errorMsg = message
    }

    fun BaseException(errorCode: Int, message: String?) {
        errorMsg = message
        this.errorCode = errorCode
    }

}