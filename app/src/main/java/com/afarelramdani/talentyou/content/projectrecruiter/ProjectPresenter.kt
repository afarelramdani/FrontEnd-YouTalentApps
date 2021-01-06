package com.afarelramdani.talentyou.content.projectrecruiter

import android.content.Context
import android.util.Log
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProjectPresenter(private val coroutineScope: CoroutineScope,
                       private val service: ApiService?, private val sharePref: SharedPreferences?) : ProjectContract.Presenter {


    private var view: ProjectContract.View? = null

    override fun bindToView(view: ProjectContract.View) {
        this.view = view
    }

    override fun unbind() {
        this.view = null
    }

    override fun getAllProject() {

        coroutineScope.launch {
            Log.d("Result Project", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android hans", "CallApi: ${Thread.currentThread().name}")


                try {
                    service?.getProjectById(sharePref?.getCompanyId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ProjectResponse) {
                Log.d("android res", result.toString())
                val list = result.data?.map{
                    ProjectModel(it.projectId, it.companyId, it.projectName, it.projectDesc, it.projectPicture, it.projectDeadline)
                }
                view?.addListProject(list)

            }  else if (result is Throwable) {
                Log.e("android1", result.message ?: "Error")
            }

        }
    }

}