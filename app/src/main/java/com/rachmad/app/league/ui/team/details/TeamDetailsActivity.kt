package com.rachmad.app.league.ui.team.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.tabs.TabLayout
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.PlayerData
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.player.PlayerItemFragment
import com.rachmad.app.league.ui.player.details.PLAYER_DATA
import com.rachmad.app.league.ui.player.details.PlayerDetailsActivity
import com.rachmad.app.league.viewmodel.PlayerViewModel
import com.rachmad.app.league.viewmodel.TeamViewModel
import kotlinx.android.synthetic.main.activity_team_details.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

const val TEAM_ID = "TeamId"
const val TEAM_NAME = "TeamName"
const val TEAM_ALTERNATE = "TeamAlternate"
class TeamDetailsActivity : AppCompatActivity(), PlayerItemFragment.OnPlayerFragmentListener {
    var id = "0"

    lateinit var viewModel: TeamViewModel
    lateinit var playerViewModel: PlayerViewModel
    lateinit var teamViewPager: TeamViewModel
    lateinit var teamInfo: TeamData
    lateinit var teamPagerAdapter: TeamPagerAdapter

    override fun onListFragmentInteraction(item: PlayerData?) {
        startActivity(intentFor<PlayerDetailsActivity>(PLAYER_DATA to item).singleTop())
    }

    private fun initialize(){
        viewModel = ViewModelProviders.of(this).get(TeamViewModel::class.java)
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel::class.java)

        team_tab.addTab(team_tab.newTab().setText(getString(R.string.team_info)))
        team_tab.addTab(team_tab.newTab().setText(getString(R.string.player)))

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        id = intent.getStringExtra(TEAM_ID) ?: ""
        val team1 = intent.getStringExtra(TEAM_NAME)
        val team2 = intent.getStringExtra(TEAM_ALTERNATE)

        supportActionBar?.title = HtmlCompat.fromHtml(
            "<font color='#ffffff'>${team1 ?: team2 ?: "" }</font>",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)

        initialize()

        val connection = viewModel.connectionTeamDataDetails()
        viewModel.team(id.toInt())

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                viewModel.teamDetails()?.let { data ->
                    with(data){
                        teamInfo = this

                        val backdrop = strTeamBanner

                        GlideApp.with(back_drop)
                            .load(strTeamBadge)
                            .fitCenter()
                            .placeholder(R.drawable.no_image)
                            .into(back_drop)

//                        GlideApp.with(logo)
//                            .load(strTeamBadge)
//                            .fitCenter()
//                            .placeholder(R.drawable.no_image)
//                            .into(logo)
//
//                        description_team.text = strDescriptionEN
                        title_team.text = if(!strTeam.isNullOrBlank())
                            strTeam
                        else
                            strAlternate

                        date.text = intFormedYear

                        tabTeam()
                    }
                }
            }
        })
    }

    private fun tabTeam(){
        teamPagerAdapter = TeamPagerAdapter(supportFragmentManager, 2)

        team_view_pager.adapter = teamPagerAdapter
        team_view_pager.offscreenPageLimit = 2
        team_view_pager.adapter?.notifyDataSetChanged()

        team_view_pager.addOnPageChangeListener(object: TabLayout.TabLayoutOnPageChangeListener(team_tab) {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        team_tab.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(team_view_pager))
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    main.visibility = ViewGroup.VISIBLE
                    loading_layout.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    main.visibility = ViewGroup.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error_text.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    main.visibility = ViewGroup.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error_text.visibility = TextView.VISIBLE

                    error_text.text = viewModel.errorTeamDataDetails()?.status_message
                    return false
                }
                else -> {
                    main.visibility = ViewGroup.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error_text.visibility = TextView.VISIBLE

                    error_text.text = getString(R.string.unknown_error)
                    return false
                }
            }
        } ?: run {
            return false
        }
    }

    inner class TeamPagerAdapter(fm: FragmentManager, val countTabs: Int): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getItem(position: Int): Fragment {
            Log.d("main", "POSITION : " + position)
            if(position == 0)
                return TeamInfoFragment.newInstance(teamInfo)
            else if (position == 1){
                return PlayerItemFragment.newInstance(teamInfo.strTeam ?: "")
            }
            else{
                return Fragment()
            }
        }

        override fun getCount(): Int = countTabs
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
