package com.rachmad.app.league.helper.ui

import android.content.Context
import com.rachmad.app.league.sqlite.MatchDB

object DatabaseHelper {
    const val TABLE_MATCH = "match"
    fun getInstance(c: Context) = MatchDB(c)
}