package com.afarelramdani.talentyou.content.projectrecruiter.detailproject

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.home.HomeActivity
import com.afarelramdani.talentyou.content.projectrecruiter.deleteproject.DeleteProjectViewModel
import com.afarelramdani.talentyou.content.projectrecruiter.detailproject.listhire.DetailProjectTabAdapter
import com.afarelramdani.talentyou.content.projectrecruiter.updateproject.UpdateProjectActivity
import com.afarelramdani.talentyou.databinding.ActivityDetailProjectBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService

class DetailProjectActivity : BaseActivity<ActivityDetailProjectBinding>() {
    private lateinit var pageAdapter: DetailProjectTabAdapter
    private lateinit var viewModelDelete: DeleteProjectViewModel
    private lateinit var service: ApiService
    private lateinit var viewModelGet: DetailProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_detail_project
        super.onCreate(savedInstanceState)

        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        pageAdapter = DetailProjectTabAdapter(supportFragmentManager)
        binding.viewPager.adapter = pageAdapter
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        viewModelDelete = ViewModelProvider(this).get(DeleteProjectViewModel::class.java)
        viewModelDelete.setSharedPreference(sharePref)

        viewModelGet = ViewModelProvider(this).get(DetailProjectViewModel::class.java)
        viewModelGet.setSharedPreference(sharePref)

        if (service != null) {
            viewModelDelete.setDeleteService(service)
        }

        if (service != null) {
            viewModelGet.setService(service)
        }

        viewModelGet.setBinding(binding)
        viewModelGet.getAllProject(sharePref.getProjectId())
        viewModelGet.getHireByProject(sharePref.getProjectId())
        setToolbarActionBar()
        subscribeLiveData()
        }



    private fun setToolbarActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail Project"
        binding.toolbar.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.detail_project_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                showDialogDelete()
            }
            R.id.update -> {
                baseStartActivity<UpdateProjectActivity>(this)
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val delete = menu.findItem(R.id.delete)
        val update = menu.findItem(R.id.update)
        viewModelGet.isHireLiveData.observe(this, Observer {
            if (it) {
                delete.isVisible = false
                update.isVisible = false
            }
        })
        return true
    }

    private fun showDialogDelete() {
        val projectId = sharePref.getProjectId()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete Project")
        builder.setMessage("Do you want to deleted this project?")
        builder.setPositiveButton("Yes") { dialogInterface : DialogInterface, i : Int ->
            viewModelDelete.deleteProjectById(projectId)
            baseStartActivity<HomeActivity>(this)
            finish()
        }
        builder.setNegativeButton("No") { dialogInterface : DialogInterface, i : Int ->}
        builder.show()
    }

    private fun subscribeLiveData() {
        viewModelGet.isProjectLiveData.observe(this, Observer {
            if (it) {
                binding.progressBar.visibility = View.GONE
            } else {
                binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

}