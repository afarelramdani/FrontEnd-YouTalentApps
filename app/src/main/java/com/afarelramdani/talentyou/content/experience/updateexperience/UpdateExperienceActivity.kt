package com.afarelramdani.talentyou.content.experience.updateexperience

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityUpdateExperienceBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*

class UpdateExperienceActivity : BaseActivity<ActivityUpdateExperienceBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_update_experience
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

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

    }

    fun updateExperience(id: Int, exPosition: String, exCompany: String, exStart: String, exEnd: String, exDesc: String) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateExperience(id, exPosition, exCompany, exStart, exEnd, exDesc)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is UpdateExperienceResponse) {
                Toast.makeText(this@UpdateExperienceActivity, "Update experience success!", Toast.LENGTH_SHORT).show()
                this@UpdateExperienceActivity.finish()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_update_experience_data -> {
                val id = intent.getIntExtra("id", 0)
                updateExperience(id, binding.etPosition.text.toString(), binding.etCompanyName.text.toString(),
                    binding.etExperienceStart.text.toString(), binding.etExperienceEnd.text.toString(), binding.etExperienceDesc.text.toString())
            }
        }
    }
}