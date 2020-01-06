package com.rachmad.app.league.helper.ui

import android.content.Context
import com.rachmad.app.league.sqlite.MatchDB
import com.rachmad.app.league.sqlite.TeamDB

object DatabaseHelper {
    const val TABLE_MATCH = "match"
    const val TABLE_TEAM = "team"
    fun getMatchInstance(c: Context) = MatchDB(c)
    fun getTeamInstance(c: Context) = TeamDB(c)
}