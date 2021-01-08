package com.afarelramdani.talentyou.content.experience.addexperience

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityAddExperienceBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class AddExperienceActivity : BaseActivity<ActivityAddExperienceBinding>(), View.OnClickListener {
    private lateinit var dateExperienceStart: DatePickerDialog.OnDateSetListener
    private lateinit var dateExperienceEnd: DatePickerDialog.OnDateSetListener
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var c: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_add_experience
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        c = Calendar.getInstance()

        binding.experienceStart.setOnClickListener(this)
        binding.experienceEnd.setOnClickListener(this)

        startExperience()
        endExperience()

    }


    private fun startExperience() {
            dateExperienceStart = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.experience_start)
            val Format = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(Format, Locale.US)

            day.text = sdf.format(c.time)
        }
    }

    private fun endExperience() {
        dateExperienceEnd = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.experience_end)
            val Format = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(Format, Locale.US)

            day.text = sdf.format(c.time)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.experience_start -> {
                DatePickerDialog(
                    this, dateExperienceStart, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.experience_end -> {
                DatePickerDialog(
                    this, dateExperienceEnd, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.btn_add_experience_data -> {
                val enId = sharePref.getEngineerId()
                val exCompany = binding.etCompanyName.text.toString()
                val exPosition = binding.etPosition.text.toString()
                val exDesc = binding.experienceDesc.text.toString()
                val exStart = binding.experienceStart.text.toString()
                val exEnd = binding.experienceEnd.text.toString()

                AddExperience(enId, exPosition, exCompany, exStart, exEnd, exDesc)
                this@AddExperienceActivity.finish()

            }
        }
    }

    private fun AddExperience(enId: Int, exPosition: String, exCompany: String, exStart: String, exEnd: String, exDesc: String) {
        coroutineScope.launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    service.addExperience(enId,exPosition,exCompany,exStart,exEnd,exDesc)
                } catch (e:Throwable) {
                    e.printStackTrace()
                }
            }
            Log.d("errornih", response.toString())

            if (response is AddExperienceResponse) {
                Log.d("masukga", response.toString())
                Toast.makeText(this@AddExperienceActivity, "Success Add Experience", Toast.LENGTH_SHORT).show()
                this@AddExperienceActivity.finish()
            }
        }
    }
}