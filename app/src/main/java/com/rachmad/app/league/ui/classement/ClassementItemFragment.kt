package com.rachmad.app.league.ui.classement

import android.content.Context
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
import androidx.lifecycle.Observer
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.ClassementData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.league.details.LeagueDetailsActivity
import com.rachmad.app.league.viewmodel.ClassementViewModel
import kotlinx.android.synthetic.main.fragment_classement_item_list.*
import kotlinx.android.synthetic.main.fragment_classement_item_list.view.*
import kotlin.error

class ClassementItemFragment : Fragment() {

    var leagueId: Int = 0
    private var listener: OnClassementListener? = null
    lateinit var viewModel: ClassementViewModel

    lateinit var adapterList: MyClassementItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            leagueId = it.getInt(ARG_LEAGUE_ID, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_classement_item_list, container, false)
        viewModel = (activity as LeagueDetailsActivity).classementViewModel

        view.classement_list.apply {
            val manager = LinearLayoutManager(context)
            adapterList = MyClassementItemRecyclerViewAdapter(listener)

            layoutManager = manager
            adapter = adapterList
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val connection = viewModel.connectionClassementList()
        viewModel.classement(leagueId)

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                adapterList.submitList(viewModel.classementList())
            }
        })
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    classement_list.visibility = RecyclerView.VISIBLE
                    loadingLayout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    classement_list.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    classement_list.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorClassementList()?.status_message
                    return false
                }
                else -> {
                    classement_list.visibility = RecyclerView.GONE
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
