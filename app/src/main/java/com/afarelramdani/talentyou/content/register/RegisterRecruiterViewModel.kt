package com.afarelramdani.talentyou.content.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RegisterRecruiterViewModel: ViewModel(), CoroutineScope {
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

    fun registerAccountRecruiter(name: String, noHp: String, email: String,  password: String, accountLevel: Int, companyName: String, companyPosition: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.registerRecruiter(
                        name,
                        noHp,
                        email,
                        password,
                        accountLevel,
                        companyName,
                        companyPosition,
                    )
                } catch (t: Throwable) {
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