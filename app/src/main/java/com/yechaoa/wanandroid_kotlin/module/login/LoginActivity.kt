package com.yechaoa.wanandroid_kotlin.module.login

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.yechaoa.wanandroid_kotlin.R
import com.yechaoa.wanandroid_kotlin.base.BaseActivity
import com.yechaoa.wanandroid_kotlin.module.MainActivity
import com.yechaoa.yutilskt.YUtilsKt
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        btn_login.setOnClickListener {
            Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show()
            attemptLogin()
        }
    }

    private fun attemptLogin() {
        closeSoftKeyboard()

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

        YUtilsKt.showLoading(this, "测试加载中")

        Toast.makeText(this, "账号---$username，密码---$password", Toast.LENGTH_SHORT).show()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    private fun closeSoftKeyboard() {
        val inputManger = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManger.hideSoftInputFromWindow(this.window.decorView.windowToken, 0)
    }


}
