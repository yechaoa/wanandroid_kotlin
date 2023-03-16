package com.yechaoa.wanandroid_kotlin.module.login

import android.content.Intent
import android.view.View
import android.view.inputmethod.EditorInfo
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.base.BaseBean
import com.yechaoa.wanandroid_kotlin.bean.User
import com.yechaoa.wanandroid_kotlin.common.MyConfig
import com.yechaoa.wanandroid_kotlin.module.MainActivity
import com.yechaoa.wanandroid_kotlin.module.register.RegisterActivity
import com.yechaoa.yutilskt.SpUtil
import com.yechaoa.yutilskt.ToastUtil
import com.yechaoa.yutilskt.YUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), ILoginView {

    private lateinit var mLoginPresenter: LoginPresenter

    override fun createPresenter() {
        mLoginPresenter = LoginPresenter(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        setMyTitle("登录")

        tv_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btn_login.setOnClickListener {
            attemptLogin()
        }

        et_password.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                attemptLogin()
                true
            } else
                false
        }
    }

    private fun attemptLogin() {
        YUtils.closeSoftKeyboard()

        et_username.error = null
        et_password.error = null

        val username = et_username.text.toString().trim()
        val password = et_password.text.toString().trim()

        var cancel = false
        var focusView: View? = null

        if (password.isEmpty()) {
            et_password.error = "密码不能为空"
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
            doLogin(username, password)

    }

    private fun doLogin(username: String, password: String) {
        YUtils.showLoading(this, "加载中")
        mLoginPresenter.submit(username, password)
    }

    override fun showLoginSuccess(successMessage: String) {
        YUtils.hideLoading()
        ToastUtil.showCenter(successMessage)
    }

    override fun showLoginFailed(errorMessage: String) {
        YUtils.hideLoading()
        ToastUtil.showCenter(errorMessage)
    }

    override fun doSuccess(user: BaseBean<User>) {
        SpUtil.setBoolean(MyConfig.IS_LOGIN, true)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        mLoginPresenter.unSubscribe()
    }

}
