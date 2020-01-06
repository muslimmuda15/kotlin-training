package com.app.rachmad.movie.ui.helper

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.rachmad.app.league.R
import com.rachmad.app.league.viewmodel.MatchViewModel
import com.rachmad.app.league.viewmodel.TeamViewModel
import kotlinx.android.synthetic.main.activity_unfavorite_dialog.*

class UnfavoriteDialog(val c: Context, val matchViewModel: MatchViewModel?, val teamViewModel: TeamViewModel? ,val id: String) : Dialog(c, R.style.dialogStyle) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unfavorite_dialog)

        yes.setBackgroundColor(ContextCompat.getColor(c, R.color.white))
        no.setBackgroundColor(ContextCompat.getColor(c, R.color.white))

        no.setOnClickListener {
            dismiss()
        }

        yes.setOnClickListener {
            matchViewModel?.deleteMatch(id)
            matchViewModel?.updateMatchDetails(id)

            teamViewModel?.deleteTeam(id)
            teamViewModel?.updateTeamDetails(id)
            dismiss()
        }
    }
}
