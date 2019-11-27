package com.rachmad.app.league.ui.match.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.helper.Utils
import com.rachmad.app.league.ui.search.SearchMatchActivity
import com.rachmad.app.league.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.activity_match_details.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val MATCH_ID = "MatchId"
const val HOME_PATH = "HomePath"
const val AWAY_PATH = "AwayPath"
class MatchDetailsActivity : AppCompatActivity() {
    var idMatch = 0
    val viewModel: MatchViewModel by lazy { ViewModelProviders.of(this).get(
        MatchViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_details)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        idMatch = intent.getIntExtra(MATCH_ID, 0)
        val homeImage = intent.getStringExtra(HOME_PATH)
        val awayImage = intent.getStringExtra(AWAY_PATH)

        GlideApp.with(home_image)
            .load(homeImage)
            .fitCenter()
            .into(home_image)

        GlideApp.with(away_image)
            .load(awayImage)
            .fitCenter()
            .into(away_image)

        val connection = viewModel.connectionMatchDetails()
        viewModel.matchDetails(idMatch)
        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                loadData()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
            R.id.search -> {
                startActivity(intentFor<SearchMatchActivity>().singleTop())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun listTeam(data: String): List<String>{
        val teams = ArrayList(data.split(";"))
        teams.removeAll { it.isBlank() }
        return teams
    }

    private fun loadData(){
        val data = viewModel.matchDetailsList()[0]

        supportActionBar?.title = data.strFilename

        var subHome = HashMap<String, List<String>>()
        var subAway = HashMap<String, List<String>>()

        home_name.text = data.strHomeTeam
        away_name.text = data.strAwayTeam
        score.text = "${data.intHomeScore ?: 0} - ${data.intAwayScore ?: 0}"
        date_match.text = Utils.dateFormat(data.dateEvent ?: Date().toString())
        time_match.text = Utils.timeFormat(data.strTime ?: Date().toString())

        val status = ArrayList<String>()

        status.add("Goal")
        status.add("Yellow Card")
        status.add("Red Card")
        status.add("Lineup Goal Keeper")
        status.add("Lineup Defense")
        status.add("Lineup Mid Field")
        status.add("Lineup Forward")
        status.add("Lineup Subtitution")
        status.add("Formation")

        subHome.put("Goal", listTeam(data.strHomeGoalDetails ?: ""))
        subHome.put("Yellow Card", listTeam(data.strHomeYellowCards?: ""))
        subHome.put("Red Card", listTeam(data.strHomeRedCards ?: ""))
        subHome.put("Lineup Goal Keeper", listTeam(data.strHomeLineupGoalkeeper ?: ""))
        subHome.put("Lineup Defense", listTeam(data.strHomeLineupDefense ?: ""))
        subHome.put("Lineup Mid Field", listTeam(data.strHomeLineupMidfield ?: ""))
        subHome.put("Lineup Forward", listTeam(data.strHomeLineupForward ?: ""))
        subHome.put("Lineup Subtitution", listTeam(data.strHomeLineupSubstitutes ?: ""))
        subHome.put("Formation", listTeam(data.strHomeFormation ?: ""))

        subAway.put("Goal", listTeam(data.strAwayGoalDetails ?: ""))
        subAway.put("Yellow Card", listTeam(data.strAwayYellowCards?: ""))
        subAway.put("Red Card", listTeam(data.strAwayRedCards ?: ""))
        subAway.put("Lineup Goal Keeper", listTeam(data.strAwayLineupGoalkeeper ?: ""))
        subAway.put("Lineup Defense", listTeam(data.strAwayLineupDefense ?: ""))
        subAway.put("Lineup Mid Field", listTeam(data.strAwayLineupMidfield ?: ""))
        subAway.put("Lineup Forward", listTeam(data.strAwayLineupForward ?: ""))
        subAway.put("Lineup Subtitution", listTeam(data.strAwayLineupSubstitutes ?: ""))
        subAway.put("Formation", listTeam(data.strAwayFormation ?: ""))

        val adapter = MatchDetailsListAdapter(this, subHome, subAway, status)
        info_list.setAdapter(adapter)

        info_list.setOnGroupClickListener { parent, view, groupPosition, id ->
            if(info_list.isGroupExpanded((groupPosition)))
                info_list.collapseGroupWithAnimation(groupPosition)
            else
                info_list.expandGroupWithAnimation(groupPosition)
            true
        }
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    main.visibility = RecyclerView.VISIBLE
                    loading_layout.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    main.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    main.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorMatchLast()?.status_message ?: ""
                    return false
                }
                else -> {
                    main.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = getString(R.string.unknown_error)
                    return false
                }
            }
        } ?: run {
            return false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
