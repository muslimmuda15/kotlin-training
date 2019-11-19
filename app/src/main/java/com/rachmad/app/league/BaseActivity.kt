package com.rachmad.app.league

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.rachmad.app.league.viewmodel.ListModel

open class BaseActivity: AppCompatActivity() {
    val viewModel: ListModel by lazy { ViewModelProviders.of(this).get(
        ListModel::class.java) }
}

@GlideModule
class MyAppGlideModule: AppGlideModule()