package com.rachmad.app.league.ui.details

import android.util.Log
import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.`object`.MatchList


import com.rachmad.app.league.ui.details.MatchFragment.OnTabListener
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class MyMatchRecyclerViewAdapter(
    private val mListener: OnTabListener?
) : RecyclerView.Adapter<MyMatchRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener
    var matchList: List<MatchList> = ArrayList()

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as MatchList
            mListener?.onTabFragmentInteraction(item)
        }
    }

    fun submitList(data: List<MatchList>){
        matchList = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(MatchItemData().createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(holder) {
        val item = matchList[position]

        Log.d("image", item.strThumb ?: "null")
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
        val imageMatch: ImageView = mView.findViewById(MatchItemData.imageLeague)
        val textMatch: TextView = mView.findViewById(MatchItemData.scoreText)
        val textEvent: TextView = mView.findViewById(MatchItemData.eventText)
    }

    class MatchItemData: AnkoComponent<ViewGroup> {
        companion object {
            val imageLeague = 4001
            val scoreText = 4002
            val eventText = 4003
        }

        override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
            cardView {
                elevation = dip(3).toFloat()
                radius = dip(8).toFloat()

                lparams{
                    width = matchParent
                    height = wrapContent
                    margin = dip(4)
                }

                verticalLayout {
                    lparams(matchParent, matchParent)
                    val outValue = TypedValue()
                    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                    backgroundResource = outValue.resourceId

                    imageView {
                        id = imageLeague
                        adjustViewBounds = true
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }.lparams(matchParent, wrapContent)

                    textView {
                        id = eventText
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textSize = sp(8).toFloat()
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        margin = dip(8)
                    }

                    textView {
                        id = scoreText
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textSize = sp(12).toFloat()
                    }.lparams{
                        width = matchParent
                        height = wrapContent
                        margin = dip(8)
                    }
                }
            }
        }
    }
}
