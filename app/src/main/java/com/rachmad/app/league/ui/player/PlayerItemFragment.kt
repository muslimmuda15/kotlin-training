package com.rachmad.app.league.ui.player

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.PlayerData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.team.details.TeamDetailsActivity
import com.rachmad.app.league.viewmodel.PlayerViewModel
import kotlinx.android.synthetic.main.fragment_player_item_list.*
import kotlinx.android.synthetic.main.fragment_player_item_list.view.*

class PlayerItemFragment : Fragment() {
    private var teamName: String? = null
    private var listener: OnPlayerFragmentListener? = null
    lateinit var viewModel: PlayerViewModel
    lateinit var adapterList: MyPlayerItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            teamName = it.getString(ARG_TEAM_NAME)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_player_item_list, container, false)
        viewModel = (activity as TeamDetailsActivity).playerViewModel

        with(view) {
            val manager = LinearLayoutManager(context)
            adapterList = MyPlayerItemRecyclerViewAdapter(listener)

            player_list.layoutManager = manager
            player_list.adapter = adapterList
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val connection = viewModel.connectionPalyerList()
        viewModel.playerList(teamName ?: "")

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                adapterList.submitList(viewModel.playerList())
            }
        })
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    player_list.visibility = RecyclerView.VISIBLE
                    loadingLayout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    player_list.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    player_list.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorPlayerList()?.status_message
                    return false
                }
                else -> {
                    player_list.visibility = RecyclerView.GONE
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
        if (context is OnPlayerFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnPlayerFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnPlayerFragmentListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: PlayerData?)
    }

    companion object {
        const val ARG_TEAM_NAME = "TeamName"

        @JvmStatic
        fun newInstance(teamName: String) =
            PlayerItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEAM_NAME, teamName)
                }
            }
    }
}
