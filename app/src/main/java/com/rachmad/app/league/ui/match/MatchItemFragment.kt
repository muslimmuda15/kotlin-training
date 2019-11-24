package com.rachmad.app.league.ui.match

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
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.ui.league.details.LeagueDetailsActivity
import com.rachmad.app.league.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.fragment_match_item_list.*
import kotlinx.android.synthetic.main.fragment_match_item_list.view.*
import kotlinx.android.synthetic.main.fragment_match_item_list.view.list

const val ARG_POSITION = "position"
const val ARG_ID = "id"
class MatchItemFragment : Fragment() {
    private var position = -1
    private var idLeague = 0
    private var listener: OnTabFragmentListener? = null
    lateinit var adapterList: MyMatchItemRecyclerViewAdapter
    lateinit var viewModel: MatchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            idLeague = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_match_item_list, container, false)

        val manager = GridLayoutManager(context, 2)
        adapterList = MyMatchItemRecyclerViewAdapter(this, listener)

        viewModel = (activity as LeagueDetailsActivity).matchViewModel
        view.list.apply {
            layoutManager = manager
            adapter = adapterList
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when(position){
            0 -> {
                val connection = viewModel.connectionMatchLast()
                viewModel.matchLast(idLeague)

                connection.observe(this, Observer<Int> {
                    if (checkConnection(it))
                        adapterList.submitList(viewModel.matchLastList())
                })
            }
            1 -> {
                val connection = viewModel.connectionMatchNext()
                viewModel.matchNext(idLeague)

                connection.observe(this, Observer<Int> {
                    if (checkConnection(it))
                        adapterList.submitList(viewModel.matchNextList())
                })
            }
        }
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    list.visibility = RecyclerView.VISIBLE
                    loading_layout.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error_text.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error_text.visibility = TextView.VISIBLE

                    error_text.text = when(position) {
                        0 -> viewModel.errorMatchLast()?.status_message ?: ""
                        1 -> viewModel.errorMatchNext()?.status_message ?: ""
                        else -> ""
                    }
                    return false
                }
                else -> {
                    list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error_text.visibility = TextView.VISIBLE

                    error_text.text = getString(R.string.unknown_error)
                    return false
                }
            }
        } ?: run {
            return false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnTabFragmentListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnTabFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnTabFragmentListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: MatchList)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MatchItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}
