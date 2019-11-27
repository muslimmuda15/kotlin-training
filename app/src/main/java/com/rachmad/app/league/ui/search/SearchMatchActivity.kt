package com.rachmad.app.league.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProviders
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchDetails
import com.rachmad.app.league.ui.match.details.AWAY_PATH
import com.rachmad.app.league.ui.match.details.HOME_PATH
import com.rachmad.app.league.ui.match.details.MATCH_ID
import com.rachmad.app.league.ui.match.details.MatchDetailsActivity
import com.rachmad.app.league.viewmodel.MatchViewModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class SearchMatchActivity : AppCompatActivity(), SearchItemFragment.OnSearchMatchListener {
    override fun onListFragmentInteraction(item: MatchDetails, homeImage: String?, awayImage: String?) {
        startActivity(intentFor<MatchDetailsActivity>(
            MATCH_ID to item.idEvent?.toInt(),
            HOME_PATH to homeImage,
            AWAY_PATH to awayImage).singleTop())
    }

    val viewModel: MatchViewModel by lazy { ViewModelProviders.of(this).get(
        MatchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.search)
        supportActionBar?.elevation = 0F

        supportFragmentManager.commitNow {
            add(R.id.container, SearchItemFragment.newInstance())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
