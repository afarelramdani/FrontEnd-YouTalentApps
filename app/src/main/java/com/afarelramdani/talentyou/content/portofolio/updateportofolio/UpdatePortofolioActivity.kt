package com.afarelramdani.talentyou.content.portofolio.updateportofolio

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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.loader.content.CursorLoader
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.experience.updateexperience.UpdateExperienceViewModel
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.portofolio.addportofolio.AddPortofolioActivity
import com.afarelramdani.talentyou.databinding.ActivityUpdatePortofolioBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UpdatePortofolioActivity : BaseActivity<ActivityUpdatePortofolioBinding>(), View.OnClickListener {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private var img: MultipartBody.Part? = null
    private var image: String = ""
    private lateinit var viewModel: UpdatePortofolioViewModel

    companion object {
        private const val IMAGE_PICK_CODE = 1000;
        const val PERMISSION_CODE = 1001;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_update_portofolio
        super.onCreate(savedInstanceState)

        setToolbarActionBar()

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(UpdatePortofolioViewModel::class.java)

        if (service != null) {
            viewModel.setCompanyService(service)
        }
        val img = "http://3.80.117.134:2000/image/"

        val portoName = intent.getStringExtra("prApplication")
        binding.etPortofolioName.setText(portoName)
        val portoDesc = intent.getStringExtra("prDesc")
        binding.etPortofolioDesc.setText(portoDesc)
        val portoLinkPub = intent.getStringExtra("prLinkPub")
        binding.etPortofolioLinkpublikasi.setText(portoLinkPub)
        val portoLinkRepo = intent.getStringExtra("prLinkRepo")
        binding.etPortofolioRepo.setText(portoLinkRepo)
        val portoTpKerja = intent.getStringExtra("prTpKerja")
        binding.etTypeWork.setText(portoTpKerja)
        val portoType = intent.getStringExtra("prType")
        binding.etTypePortofolio.setText(portoType)

        Glide.with(binding.root)
            .load(img+intent.getStringExtra("image"))
            .placeholder(R.drawable.projectdefault)
            .error(R.drawable.projectdefault)
            .into(binding.ivPortofolioUpdate)

        binding.ivPortofolioUpdate.setOnClickListener(this)
        binding.btnUpdatePortofolioData.setOnClickListener(this)
    }

    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Update Portofolio"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }



    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.iv_portofolio_update-> {
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

            R.id.btn_update_portofolio_data -> {
                var prId = intent.getIntExtra("prId", 0)
                var portofolioName = binding.etPortofolioName.text.toString()
                var portofolioDesc = binding.etPortofolioDesc.text.toString()
                var portofolioLinkPub = binding.etPortofolioLinkpublikasi.text.toString()
                var portofolioLinkRepo = binding.etPortofolioRepo.text.toString()
                var portofolioTypeWork= binding.etTypePortofolio.text.toString()
                var portofolioType = binding.etTypeWork.text.toString()


                val name = createPartFromString(portofolioName)
                val prDesc = createPartFromString(portofolioDesc)
                val prLinkPub = createPartFromString(portofolioLinkPub)
                val prLinkRepo = createPartFromString(portofolioLinkRepo)
                val prTypeWork = createPartFromString(portofolioTypeWork)
                val prType = createPartFromString(portofolioType)

                    if (image != "") {
                        viewModel.setImage(img!!)
                        viewModel.updatePortofolio(1, prId, name, prDesc, prLinkPub, prLinkRepo, prTypeWork, prType)
                        subscribeLiveDataUpdate()
                    } else {
                        viewModel.updatePortofolio(0, prId, name, prDesc, prLinkPub, prLinkRepo, prTypeWork, prType)
                        subscribeLiveDataUpdate()

                }
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
            binding.ivPortofolioUpdate.setImageURI(data?.data)

            val filePath = data?.data?.let { getPath(this, it) }
            val file = File(filePath)
            Toast.makeText(this, "$file", Toast.LENGTH_SHORT).show()
            if (filePath != null) {
                image = filePath
            }

            val mediaTypeImg = "image/jpeg".toMediaType()
            val inputStream = data?.data?.let { contentResolver.openInputStream(it) }
            val reqFile: RequestBody? = inputStream?.readBytes()?.toRequestBody(mediaTypeImg)
            img = reqFile?.let { it1 ->
                MultipartBody.Part.createFormData("image", file.name, it1)
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

    private fun subscribeLiveDataUpdate() {
        viewModel.isUpdateCompanyLiveData.observe(this, Observer {
            if (it) {
                val intent = Intent(this, HomeActivity::class.java)
                Toast.makeText(this@UpdatePortofolioActivity, "Success update profile", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            } else {
                Toast.makeText(this@UpdatePortofolioActivity, "Edit profile failed", Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}