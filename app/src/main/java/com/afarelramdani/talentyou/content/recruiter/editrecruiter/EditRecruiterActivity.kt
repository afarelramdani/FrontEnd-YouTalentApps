package com.afarelramdani.talentyou.content.recruiter.editrecruiter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceResponse
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityEditRecruiterBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.create
import okhttp3.RequestBody.Companion.toRequestBody

class EditRecruiterActivity : BaseActivity<ActivityEditRecruiterBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_recruiter
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        var name = sharePref.getCompanyName()
        binding.etCompanyNames.setText(name)

        var companyPosition = sharePref.getCompanyPosition()
        binding.etCompanyPosition.setText(companyPosition)

        var companyPart = sharePref.getCompanyPart()
        binding.etPart.setText(companyPart)

        var companyCity = sharePref.getCompanyLocation()
        binding.etLocation.setText(companyCity)

        var companyDesc = sharePref.getCompanyDesc()
        binding.etDesc.setText(companyDesc)

        var companyIg= sharePref.getCompanyInstagram()
        binding.etLinkInstagram.setText(companyIg)

        var companyLinkedin = sharePref.getCompanyLinkedin()
        binding.etLinkLinkedin.setText(companyLinkedin)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }

        binding.btnUpdateProfileCompany.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_update_profile_company -> {

                var cnName = binding.etCompanyNames.text.toString()
                var cnPosition = binding.etCompanyPosition.text.toString()
                var cnPart = binding.etPart.text.toString()
                var cnCity = binding.etLocation.text.toString()
                var cnDesc = binding.etDesc.text.toString()
                var cnInstagram = binding.etLinkInstagram.text.toString()
                var cnLinkedIn = binding.etLinkLinkedin.text.toString()

                var companyId = sharePref.getCompanyId()
                var companyName = createPartFromString(cnName)
                var companyPosition = createPartFromString(cnPosition)
                var companyPart = createPartFromString(cnPart)
                var companyCity = createPartFromString(cnCity)
                var companyDesc = createPartFromString(cnDesc)
                var instagram = createPartFromString(cnInstagram)
                var linkedin = createPartFromString(cnLinkedIn)

                updateRecruiterProfile(companyId, companyName, companyPosition, companyPart, companyCity, companyDesc, instagram, linkedin)
            }
        }
    }

    fun updateRecruiterProfile(cnId: Int, cnName: RequestBody, cnPosition: RequestBody, cnPart: RequestBody, cnCity: RequestBody, cnDesc: RequestBody, cnInstagram: RequestBody, cnLinkedin: RequestBody) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateCompanyProfile(cnId, cnName, cnPosition, cnPart, cnCity, cnDesc, cnInstagram, cnLinkedin)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is EditRecruiterResponse) {
                Toast.makeText(this@EditRecruiterActivity, "Update Profile success!", Toast.LENGTH_SHORT).show()
                baseStartActivity<HomeActivity>(this@EditRecruiterActivity)
            }
        }
    }

    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}