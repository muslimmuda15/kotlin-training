package com.rachmad.app.league.ui.match

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.repository.LeagueRepository
import com.rachmad.app.league.repository.TeamRepository

import com.rachmad.app.league.ui.match.MatchItemFragment.OnTabFragmentListener

import kotlinx.android.synthetic.main.fragment_match_item.view.*

class MyMatchItemRecyclerViewAdapter(
    private val fr: Fragment,
    private val mListener: OnTabFragmentListener?
) : RecyclerView.Adapter<MyMatchItemRecyclerViewAdapter.ViewHolder>() {

    var matchList: List<MatchList> = ArrayList()

    fun submitList(data: List<MatchList>){
        matchList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_match_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = matchList[position]

        val teamRepository = TeamRepository()
        val connection = teamRepository.connectionTeamData
        teamRepository.team(item.idHomeTeam?.toInt() ?: 0)
        connection.observe(fr, Observer<Int> {
            Log.d("Image", "IMAGE : " + it)
            if(it == Connection.OK.Status){
                Log.d("Image", "IMAGE : " + teamRepository.teamData?.strTeamBadge)
                GlideApp.with(homeImage)
                    .load(teamRepository.teamData?.strTeamBadge)
                    .fitCenter()
                    .into(homeImage)
            }
        })

        val teamRepository2 = TeamRepository()
        val connection2 = teamRepository2.connectionTeamData
        teamRepository2.team(item.idAwayTeam?.toInt() ?: 0)
        connection2.observe(fr, Observer<Int> {
            Log.d("Image", "IMAGE : " + it)
            if(it == Connection.OK.Status){
                Log.d("Image", "IMAGE : " + teamRepository2.teamData?.strTeam)
                GlideApp.with(awayImage)
                    .load(teamRepository2.teamData?.strTeamBadge)
                    .fitCenter()
                    .into(awayImage)
            }
        })

        homeName.text = item.strHomeTeam
        awayName.text = item.strAwayTeam
        score.text = "${item.intHomeScore ?: 0} - ${item.intAwayScore ?: 0}"

        mView.setOnClickListener{
            mListener?.onListFragmentInteraction(
                item,
                teamRepository.teamData?.idTeam,
                teamRepository.teamData?.strTeamBadge,
                teamRepository2.teamData?.idTeam,
                teamRepository2.teamData?.strTeamBadge)
        }
    }

    override fun getItemCount(): Int = matchList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val homeImage: ImageView = mView.home_image
        val awayImage: ImageView = mView.away_image
        val homeName: TextView = mView.home_name
        val awayName: TextView = mView.away_name
        val score: TextView = mView.score
    }
}
