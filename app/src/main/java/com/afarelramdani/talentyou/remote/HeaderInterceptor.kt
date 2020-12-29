package com.afarelramdani.talentyou.remote

import android.content.Context
import android.util.Log
import com.afarelramdani.talentyou.util.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor(private val context: Context) : Interceptor {
    private lateinit var sharePref: SharedPreferences
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        sharePref = SharedPreferences(context)
        val token = sharePref.getToken()
        val tokenAuth = token
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $tokenAuth")
                .build()

        )
    }

}