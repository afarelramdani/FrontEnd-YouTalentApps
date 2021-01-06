package com.afarelramdani.talentyou.content.talent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.webview.WebViewActivity
import com.afarelramdani.talentyou.databinding.FragmentHireTalentBinding
import com.afarelramdani.talentyou.model.hire.HireModel
import com.afarelramdani.talentyou.model.hire.HireResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*

class FragmentHireTalent: BaseFragment<FragmentHireTalentBinding>(), HireListAdapter.clickOnListHireListener  {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    private var listHire = ArrayList<HireModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hire_talent, container, false)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        binding.rvHire.adapter = HireListAdapter(listHire, this)
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
                    HireModel(it.hireId, it.engineerId, it.projectId, it.hirePrice, it.hireMessage, it.hireStatus,
                        it.hireDateConfirm, it.hireCreated, it.projectPicture, it.projectName, it.projectDeadline)
                }
                (binding.rvHire.adapter as HireListAdapter).addList(list)
            }
        }
    }

    override fun approveProjectClick(position: Int) {
        val hireId = listHire[position].hireId
        updateHireStatus(hireId!!, "approve")
        val moveToHome = Intent(activity, HomeActivity::class.java)
        startActivity(moveToHome)
    }

    override fun rejectProjectClick(position: Int) {
        val hireId = listHire[position].hireId
        updateHireStatus(hireId!!, "reject")
        val moveToHome = Intent(activity, HomeActivity::class.java)
        startActivity(moveToHome)
    }


    private fun updateHireStatus(id: String, status: String) {
        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateHireStatus(id, status)
                } catch (e:Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}