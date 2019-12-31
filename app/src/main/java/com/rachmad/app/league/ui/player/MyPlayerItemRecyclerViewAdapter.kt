package com.rachmad.app.league.ui.player

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.PlayerData
import com.rachmad.app.league.ui.player.PlayerItemFragment.OnPlayerFragmentListener

import kotlinx.android.synthetic.main.fragment_player_item.view.*

class MyPlayerItemRecyclerViewAdapter(
    private val mListener: OnPlayerFragmentListener?
) : RecyclerView.Adapter<MyPlayerItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    var playerList: List<PlayerData> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as PlayerData
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun submitList(list: List<PlayerData>){
        playerList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_player_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = playerList[position]

        GlideApp.with(photo)
            .load(item.strCutout)
            .fitCenter()
            .placeholder(R.drawable.no_image)
            .into(photo)

        name.text = item.strPlayer
        positionPlayer.text = item.strPosition

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = playerList.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val photo: ImageView = mView.photo
        val name: TextView = mView.name
        val positionPlayer: TextView = mView.position
    }
}
