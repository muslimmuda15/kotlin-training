package com.rachmad.app.league.ui.match.details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.rachmad.app.league.R
import kotlinx.android.synthetic.main.custom_match_list.view.*
import kotlinx.android.synthetic.main.custom_sub_match_list.view.*
import kotlinx.android.synthetic.main.custom_sub_match_list.view.away_count
import kotlinx.android.synthetic.main.custom_sub_match_list.view.home_count
import kotlinx.android.synthetic.main.custom_sub_match_list.view.status

class MatchDetailsListAdapter(
    val c: Context,
    val subHomeList: HashMap<String, List<String>>,
    val subAwayList: HashMap<String, List<String>>,
    val statusList: List<String>
): BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        var home = ""
        var away = ""
        val homeChild = subHomeList.get(statusList.get(listPosition))
        val awayChild = subAwayList.get(statusList.get(listPosition))

        try {
            home = homeChild!!.get(expandedListPosition)
        }catch(e: Exception){

        }
        try {
            away = awayChild!!.get(expandedListPosition)
        }catch(e: Exception){

        }
        
        return home + "|" + away
    }

    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }

    override fun getChildView(listPosition: Int, expandedListPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val dataText: String = getChild(listPosition, expandedListPosition) as String
        val dataArray = dataText.split("|")

        val layoutInflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_sub_match_list, null)
        view.home_count.text = dataArray.get(0) ?: ""
        view.away_count.text = dataArray.get(1) ?: ""
        return view
    }

    override fun getChildrenCount(listPosition: Int): Int {
        val home = subHomeList.get(statusList.get(listPosition))?.size ?: 0
        val away = subAwayList.get(statusList.get(listPosition))?.size ?: 0
        if(home > away)
            return home
        else if(home < away)
            return away
        else
            return home
    }

    override fun getGroup(listPosition: Int): Any? {
        return statusList.get(listPosition)
    }

    override fun getGroupCount(): Int {
        return statusList.size
    }

    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }

    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.custom_match_list, null)

        if(statusList.size - 1 == listPosition){
            view.home_count.text = subHomeList.get(statusList.get(listPosition))?.let {
                if(it.size > 0)
                    it.get(0)
                else
                    ""
            } ?: run {
                ""
            }
            view.away_count.text = subAwayList.get(statusList.get(listPosition))?.let {
                if(it.size > 0)
                    it.get(0)
                else
                    ""
            } ?: run {
                ""
            }
        }
        else {
            view.home_count.text = (subHomeList.get(statusList.get(listPosition))?.size ?: 0).toString()
            view.away_count.text = (subAwayList.get(statusList.get(listPosition))?.size ?: 0).toString()
        }
        view.status.text = statusList[listPosition]

        return view
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }
}