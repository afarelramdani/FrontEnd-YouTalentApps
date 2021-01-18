package com.afarelramdani.talentyou.content.experience.updateexperience

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.addexperience.AddExperienceViewModel
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityUpdateExperienceBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*

class UpdateExperienceActivity : BaseActivity<ActivityUpdateExperienceBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: UpdateExperienceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_update_experience
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(UpdateExperienceViewModel::class.java)
        viewModel.setSharedPreference(sharePref)

        if (service != null) {
            viewModel.setLoginService(service)
        }

        val posText = intent.getStringExtra("position")
        binding.etPosition.setText(posText)
        val compText = intent.getStringExtra("company")
        binding.etCompanyName.setText(compText)
        val startText = intent.getStringExtra("start")!!.split("T")[0]
        binding.etExperienceStart.setText(startText)
        val endText = intent.getStringExtra("end")!!.split("T")[0]
        binding.etExperienceEnd.setText(endText)
        val descText = intent.getStringExtra("desc")
        binding.etExperienceDesc.setText(descText)

        binding.btnUpdateExperienceData.setOnClickListener(this)

        setToolbarActionBar()

        subscribeLiveData()


    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "UpdateExperience"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_update_experience_data -> {
                val id = intent.getIntExtra("id", 0)
                viewModel.updateExperience(
                    id,
                    binding.etPosition.text.toString(),
                    binding.etCompanyName.text.toString(),
                    binding.etExperienceStart.text.toString(),
                    binding.etExperienceEnd.text.toString(),
                    binding.etExperienceDesc.text.toString())
            }
        }
    }

    private fun subscribeLiveData() {
        viewModel.isUpdateExperienceLiveData.observe(this, {
            Log.d("android1", "$it")
            if (it) {
                Toast.makeText(this, "UpdateExperience Succcess", Toast.LENGTH_SHORT).show()
                baseStartActivity<HomeActivity>(this)
                finish()
            } else {
                Toast.makeText(this, "Update Experience Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}