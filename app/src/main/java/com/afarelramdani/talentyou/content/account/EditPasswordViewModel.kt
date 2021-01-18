package com.afarelramdani.talentyou.content.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class EditPasswordViewModel: ViewModel(), CoroutineScope {
    val isUpdatePasswordLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setUpdateProfileService(service: ApiService) {
        this.service = service
    }

    fun updatePassword(
        id: Int,
        acPassword: String
    ) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updatePasswordAccount(id, acPassword)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isUpdatePasswordLiveData.value = false
                    }
                }
            }
            if (result is ResultResponse) {
                Log.d("this", result.toString())
                if (result.success) {
                    isUpdatePasswordLiveData.value = true
                } else{
                    isUpdatePasswordLiveData.value = false
                }
            }
        }
    }
}