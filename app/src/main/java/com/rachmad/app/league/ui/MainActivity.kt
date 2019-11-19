package com.rachmad.app.league.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.commitNow
import androidx.fragment.app.transaction
import com.rachmad.app.league.BaseActivity
import com.rachmad.app.league.dataclass.LeagueList
import com.rachmad.app.league.ui.details.LEAGUE_ID
import com.rachmad.app.league.ui.details.LeagueDetailsActivity
import com.rachmad.app.league.ui.league.LeagueFragment
import org.jetbrains.anko.*

class MainActivity : BaseActivity(), LeagueFragment.OnListFragmentInteractionListener {
    override fun onListFragmentInteraction(item: LeagueList) {
        startActivity(intentFor<LeagueDetailsActivity>(LEAGUE_ID to item.idLeague))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        linearLayout {
            id = 1
            supportFragmentManager.commitNow {
                add(1, LeagueFragment.newInstance())
            }
        }
    }
}