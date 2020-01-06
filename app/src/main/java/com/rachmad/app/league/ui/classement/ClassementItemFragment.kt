package com.rachmad.app.league.ui.classement

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.ClassementData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.league.details.LeagueDetailsActivity
import com.rachmad.app.league.viewmodel.ClassementViewModel
import de.codecrafters.tableview.TableDataAdapter
import de.codecrafters.tableview.TableHeaderAdapter
import de.codecrafters.tableview.model.TableColumnDpWidthModel
import de.codecrafters.tableview.model.TableColumnWeightModel
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter
import kotlinx.android.synthetic.main.fragment_classement_item_list.*
import kotlinx.android.synthetic.main.fragment_classement_item_list.view.*
import kotlin.error

class ClassementItemFragment : Fragment() {

    var leagueId: Int = 0
    private var listener: OnClassementListener? = null
    lateinit var viewModel: ClassementViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            leagueId = it.getInt(ARG_LEAGUE_ID, 0)
        }
    }

    private fun tableColumnWeightModel(): TableColumnWeightModel{
        val tableColumn = TableColumnWeightModel(7)
        tableColumn.setColumnWeight(0,2)
        tableColumn.setColumnWeight(1,1)
        tableColumn.setColumnWeight(2,1)
        tableColumn.setColumnWeight(3,1)
        tableColumn.setColumnWeight(4,1)
        tableColumn.setColumnWeight(5,1)
        tableColumn.setColumnWeight(6,1)
        return tableColumn
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_classement_item_list, container, false)
        viewModel = (activity as LeagueDetailsActivity).classementViewModel

        view.classement_list.apply {
            headerAdapter = ClassementHeaderAdapter(this.context, 7)
            columnModel = tableColumnWeightModel()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val connection = viewModel.connectionClassementList()
        viewModel.classement(leagueId)

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                classement_list.dataAdapter = ClassementDataAdapter(this.context, viewModel.classementList())
            }
        })
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    classement_layout.visibility = RecyclerView.VISIBLE
                    loadingLayout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    classement_layout.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    classement_layout.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorClassementList()?.status_message
                    return false
                }
                else -> {
                    classement_layout.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = getString(R.string.unknown_error)
                    return false
                }
            }
        } ?: run {
            return false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnClassementListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnClassementListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnClassementListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: ClassementData?)
    }

    inner class ClassementHeaderAdapter(val c: Context, columnCount: Int): TableHeaderAdapter(c, columnCount){
        override fun getHeaderView(columnIndex: Int, parentView: ViewGroup?): View {
            val view = TextView(c)
            when(columnIndex){
                0 -> {
                    view.text = getString(R.string.team)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setTypeface(null, Typeface.BOLD)
                    view.setPadding(8, 24, 0, 24)
                }
                1 -> {
                    view.text = getString(R.string.played)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setPadding(0, 24, 0, 24)
                    view.setTypeface(null, Typeface.BOLD)
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                2 -> {
                    view.text = getString(R.string.goals)
                    view.setPadding(0, 24, 0, 24)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setTypeface(null, Typeface.BOLD)
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                3 -> {
                    view.text = getString(R.string.win)
                    view.setPadding(0, 24, 0, 24)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setTypeface(null, Typeface.BOLD)
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                4 -> {
                    view.text = getString(R.string.draw)
                    view.setPadding(0, 24, 0, 24)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setTypeface(null, Typeface.BOLD)
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                5 -> {
                    view.text = getString(R.string.loss)
                    view.setPadding(0, 24, 0, 24)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setTypeface(null, Typeface.BOLD)
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                6 -> {
                    view.text = getString(R.string.total)
                    view.setPadding(0, 24, 0, 24)
                    view.setTextColor(ContextCompat.getColor(c, R.color.white))
                    view.setTypeface(null, Typeface.BOLD)
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
            }
            return view
        }
    }

    inner class ClassementDataAdapter(val c: Context?, list: List<ClassementData>): TableDataAdapter<ClassementData>(c, list){
        override fun getCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup?): View {
            val data = getRowData(rowIndex)
            val view = TextView(c)
            when(columnIndex){
                0 -> {
                    view.text = data.name
                    view.setPadding(8, 0, 0, 0)
                }
                1 -> {
                    view.text = data.played.toString()
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                2 -> {
                    view.text = data.goalsfor.toString()
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                3 -> {
                    view.text = data.win.toString()
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                4 -> {
                    view.text = data.draw.toString()
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                5 -> {
                    view.text = data.loss.toString()
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
                6 -> {
                    view.text = data.total.toString()
                    view.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                }
            }
            return view
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_LEAGUE_ID = "LeagueId"
        @JvmStatic
        fun newInstance(leagueId: Int) =
            ClassementItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LEAGUE_ID, leagueId)
                }
            }
    }
}
