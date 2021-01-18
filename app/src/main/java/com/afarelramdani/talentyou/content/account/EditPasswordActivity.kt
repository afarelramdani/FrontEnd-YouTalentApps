package com.afarelramdani.talentyou.content.account

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.MainActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.login.LoginActivity
import com.afarelramdani.talentyou.databinding.ActivityEditPasswordBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class EditPasswordActivity : BaseActivity<ActivityEditPasswordBinding>(), View.OnClickListener {
    private lateinit var viewModelUpdatePassword: EditPasswordViewModel
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope


    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_password
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModelUpdatePassword = ViewModelProvider(this).get(EditPasswordViewModel::class.java)
        viewModelUpdatePassword.setSharedPreference(sharePref)
        binding.btnSavePassword.setOnClickListener(this)

        if (service != null) {
            viewModelUpdatePassword.setUpdateProfileService(service)
        }

        subscribeLiveData()

    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_save_password -> {
                var tempPassword = sharePref.getTempPassword()
                var oldPassword =  binding.etOldPassword.text.toString()
                var newPassword = binding.etNewPassword.text.toString()
                var confirmPassword = binding.etNewPasswordConfirm.text.toString()

                if( tempPassword != oldPassword ) {
                    binding.etOldPassword.error = getString(R.string.erorconfirm)
                } else if(newPassword != confirmPassword) {
                    binding.etNewPasswordConfirm.error = getString(R.string.eror)
                } else if(oldPassword.isEmpty() ) {
                    binding.etOldPassword.error = getString(R.string.erorfild)
                } else if(newPassword.isEmpty() ) {
                    binding.etNewPassword.error = getString(R.string.erorfild)
                } else if(confirmPassword.isEmpty() ) {
                    binding.etNewPasswordConfirm.error = getString(R.string.erorfild)
                } else {
                    val id = sharePref.getAccountId()
                    viewModelUpdatePassword.updatePassword(id, binding.etNewPassword.text.toString() )
                }

            }
        }
    }

    private fun subscribeLiveData() {
        viewModelUpdatePassword.isUpdatePasswordLiveData.observe(this, {
            Log.d("android1", "$it")
            if (it) {
                Toast.makeText(this, "Update Password Succcess", Toast.LENGTH_SHORT).show()
                sharePref.clear()

                dialogLoginAgain()
            } else {
                Toast.makeText(this, "Update Password Failed!", Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun dialogLoginAgain() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Login Kembali")
            .setMessage("Silahkan Login Kembali")
            .setPositiveButton("OK") { dialog: DialogInterface?, which: Int ->
                baseStartActivity<LoginActivity>(this)
                finish()
            }
            .setCancelable(false)
        dialog.show()
    }

}