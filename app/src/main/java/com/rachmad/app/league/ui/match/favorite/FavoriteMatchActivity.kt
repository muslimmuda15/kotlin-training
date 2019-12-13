package com.rachmad.app.league.ui.match.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProviders
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.ui.match.MatchItemFragment
import com.rachmad.app.league.ui.match.details.*
import com.rachmad.app.league.viewmodel.MatchViewModel
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class FavoriteMatchActivity : AppCompatActivity(), MatchItemFragment.OnTabFragmentListener {
    override fun onListFragmentInteraction(
        item: MatchList,
        idHome: String?,
        homeImage: String?,
        idAway: String?,
        awayImage: String?
    ) {
        startActivity(intentFor<MatchDetailsActivity>(
            MATCH_ID to item.idEvent?.toInt(),
            HOME_ID to item.idHomeTeam?.toInt(),
            HOME_PATH to homeImage,
            AWAY_ID to item.idAwayTeam?.toInt(),
            AWAY_PATH to awayImage).singleTop())
    }

    val matchViewModel: MatchViewModel by lazy { ViewModelProviders.of(this).get(
        MatchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_match)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.favorite)

        supportFragmentManager.commitNow {
            add(R.id.container, MatchItemFragment.newInstance(true, 0, 0))
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
