package com.afarelramdani.talentyou.content.account

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceResponse
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UpdateAccountViewModel: ViewModel(), CoroutineScope {
    val isUpdateProfileLiveData = MutableLiveData<Boolean>()
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

    fun updateAccount(
        id: Int,
        acName: String,
        email: String,
        acNoHp: String
    ) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateAccount(id, acName, email, acNoHp)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isUpdateProfileLiveData.value = false
                    }
                }
            }
            if (result is ResultResponse) {
                Log.d("this", result.toString())
                if (result.success) {
                    isUpdateProfileLiveData.value = true
                } else{
                    isUpdateProfileLiveData.value = false
                }
            }
        }
    }

}