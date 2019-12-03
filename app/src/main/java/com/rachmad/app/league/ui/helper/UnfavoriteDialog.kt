package com.app.rachmad.movie.ui.helper

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.rachmad.app.league.R
import com.rachmad.app.league.viewmodel.MatchViewModel
import kotlinx.android.synthetic.main.activity_unfavorite_dialog.*

class UnfavoriteDialog(val c: Context, val viewModel: MatchViewModel, val id: String) : Dialog(c, R.style.dialogStyle) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unfavorite_dialog)

        yes.setBackgroundColor(ContextCompat.getColor(c, R.color.white))
        no.setBackgroundColor(ContextCompat.getColor(c, R.color.white))

        no.setOnClickListener {
            dismiss()
        }

        yes.setOnClickListener {
            viewModel.deleteMatch(id)
            viewModel.updateMatchDetails(id)
            dismiss()
        }
    }
}
