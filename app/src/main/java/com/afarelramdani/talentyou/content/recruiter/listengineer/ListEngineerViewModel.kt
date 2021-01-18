package com.afarelramdani.talentyou.content.recruiter.listengineer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ListEngineerViewModel: ViewModel(), CoroutineScope {
    val isListEngineerData = MutableLiveData<Boolean>()
    var listENG = MutableLiveData<List<ListEngineerModel>>()


    private lateinit var service: ApiService

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun getAllData(): LiveData<List<ListEngineerModel>> {
        return listENG
    }

    fun setLoginService(service: ApiService) {
        this.service = service
    }



    fun getAllEngineer() {
        launch {
            Log.d("android2", "Start: ${Thread.currentThread(). name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAllEngineer()
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isListEngineerData.value = false
                    }
                }
            }

            if (result is ListEngineerResponse) {
                if (result.success) {
                    isListEngineerData.value = true
                    val list = result.data.map{
                        ListEngineerModel(it.engineerId, it.accountId, it.accountName,
                            it.accountEmail, it.accountNoHp, it.engineerJobTittle, it.engineerJobType, it.engineerOrigin,
                            it.engineerDesc, it.engineerFotoProfile)
                    }
                    listENG.value = list
                } else{
                    isListEngineerData.value = false
                }

            }

        }

    }

}