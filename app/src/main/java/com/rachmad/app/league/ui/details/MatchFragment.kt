package com.rachmad.app.league.ui.details

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.viewmodel.MatchViewModel
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.UI

class MatchFragment : Fragment() {
    private var position = -1
    private var idLeague = 0
    private var listener: OnTabListener? = null

    lateinit var viewModel: MatchViewModel
    lateinit var matchList: RecyclerView
    var loadingLayout: LinearLayout? = null
    var progressBar: ProgressBar? = null
    var errorText: TextView? = null

    lateinit var adapterList: MyMatchRecyclerViewAdapter
    var status = -1

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
        Log.d("main", "POSITION SUB : " + position)
        Log.d("main", "STATUS : " + status)

        viewModel = (activity as LeagueDetailsActivity).matchViewModel
        adapterList = MyMatchRecyclerViewAdapter(listener)

        return UI {
            frameLayout {
                lparams(matchParent, matchParent)

                recyclerView {
                    id = if(position == 0) listLast else listNext
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
                    id = if(position == 0) loadingErrorLayoutLast else loadingErrorLayoutNext
                    lparams(matchParent, matchParent)
                    gravity = Gravity.CENTER
                    elevation = 2.0F
                    isFocusable = true
                    isClickable = true
                    backgroundColor = ContextCompat.getColor(this.context, R.color.white)

                    progressBar {
                        id = if(position == 0) loadingLast else loadingNext
                        visibility = View.VISIBLE
                    }.lparams(width = wrapContent, height = wrapContent)

                    textView {
                        id = if(position == 0) errorsLast else errorsNext
                    }
                }
            }
        }.view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(activity!!){
            matchList = findViewById(if(position == 0) listLast else listNext)
            loadingLayout = findViewById(if(position == 0) loadingErrorLayoutLast else loadingErrorLayoutNext)
            progressBar = findViewById(if(position == 0) loadingLast else loadingNext)
            errorText = findViewById(if(position == 0) errorsLast else errorsNext)
        }

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
                    matchList.visibility = RecyclerView.VISIBLE
                    loadingLayout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    matchList!!.visibility = RecyclerView.GONE
                    loadingLayout!!.visibility = ViewGroup.VISIBLE
                    progressBar!!.visibility = ProgressBar.VISIBLE
                    errorText!!.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    matchList!!.visibility = RecyclerView.GONE
                    loadingLayout!!.visibility = ViewGroup.VISIBLE
                    progressBar!!.visibility = ProgressBar.GONE
                    errorText!!.visibility = TextView.VISIBLE

                    errorText!!.text = when(position) {
                        0 -> viewModel.errorMatchLast()?.status_message ?: ""
                        1 -> viewModel.errorMatchNext()?.status_message ?: ""
                        else -> ""
                    }
                    return false
                }
                else -> {
                    matchList!!.visibility = RecyclerView.GONE
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
        if (context is OnTabListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnTabListener {
        fun onTabFragmentInteraction(item: MatchList?)
    }

    companion object {

        const val ARG_POSITION = "position"
        const val ARG_ID = "id"
        const val listNext = 3000
        const val errorsNext = 3001
        const val loadingNext = 3002
        const val loadingErrorLayoutNext = 3003
        const val listLast = 5000
        const val errorsLast = 5001
        const val loadingLast = 5002
        const val loadingErrorLayoutLast = 5003

        @JvmStatic
        fun newInstance(id: Int, position: Int) =
            MatchFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                    putInt(ARG_ID, id)
                }
            }
    }
}
