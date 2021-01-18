package com.afarelramdani.talentyou.content.projectrecruiter.updateproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectResponse
import com.afarelramdani.talentyou.databinding.ActivityDetailProjectBinding
import com.afarelramdani.talentyou.databinding.ActivityUpdateProjectBinding
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import kotlin.coroutines.CoroutineContext

class UpdateProjectViewModel: ViewModel(), CoroutineScope {
    val isProjectGetLiveData = MutableLiveData<Boolean>()
    val isProjectUpdateLiveData = MutableLiveData<Boolean>()

    private lateinit var service: ApiService
    private lateinit var sharedPref: SharedPreferences
    private lateinit var binding: ActivityUpdateProjectBinding
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main

    fun setBinding(binding: ActivityUpdateProjectBinding) {
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
                        isProjectUpdateLiveData.value = false
                    }
                }
            }

            if (result is ProjectResponse) {
                binding.model = result.data[0]
                binding.etProjectName.setText(result.data[0].projectName)
                binding.etProjectDesc.setText(result.data[0].projectDesc)

                val img = "http://3.80.117.134:2000/image/"
                Glide.with(binding.ivProjectUpload)
                    .load(img + result.data[0].projectPicture)
                    .placeholder(R.drawable.projectdefault)
                    .error(R.drawable.projectdefault)
                    .into(binding.ivProjectUpload)

                isProjectGetLiveData.value = true
                isProjectGetLiveData.value = true
            }  else if (result is Throwable) {
                isProjectGetLiveData.value = false
            }

        }
    }

    fun updateProject(projectId: Int, cnId: Int, name: RequestBody, desc: RequestBody, deadline: RequestBody, img: MultipartBody.Part) {
        launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service.updateProject(projectId, cnId, name, desc, deadline, img)
                } catch (e: Throwable) {
                    e.printStackTrace()

                    withContext(Dispatchers.Main) {
                        isProjectUpdateLiveData.value = false
                    }
                }
            }

            if (result is UpdateProjectResponse) {
                if (result.success) {
                    isProjectUpdateLiveData.value = true
                } else {
                    isProjectUpdateLiveData.value = false
                }
            }
        }
    }


}