package com.rachmad.app.league.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commitNow
import com.rachmad.app.league.R
import com.rachmad.app.league.ui.league.LeagueFragment
import com.rachmad.app.league.LeagueActivity
import com.rachmad.app.league.`object`.LeagueList
import com.rachmad.app.league.ui.league.details.LEAGUE_ID
import com.rachmad.app.league.ui.league.details.LeagueDetailsActivity
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class MainActivity : LeagueActivity(), LeagueFragment.OnLeagueListFragmentListener {
    override fun onListFragmentInteraction(item: LeagueList) {
        startActivity(intentFor<LeagueDetailsActivity>(LEAGUE_ID to item.idLeague).singleTop())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportActionBar?.setDisplayShowHomeEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowTitleEnabled(true)

        supportFragmentManager.commitNow {
            add(R.id.container, LeagueFragment.newInstance())
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
