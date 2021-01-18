package com.afarelramdani.talentyou.content.portofolio.addportofolio

import android.util.Log
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.content.projectrecruiter.addproject.AddProjectResponse
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class AddPortofolioViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setService(service: ApiService) {
        this.service = service
    }

    fun addPortofolio(enId: RequestBody, prName: RequestBody, prDesc: RequestBody, prLinkPub: RequestBody, prLinkRepo: RequestBody, prTypeWork: RequestBody, prType: RequestBody, image: MultipartBody.Part) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service?.addPortofolio(enId, prName, prDesc, prLinkPub, prLinkRepo, prTypeWork, prType, image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is AddPortofolioResponse) {
                // Action success
                Log.d("responseAddProject", response.toString())
            }
        }
    }

}