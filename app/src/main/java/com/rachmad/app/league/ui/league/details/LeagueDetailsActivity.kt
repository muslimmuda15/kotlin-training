package com.rachmad.app.league.ui.league.details

import android.os.Bundle
import android.util.Log
import android.view.Menu
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
import com.rachmad.app.league.LeagueActivity
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.helper.Utils
import com.rachmad.app.league.ui.match.MatchItemFragment
import com.rachmad.app.league.ui.match.details.*
import com.rachmad.app.league.ui.match.favorite.FavoriteMatchActivity
import com.rachmad.app.league.ui.search.SearchMatchActivity
import com.rachmad.app.league.ui.team.TEAM_LEAGUE_NAME
import com.rachmad.app.league.ui.team.TeamActivity
import com.rachmad.app.league.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.activity_league_details.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop

const val LEAGUE_ID = "id"
class LeagueDetailsActivity : LeagueActivity(), MatchItemFragment.OnTabFragmentListener {
    var title = ""

    override fun onListFragmentInteraction(
        item: MatchList,
        idHome: String?,
        homeImage: String?,
        idAway: String?,
        awayImage: String?
    ) {
        startActivity(intentFor<MatchDetailsActivity>(
            MATCH_ID to item.idEvent?.toInt(),
            HOME_ID to idHome?.toInt(),
            HOME_PATH to homeImage,
            AWAY_ID to idAway?.toInt(),
            AWAY_PATH to awayImage).singleTop())
    }

    var idLeague: Int = 0

    val matchViewModel: MatchViewModel by lazy { ViewModelProviders.of(this).get(
        MatchViewModel::class.java)
    }

    lateinit var tabPagerAdapter: TabPagerAdapter


    private fun initialize(){
        match_tab.addTab(match_tab.newTab().setText(getString(R.string.last_match)))
        match_tab.addTab(match_tab.newTab().setText(getString(R.string.next_match)))

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        idLeague = intent.getIntExtra(LEAGUE_ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league_details)

        initialize()

        val connection = viewModel.connectionLeagueDetails()
        viewModel.connectLeagueDetails(idLeague)

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                viewModel.leagueDetails()?.let { data ->
                    with(data) {
                        title = strLeague ?: strLeagueAlternate ?: ""
                        supportActionBar?.title = HtmlCompat.fromHtml(
                            "<font color='#ffffff'>${title}</font>",
                            HtmlCompat.FROM_HTML_MODE_LEGACY
                        )
                        val backdrop = strFanart1
                            ?: strFanart2
                            ?: strFanart3
                            ?: strFanart4

                        GlideApp.with(back_drop)
                            .load(backdrop)
                            .centerCrop()
                            .placeholder(R.drawable.no_image)
                            .into(back_drop)

                        GlideApp.with(logo)
                            .load(strBadge)
                            .fitCenter()
                            .placeholder(R.drawable.no_image)
                            .into(logo)

                        description_league.text = strDescriptionEN
                        title_league.text = if (!strLeague.isNullOrBlank())
                            strLeague
                        else
                            strLeagueAlternate

                        date.text = Utils.dateFormat(dateFirstEvent ?: "")

                        tabMatch()
                    }
                }
            }
        })
    }

    private fun tabMatch(){
        tabPagerAdapter = TabPagerAdapter(supportFragmentManager, 2)

        view_pager.adapter = tabPagerAdapter
        view_pager.offscreenPageLimit = 2
        view_pager.adapter?.notifyDataSetChanged()

        view_pager.addOnPageChangeListener(object: TabLayout.TabLayoutOnPageChangeListener(match_tab) {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        match_tab.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(view_pager))
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

                    error_text.text = viewModel.errorLeagueDetails()?.status_message
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.search -> {
                startActivity(intentFor<SearchMatchActivity>().singleTop())
            }
            R.id.favorite -> {
                startActivity(intentFor<FavoriteMatchActivity>().singleTop())
            }
            R.id.team -> {
                startActivity(intentFor<TeamActivity>(TEAM_LEAGUE_NAME to title).singleTop())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class TabPagerAdapter(fm: FragmentManager, val countTabs: Int): FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
        override fun getItem(position: Int): Fragment {
            Log.d("main", "POSITION : " + position)
            return MatchItemFragment.newInstance(false, position, idLeague)
        }

        override fun getCount(): Int = countTabs
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
