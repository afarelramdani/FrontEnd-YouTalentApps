package com.afarelramdani.talentyou.content.experience.addexperience

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class AddExperienceViewModel: ViewModel(), CoroutineScope {
    val isAddExperienceLiveData = MutableLiveData<Boolean>()
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

    fun AddExperience(enId: Int, exPosition: String, exCompany: String, exStart: String, exEnd: String, exDesc: String) {
        launch {
            val result  = withContext(Dispatchers.IO) {
                try {
                    service.addExperience(enId,exPosition,exCompany,exStart,exEnd,exDesc)
                } catch (t:Throwable) {
                    t.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isAddExperienceLiveData.value = false
                    }
                }
            }
            if (result is AddExperienceResponse) {
                Log.d("android1", result.toString())
                if (result.success) {
                    isAddExperienceLiveData.value = true
                } else {
                    isAddExperienceLiveData.value = false
                }
            }
        }
    }

}