package com.afarelramdani.talentyou.content.projectrecruiter.addproject

import android.util.Log
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddProjectViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ApiService) {
        this.service = service
    }


    fun addProject(cnId: RequestBody, name: RequestBody, desc: RequestBody, deadline: RequestBody, image: MultipartBody.Part) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.addProject(cnId, name, desc, deadline, image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is AddProjectResponse) {
                // Action success
                Log.d("responseAddProject", response.toString())
            }
        }
    }
}