package com.afarelramdani.talentyou.content.experience.deleteexperience

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DeleteExperienceViewModel: ViewModel(), CoroutineScope {
    val isDeleteExperienceLiveData = MutableLiveData<Boolean>()
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

    fun deleteExpById(id: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.deleteExperience(id)
                } catch (e:Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isDeleteExperienceLiveData.value = false
                    }
                }
            }

            if (result is DeleteExperienceResponse) {
                if (result.success) {
                    isDeleteExperienceLiveData.value = true
                } else{
                    isDeleteExperienceLiveData.value = false
                }
            }
        }
    }
}