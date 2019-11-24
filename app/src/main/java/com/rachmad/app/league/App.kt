package com.rachmad.app.league

import android.app.Application
import android.content.Context
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
    companion object {
        lateinit var context: Context
    }
}

@GlideModule
class MyAppGlideModule: AppGlideModule()