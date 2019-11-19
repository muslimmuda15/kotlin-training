package com.rachmad.app.league.ui.league

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import android.util.TypedValue

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