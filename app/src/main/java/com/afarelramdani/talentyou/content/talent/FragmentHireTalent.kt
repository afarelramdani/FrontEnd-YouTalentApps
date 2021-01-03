package com.afarelramdani.talentyou.content.talent

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
import com.afarelramdani.talentyou.databinding.FragmentHireTalentBinding
import com.afarelramdani.talentyou.model.hire.HireModel
import com.afarelramdani.talentyou.model.hire.HireResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*

class FragmentHireTalent: BaseFragment<FragmentHireTalentBinding>() {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_talent, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        binding.rvHire.adapter = HireListAdapter()
        binding.rvHire.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        getListHire()
        return binding.root

    }

    private fun getListHire() {
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                var sharePref = context?.let { SharedPreferences(it) }
                try {
                    service?.getHireByEngineerId(sharePref!!.getEngineerId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is HireResponse) {
                Log.d("hire list", result.toString())
                val list = result.data?.map {
                    HireModel(it.hireId, it.engineerId, it.projectId, it.hirePrice, it.hireMessage, it.hireStatus,it.hireDateConfirm, it.hireCreated)
                }
                (binding.rvHire.adapter as HireListAdapter).addList(list)
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}