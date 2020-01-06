package com.rachmad.app.league.sqlite

import com.rachmad.app.league.App
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.helper.ui.DatabaseHelper
import com.rachmad.app.league.helper.ui.DatabaseHelper.TABLE_TEAM
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamQuery {
    private val database: TeamDB
        get() = DatabaseHelper.getTeamInstance(App.context)

    fun teamList(): List<TeamData>{
        return database.use {
            select(TABLE_TEAM,
                "idTeam",
                "strTeam",
                "strTeamBadge",
                "strTeamJersey",
                "strAlternate",
                "strDescriptionEN",
                "intFormedYear",
                "strStadium",
                "strStadiumThumb",
                "strStadiumDescription",
                "strStadiumLocation",
                "intStadiumCapacity",
                "strWebsite",
                "strCountry",
                "strTeamBanner",
                "strTeamFanart1",
                "strTeamFanart2",
                "strTeamFanart3",
                "strTeamFanart4"
            ).parseList(classParser())
        }
    }

    fun getTeam(id: String): Boolean{
        return database.use {
            select(TABLE_TEAM, "strTeam").whereArgs(
                "idTeam = {id}",
                "id" to id
            ).exec {
                if(count > 0)
                    true
                else
                    false
            }
        }
    }

    fun deleteTeam(id: String){
        database.use {
            delete(TABLE_TEAM, "idTeam = {idTeam}", "idTeam" to id)
        }
    }

    fun insertTeam(data: TeamData){
        database.use {
            insert(TABLE_TEAM,
                "idTeam" to data.idTeam,
                "strTeam" to data.strTeam,
                "strTeamBadge" to data.strTeamBadge,
                "strTeamJersey" to data.strTeamJersey,
                "strAlternate" to data.strAlternate,
                "strDescriptionEN" to data.strDescriptionEN,
                "intFormedYear" to data.intFormedYear,
                "strStadium" to data.strStadium,
                "strStadiumThumb" to data.strStadiumThumb,
                "strStadiumDescription" to data.strStadiumDescription,
                "strStadiumLocation" to data.strStadiumLocation,
                "intStadiumCapacity" to data.intStadiumCapacity,
                "strWebsite" to data.strWebsite,
                "strCountry" to data.strCountry,
                "strTeamBanner" to data.strTeamBanner,
                "strTeamFanart1" to data.strTeamFanart1,
                "strTeamFanart2" to data.strTeamFanart2,
                "strTeamFanart3" to data.strTeamFanart3,
                "strTeamFanart4" to data.strTeamFanart4)
        }
    }
}