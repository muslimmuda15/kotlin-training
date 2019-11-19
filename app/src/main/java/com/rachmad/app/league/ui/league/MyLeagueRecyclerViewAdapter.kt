package com.rachmad.app.league.ui.league

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.dataclass.LeagueList
import com.rachmad.app.league.ui.league.LeagueFragment.OnListFragmentInteractionListener
import org.jetbrains.anko.AnkoContext

class MyLeagueRecyclerViewAdapter(
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyLeagueRecyclerViewAdapter.ViewHolder>() {

    var leagueList: List<LeagueList> = ArrayList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as LeagueList
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun submitList(data: List<LeagueList>){
        leagueList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LeagueItemData().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = leagueList[position]

        GlideApp.with(logo)
            .load(item.strBadge)
            .fitCenter()
            .placeholder(R.drawable.no_image)
            .into(logo)

        name.text = item.strLeagueAlternate

        with(mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = leagueList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val logo: ImageView = mView.findViewById(LeagueItemData.imageLogo)
        val name: TextView = mView.findViewById(LeagueItemData.name)
    }
}
