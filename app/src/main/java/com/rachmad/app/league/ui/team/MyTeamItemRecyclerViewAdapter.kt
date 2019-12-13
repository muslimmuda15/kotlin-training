package com.rachmad.app.league.ui.team

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.TeamData

import kotlinx.android.synthetic.main.fragment_team_item.view.*

class MyTeamItemRecyclerViewAdapter(
    private val mListener: TeamItemFragment.OnTeamFragmentListener?
) : RecyclerView.Adapter<MyTeamItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    var teamList: List<TeamData> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as TeamData
            mListener?.onTeamFragmentListener(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_team_item, parent, false)
        return ViewHolder(view)
    }

    fun submitList(list: List<TeamData>){
        teamList = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = teamList[position]

        GlideApp.with(teamImage)
            .load(item.strTeamBadge)
            .fitCenter()
            .into(teamImage)

        teamName.text = item.strTeam

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = teamList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val teamImage = mView.team_image
        val teamName = mView.team_name
    }
}
