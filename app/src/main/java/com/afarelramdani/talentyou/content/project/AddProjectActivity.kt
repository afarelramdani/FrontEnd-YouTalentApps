package com.afarelramdani.talentyou.content.project

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityAddProjectBinding
import com.afarelramdani.talentyou.model.datarecruiter.DataRecruiterResponse
import com.afarelramdani.talentyou.model.project.AddProjectResponse
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.util.*

class AddProjectActivity : BaseActivity<ActivityAddProjectBinding>(), View.OnClickListener {
    private var IMG_REQUEST = 21
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_add_project
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        binding.ivProjectUpload.setOnClickListener(this)
        binding.btnAddProject.setOnClickListener(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_project_upload -> {
                var intent = Intent()
                intent.setType("image/*")
                intent.setAction(Intent.ACTION_GET_CONTENT)
                startActivityForResult(intent, IMG_REQUEST)
            }

            R.id.btn_add_project -> {
                addProject()
            }

        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
//            var path = data.data
//            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path)
//            iv_project_upload.setImageBitmap(bitmap)
//
//        }
//    }

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun addImage() {
//        var ByteArrayOutputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ByteArrayOutputStream)
//        val imageInByte: ByteArray = ByteArrayOutputStream.toByteArray()
//        val encodedString: String = Base64.getEncoder().encodeToString(imageInByte)
//        Toast.makeText(this, encodedString, Toast.LENGTH_LONG).show()
//
//    }

    private fun addProject() {
        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    var idCompany = sharePref.getCompanyId().toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())
                    var name = binding.tvProjectName.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())
                    var projectDesc = binding.projectDesc.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())
                    var projectDeadline = binding.projectDeadline.text.toString()
                        .toRequestBody("text/plain".toMediaTypeOrNull())
                    service?.addProject(idCompany, name, projectDesc, projectDeadline)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is AddProjectResponse) {
                Log.d("Data Company", response.toString())
            }

        }
    }
}