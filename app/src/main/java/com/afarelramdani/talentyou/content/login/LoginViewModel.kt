package com.afarelramdani.talentyou.content.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class LoginViewModel: ViewModel(), CoroutineScope {
    val isLoginLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setLoginService(service: ApiService) {
        this.service = service
    }

    fun loginAccountTalent(email: String ,password: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.loginAccount(email, password)
                } catch (t: Throwable) {
                    Log.e("Connetion Message", "${t.message}")
                }
            }

            if (response is LoginResponse) {
                if (response.success) {
                    Log.d("android hans", response.toString())
                    var response = response.data
                    sharedPref.Remember(true)
                    sharedPref.createAccountUser(response.acId, response.acName, response.acEmail, response.acLevel, response.token)
                    isLoginLiveData.value = true
                } else {
                    isLoginLiveData.value = false
                }


            }
        }
    }
}