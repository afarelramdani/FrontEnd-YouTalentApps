package com.afarelramdani.talentyou.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.MainActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityHomeTalentBinding
import com.afarelramdani.talentyou.talent.FragmentHomeTalent
import com.afarelramdani.talentyou.talent.FragmentProfileTalent
import com.afarelramdani.talentyou.project.ProjectFragment

class HomeTalentActivity : BaseActivity<ActivityHomeTalentBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_home_talent
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.toolbar)


        val fragmentHomeTalent = FragmentHomeTalent()
        setSupportActionBar(binding.toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_talent, fragmentHomeTalent).commit()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    val fragmentHomeTalent = FragmentHomeTalent()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_talent, fragmentHomeTalent).commit()
                    binding.tvToolbar.setText("Home")
                    true
                }

                R.id.page_3 -> {
                    val fragmentProject = ProjectFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_talent, fragmentProject).commit()
                    binding.tvToolbar.setText("Inbox")
                    true
                }

                R.id.page_4 -> {
                    val fragmentProfileTalent = FragmentProfileTalent()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_talent, fragmentProfileTalent).commit()
                    binding.tvToolbar.setText("Profile")
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
            .setPositiveButton("YA") { dialog: DialogInterface? , which: Int ->
                sharePref.clear()
                baseStartActivity<MainActivity>(this)
            }
            .setNegativeButton("Tidak") { dialogInterface, which: Int ->
                dialogInterface.dismiss()
            }
        dialog.show()
    }

}

