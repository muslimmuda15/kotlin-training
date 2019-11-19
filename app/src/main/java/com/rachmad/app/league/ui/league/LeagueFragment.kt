package com.rachmad.app.league.ui.league

import android.content.Context
import android.os.Bundle
import android.view.Gravity
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
import com.rachmad.app.league.ui.MainActivity
import com.rachmad.app.league.R
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.dataclass.LeagueList
import com.rachmad.app.league.viewmodel.ListModel
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class LeagueFragment : Fragment() {
    private var listener: OnListFragmentInteractionListener? = null
    lateinit var viewModel: ListModel

    var leagueList: RecyclerView? = null
    var loadingLayout: LinearLayout? = null
    var progressBar: ProgressBar? = null
    var errorText: TextView? = null

    lateinit var adapterList: MyLeagueRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = (activity as MainActivity).viewModel
        adapterList = MyLeagueRecyclerViewAdapter(listener)

        return UI {
            frameLayout {
                lparams(matchParent, matchParent)

                recyclerView {
                    id = list
                    elevation = 1.0F
                    val manager = GridLayoutManager(context, 2)
                    layoutManager = manager
                    adapter = adapterList
                }.lparams{
                    width = matchParent
                    height = matchParent
                    rightPadding = dip(8)
                }

                verticalLayout {
                    id =
                        loadingErrorLayout
                    lparams(matchParent, matchParent)
                    gravity = Gravity.CENTER
                    elevation = 2.0F
                    isFocusable = true
                    isClickable = true

                    progressBar {
                        id = loading
                        visibility = View.VISIBLE
                    }.lparams(width = wrapContent, height = wrapContent)

                    textView {
                        id = errors
                    }
                }
            }
        }.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(activity!!) {
            leagueList = findViewById(list)
            loadingLayout = findViewById(loadingErrorLayout)
            progressBar = findViewById(loading)
            errorText = findViewById(errors)
        }

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
                    leagueList!!.visibility = RecyclerView.VISIBLE
                    loadingLayout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    leagueList!!.visibility = RecyclerView.GONE
                    loadingLayout!!.visibility = ViewGroup.VISIBLE
                    progressBar!!.visibility = ProgressBar.VISIBLE
                    errorText!!.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    leagueList!!.visibility = RecyclerView.GONE
                    loadingLayout!!.visibility = ViewGroup.VISIBLE
                    progressBar!!.visibility = ProgressBar.GONE
                    errorText!!.visibility = TextView.VISIBLE

                    errorText!!.text = viewModel.errorLeagueList()?.status_message
                    return false
                }
                else -> {
                    leagueList!!.visibility = RecyclerView.GONE
                    loadingLayout!!.visibility = ViewGroup.VISIBLE
                    progressBar!!.visibility = ProgressBar.GONE
                    errorText!!.visibility = TextView.VISIBLE

                    errorText!!.text = getString(R.string.unknown_error)
                    return false
                }
            }
        } ?: run {
            return false
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: LeagueList)
    }

    companion object {
        const val list = 1000
        const val errors = 1001
        const val loading = 1002
        const val loadingErrorLayout = 1003

        @JvmStatic
        fun newInstance() = LeagueFragment()
    }
}
