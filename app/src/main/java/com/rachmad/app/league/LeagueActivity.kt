package com.rachmad.app.league

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.rachmad.app.league.viewmodel.LeagueViewModel

open class LeagueActivity: AppCompatActivity() {
    val viewModel: LeagueViewModel by lazy { ViewModelProviders.of(this).get(
        LeagueViewModel::class.java)
    }
}

@GlideModule
class MyAppGlideModule: AppGlideModule()