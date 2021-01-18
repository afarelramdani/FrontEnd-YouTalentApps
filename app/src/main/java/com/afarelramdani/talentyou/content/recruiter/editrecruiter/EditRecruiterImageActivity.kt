package com.afarelramdani.talentyou.content.recruiter.editrecruiter

import android.Manifest
import android.app.Activity
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
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.loader.content.CursorLoader
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.portofolio.updateportofolio.UpdatePortofolioActivity
import com.afarelramdani.talentyou.content.portofolio.updateportofolio.UpdatePortofolioActivity.Companion.PERMISSION_CODE
import com.afarelramdani.talentyou.content.portofolio.updateportofolio.UpdatePortofolioResponse
import com.afarelramdani.talentyou.content.recruiter.FragmentProfileRecruiter
import com.afarelramdani.talentyou.content.recruiter.editrecruiter.EditRecruiterImageActivity.Companion.PERMISSION_CODE
import com.afarelramdani.talentyou.databinding.ActivityEditRecruiterImageBinding
import com.afarelramdani.talentyou.databinding.LayoutDialogImageUpdateBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class EditRecruiterImageActivity : BaseActivity<ActivityEditRecruiterImageBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope


    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        const val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_edit_recruiter_image
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        binding.ivImageProfile.setOnClickListener(this)

        setToolbarActionBar()
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Edit Image"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_image_profile -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_DENIED
                    ) {
                        //permission denied
                        val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                        //show popup to request runtime permission
                        requestPermissions(permissions, UpdatePortofolioActivity.PERMISSION_CODE);
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

    fun updateImageProfile(cnId: Int, image: MultipartBody.Part) {
        coroutineScope.launch {
            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateCompanyImageProfile(cnId, image)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (result is EditRecruiterResponse) {
                Toast.makeText(this@EditRecruiterImageActivity, "Update Image success!", Toast.LENGTH_SHORT).show()
                baseStartActivity<HomeActivity>(this@EditRecruiterImageActivity)
            }
        }
    }

    //IMAGE
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
            binding.ivImageProfile.setImageURI(data?.data)

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)
            Log.d("This", "$file")
            Toast.makeText(this, "$file", Toast.LENGTH_SHORT).show()

            var img: MultipartBody.Part? = null
            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver?.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("image", file.name, it1)
            }

            binding.btnSaveImage.setOnClickListener {
                var id = sharePref.getCompanyId()

                if (img != null) {
                    updateImageProfile(id, img)
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


    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

}