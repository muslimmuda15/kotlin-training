package com.rachmad.app.league.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.rachmad.app.league.BuildConfig
import com.rachmad.app.league.helper.ui.DatabaseHelper
import org.jetbrains.anko.db.*

class TeamDB(c: Context): ManagedSQLiteOpenHelper(c, BuildConfig.TEAM_DB, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(
            DatabaseHelper.TABLE_TEAM,
            true,
            "idTeam" to TEXT + PRIMARY_KEY + UNIQUE,
            "strTeam" to TEXT,
            "strTeamBadge" to TEXT,
            "strTeamJersey" to TEXT,
            "strAlternate" to TEXT,
            "strDescriptionEN" to TEXT,
            "intFormedYear" to TEXT,
            "strStadium" to TEXT,
            "strStadiumThumb" to TEXT,
            "strStadiumDescription" to TEXT,
            "strStadiumLocation" to TEXT,
            "intStadiumCapacity" to TEXT,
            "strWebsite" to TEXT,
            "strCountry" to TEXT,
            "strTeamBanner" to TEXT,
            "strTeamFanart1" to TEXT,
            "strTeamFanart2" to TEXT,
            "strTeamFanart3" to TEXT,
            "strTeamFanart4" to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable(DatabaseHelper.TABLE_TEAM, true)
    }
}