package com.afarelramdani.talentyou.content.project

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityAddProjectBinding
import kotlinx.android.synthetic.main.activity_add_project.*
import java.io.ByteArrayOutputStream
import java.util.*

class AddProjectActivity : BaseActivity<ActivityAddProjectBinding>(), View.OnClickListener {
    private var IMG_REQUEST = 21
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_add_project
        super.onCreate(savedInstanceState)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            var path = data.data
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path)
            iv_project_upload.setImageBitmap(bitmap)

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addProject() {
        var ByteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ByteArrayOutputStream)
        val imageInByte: ByteArray = ByteArrayOutputStream.toByteArray()
        val encodedString: String = Base64.getEncoder().encodeToString(imageInByte)
        Toast.makeText(this, encodedString, Toast.LENGTH_LONG).show()

    }
}