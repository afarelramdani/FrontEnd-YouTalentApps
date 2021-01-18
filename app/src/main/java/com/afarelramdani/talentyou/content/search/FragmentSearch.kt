package com.afarelramdani.talentyou.content.search

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afarelramdani.talentyou.BaseFragment
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.recruiter.listengineer.DetailTalentActivity
import com.afarelramdani.talentyou.content.recruiter.listengineer.ListEngineerModel
import com.afarelramdani.talentyou.databinding.FragmentSearchBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import com.afarelramdani.talentyou.util.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel


class FragmentSearch : BaseFragment<FragmentSearchBinding>(), SearchAdapter.OnListEngineerClickListener, SearchContract.View {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private var presenter: SearchPresenter? = null
    var listEngineer = ArrayList<ListEngineerModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search,container,false)

        service = context?.let { ApiClient.getApiClient(it) }!!.create(ApiService::class.java)
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main )

        presenter = SearchPresenter(coroutineScope, service)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecyclerView()
        binding.btnFilterTalent.setOnClickListener {
            setFilter()
        }
        setSearchView()


    }

    override fun onResultSuccess(list: List<ListEngineerModel>) {
        (binding.rvSearchEngineer.adapter as SearchAdapter).addList(list)
        binding.rvSearchEngineer.visibility = View.VISIBLE
        binding.tvDataNotFound.visibility = View.GONE
    }

    override fun onResultFail(message: String) {
        binding.rvSearchEngineer.visibility = View.GONE
        binding.tvDataNotFound.visibility = View.VISIBLE
        binding.message = message
    }

    override fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvSearchEngineer.visibility = View.GONE
        binding.tvDataNotFound.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    private fun setSearchView() {
        binding.svSearchTalent.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == "") {
                    presenter?.callServiceSearch(null, null)
                } else {
                    if (newText?.length!! == 3) {
                        presenter?.callServiceSearch(newText, null)
                    }
                }
                return true
            }
        })
    }

    private fun setRecyclerView() {
        binding.rvSearchEngineer.isNestedScrollingEnabled = false
        binding.rvSearchEngineer.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

        val adapter = SearchAdapter(listEngineer, this)
        binding.rvSearchEngineer.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        presenter?.bindToView(this)
        presenter?.callServiceSearch(null, null)
    }

    override fun onStop() {
        presenter?.unbind()
        super.onStop()
    }


    override fun onDestroy() {
        coroutineScope.cancel()
        presenter = null
        super.onDestroy()
    }

    private fun setFilter() {
        val builder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        builder?.setTitle("Filter")
        builder?.setIcon(R.drawable.filterbutton)

        val user = arrayOf("Name", "Skill", "Location")
        builder?.setItems(user) { _, which ->
            when (which) {
                0 -> {
                    presenter?.callServiceSearch(null, 0)
                }
                1 -> {
                    presenter?.callServiceSearch(null, 1)
                }
                2 -> {
                    presenter?.callServiceSearch(null, 2)
                }
            }
        }?.show()
    }

    override fun onEngineerItem(position: Int) {
        val intent = Intent(requireContext(), DetailTalentActivity::class.java)
        var sharePref = context?.let { SharedPreferences(it) }
        sharePref?.setEnginnerId(listEngineer[position].engineerId!!)
        intent.putExtra("name", listEngineer[position].accountName)
        intent.putExtra("jobTitle", listEngineer[position].engineerJobTitle)
        intent.putExtra("jobType", listEngineer[position].engineerJobType)
        intent.putExtra("image", listEngineer[position].engineerProfilePict)
        intent.putExtra("location", listEngineer[position].engineerLocation)
        intent.putExtra("engId", listEngineer[position].engineerId)
        intent.putExtra("acEmail", listEngineer[position].accountEmail)
        intent.putExtra("image", listEngineer[position].engineerProfilePict)
        startActivity(intent)
    }
}