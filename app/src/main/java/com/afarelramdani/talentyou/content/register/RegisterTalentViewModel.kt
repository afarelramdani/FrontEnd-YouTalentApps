package com.afarelramdani.talentyou.content.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.content.login.LoginActivity
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterTalentViewModel: ViewModel(), CoroutineScope {
    val isRegisterLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setRegisterService(service: ApiService) {
        this.service = service
    }

    fun registerAccountTalent(name: String, email: String,  noHp: String, password: String, accountLevel: Int) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.registerTalent(
                        name,
                        email,
                        noHp,
                        password,
                        accountLevel

                    )
                } catch (t: Throwable) {
                    t.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isRegisterLiveData.value = false
                    }
                }
            }

            if (response is RegisterResponse) {
                if(response?.success) {
                    isRegisterLiveData.value = true
                } else {
                    isRegisterLiveData.value = false
                }

            }
        }
    }
}