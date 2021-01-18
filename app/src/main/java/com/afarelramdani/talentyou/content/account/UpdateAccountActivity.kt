package com.afarelramdani.talentyou.content.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceViewModel
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityUpdateAccountBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class UpdateAccountActivity : BaseActivity<ActivityUpdateAccountBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: UpdateAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_update_account
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(UpdateAccountViewModel::class.java)
        viewModel.setSharedPreference(sharePref)


        if (service != null) {
            viewModel.setUpdateProfileService(service)
        }

        binding.btnUpdatePassword.setOnClickListener(this)
        setDataRecruiter()
        setToolbarActionBar()
        subscribeLiveData()
    }


    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Update Account"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    fun setDataRecruiter() {
        var name = sharePref.getAccountName()
        binding.etAccountName.setText(name)

        var acNoHp = sharePref.getNoHp()
        binding.etNoHp.setText(acNoHp)

        var email = sharePref.getAccountEmail()
        binding.etEmail.setText(email)
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_update_profile -> {
                val id = sharePref.getAccountId()
                viewModel.updateAccount(
                    id,
                    binding.etAccountName.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etNoHp.text.toString()

                )
            }
            R.id.btn_update_password -> {
                baseStartActivity<EditPasswordActivity>(this)
            }
        }
    }

    private fun subscribeLiveData() {
        viewModel.isUpdateProfileLiveData.observe(this, {
            Log.d("android1", "$it")
            if (it) {
                Toast.makeText(this, "Update Account Succcess", Toast.LENGTH_SHORT).show()
                baseStartActivity<HomeActivity>(this)
                finish()
            } else {
                Toast.makeText(this, "Update Account Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }
}