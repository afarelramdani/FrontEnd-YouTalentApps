package com.afarelramdani.talentyou.content.projectrecruiter.updateproject

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.portofolio.addportofolio.AddPortofolioActivity
import com.afarelramdani.talentyou.content.projectrecruiter.addproject.AddProjectActivity
import com.afarelramdani.talentyou.content.projectrecruiter.deleteproject.DeleteProjectViewModel
import com.afarelramdani.talentyou.content.projectrecruiter.detailproject.DetailProjectViewModel
import com.afarelramdani.talentyou.databinding.ActivityUpdateProjectBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.cancel
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UpdateProjectActivity : BaseActivity<ActivityUpdateProjectBinding>(), View.OnClickListener {
    private lateinit var viewModelUpdate: UpdateProjectViewModel

    private lateinit var service: ApiService
    private lateinit var deadlineProject: DatePickerDialog.OnDateSetListener
    private lateinit var c: Calendar

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        private const val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_update_project
        super.onCreate(savedInstanceState)
        
        c = Calendar.getInstance()
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModelUpdate = ViewModelProvider(this).get(UpdateProjectViewModel::class.java)
        viewModelUpdate.setSharedPreference(sharePref)
        viewModelUpdate.setBinding(binding)

        if (service != null) {
            viewModelUpdate.setService(service)
        }

        viewModelUpdate.getAllProject(sharePref.getProjectId())

        binding.etProjectDeadline.setOnClickListener(this)
        binding.ivProjectUpload.setOnClickListener(this)

        deadlineProject()
        setToolbarActionBar()
    }


    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Update Project"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private fun deadlineProject() {
        deadlineProject = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            c.set(Calendar.YEAR, year)
            c.set(Calendar.MONTH, month)
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val day = findViewById<TextView>(R.id.et_project_deadline)
            val Format = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(Format, Locale.US)
            day.setText(sdf.format(c.time))
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.et_project_deadline -> {
                DatePickerDialog(
                    this, deadlineProject, c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            R.id.iv_project_upload -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED
                    ) {
                        //permission denied
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system OS is < Marshmallow
                    pickImageFromGallery();
                }

            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.ivProjectUpload.setImageURI(data?.data)

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)
            Toast.makeText(this, "$file", Toast.LENGTH_SHORT).show()

            var img: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)

            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("image", file.name, it1)
            }

            var nameField = binding.etProjectName.text.toString()
            var projectDesc = binding.etProjectDesc.text.toString()
            var d = binding.etProjectDeadline.text.toString()

            Log.d("UpdateProjectAkyu" , "$d")

            val cnId = sharePref.getCompanyId()
            val projectId = sharePref.getProjectId()
            val name = createPartFromString(nameField)
            val desc = createPartFromString(projectDesc)
            val deadline = createPartFromString(d)

            binding.btnUpdateProject.setOnClickListener {
                if(img != null) {
                    viewModelUpdate.updateProject(projectId, cnId, name, desc, deadline, img)

                }

            }
        }
    }

    fun getPath(context: Context, contentUri: Uri) : String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)

        val cursorLoader = CursorLoader(context, contentUri, proj, null, null, null)
        val cursor = cursorLoader.loadInBackground()

        if (cursor != null) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            result = cursor.getString(columnIndex)
            cursor.close()
        }
        return result
    }

    @NonNull
    private fun createPartFromString(json: String): RequestBody {
        val mediaType = "multipart/form-data".toMediaType()
        return json
            .toRequestBody(mediaType)
    }

}