package com.afarelramdani.talentyou.content.portofolio

import android.content.DialogInterface
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
import com.afarelramdani.talentyou.content.experience.ExperienceAdapter
import com.afarelramdani.talentyou.content.experience.ExperienceViewModel
import com.afarelramdani.talentyou.content.experience.deleteexperience.DeleteExperienceResponse
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceActivity
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.portofolio.addportofolio.AddPortofolioActivity
import com.afarelramdani.talentyou.content.portofolio.updateportofolio.UpdatePortofolioActivity
import com.afarelramdani.talentyou.content.projectrecruiter.addproject.AddProjectActivity
import com.afarelramdani.talentyou.databinding.FragmentPortofolioBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*


class PortofolioFragment: BaseFragment<FragmentPortofolioBinding>(), PortofolioAdapter.onListPortofolioClickListener{
    private var listPortofolio = ArrayList<PortofolioModel>()
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService
    private lateinit var intent: Intent
    private lateinit var viewModelGetPorto: PortofolioViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        intent = Intent(requireContext(), UpdatePortofolioActivity::class.java)
        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        viewModelGetPorto = ViewModelProvider(this).get(PortofolioViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_portofolio, container, false)
        binding.rvListPortofolio.adapter = PortofolioAdapter(listPortofolio,this)
        binding.rvListPortofolio.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharePref = context?.let { SharedPreferences(it) }!!

        if (sharePref.getAccountLevel() == 0) {
            binding.lrPrLayput.visibility = View.GONE
        } else if (sharePref.getEngineerId() == sharePref.getEngineerId()) {
            binding.btnAddPortofolio.visibility = View.VISIBLE
        }

        if (service != null) {
            viewModelGetPorto.setLoginService(service)
        }

        viewModelGetPorto.setSharedPreference(sharePref)
        viewModelGetPorto.getListPortofolio()
        subscribeLiveData()

        binding.btnAddPortofolio.setOnClickListener {
            Toast.makeText(requireContext(), "Go To Portofolio", Toast.LENGTH_SHORT).show()
            val AddPortofolio = Intent(activity, AddPortofolioActivity::class.java)
            requireActivity().startActivity(AddPortofolio)
        }
    }



    private fun deletePortoById(id: Int) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.deletePortofolio(id)
                } catch (e:Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is DeleteExperienceResponse) {
                if (result.success) {
                    message("Delete Succes")
                }
            }
        }
    }

    private fun showDialogDelete(position: Int) {
        val id = listPortofolio[position].prId
        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle("Delete Experience")
        builder.setMessage("Do you want to deleted this portofolio?")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->
            deletePortoById(id!!)
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

    override fun onPortofolioDelete(position: Int) {
        showDialogDelete(position)
    }

    override fun onPortofolioEdit(position: Int) {
        Toast.makeText(context, "Update ${listPortofolio[position].prId}", Toast.LENGTH_SHORT).show()

        intent.putExtra("prId", listPortofolio[position].prId)
        intent.putExtra("prApplication", listPortofolio[position].prName)
        intent.putExtra("prDesc", listPortofolio[position].prDesc)
        intent.putExtra("prLinkPub", listPortofolio[position].prLinkPub)
        intent.putExtra("prLinkRepo", listPortofolio[position].prLinkRepo)
        intent.putExtra("prTpKerja", listPortofolio[position].prTypeWork)
        intent.putExtra("prType", listPortofolio[position].prType)
        intent.putExtra("image", listPortofolio[position].prImage)
        startActivity(intent)
    }

    private fun subscribeLiveData() {
        viewModelGetPorto.isGetPortofolioLiveData.observe(requireActivity(), {
            if (it) {
                Toast.makeText(requireContext(), "Succcess get Portofolio", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Faield Get Portofolio!", Toast.LENGTH_SHORT).show()
            }
        })
        viewModelGetPorto.getAllPorto().observe(requireActivity(), {
            (binding.rvListPortofolio.adapter as PortofolioAdapter).addList(it)
        })
    }

}