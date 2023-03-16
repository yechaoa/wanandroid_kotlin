package com.yechaoa.wanandroid_kotlin.module.register

import android.view.View
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.User
import com.yechaoa.yutilskt.ToastUtil
import com.yechaoa.yutilskt.YUtils
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.activity_login.et_username
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), IRegisterView {

    private lateinit var mRegisterPresenter: RegisterPresenter

    override fun createPresenter() {
        mRegisterPresenter = RegisterPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_register
    }

    override fun initView() {
        setMyTitle("注册")
        setBackEnabled()

        btn_register.setOnClickListener {
            attemptRegister()
        }
    }

    private fun attemptRegister() {
        YUtils.closeSoftKeyboard()

        et_username.error = null
        et_password.error = null
        et_repassword.error = null

        val username = et_username.text.toString().trim()
        val password = et_password.text.toString().trim()
        val repassword = et_repassword.text.toString().trim()

        var cancel = false
        var focusView: View? = null

        if (repassword.isEmpty()) {
            et_password.error = "确认密码不能为空"
            focusView = et_password
            cancel = true
        }

        if (password.isEmpty()) {
            et_password.error = "密码不能为空"
            focusView = et_password
            cancel = true
        }

        if (password != repassword) {
            et_password.error = "两次密码不一致"
            focusView = et_password
            cancel = true
        }

        if (username.isEmpty()) {
            et_username.error = "账号不能为空"
            focusView = et_username
            cancel = true
        }

        if (cancel)
            focusView?.requestFocus()
        else
            doRegister(username, password, repassword)

    }

    private fun doRegister(
        username: String,
        password: String,
        repassword: String
    ) {
        YUtils.showLoading(this, "测试加载中")
        mRegisterPresenter.submit(username, password, repassword)
    }

    override fun showRegisterSuccess(successMessage: String) {
        YUtils.hideLoading()
        ToastUtil.showCenter(successMessage)
    }

    override fun showRegisterFailed(errorMessage: String) {
        YUtils.hideLoading()
        ToastUtil.showCenter(errorMessage)
    }

    override fun doSuccess(user: BaseBean<User>) {
        ToastUtil.showCenter("注册成功，请登录")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mRegisterPresenter.unSubscribe()
    }

}
