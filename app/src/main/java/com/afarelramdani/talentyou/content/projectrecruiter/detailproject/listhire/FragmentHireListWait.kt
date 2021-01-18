package com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire

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
import com.afarelramdani.talentyou.databinding.FragmentHireListWaitBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*

class FragmentHireListWait : BaseFragment<FragmentHireListWaitBinding>() {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope

    var listHire = ArrayList<DetailHireByProjectModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_list_wait,container,false)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)

        var sharePref = context?.let { SharedPreferences(it) }!!
        val pjId = sharePref.getProjectId()
        getListHireByProject(pjId)

        binding.rvListHireWait.adapter = ListHireByProjectRecyclerViewAdapter(listHire)
        binding.rvListHireWait.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        return binding.root

    }

    private fun getListHireByProject(id : Int) {
        var mutable: MutableList<DetailHireByProjectModel>
        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getHireByProjectId(id )
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is DetailHireByProjectResponse) {
                Log.d("Detail Hire Wait", result.toString())
                val list = result.data?.map {
                    DetailHireByProjectModel(it.hireId, it.engineerId, it.projectId, it.hirePrice, it.hireMessage, it.hireStatus, it.hireDateConfirm, it.hireCreated, it.engineerName, it.engineerJobTitle, it.engineerPhoto)
                }
                mutable = list!!.toMutableList()
                mutable.removeAll { it.hireStatus != "wait"}
                (binding.rvListHireWait.adapter as ListHireByProjectRecyclerViewAdapter).addList(mutable)
            }
        }
    }

}