package com.rachmad.app.league.ui.classement

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.ClassementData
import com.rachmad.app.league.ui.classement.ClassementItemFragment.OnClassementListener

import kotlinx.android.synthetic.main.fragment_classement_item.view.*

class MyClassementItemRecyclerViewAdapter(
    private val mListener: OnClassementListener?
) : RecyclerView.Adapter<MyClassementItemRecyclerViewAdapter.ViewHolder>() {

    private var mValues: List<ClassementData> = ArrayList()
    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as ClassementData
            mListener?.onListFragmentInteraction(item)
        }
    }

    fun submitList(data: List<ClassementData>){
        mValues = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_classement_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder){
        val item = mValues[position]

        teamName.text = item.name
        played.text = item.played.toString()
        goals.text = item.goalsfor.toString()
        win.text = item.win.toString()
        draw.text = item.draw.toString()
        loss.text = item.loss.toString()
        total.text = item.total.toString()

        with(mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val teamName: TextView = mView.team_name
        val played: TextView = mView.played
        val goals: TextView = mView.goals
        val win: TextView = mView.win
        val draw: TextView = mView.draw
        val loss: TextView = mView.loss
        val total: TextView = mView.total
    }
}
