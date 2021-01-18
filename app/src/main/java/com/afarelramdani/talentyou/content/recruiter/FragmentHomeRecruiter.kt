package com.afarelramdani.talentyou.content.recruiter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceViewModel
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.recruiter.listengineer.DetailTalentActivity
import com.afarelramdani.talentyou.content.listhireengineer.ListEngineerAdapter
import com.afarelramdani.talentyou.databinding.FragmentHomeRecruiterBinding
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerModel
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerResponse
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerViewModel
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*

class FragmentHomeRecruiter: BaseFragment<FragmentHomeRecruiterBinding>(), ListEngineerAdapter.OnListEngineerClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private var listEngineer = ArrayList<ListEngineerModel>()
    private lateinit var viewModel: ListEngineerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_recruiter, container, false)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(ListEngineerViewModel::class.java)

        if (service != null) {
            viewModel.setLoginService(service)
        }
        subscribeLiveData()
        viewModel.getAllEngineer()

        binding.rvHome.adapter = ListEngineerAdapter(listEngineer, this)
        binding.rvHome.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        return binding.root
    }



    override fun onEngineerItem(position: Int) {
        val intent = Intent(requireContext(), DetailTalentActivity::class.java)
        var sharePref = context?.let { SharedPreferences(it) }
        sharePref?.setEnginnerId(listEngineer[position].engineerId!!)
        intent.putExtra("name", listEngineer[position].accountName)
        intent.putExtra("jobTitle", listEngineer[position].engineerJobTitle)
        intent.putExtra("jobType", listEngineer[position].engineerJobType)
        intent.putExtra("image", listEngineer[position].engineerProfilePict)
        intent.putExtra("location", listEngineer[position].engineerLocation)
        intent.putExtra("desc", listEngineer[position].engineerDescription)
        intent.putExtra("engId", listEngineer[position].engineerId)
        intent.putExtra("acEmail", listEngineer[position].accountEmail)
        intent.putExtra("image", listEngineer[position].engineerProfilePict)
        startActivity(intent)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    private fun subscribeLiveData() {
        viewModel.isListEngineerData.observe(viewLifecycleOwner, {
            if (it) {
                Toast.makeText(requireContext(), "Get Data Succes", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Get Data Faailed!", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getAllData().observe(requireActivity(), {
            (binding.rvHome.adapter as ListEngineerAdapter).addList(it)
        })
    }

}