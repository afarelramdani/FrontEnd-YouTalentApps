package com.afarelramdani.talentyou.content.experience

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ExperienceViewModel: ViewModel(), CoroutineScope {
    val isGetExperienceLiveData = MutableLiveData<Boolean>()
    var listExp = MutableLiveData<List<ExperienceModel>>()

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


    fun getAllExperience(): LiveData<List<ExperienceModel>> {
        return listExp
    }


    fun getListExperience(enId: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getExperience(enId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isGetExperienceLiveData.value = false
                    }
                }
            }

            if (result is ExperienceResponse) {
                if (result.success) {
                    isGetExperienceLiveData.value = true
                    val list = result.data?.map {
                        ExperienceModel(
                            it.experienceId,
                            it.engineerId,
                            it.exPosition,
                            it.experienceCompany,
                            it.experienceStart,
                            it.experienceEnd,
                            it.experienceDesc
                        )
                    }
                    listExp.value = list
                } else{
                    isGetExperienceLiveData.value = false
                }

            }
        }
    }

}