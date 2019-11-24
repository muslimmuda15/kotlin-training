package com.rachmad.app.league

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.rachmad.app.league.viewmodel.LeagueViewModel

open class LeagueActivity: AppCompatActivity() {
    val viewModel: LeagueViewModel by lazy { ViewModelProviders.of(this).get(
        LeagueViewModel::class.java)
    }
}