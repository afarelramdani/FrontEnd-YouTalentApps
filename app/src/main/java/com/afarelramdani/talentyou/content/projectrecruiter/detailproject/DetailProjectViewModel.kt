package com.afarelramdani.talentyou.content.projectrecruiter.detailproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.hire.addhire.HireResponse
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectModel
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectResponse
import com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire.DetailHireByProjectResponse
import com.afarelramdani.talentyou.databinding.ActivityDetailProjectBinding
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DetailProjectViewModel: ViewModel(), CoroutineScope {
    val isProjectLiveData = MutableLiveData<Boolean>()
    val isHireLiveData = MutableLiveData<Boolean>()
    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityDetailProjectBinding


    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setBinding(binding:ActivityDetailProjectBinding) {
        this.binding = binding
    }

    fun setService(service: ApiService) {
        this.service = service
    }

    fun setSharedPreference(sharedPreferences: SharedPreferences) {
        this.sharedPref = sharedPreferences
    }

    fun getAllProject(id: Int) {
        launch {
            Log.d("Result Project", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android hans", "CallApi: ${Thread.currentThread().name}")


                try {
                    service?.getProjectByIdProject(id)
                } catch (e: Throwable) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        isProjectLiveData.value = false
                    }
                }
            }

            if (result is ProjectResponse) {
                binding.model = result.data[0]
                binding.tvProjectDeadline.text = result.data[0].projectDeadline.split("T")[0]
                binding.tvProjectName.text = result.data[0].projectName
                binding.tvDesc.text = result.data[0].projectDesc

                val img = "http://3.80.117.134:2000/image/"
                Glide.with(binding.ivProject)
                    .load(img + result.data[0].projectPicture)
                    .placeholder(R.drawable.projectdefault)
                    .error(R.drawable.projectdefault)
                    .into(binding.ivProject)

                isProjectLiveData.value = true
                isProjectLiveData.value = true
            }  else if (result is Throwable) {
                isProjectLiveData.value = false
            }

        }
    }


    fun getHireByProject(id: Int) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.getHireByProjectId(id)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isHireLiveData.value = false
                    }
                }
            }

            if (result is DetailHireByProjectResponse) {
                if (result.success) {
                    if (result.data[0].hireStatus == "approve" || result.data[0].hireStatus == "reject")   {
                        isHireLiveData.value = true
                    } else if (result.data[0].hireStatus == "reject") {
                        isHireLiveData.value = false
                    } else if (result.data[0].hireStatus == "wait") {
                        isHireLiveData.value = false
                    }
                }
            }
        }
    }


}