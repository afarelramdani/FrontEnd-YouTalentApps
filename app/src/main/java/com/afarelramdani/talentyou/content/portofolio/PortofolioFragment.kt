package com.afarelramdani.talentyou.content.portofolio

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
import com.afarelramdani.talentyou.databinding.FragmentPortofolioBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*


class PortofolioFragment: BaseFragment<FragmentPortofolioBinding>(), PortofolioAdapter.onListPortofolioClickListener {
    private var listPortofolio = ArrayList<PortofolioModel>()
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        getListPortofolio()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portofolio, container, false)
        binding.rvListPortofolio.adapter = PortofolioAdapter(listPortofolio,this)
        binding.rvListPortofolio.layoutManager = LinearLayoutManager(activity,
            RecyclerView.VERTICAL,false)

        return binding.root
    }

    private fun getListPortofolio() {
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        var sharePref = context?.let { SharedPreferences(it) }
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getPortofolio(sharePref!!.getEngineerId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is PortofolioResponse) {
                Log.d("android hans", result.toString())
                val list = result.data?.map {
                    PortofolioModel(it.projectId, it.engineerId, it.projectName, it.projectDesc, it.projectPicture)
                }
                (binding.rvListPortofolio.adapter as PortofolioAdapter).addList(list)
            }
        }
    }

    override fun onHireDelete(position: Int) {
        TODO("Not yet implemented")
    }

    override fun onHireEdit(position: Int) {
        TODO("Not yet implemented")
    }
}