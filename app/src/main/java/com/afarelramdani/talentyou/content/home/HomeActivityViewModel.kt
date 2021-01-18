package com.afarelramdani.talentyou.content.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HomeActivityViewModel: ViewModel(), CoroutineScope {
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences
    val isGetLiveData = MutableLiveData<Boolean>()

    override val coroutineContext: CoroutineContext

        get() =  Job() + Dispatchers.Main

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun setRegisterService(service: ApiService) {
        this.service = service
    }

    fun getEngineerByAccountId() {

        launch {
            val response = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getEngineerIdByAccountId(sharedPref.getAccountId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isGetLiveData.value = false
                    }
                }
            }

            if (response is DataEngineerRepsonse) {
                Log.d("Data Engineer", response.toString())
                sharedPref.setEngineerData(
                    response.data.engineerId,
                    response.data.engineerPhoto,
                    response.data.engineerJobTitle)
            }

        }

    }

    fun getCompanyByAccountId() {
        launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getCompanyIdByAccountId(sharedPref.getAccountId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isGetLiveData.value = false
                    }
                }
            }

            if (response is DataRecruiterResponse) {
                if (response.success) {
                    isGetLiveData.value = true
                    Log.d("Data Company", response.toString())
                    sharedPref.setCompanyData(
                        response.data.companyId,
                        response.data.companyName,
                        response.data.companyPosition,
                        response.data.companyPart,
                        response.data.companyLocation,
                        response.data.companyDesc,
                        response.data.companyInstagram,
                        response.data.companyLinkedin,
                        response.data.companyFoto)
                } else{
                    isGetLiveData.value = false
                }


            }

        }

    }


}