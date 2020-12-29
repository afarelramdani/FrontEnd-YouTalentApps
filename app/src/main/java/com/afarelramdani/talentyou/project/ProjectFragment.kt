package com.afarelramdani.talentyou.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.FragmentProjectBinding
import com.afarelramdani.talentyou.model.project.ProjectModel
import com.afarelramdani.talentyou.model.project.ProjectResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*


class ProjectFragment : BaseFragment<FragmentProjectBinding>()  {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        binding.rvListProject.adapter = ProjectListAdapter()
        binding.rvListProject.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        getAllProject()
        return binding.root
    }

    private fun getAllProject() {
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getProjectById()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ProjectResponse) {
                Log.d("android2", result.toString())
                val list = result.data?.map{
                    ProjectModel(it.projectId, it.companyId, it.projectName, it.proejctDesc, it.projectDeadline)
                }

                (binding.rvListProject.adapter as ProjectListAdapter).addList(list)
            }

        }

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}