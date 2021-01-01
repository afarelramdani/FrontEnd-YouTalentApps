package com.afarelramdani.talentyou.content.recruiter

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
import com.afarelramdani.talentyou.content.listhireengineer.ListEngineerAdapter
import com.afarelramdani.talentyou.databinding.FragmentHomeRecruiterBinding
import com.afarelramdani.talentyou.model.recruiter.ListEngineerModel
import com.afarelramdani.talentyou.model.recruiter.ListEngineerResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*

class FragmentHomeRecruiter: BaseFragment<FragmentHomeRecruiterBinding>(), ListEngineerAdapter.OnListEngineerClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private var listEngineer = ArrayList<ListEngineerModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_recruiter, container, false)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        getAllEngineer()

        binding.rvHome.adapter = ListEngineerAdapter(listEngineer, this)
        binding.rvHome.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        return binding.root
    }

    private fun getAllEngineer() {
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread(). name}")

            val result = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getAllEngineer()
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ListEngineerResponse) {
                Log.d("android hans", result.toString())
                val list = result.data.map{
                    ListEngineerModel(it.engineerId, it.accountId, it.accountName,
                        it.accountEmail, it.accountNoHp, it.engineerJobTittle, it.engineerJobType, it.engineerOrigin,
                        it.engineerDesc, it.engineerFotoProfile)
                }

                (binding.rvHome.adapter as ListEngineerAdapter).addList(list)
            }

        }

    }

    override fun onEngineerItem(position: Int) {
        Toast.makeText(requireContext(), "${listEngineer[position].accountName} clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }


}