package com.rachmad.app.league.ui.league

import android.util.TypedValue
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.rachmad.app.league.GlideApp
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.LeagueList
import com.rachmad.app.league.ui.league.LeagueFragment.OnListFragmentInteractionListener
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

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

        name.text = if(!item.strLeague.isNullOrBlank())
            item.strLeague
        else
            item.strLeagueAlternate

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

    class LeagueItemData: AnkoComponent<ViewGroup> {
        companion object {
            const val imageLogo = 100
            const val name = 101
        }
        override fun createView(ui: AnkoContext<ViewGroup>): View = with(ui){
            cardView {
                elevation = dip(3).toFloat()
                radius = dip(8).toFloat()

                lparams{
                    width = matchParent
                    height = wrapContent
                    bottomMargin = dip(8)
                    leftMargin = dip(8)
                }
                verticalLayout {
                    lparams(matchParent, wrapContent)
                    val outValue = TypedValue()
                    context.theme.resolveAttribute(android.R.attr.selectableItemBackground, outValue, true)
                    backgroundResource = outValue.resourceId

                    imageView {
                        id = imageLogo
                        adjustViewBounds = true
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }.lparams{
                        width = matchParent
                        height = dip(200)
                        marginStart = 2
                        marginEnd = 2
                    }

                    textView {
                        id = name
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
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
