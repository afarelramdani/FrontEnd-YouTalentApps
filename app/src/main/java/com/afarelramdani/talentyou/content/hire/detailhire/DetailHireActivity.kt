package com.afarelramdani.talentyou.content.hire.detailhire

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.databinding.ActivityDetailHireBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.*

class DetailHireActivity : BaseActivity<ActivityDetailHireBinding>() {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_hire
        super.onCreate(savedInstanceState)

        val hrId = intent.getStringExtra("hrId")

        val nameProject = intent.getStringExtra("name")
        binding.tvProjectName.text = nameProject

        val price = intent.getStringExtra("price")
        binding.tvHrPrice.text = price

        val img = "http://3.80.117.134:2000/image/"
        Glide.with(binding.root)
            .load(img+intent.getStringExtra("image"))
            .placeholder(R.drawable.projectdefault)
            .error(R.drawable.projectdefault)
            .into(binding.ivProjectList)

        val deadline = intent.getStringExtra("deadline")
        binding.tvProjectDeadline.text = deadline!!.split("T")[0]
        val status = intent.getStringExtra("status")
        binding.tvStatus.text = status

        if(status == "approve") {
            binding.btnApproveHire.visibility = View.GONE
            binding.btnRejectHire.visibility = View.GONE
            binding.tvStatus.setTextColor(Color.rgb(0, 122, 0))
        } else if (status == "reject") {
            binding.btnApproveHire.visibility = View.GONE
            binding.btnRejectHire.visibility = View.GONE
            binding.tvStatus.setTextColor(Color.rgb(120, 0, 0))
        }

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)

        binding.btnApproveHire.setOnClickListener {
            val hireId = hrId
            dialogAprrove(hireId!!)
        }
        binding.btnRejectHire.setOnClickListener {
            val hireId = hrId
            dialogReject(hireId!!)
        }
    }


    private fun updateHireStatus(id: String, status: String) {
        coroutineScope.launch {

            val result = withContext(Dispatchers.IO) {
                try {
                    service?.updateHireStatus(id, status)
                } catch (e:Throwable) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun dialogAprrove(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hiring Approval")
        builder.setMessage("Are you sure to approve this hiring?")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->
            updateHireStatus(id!!, "approve")
            baseStartActivity<HomeActivity>(this)
        }
        builder.setNegativeButton("No") { dialogInterface : DialogInterface, i : Int ->}
        builder.show()
    }

    private fun dialogReject(id: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Reject Hire")
        builder.setMessage("Are you sure to Reject this hiring?")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->
            updateHireStatus(id!!, "reject")
            baseStartActivity<HomeActivity>(this)
        }
        builder.setNegativeButton("No") { dialogInterface : DialogInterface, i : Int ->}
        builder.show()
    }


}