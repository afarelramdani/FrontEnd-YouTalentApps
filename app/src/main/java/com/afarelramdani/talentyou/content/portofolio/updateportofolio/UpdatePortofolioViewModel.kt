package com.afarelramdani.talentyou.content.portofolio.updateportofolio

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.databinding.ActivityUpdatePortofolioBinding
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class UpdatePortofolioViewModel: ViewModel(), CoroutineScope {
    val isUpdateCompanyLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var image: MultipartBody.Part
    private lateinit var sharedPref: SharedPreferences

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setCompanyService(service: ApiService) {
        this.service = service
    }



    fun setImage(image: MultipartBody.Part) {
        this.image = image
    }


    fun updatePortofolio(type: Int, prId: Int, prName: RequestBody, prDesc: RequestBody, prLinkPub: RequestBody, prLinkRepo: RequestBody, prTypeWork: RequestBody, prType: RequestBody) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    if(type == 1) {
                        service?.updateImagePortofolio(prId, prName, prDesc, prLinkPub, prLinkRepo, prTypeWork, prType, image)
                    } else {
                        service?.updatePortofolio(prId, prName, prDesc, prLinkPub, prLinkRepo, prTypeWork, prType)
                    }
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isUpdateCompanyLiveData.value = false
                    }
                }
            }
            if (result is UpdatePortofolioResponse) {
                if(result.succes) {
                    isUpdateCompanyLiveData.value = true
                } else {
                    isUpdateCompanyLiveData.value = false
                }

            }
        }
    }

}