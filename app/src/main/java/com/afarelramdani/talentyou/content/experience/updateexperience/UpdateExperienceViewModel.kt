package com.afarelramdani.talentyou.content.experience.updateexperience

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class UpdateExperienceViewModel: ViewModel(), CoroutineScope {
    val isUpdateExperienceLiveData = MutableLiveData<Boolean>()
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

    fun updateExperience(
        id: Int,
        exPosition: String,
        exCompany: String,
        exStart: String,
        exEnd: String,
        exDesc: String
        ) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateExperience(id, exPosition, exCompany, exStart, exEnd, exDesc)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isUpdateExperienceLiveData.value = false
                    }
                }
            }
            if (result is UpdateExperienceResponse) {
                if (result.success) {
                    isUpdateExperienceLiveData.value = true
                } else{
                    isUpdateExperienceLiveData.value = false
                }
            }
        }
    }
}