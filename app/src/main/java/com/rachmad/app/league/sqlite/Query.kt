package com.rachmad.app.league.sqlite

import com.rachmad.app.league.App
import com.rachmad.app.league.`object`.MatchDetails
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.helper.ui.DatabaseHelper
import com.rachmad.app.league.helper.ui.DatabaseHelper.TABLE_MATCH
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.select

class Query {
    private val database: MatchDB
        get() = DatabaseHelper.getInstance(App.context)

    fun matchList(): List<MatchList> {
        return database.use {
            select(TABLE_MATCH,
                "idEvent",
                "strEvent",
                "strHomeTeam",
                "strAwayTeam",
                "strFilename",
                "strLeague",
                "intHomeScore",
                "intAwayScore",
                "strThumb",
                "intRound",
                "strSeason",
                "strTime",
                "idHomeTeam",
                "idAwayTeam"
            ).parseList(classParser())
        }
    }

    fun getMatch(id: String): Boolean {
        return database.use {
            select(TABLE_MATCH,"strEvent").whereArgs(
                "idEvent = {id}",
                "id" to id
            ).exec {
                if (count > 0)
                    true
                else
                    false
            }
        }
    }

    fun insertMatch(data: MatchDetails){
        database.use {
            insert(DatabaseHelper.TABLE_MATCH,
                "idEvent" to data.idEvent,
                "strEvent" to data.strEvent,
                "strEventAlternate" to data.strEventAlternate,
                "strFilename" to data.strFilename,
                "strSport" to data.strSport,
                "strLeague" to data.strLeague,
                "strSeason" to data.strSeason,
                "strHomeTeam" to data.strHomeTeam,
                "strAwayTeam" to data.strAwayTeam,
                "idHomeTeam" to data.idHomeTeam,
                "idAwayTeam" to data.idAwayTeam,
                "intHomeScore" to data.intHomeScore,
                "intRound" to data.intRound,
                "intAwayScore" to data.intAwayScore,
                "intSpectator" to data.intSpectator,
                "strHomeGoalDetails" to data.strHomeGoalDetails,
                "strHomeRedCards" to data.strHomeRedCards,
                "strHomeYellowCards" to data.strHomeYellowCards,
                "strHomeLineupGoalkeeper" to data.strHomeLineupGoalkeeper,
                "strHomeLineupDefense" to data.strHomeLineupDefense,
                "strHomeLineupMidfield" to data.strHomeLineupMidfield,
                "strHomeLineupForward" to data.strHomeLineupForward,
                "strHomeLineupSubstitutes" to data.strHomeLineupSubstitutes,
                "strHomeFormation" to data.strHomeFormation,
                "strAwayGoalDetails" to data.strAwayGoalDetails,
                "strAwayRedCards" to data.strAwayRedCards,
                "strAwayYellowCards" to data.strAwayYellowCards,
                "strAwayLineupGoalkeeper" to data.strAwayLineupGoalkeeper,
                "strAwayLineupDefense" to data.strAwayLineupDefense,
                "strAwayLineupMidfield" to data.strAwayLineupMidfield,
                "strAwayLineupForward" to data.strAwayLineupForward,
                "strAwayLineupSubstitutes" to data.strAwayLineupSubstitutes,
                "strAwayFormation" to data.strAwayFormation,
                "intHomeShots" to data.intHomeShots,
                "intAwayShots" to data.intAwayShots,
                "dateEvent" to data.dateEvent,
                "strTime" to data.strTime,
                "strThumb" to data.strThumb)
        }
    }
}