package com.rachmad.app.league.ui.match

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList

import com.rachmad.app.league.ui.match.MatchItemFragment.OnTabFragmentListener

import kotlinx.android.synthetic.main.fragment_match_item.view.*

class MyMatchItemRecyclerViewAdapter(
    private val mListener: OnTabFragmentListener?
) : RecyclerView.Adapter<MyMatchItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    var matchList: List<MatchList> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MatchList
            mListener?.onListFragmentInteraction(item)
        }
    }

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
        GlideApp.with(imageMatch)
            .load(item.strThumb)
            .fitCenter()
            .into(imageMatch)

        if(item.strThumb.isNullOrBlank())
            textEvent.visibility = View.VISIBLE
        else
            textEvent.visibility = View.GONE

        textEvent.text = item.strEvent

        if(item.intHomeScore.isNullOrBlank() || item.intAwayScore.isNullOrBlank())
            textMatch.visibility = View.GONE
        else
            textMatch.visibility = View.VISIBLE

        textMatch.text = "${item.intHomeScore} - ${item.intAwayScore}"

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = matchList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imageMatch: ImageView = mView.image
        val textEvent: TextView = mView.event_name
        val textMatch: TextView = mView.event_score
    }
}
