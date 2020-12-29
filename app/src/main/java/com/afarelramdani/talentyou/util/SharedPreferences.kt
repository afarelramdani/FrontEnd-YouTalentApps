package com.afarelramdani.talentyou.util

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString

class SharedPreferences(private val context: Context) {
   companion object {
       private val SHARED_PREF_NAME = "My Shared"
       private val KEY_AC_NAME = "AC NAME"
       private val TOKEN = "TOKEN"
       private val KEY_EMAIL = "EMAIL"
       private val KEY_LOGIN = "LOGIN"
       private val GITHUB = "GITHUB"

   }

   private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
       SHARED_PREF_NAME, Context.MODE_PRIVATE)

   private val edit: SharedPreferences.Editor = sharedPreferences.edit()

    fun createAccountUser(acName: String, acEmail: String, token: String) {
        edit.putString(KEY_AC_NAME, acName)
        edit.putString(KEY_EMAIL, acEmail)
        edit.putString(TOKEN, token)
        edit.commit()
    }


    fun getAccountName(): String {
        var name = sharedPreferences.getString(KEY_AC_NAME, "Not set")!!
        return name
    }

    fun getToken(): String {
        var token = sharedPreferences.getString(TOKEN, "Not set")!!
        return token
    }

    fun getAccountEmail(): String {
        var email = sharedPreferences.getString(KEY_EMAIL, "Not set")!!
        return email
    }

    fun isLogin(): Boolean {
        var isLogin = sharedPreferences.getBoolean(KEY_LOGIN, false)
        return isLogin
    }

    fun Remember(checked: Boolean) {
        edit.putBoolean(KEY_LOGIN, checked)
        edit.commit()
    }

    fun Github(github: String) {
        edit.putString(GITHUB, github)
        edit.commit()
    }

    fun getGithub(): String {
        var github = sharedPreferences.getString(GITHUB, "Not set")!!
        return github
    }

    fun clear() {
        edit.clear().apply()
    }

}