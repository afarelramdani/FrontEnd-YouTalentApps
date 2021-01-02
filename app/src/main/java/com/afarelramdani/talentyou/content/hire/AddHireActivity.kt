package com.afarelramdani.talentyou.content.hire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityAddHireBinding
import com.afarelramdani.talentyou.model.hire.HireResponse
import com.afarelramdani.talentyou.model.project.ProjectModel
import com.afarelramdani.talentyou.model.project.ProjectResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*

class AddHireActivity : BaseActivity<ActivityAddHireBinding>(), View.OnClickListener {
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var service: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_add_hire
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        listProjectSpinner()

        binding.btnHire.setOnClickListener(this)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private fun listProjectSpinner() {
        binding.spinnerListProject

        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.getProjectById(sharePref.getCompanyId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is ProjectResponse) {
                val list = result.data?.map {
                    ProjectModel(it.projectId, it.companyId, it.projectName, it.projectDesc, it.projectPicture, it.projectDeadline)
                }
                val projectName =
                    arrayOfNulls<String>(list.size)
                val projectId =
                    arrayOfNulls<Int>(list.size)

                for (i in 0 until list.size) {
                    projectName[i] = list.get(i).projectName
                    projectId[i] = list.get(i).projectId
                }

                binding.spinnerListProject.adapter = ArrayAdapter<String>(this@AddHireActivity, R.layout.support_simple_spinner_dropdown_item, projectName)

                binding.spinnerListProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        Toast.makeText(this@AddHireActivity, "${projectId[position]} clicked", Toast.LENGTH_LONG).show()
                        sharePref.setProjectId(projectId[position]!!)
                    }
                }
            }
        }
    }

    fun callHireApi(enId: Int, pjId: Int, hirePrice: String, hireMessage: String, hireStatus: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.addHire(enId, pjId, hirePrice, hireMessage, hireStatus)
                } catch (e:Throwable) {
                    e.printStackTrace()
                }
            }

            if (result is HireResponse) {
                if (result.success) {
                    Toast.makeText(this@AddHireActivity, "Success Hire", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@AddHireActivity, HomeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_hire -> {
                val projectId = sharePref.getProjectId()
                val engineerId = sharePref.getEngineerId()
                val hirePrice = binding.etHirePrice.text.toString()
                val hireMessage = binding.etHireMessage.text.toString()

                if (binding.etHireMessage.text!!.isEmpty() || binding.etHirePrice.text!!.isEmpty()) {
                    Toast.makeText(this, "All field must be filled!", Toast.LENGTH_SHORT).show()
                } else {
                    callHireApi(engineerId!!.toInt(), projectId, hirePrice, hireMessage, "wait")
                }
            }
        }
    }
}