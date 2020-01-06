package com.rachmad.app.league.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rachmad.app.league.BuildConfig
import com.rachmad.app.league.helper.ui.DatabaseHelper
import org.jetbrains.anko.db.*

class MatchDB(c: Context): ManagedSQLiteOpenHelper(c, BuildConfig.MATCH_DB, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            DatabaseHelper.TABLE_MATCH,
            true,
            "idEvent" to  TEXT + PRIMARY_KEY + UNIQUE,
            "strEvent" to TEXT,
            "strEventAlternate" to TEXT,
            "strFilename" to TEXT,
            "strSport" to TEXT,
            "strLeague" to TEXT,
            "strSeason" to TEXT,
            "strHomeTeam" to TEXT,
            "strAwayTeam" to TEXT,
            "idHomeTeam" to TEXT,
            "idAwayTeam" to TEXT,
            "intHomeScore" to TEXT,
            "intRound" to TEXT,
            "intAwayScore" to TEXT,
            "intSpectator" to TEXT,
            "strHomeGoalDetails" to TEXT,
            "strHomeRedCards" to TEXT,
            "strHomeYellowCards" to TEXT,
            "strHomeLineupGoalkeeper" to TEXT,
            "strHomeLineupDefense" to TEXT,
            "strHomeLineupMidfield" to TEXT,
            "strHomeLineupForward" to TEXT,
            "strHomeLineupSubstitutes" to TEXT,
            "strHomeFormation" to TEXT,
            "strAwayGoalDetails" to TEXT,
            "strAwayRedCards" to TEXT,
            "strAwayYellowCards" to TEXT,
            "strAwayLineupGoalkeeper" to TEXT,
            "strAwayLineupDefense" to TEXT,
            "strAwayLineupMidfield" to TEXT,
            "strAwayLineupForward" to TEXT,
            "strAwayLineupSubstitutes" to TEXT,
            "strAwayFormation" to TEXT,
            "intHomeShots" to TEXT,
            "intAwayShots" to TEXT,
            "dateEvent" to TEXT,
            "strTime" to TEXT,
            "strThumb" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable(DatabaseHelper.TABLE_MATCH, true)
    }
}