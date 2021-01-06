package com.afarelramdani.talentyou.util

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import kotlinx.coroutines.CoroutineScope

class SharedPreferences(private val context: Context) {
   companion object {
       private val SHARED_PREF_NAME = "My Shared"
       private val KEY_AC_NAME = "AC NAME"
       private val TOKEN = "TOKEN"
       private val KEY_EMAIL = "EMAIL"
       private val KEY_LOGIN = "LOGIN"
       private val GITHUB = "GITHUB"
       private val AC_LEVEL = "LEVEL"
       private val AC_ID= "ACCOUNT ID COMPANY"
       private val CN_ID = "COMPANY ID"
       private val EN_ID = "ENGINEER ID"
       private val DETAIL_EN_ID = "ENGINEER ID"
       private val ENGINEER_NAME = "ENGINEER NAME"
       private val ENGINEER_EMAIL = "ENGINEER EMAIL"
       private val JOB_TITLE = "JOB_TITTLE"
       private val PROJECT_ID = "PROJECT ID"
       private val IMAGE_PROFILE = "IMAGE PROFILE"

   }

   private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
       SHARED_PREF_NAME, Context.MODE_PRIVATE)

   private val edit: SharedPreferences.Editor = sharedPreferences.edit()

    fun createAccountUser(acId: Int, acName: String, acEmail: String, acLevel: Int, token: String) {
        edit.putString(KEY_AC_NAME, acName)
        edit.putString(KEY_EMAIL, acEmail)
        edit.putInt(AC_LEVEL, acLevel)
        edit.putInt(AC_ID, acId)
        edit.putString(TOKEN, token)
        edit.commit()
    }

    fun setEnginnerId(engineerId: Int) {
        edit.putInt(DETAIL_EN_ID, engineerId)
        edit.commit()
    }

    fun getEngineerId(): Int {
        var engineerId = sharedPreferences.getInt(DETAIL_EN_ID, 0)!!
        return engineerId
    }


    fun setProjectId(projectId: Int) {
        edit.putInt(PROJECT_ID, projectId)
        edit.commit()
    }

    fun getProjectId(): Int {
        var projectId = sharedPreferences.getInt(PROJECT_ID, 0)!!
        return projectId
    }


    fun getNameDetailEngineer(): String {
        var engineerName = sharedPreferences.getString(ENGINEER_NAME, "Not Set")!!
        return engineerName
    }

    fun getJobTitle(): String {
        var jobTitle = sharedPreferences.getString(JOB_TITLE, "Not Set")!!
        return jobTitle
    }



    fun setCompanyData(cnId: Int, image: String?) {
        edit.putInt(CN_ID, cnId)
        edit.putString(IMAGE_PROFILE, image)
        edit.commit()
    }

    fun setEngineerData(enId: Int, image: String?, jobTitle: String?) {
        edit.putInt(EN_ID, enId)
        edit.putString(IMAGE_PROFILE, image)
        edit.putString(JOB_TITLE, jobTitle)
        edit.commit()
    }

    fun getCompanyId(): Int {
        var companyId = sharedPreferences.getInt(CN_ID, 0)!!
        return companyId
    }

    fun getImageProfile(): String {
        var ImageProfile = sharedPreferences.getString(IMAGE_PROFILE, "NOT SET")!!
        return ImageProfile
    }


    fun getAccountId(): Int {
        var accountId = sharedPreferences.getInt(AC_ID, 0)!!
        return accountId
    }

    fun getAccountLevel(): Int {
        var accountLevel = sharedPreferences.getInt(AC_LEVEL, 0)!!
        return accountLevel
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

    fun Remember(login: Boolean) {
        edit.putBoolean(KEY_LOGIN, login)
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