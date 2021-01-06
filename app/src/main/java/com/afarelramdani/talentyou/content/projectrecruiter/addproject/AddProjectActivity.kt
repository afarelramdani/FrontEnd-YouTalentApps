package com.afarelramdani.talentyou.content.projectrecruiter.addproject

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityAddProjectBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.android.synthetic.main.activity_add_project.*
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AddProjectActivity : BaseActivity<ActivityAddProjectBinding>(), View.OnClickListener {
    private lateinit var deadlineProject: DatePickerDialog.OnDateSetListener
    private lateinit var c: Calendar
    private var IMG_REQUEST = 21
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var imageName: MultipartBody.Part
    private lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_add_project
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        c = Calendar.getInstance()


        binding.ivProjectUpload.setOnClickListener(this)
        binding.btnAddProject.setOnClickListener(this)

        binding.projectDeadline.setOnClickListener(this)

        deadlineProject()
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_project_upload -> {

                if (EasyPermissions.hasPermissions(v.context,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.setType("image/jpeg")
                    startActivityForResult(intent, IMG_REQUEST)
                } else {
                    EasyPermissions.requestPermissions(this,"This application need your permission to access image gallery.",991,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }

            }

            R.id.btn_add_project -> {
                addProject()
                this@AddProjectActivity.finish()
            }

            R.id.project_deadline -> {
                DatePickerDialog(
                    this, deadlineProject, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

        }
    }

    private fun deadlineProject() {
        deadlineProject = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.project_deadline)
            val Format = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(Format, Locale.US)

            day.text = sdf.format(c.time)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            var image = data.data

            val dataResponse = image?.path?.replace("/raw/".toRegex(), "")
            Log.d("Log Result Image", dataResponse.toString())
            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), File(dataResponse!!))
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.data)
            iv_project_upload.setImageBitmap(bitmap)

            imageName = MultipartBody.Part.createFormData("image", File(dataResponse).name, requestBody)


        }
    }
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
                if (response.success) {
                    Log.d("Data Company", response.toString())
                    Toast.makeText(this@AddProjectActivity, "Succes Add Project", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddProjectActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}