package com.rachmad.app.league.ui.team

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
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.league.details.LeagueDetailsActivity
import com.rachmad.app.league.viewmodel.TeamViewModel
import kotlinx.android.synthetic.main.fragment_team_item_list.*
import kotlinx.android.synthetic.main.fragment_team_item_list.error
import kotlinx.android.synthetic.main.fragment_team_item_list.loading
import kotlinx.android.synthetic.main.fragment_team_item_list.view.*
import kotlin.error

const val ARG_LEAGUE_NAME = "LeagueName"
class TeamItemFragment : Fragment() {
    private var leagueName = ""
    private var listener: OnTeamFragmentListener? = null
    lateinit var viewModel: TeamViewModel
    lateinit var adapterList: MyTeamItemRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_team_item_list, container, false)

        if(activity is LeagueDetailsActivity){
            viewModel = (activity as LeagueDetailsActivity).teamViewModel
        }

        with(view.team_list) {
            val manager = GridLayoutManager(context, 2)
            adapterList = MyTeamItemRecyclerViewAdapter(listener)

            layoutManager = manager
            adapter = adapterList
        }
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            leagueName = it.getString(ARG_LEAGUE_NAME, "")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val connection = viewModel.connectionTeamByLeague()
        viewModel.teamByLeague(leagueName)

        connection.observe(this, Observer {
            if(checkConnection(it)){
                adapterList.submitList(viewModel.teamByLeague())
            }
        })
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    team_list.visibility = RecyclerView.VISIBLE
                    loading_layout.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    team_list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    team_list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorTeamByLeague()?.status_message
                    return false
                }
                else -> {
                    team_list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
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
        if (context is OnTeamFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTeamFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnTeamFragmentListener {
        fun onTeamFragmentListener(item: TeamData)
    }

    companion object {
        @JvmStatic
        fun newInstance(leagueName: String) = TeamItemFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_LEAGUE_NAME, leagueName)
            }
        }
    }
}
