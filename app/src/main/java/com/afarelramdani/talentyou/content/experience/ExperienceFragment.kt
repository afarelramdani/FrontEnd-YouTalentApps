package com.afarelramdani.talentyou.content.experience

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceActivity
import com.afarelramdani.talentyou.content.experience.deleteexperience.DeleteExperienceResponse
import com.afarelramdani.talentyou.content.experience.deleteexperience.DeleteExperienceViewModel
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceActivity
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.listhireengineer.ListEngineerAdapter
import com.afarelramdani.talentyou.content.login.LoginViewModel
import com.afarelramdani.talentyou.content.search.SearchAdapter
import com.afarelramdani.talentyou.databinding.FragmentExperienceBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_experience.*
import kotlinx.coroutines.*


class ExperienceFragment : BaseFragment<FragmentExperienceBinding>(), ExperienceAdapter.onListExperienceClickListener, View.OnClickListener {
    private var listExperience = ArrayList<ExperienceModel>()
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var intent: Intent
    private lateinit var viewModelDelete: DeleteExperienceViewModel
    private lateinit var service: ApiService
    private lateinit var viewModelGet: ExperienceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)

        intent = Intent(requireContext(), UpdateExperienceActivity::class.java)

        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        viewModelDelete = ViewModelProvider(this).get(DeleteExperienceViewModel::class.java)
        viewModelGet = ViewModelProvider(this).get(ExperienceViewModel::class.java)

        if (service != null) {
            viewModelDelete.setLoginService(service)
            viewModelGet.setLoginService(service)
        }


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)
        binding.rvListExperience.adapter = ExperienceAdapter(listExperience, this)
        binding.rvListExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)


        binding.btnAddExperience.setOnClickListener(this)
        return binding.root

        subscribeLiveData()
        subscribeLiveDataDelete()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelDelete.setSharedPreference(sharePref)

        val enId = sharePref.getEngineerId()
        val enIdDetail = sharePref.getDetailEngineerId()


        if (sharePref.getAccountLevel() == 0) {
            binding.lrLayout.visibility = View.GONE
        }

        if(sharePref.getAccountLevel() == 1 ) {
            getListExperience(enId)
        } else {
            getListExperience(enIdDetail)
        }



    }


    private fun getListExperience(enId: Int) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getExperience(enId)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ExperienceResponse) {
                Log.d("android hans", result.toString())
                val list = result.data?.map {
                    ExperienceModel(it.experienceId, it.engineerId, it.exPosition, it.experienceCompany, it.experienceStart, it.experienceEnd, it.experienceDesc)
                }
                (binding.rvListExperience.adapter as ExperienceAdapter).addList(list)
            }
        }
    }

    override fun onExperienceDelete(position: Int) {
        showDialogDelete(position)
    }

    override fun onExperienceEdit(position: Int) {
        Toast.makeText(context, "Update ${listExperience[position].exId}", Toast.LENGTH_SHORT).show()
        intent.putExtra("id", listExperience[position].exId)
        intent.putExtra("position", listExperience[position].experiencePosition)
        intent.putExtra("company", listExperience[position].experienceCompany)
        intent.putExtra("start", listExperience[position].experienceStart)
        intent.putExtra("end", listExperience[position].experienceEnd)
        intent.putExtra("desc", listExperience[position].experienceDesc)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_add_experience -> {
                val AddExperience = Intent(activity, AddExperienceActivity::class.java)
                requireActivity().startActivity(AddExperience)
            }
        }
    }


    private fun showDialogDelete(position: Int) {
        val id = listExperience[position].exId
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Delete Experience")
        builder.setMessage("Do you want to deleted this experience?")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->

            viewModelDelete.deleteExpById(id!!)
            intentActivity()
        }
        builder.setNegativeButton("No") { dialogInterface : DialogInterface, i : Int ->}
        builder.show()
    }

    private fun intentActivity() {
        startActivity(Intent(requireContext(), HomeActivity::class.java))
        activity?.finish()
    }

    private fun message(message : String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    private fun subscribeLiveDataDelete() {
        viewModelDelete.isDeleteExperienceLiveData.observe(requireActivity(), {
            if (it) {
                Toast.makeText(requireContext(), "Login Succcess", Toast.LENGTH_SHORT).show()
                baseStartActivity<HomeActivity>(requireContext())
                activity?.finish()
            } else {
                Toast.makeText(requireContext(), "Login Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun subscribeLiveData() {
        viewModelGet.isGetExperienceLiveData.observe(requireActivity(), {
            if (it) {
                Toast.makeText(requireContext(), "Succcess get Experience", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Faield Get Experience!", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelGet.getAllExperience().observe(requireActivity(), {
            (binding.rvListExperience.adapter as ExperienceAdapter).addList(it)
        })
    }



}