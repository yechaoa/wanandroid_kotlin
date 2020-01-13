package com.yechaoa.wanandroid_kotlin.base

import io.reactivex.observers.DisposableObserver

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
abstract class BaseObserver<T> : DisposableObserver<T?> {

    protected var view: BaseView?
    private var isShowDialog = false

    constructor(view: BaseView?) {
        this.view = view
    }

    constructor(view: BaseView?, isShowDialog: Boolean) {
        this.view = view
        this.isShowDialog = isShowDialog
    }

    override fun onStart() {
        if (view != null && isShowDialog) {
            view!!.showLoading()
        }
    }

    override fun onNext(o: T) {
        onSuccess(o)
    }

    override fun onError(e: Throwable) {
        if (view != null && isShowDialog) {
            view!!.hideLoading()
        }
        val be: BaseException
//        if (e != null) { //自定义异常
//            if (e is BaseException) {
//                be = e
//                //回调到view层 处理 或者根据项目情况处理
//                if (view != null) { // 处理登录失效 更新
////                    view!!.onErrorCode(BaseBean(be.getErrorCode(), be.getErrorMsg()))
//                } else {
//                    onError(be.getErrorMsg())
//                }
//                //系统异常
//            } else {
//                be = if (e is HttpException) { //HTTP错误
//                    BaseException(BaseException.BAD_NETWORK_MSG)
//                } else if (e is ConnectException || e is UnknownHostException) { //连接错误
//                    BaseException(BaseException.CONNECT_ERROR_MSG, e)
//                } else if (e is InterruptedIOException) { //连接超时
//                    BaseException(BaseException.CONNECT_TIMEOUT_MSG, e)
//                } else if (e is JsonParseException || e is JSONException || e is ParseException) { //解析错误
//                    BaseException(BaseException.PARSE_ERROR_MSG, e)
//                } else {
//                    BaseException(BaseException.OTHER_MSG, e)
//                }
//            }
//        } else {
//            be = BaseException(BaseException.OTHER_MSG)
//        }
//        onError(be.getErrorMsg())

        onError("出现错误")
    }

    override fun onComplete() {
        if (view != null && isShowDialog) {
            view!!.hideLoading()
        }
    }

    abstract fun onSuccess(o: T)
    abstract fun onError(msg: String?)
}