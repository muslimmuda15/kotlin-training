package com.rachmad.app.league.ui.league

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.rachmad.app.league.R
import com.rachmad.app.league.ui.MainActivity
import com.rachmad.app.league.`object`.LeagueList
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.viewmodel.LeagueViewModel
import kotlinx.android.synthetic.main.fragment_league_list.*
import kotlinx.android.synthetic.main.fragment_league_list.view.list

class LeagueFragment : Fragment() {
    private var listenerLeague: OnLeagueListFragmentListener? = null
    lateinit var viewModel: LeagueViewModel

    lateinit var adapterList: MyLeagueRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_league_list, container, false)

        viewModel = (activity as MainActivity).viewModel
        view.list.apply {
            val manager = GridLayoutManager(context, 2)
            adapterList = MyLeagueRecyclerViewAdapter(listenerLeague)

            layoutManager = manager
            adapter = adapterList
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val connection = viewModel.connectionLeagueList()
        viewModel.connectLeague()

        connection.observe(this, Observer<Int> {
            if(checkConnection(it)){
                adapterList.submitList(viewModel.leagueList())
            }
        })
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    list.visibility = RecyclerView.VISIBLE
                    loadingLayout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    list.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    list.visibility = RecyclerView.GONE
                    loadingLayout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorLeagueList()?.status_message
                    return false
                }
                else -> {
                    list.visibility = RecyclerView.GONE
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
        if (context is OnLeagueListFragmentListener) {
            listenerLeague = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnLeagueListFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listenerLeague = null
    }

    interface OnLeagueListFragmentListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: LeagueList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = LeagueFragment()
    }
}
