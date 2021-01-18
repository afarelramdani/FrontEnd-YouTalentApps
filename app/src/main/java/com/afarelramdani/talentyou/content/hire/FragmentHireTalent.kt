package com.afarelramdani.talentyou.content.hire

import android.content.Intent
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
import com.afarelramdani.talentyou.content.hire.addhire.HireResponse
import com.afarelramdani.talentyou.content.hire.detailhire.DetailHireActivity
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.recruiter.listengineer.DetailTalentActivity
import com.afarelramdani.talentyou.databinding.FragmentHireTalentBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*

class FragmentHireTalent: BaseFragment<FragmentHireTalentBinding>(),
    HireListAdapter.clickOnListHireListener {
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
        sharePref = SharedPreferences(requireContext())

        binding.rvHire.adapter = HireListAdapter(listHire, this)
        binding.rvHire.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        getListHire()
        return binding.root

    }

    private fun getListHire() {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {

                try {
                    service?.getHireByEngineerId(sharePref!!.getDetailEngineerId())
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

    override fun detailProjectClick(position: Int) {
        val intent = Intent(activity, DetailHireActivity::class.java)
        intent.putExtra("hrId", listHire[position].hireId)
        intent.putExtra("name", listHire[position].projectName)
        intent.putExtra("deadline", listHire[position].projectDeadline)
        intent.putExtra("price", listHire[position].hirePrice)
        intent.putExtra("message", listHire[position].hireMessage)
        intent.putExtra("status", listHire[position].hireStatus)
        intent.putExtra("engId", listHire[position].engineerId)
        intent.putExtra("image", listHire[position].hireProjectImage)
        startActivity(intent)

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}