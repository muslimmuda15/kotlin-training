package com.rachmad.app.league.ui.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import com.rachmad.app.league.R
import com.rachmad.app.league.`object`.MatchDetails
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.fragment_search_item_list.*
import kotlinx.android.synthetic.main.fragment_search_item_list.view.*

class SearchItemFragment : Fragment() {
    private var listener: OnSearchMatchListener? = null
    lateinit var viewModel: MatchViewModel

    lateinit var adapterList: MySearchItemRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search_item_list, container, false)

        val manager = GridLayoutManager(context, 2)
        adapterList = MySearchItemRecyclerViewAdapter(this, listener)

        viewModel = (activity as SearchMatchActivity).viewModel

        view.search_match_list.apply {
            layoutManager = manager
            adapter = adapterList
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loading_layout.visibility = ViewGroup.VISIBLE
        loading.visibility = View.GONE
        error.visibility = View.VISIBLE
        search_match_list.visibility = ViewGroup.GONE

        onSearch()
    }

    private fun onSearch(){
        search_layout.visibility = ViewGroup.VISIBLE
        val connectionSearch = viewModel.connectionMatchSearch()
        connectionSearch.observe(this, Observer<Int> {
            if(checkConnection(it)){
                adapterList.submitList(viewModel.matchSearchList())
            }
        })

        search_text.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString() == "") {
                    loading_layout.visibility = ViewGroup.GONE
                    loading.visibility = View.GONE
                    error.visibility = View.GONE
                    search_match_list.visibility = ViewGroup.VISIBLE

                    adapterList.submitList(viewModel.matchSearchList())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })

        search_text.setOnEditorActionListener { textView, i, keyEvent ->
            if(i == EditorInfo.IME_ACTION_SEARCH){
                viewModel.matchSearch(search_text.text.toString())
                true
            }
            false
        }

        search_text.setOnTouchListener { view, motionEvent ->
            val DRAWABLE_LEFT = 0
            val DRAWABLE_TOP = 1
            val DRAWABLE_RIGHT = 2
            val DRAWABLE_BOTTOM = 3
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                if (motionEvent.rawX >= (search_text.right - search_text.compoundDrawables[DRAWABLE_RIGHT].bounds.width() - resources.getDimension(R.dimen.double_margin))) {
                    // TODO: Close Button
                    search_text.setText("")

                    loading_layout.visibility = ViewGroup.GONE
                    loading.visibility = View.GONE
                    error.visibility = View.GONE
                    search_match_list.visibility = ViewGroup.VISIBLE

                    adapterList.submitList(viewModel.matchSearchList())
                    true
                }
                if (motionEvent.rawX <= (search_text.compoundDrawables[DRAWABLE_LEFT].bounds.width() + resources.getDimension(R.dimen.double_margin))) {
                    // TODO: Search Button
                    viewModel.matchSearch(search_text.text.toString())
                    true
                }
            }
            false
        }
    }

    private fun checkConnection(data: Int?): Boolean{
        data?.let {
            when(it){
                Connection.OK.Status -> {
                    search_match_list.visibility = RecyclerView.VISIBLE
                    loading_layout!!.visibility = ViewGroup.GONE

                    return true
                }
                Connection.ACCEPTED.Status -> {
                    search_match_list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.VISIBLE
                    error.visibility = TextView.GONE
                    return false
                }
                Connection.ERROR.Status -> {
                    search_match_list.visibility = RecyclerView.GONE
                    loading_layout.visibility = ViewGroup.VISIBLE
                    loading.visibility = ProgressBar.GONE
                    error.visibility = TextView.VISIBLE

                    error.text = viewModel.errorMatchSearch()?.status_message
                    return false
                }
                else -> {
                    search_match_list.visibility = RecyclerView.GONE
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
        if (context is OnSearchMatchListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnSearchMatchListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnSearchMatchListener {
        fun onListFragmentInteraction(item: MatchDetails, homeImage: String?, awayImage: String?)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchItemFragment()
    }
}
