package com.yechaoa.wanandroid_kotlin.http.cookie

import android.content.Context
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * Created by yechao on 2020/1/9/009.
 * Describe :
 */
class CookiesManager(context: Context?) : CookieJar {

    private val cookieStore: PersistentCookieStore

    override fun saveFromResponse(
        url: HttpUrl?,
        cookies: List<Cookie?>
    ) {
        if (cookies.isNotEmpty()) {
            for (item in cookies) {
                cookieStore.add(url!!, item!!)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl?): List<Cookie> {
        return cookieStore.get(url!!)
    }

    init {
        cookieStore = PersistentCookieStore(context!!)
    }
}


