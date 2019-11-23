package com.rachmad.app.league.ui

import android.os.Bundle
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

        supportFragmentManager.commitNow {
            add(R.id.container, LeagueFragment.newInstance())
        }
    }
}
