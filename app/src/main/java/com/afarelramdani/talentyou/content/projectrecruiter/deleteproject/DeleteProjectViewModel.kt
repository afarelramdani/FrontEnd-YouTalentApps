package com.afarelramdani.talentyou.content.projectrecruiter.deleteproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.content.experience.deleteexperience.DeleteExperienceResponse
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DeleteProjectViewModel: ViewModel(), CoroutineScope {
    val isDeleteProjectLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setDeleteService(service: ApiService) {
        this.service = service
    }

    fun deleteProjectById(id: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.deleteProject(id)
                } catch (e:Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isDeleteProjectLiveData.value = false
                    }
                }
            }

            if (result is DeleteProjectResponse) {
                if (result.success) {
                    isDeleteProjectLiveData.value = true
                } else{
                    isDeleteProjectLiveData.value = false
                }
            }
        }
    }
}