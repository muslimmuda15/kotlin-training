package com.rachmad.app.league.ui.team

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProviders
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.viewmodel.TeamViewModel

const val TEAM_LEAGUE_NAME = "TeamLeagueName"
class TeamActivity : AppCompatActivity(), TeamItemFragment.OnTeamFragmentListener {
    override fun onTeamFragmentListener(item: TeamData) {

    }

    val viewModel: TeamViewModel by lazy {
        ViewModelProviders.of(this).get(TeamViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        val leagueName = intent.getStringExtra(TEAM_LEAGUE_NAME)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = leagueName
        supportActionBar?.elevation = 0F

        supportFragmentManager.commitNow {
            add(R.id.container, TeamItemFragment.newInstance(false, leagueName))
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
