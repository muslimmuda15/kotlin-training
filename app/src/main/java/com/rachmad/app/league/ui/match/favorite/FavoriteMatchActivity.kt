package com.rachmad.app.league.ui.match.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.commitNow
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.ui.match.MatchItemFragment
import com.rachmad.app.league.ui.match.details.*
import com.rachmad.app.league.ui.team.TeamItemFragment
import com.rachmad.app.league.ui.team.details.TEAM_ALTERNATE
import com.rachmad.app.league.ui.team.details.TEAM_ID
import com.rachmad.app.league.ui.team.details.TEAM_NAME
import com.rachmad.app.league.ui.team.details.TeamDetailsActivity
import com.rachmad.app.league.viewmodel.MatchViewModel
import com.rachmad.app.league.viewmodel.TeamViewModel
import kotlinx.android.synthetic.main.activity_favorite_match.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

class FavoriteMatchActivity : AppCompatActivity(), MatchItemFragment.OnTabFragmentListener, TeamItemFragment.OnTeamFragmentListener {
    override fun onTeamFragmentListener(item: TeamData) {
        startActivity(intentFor<TeamDetailsActivity>(
            TEAM_ID to item.idTeam,
            TEAM_NAME to item.strTeam,
            TEAM_ALTERNATE to item.strAlternate
        ).singleTop())
    }

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

    lateinit var matchViewModel: MatchViewModel
    lateinit var teamViewModel: TeamViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_match)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = getString(R.string.favorite)

        matchViewModel = ViewModelProviders.of(this).get(MatchViewModel::class.java)
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)

        favorite_tab.addTab(favorite_tab.newTab().setText(R.string.match))
        favorite_tab.addTab(favorite_tab.newTab().setText(R.string.team))

        val adapter = SectionPagerAdapter(supportFragmentManager, 2)
        adapter.notifyDataSetChanged()
        content_tab.adapter = adapter
        content_tab.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(favorite_tab))

        favorite_tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                content_tab.setCurrentItem(tab.position)
            }
        })
    }

    inner class SectionPagerAdapter(fm: FragmentManager, countTabs: Int) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        val c = countTabs
        override fun getItem(position: Int): Fragment {
            when(position){
                0 -> {
                    return MatchItemFragment.newInstance(true, 0, 0)
                }
                1 -> {
                    return TeamItemFragment.newInstance(true, "")
                }
                else ->{
                    return Fragment()
                }
            }
        }

        override fun getCount(): Int {
            return c
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            return super.instantiateItem(container, position)
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
