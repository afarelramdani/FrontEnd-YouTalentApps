package com.afarelramdani.talentyou.content.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.MainActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.account.UpdateAccountActivity
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectFragment
import com.afarelramdani.talentyou.content.recruiter.FragmentHomeRecruiter
import com.afarelramdani.talentyou.content.recruiter.FragmentProfileRecruiter
import com.afarelramdani.talentyou.content.hire.FragmentHireTalent
import com.afarelramdani.talentyou.content.login.LoginActivity
import com.afarelramdani.talentyou.content.search.FragmentSearch
import com.afarelramdani.talentyou.content.talent.FragmentHomeTalent
import com.afarelramdani.talentyou.content.talent.FragmentProfileTalent
import com.afarelramdani.talentyou.databinding.ActivityHomeTalentBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*


class HomeActivity : BaseActivity<ActivityHomeTalentBinding>() {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    private lateinit var viewModel: HomeActivityViewModel
    val fragmentHomeTalent = FragmentHomeTalent()
    val fragmentHomeRecruiter = FragmentHomeRecruiter()

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_home_talent
        super.onCreate(savedInstanceState)

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)
        viewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)
        viewModel.setSharedPreference(sharePref)

        service()
        isLogin()
        showFragment()
        subscribeLiveData()

    }

    fun service() {
        if (service != null) {
            viewModel.setRegisterService(service)
        }

    }

    fun isLogin() {
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }
    }

    fun showFragment() {
        if (sharePref.getAccountLevel() == 1) {
            viewModel.getEngineerByAccountId()
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                fragmentHomeTalent
            ).commit()
            binding.tvToolbar.setText("Home")
        } else if(sharePref.getAccountLevel() == 0) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                fragmentHomeRecruiter
            ).commit()
            binding.tvToolbar.setText("Home")
        }

        if(sharePref.getAccountLevel() == 1) {
            viewModel.getEngineerByAccountId()
        } else {
            viewModel.getCompanyByAccountId()
        }

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    if (sharePref.getAccountLevel() == 1) {
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentHomeTalent
                        ).commit()
                        binding.tvToolbar.setText("Home")
                        true
                    } else if (sharePref.getAccountLevel() == 0) {
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentHomeRecruiter
                        ).commit()
                        binding.tvToolbar.setText("Home")
                    }

                    true
                }

                R.id.page_2 -> {
                    if (sharePref.getAccountLevel() == 1) {
                        val fragmentSearch = FragmentSearch()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentSearch
                        ).commit()
                        binding.tvToolbar.setText("Search")

                    } else if (sharePref.getAccountLevel() == 0) {
                        val fragmentSearch = FragmentSearch()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentSearch
                        ).commit()
                        binding.tvToolbar.setText("Search")
                    }
                    true
                }

                R.id.page_3 -> {
                    if (sharePref.getAccountLevel() == 1) {
                        val fragmentHireTalent = FragmentHireTalent()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentHireTalent
                        ).commit()
                        binding.tvToolbar.setText("List Hire")

                    } else if (sharePref.getAccountLevel() == 0) {
                        val fragmentProject = ProjectFragment()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentProject
                        ).commit()
                        binding.tvToolbar.setText("List Project")
                    }
                    true
                }

                R.id.page_4 -> {
                    setSupportActionBar(binding.toolbar)
                    if (sharePref.getAccountLevel() == 1) {
                        val fragmentProfileTalent = FragmentProfileTalent()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentProfileTalent
                        ).commit()
                        binding.tvToolbar.setText("Profile")

                    } else if (sharePref.getAccountLevel() == 0) {
                        val fragmentProfileRecruiter = FragmentProfileRecruiter()
                        supportFragmentManager.beginTransaction().replace(
                            R.id.fragment_container,
                            fragmentProfileRecruiter
                        ).commit()
                        binding.tvToolbar.setText("Profile")
                    }
                    true
                }

                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater?.inflate(R.menu.top_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.item1 -> {
                if (sharePref.getAccountLevel() == 1) {
                    baseStartActivity<UpdateAccountActivity>(this)
                } else if (sharePref.getAccountLevel() == 0) {
                    baseStartActivity<UpdateAccountActivity>(this)
                }

            }

            R.id.item2 -> {
                dialogLogout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogLogout() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Apakah Anda Ingin Logout ?")
            .setPositiveButton("YA") { dialog: DialogInterface?, which: Int ->
                sharePref.clear()
                baseStartActivity<MainActivity>(this)
            }
            .setNegativeButton("Tidak") { dialogInterface, which: Int ->
                dialogInterface.dismiss()
            }
        dialog.show()
    }

    private fun subscribeLiveData() {
        viewModel.isGetLiveData.observe(this, Observer {
            Log.d("android1", "$it")

            if (it) {
                Toast.makeText(this, "Get Data Succes", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed To Get Data!", Toast.LENGTH_SHORT).show()
                baseStartActivity<LoginActivity>(this)
            }
        })

    }

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.putExtra("EXIT", true)
            startActivity(intent)
        }
        return super.onKeyDown(keyCode, event)
    }
    }


