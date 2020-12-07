package com.afarelramdani.talentyou.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.SearchView
import com.afarelramdani.talentyou.R
import com.afarelramdani.talentyou.hire.ProfileTalentHireActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        sv_search_talent.setOnClickListener{
            val moveToSearchResult = Intent(this, SearchResultActivity::class.java)
            startActivity(moveToSearchResult)
        }
    }

}