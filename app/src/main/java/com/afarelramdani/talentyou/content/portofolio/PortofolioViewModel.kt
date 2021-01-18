package com.afarelramdani.talentyou.content.portofolio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.content.experience.ExperienceModel
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PortofolioViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences
    val isGetPortofolioLiveData = MutableLiveData<Boolean>()
    var listPorto = MutableLiveData<List<PortofolioModel>>()


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setLoginService(service: ApiService) {
        this.service = service
    }

    fun getAllPorto(): LiveData<List<PortofolioModel>> {
        return listPorto
    }

    fun getListPortofolio() {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getPortofolio(sharedPref.getEngineerId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isGetPortofolioLiveData.value = false
                    }
                }
            }

            if (result is PortofolioResponse) {
                if (result.success) {
                    isGetPortofolioLiveData.value = true
                    val list = result.data?.map {
                        PortofolioModel(it.portofolioId, it.engineerId, it.portofolioName, it.portofolioDesc, it.portofolioLinkPub, it.portofolioLinkRepo, it.portofolioTypeWork, it.portofolioType, it.portofolioPicture)
                    }
                    listPorto.value = list
                } else{
                    isGetPortofolioLiveData.value = false
                }

            }
        }
    }

}