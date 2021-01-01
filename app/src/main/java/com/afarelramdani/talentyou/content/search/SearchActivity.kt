package com.afarelramdani.talentyou.content.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afarelramdani.talentyou.R
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