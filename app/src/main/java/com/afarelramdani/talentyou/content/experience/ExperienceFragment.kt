package com.afarelramdani.talentyou.content.experience

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
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceActivity
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceActivity
import com.afarelramdani.talentyou.databinding.FragmentExperienceBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.*


class ExperienceFragment : BaseFragment<FragmentExperienceBinding>(), ExperienceAdapter.onListExperienceClickListener, View.OnClickListener {
    private var listExperience = ArrayList<ExperienceModel>()
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        intent = Intent(requireContext(), UpdateExperienceActivity::class.java)


        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_experience, container, false)
        binding.rvListExperience.adapter = ExperienceAdapter(listExperience, this)
        binding.rvListExperience.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)


        binding.btnAddExperience.setOnClickListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharePref = context?.let { SharedPreferences(it) }!!
        val enId = sharePref.getEngineerId()
        val enIdDetail = sharePref.getDetailEngineerId()

        if(sharePref.getAccountLevel() == 1 ) {
            getListExperience(enId)
        } else {
            getListExperience(enIdDetail)
        }

    }

    private fun getListExperience(enId: Int) {
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)

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
        Toast.makeText(context, "Update ${listExperience[position].exId}", Toast.LENGTH_SHORT).show()
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
}