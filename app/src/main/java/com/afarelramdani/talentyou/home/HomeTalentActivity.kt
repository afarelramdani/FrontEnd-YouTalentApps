package com.afarelramdani.talentyou.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.afarelramdani.talentyou.BaseActivity
import com.afarelramdani.talentyou.MainActivity
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.databinding.ActivityHomeTalentBinding
import com.afarelramdani.talentyou.fragmentTalent.FragmentHomeTalent
import com.afarelramdani.talentyou.fragmentTalent.FragmentProfileTalent
import com.afarelramdani.talentyou.login.LoginTalentActivity
import kotlinx.android.synthetic.main.activity_home_talent.*

class HomeTalentActivity : BaseActivity<ActivityHomeTalentBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setLayout = R.layout.activity_home_talent
        super.onCreate(savedInstanceState)

        val fragmentHomeTalent = FragmentHomeTalent()
        setSupportActionBar(binding.toolbar)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container_home, fragmentHomeTalent).commit()
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    val fragmentHomeTalent = FragmentHomeTalent()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_home, fragmentHomeTalent).commit()
                    true
                }

                R.id.page_4 -> {
                    val fragmentProfileTalent = FragmentProfileTalent()
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container_home, fragmentProfileTalent).commit()
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

