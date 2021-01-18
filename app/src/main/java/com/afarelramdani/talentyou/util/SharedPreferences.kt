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
       private val AC_NO_HP = "ACCOUNT NO HP"
       private val AC_ID= "ACCOUNT ID COMPANY"
       private val CN_ID = "COMPANY ID"
       private val EN_ID = "ENGINEER ID"
       private val DETAIL_EN_ID = "ENGINEER ID"
       private val JOB_TITLE = "JOB_TITTLE"
       private val PROJECT_ID = "PROJECT ID"
       private val IMAGE_PROFILE = "IMAGE PROFILE"
       private val CN_NAME = "COMPANY NAME"
       private val CN_POSITION = "COMPANYA POSITION"
       private val CN_PART = "CN PART"
       private val CN_CITY =" CN CITY"
       private val CN_DESC = "CN DESC"
       private val CN_INSTAGRAM = "CN INSTAGRAM"
       private val CN_LINKEDIN = "CN LINKEDIN"
       private val TEMP_PASSWORD = "TEMP PASSWORD"

   }

   private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
       SHARED_PREF_NAME, Context.MODE_PRIVATE)

   private val edit: SharedPreferences.Editor = sharedPreferences.edit()

    fun createAccountUser(acId: Int, acName: String,  acNoHp: String, acEmail: String, acLevel: Int, token: String) {
        edit.putString(KEY_AC_NAME, acName)
        edit.putString(AC_NO_HP, acNoHp)
        edit.putString(KEY_EMAIL, acEmail)
        edit.putInt(AC_LEVEL, acLevel)
        edit.putInt(AC_ID, acId)
        edit.putString(TOKEN, token)
        edit.commit()
    }

    fun createTempPassword(passwordTemp: String) {
        edit.putString(TEMP_PASSWORD, passwordTemp)
        edit.commit()
    }

    fun getTempPassword(): String {
        var tempPassword= sharedPreferences.getString(TEMP_PASSWORD, "")!!
        return tempPassword
    }

    fun setEnginnerId(engineerId: Int) {
        edit.putInt(DETAIL_EN_ID, engineerId)
        edit.commit()
    }

    fun getDetailEngineerId(): Int {
        var detailEngineerId = sharedPreferences.getInt(DETAIL_EN_ID, 0)!!
        return detailEngineerId
    }

    fun getEngineerId(): Int {
        var engineerId = sharedPreferences.getInt(EN_ID, 0)!!
        return engineerId
    }

    fun getNoHp(): String {
        var nohp = sharedPreferences.getString(AC_NO_HP, "")!!
        return nohp
    }


    fun setProjectId(projectId: Int) {
        edit.putInt(PROJECT_ID, projectId)
        edit.commit()
    }

    fun getProjectId(): Int {
        var projectId = sharedPreferences.getInt(PROJECT_ID, 0)!!
        return projectId
    }


    fun getJobTitle(): String {
        var jobTitle = sharedPreferences.getString(JOB_TITLE, "Not Set")!!
        return jobTitle
    }



    fun setCompanyData(cnId: Int?, cnName:  String?, cnPosition: String?,  cnPart: String?, cnCity: String?, cnDesc: String?, cnInstagram: String?, cnLinkedin: String?, image: String?) {
        edit.putInt(CN_ID, cnId!!)
        edit.putString(CN_NAME, cnName)
        edit.putString(CN_POSITION, cnPosition)
        edit.putString(CN_PART, cnPart)
        edit.putString(CN_CITY, cnCity)
        edit.putString(CN_DESC, cnDesc)
        edit.putString(CN_INSTAGRAM, cnInstagram)
        edit.putString(CN_LINKEDIN, cnLinkedin)
        edit.putString(IMAGE_PROFILE, image)
        edit.commit()
    }

    //data company
    fun getCompanyPosition(): String {
        var companyPosition = sharedPreferences.getString(CN_POSITION, "")!!
        return companyPosition
    }

    fun getCompanyPart(): String {
        var companyPart = sharedPreferences.getString(CN_PART, "")!!
        return companyPart
    }

    fun getCompanyLocation(): String {
        var companyLocation = sharedPreferences.getString(CN_CITY, "")!!
        return companyLocation
    }

    fun getCompanyDesc(): String {
        var companyDesc = sharedPreferences.getString(CN_DESC, "")!!
        return companyDesc
    }

    fun getCompanyInstagram(): String {
        var companyIg = sharedPreferences.getString(CN_INSTAGRAM, "")!!
        return companyIg
    }

    fun getCompanyLinkedin(): String {
        var companyLinkedin = sharedPreferences.getString(CN_LINKEDIN, "")!!
        return companyLinkedin
    }

    fun getCompanyName(): String {
        var companyName = sharedPreferences.getString(CN_NAME, "")!!
        return companyName
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