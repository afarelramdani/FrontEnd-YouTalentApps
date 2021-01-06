package com.afarelramdani.talentyou.content.projectrecruiter

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
import com.afarelramdani.talentyou.content.projectrecruiter.addproject.AddProjectActivity
import com.afarelramdani.talentyou.databinding.FragmentProjectBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*


class ProjectFragment : BaseFragment<FragmentProjectBinding>(), View.OnClickListener, ProjectContract.View  {
    private lateinit var coroutineScope: CoroutineScope
    private var presenter: ProjectPresenter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )
        val service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)

        binding.rvListProject.adapter = ProjectListAdapter()
        binding.rvListProject.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        var sharePref = context?.let { SharedPreferences(it) }
        presenter = ProjectPresenter(coroutineScope, service, sharePref)

        binding.btnAddProject.setOnClickListener(this)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
        presenter?.getAllProject()
    }

    override fun onStop() {
        presenter?.unbind()
        super.onStop()
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        presenter = null
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_add_project -> {
                Toast.makeText(requireContext(), "TES", Toast.LENGTH_SHORT).show()
                val AddProject = Intent(activity, AddProjectActivity::class.java)
                requireActivity().startActivity(AddProject)
            }
        }
    }

    override fun addListProject(list: List<ProjectModel>) {
        (binding.rvListProject.adapter as ProjectListAdapter).addList(list)
    }


}