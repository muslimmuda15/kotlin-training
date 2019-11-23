package com.rachmad.app.league.ui.league

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachmad.app.league.R
import com.rachmad.app.league.ui.league.LeagueFragment.OnLeagueListFragmentListener
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.`object`.LeagueList

import kotlinx.android.synthetic.main.fragment_league.view.*

class MyLeagueRecyclerViewAdapter(
    private val mListenerLeague: OnLeagueListFragmentListener?
) : RecyclerView.Adapter<MyLeagueRecyclerViewAdapter.ViewHolder>() {

    var leagueList: List<LeagueList> = ArrayList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LeagueList
            mListenerLeague?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_league, parent, false)
        return ViewHolder(view)
    }

    fun submitList(data: List<LeagueList>){
        leagueList = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder){
        val item = leagueList[position]

        GlideApp.with(image)
            .load(item.strBadge)
            .fitCenter()
            .placeholder(R.drawable.no_image)
            .into(image)

        name.text = if(!item.strLeague.isNullOrBlank())
            item.strLeague
        else
            item.strLeagueAlternate

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = leagueList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val image: ImageView = mView.image
        val name: TextView = mView.name
    }
}
