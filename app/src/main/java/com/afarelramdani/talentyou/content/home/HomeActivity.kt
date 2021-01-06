package com.afarelramdani.talentyou.content.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.MainActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.content.projectrecruiter.ProjectFragment
import com.afarelramdani.talentyou.content.recruiter.FragmentHomeRecruiter
import com.afarelramdani.talentyou.content.recruiter.FragmentProfileRecruiter
import com.afarelramdani.talentyou.content.talent.FragmentHireTalent
import com.afarelramdani.talentyou.content.talent.FragmentHomeTalent
import com.afarelramdani.talentyou.content.talent.FragmentProfileTalent
import com.afarelramdani.talentyou.databinding.ActivityHomeTalentBinding
import com.afarelramdani.talentyou.remote.ApiClient
import com.afarelramdani.talentyou.util.ApiService
import kotlinx.coroutines.*


class HomeActivity : BaseActivity<ActivityHomeTalentBinding>() {
    private lateinit var service: ApiService
    private lateinit var coroutineScope: CoroutineScope
    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_home_talent
        super.onCreate(savedInstanceState)

        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
            return;
        }

        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        service = ApiClient.getApiClient(this)!!.create(ApiService::class.java)


        val fragmentHomeTalent = FragmentHomeTalent()
        val fragmentHomeRecruiter = FragmentHomeRecruiter()


        if (sharePref.getAccountLevel() == 1) {
            getEngineerByAccountId()
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
            getEngineerByAccountId()
        } else {
            getCompanyByAccountId()
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
            R.id.item -> {
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

    private fun getEngineerByAccountId() {

        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")
            val response = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getEngineerIdByAccountId(sharePref.getAccountId())
                } catch (e: Throwable) {

                    e.printStackTrace()
                }
            }

            if (response is DataEngineerRepsonse) {
                Log.d("Data Engineer", response.toString())
                sharePref.setEngineerData(response.data.engineerId, response.data.engineerPhoto, response.data.engineerJobTitle )
                }


            }

        }

    private fun getCompanyByAccountId() {

        coroutineScope.launch {
            Log.d("android2", "Start: ${Thread.currentThread().name}")

            val response = withContext(Dispatchers.IO) {
                Log.d("android2", "CallApi: ${Thread.currentThread().name}")
                try {
                    service?.getCompanyIdByAccountId(sharePref.getAccountId())
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }

            if (response is DataRecruiterResponse) {
                Log.d("Data Company", response.toString())
                sharePref.setCompanyData(response.data.companyId, response.data.companyFoto)
            }

        }

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


