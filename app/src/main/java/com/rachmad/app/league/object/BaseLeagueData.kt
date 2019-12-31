package com.rachmad.app.league.`object`

import java.io.Serializable

data class BaseLeagueData(
    val countrys: List<LeagueList>?
)

data class LeagueList(
    val idLeague: Int,
    val strLeagueAlternate: String?,
    val strLeague: String?,
    val strDescriptionEN: String?,
    val strBadge: String?,
    val strFanart1: String?,
    val strFanart2: String?,
    val strFanart3: String?,
    val strFanart4: String?,
    val dateFirstEvent: String?
)

data class BaseLeagueDetailsData(
    val leagues: List<LeagueList>?
)

data class BaseMatchData(
    val events: List<MatchList>?
)

data class MatchList(
    val idEvent: String?,
    val strEvent: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val strFilename: String?,
    val strLeague: String?,
    val intHomeScore: String?,
    val intAwayScore: String?,
    val strThumb: String?,
    val intRound: String?,
    val strSeason: String?,
    val strTime: String?,
    val idHomeTeam: String?,
    val idAwayTeam: String?
)

data class BaseMatchDetails(
    val events: List<MatchDetails>?
)

data class MatchDetails(
    val idEvent: String?,
    var strEvent: String?,
    val strEventAlternate: String?,
    val strFilename: String?,
    val strSport: String?,
    val strLeague: String?,
    val strSeason: String?,
    val strHomeTeam: String?,
    val strAwayTeam: String?,
    val idHomeTeam: String?,
    val idAwayTeam: String?,
    val intHomeScore: String?,
    val intRound: String?,
    val intAwayScore: String?,
    val intSpectator: String?,
    val strHomeGoalDetails: String?,
    val strHomeRedCards: String?,
    val strHomeYellowCards: String?,
    val strHomeLineupGoalkeeper: String?,
    val strHomeLineupDefense: String?,
    val strHomeLineupMidfield: String?,
    val strHomeLineupForward: String?,
    val strHomeLineupSubstitutes: String?,
    val strHomeFormation: String?,
    val strAwayGoalDetails: String?,
    val strAwayRedCards: String?,
    val strAwayYellowCards: String?,
    val strAwayLineupGoalkeeper: String?,
    val strAwayLineupDefense: String?,
    val strAwayLineupMidfield: String?,
    val strAwayLineupForward: String?,
    val strAwayLineupSubstitutes: String?,
    val strAwayFormation: String?,
    val intHomeShots: String?,
    val intAwayShots: String?,
    val dateEvent: String?,
    val strTime: String?,
    val strThumb: String?
){
    constructor(): this(null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null)
}

data class BaseMatchSearch(
    val event: List<MatchDetails>?
)

data class BaseTeamData(
    val teams: List<TeamData>?
)

data class TeamData(
    val idTeam: String?,
    val strTeam: String?,
    val strTeamBadge: String?,
    val strTeamJersey: String?,
    val strAlternate: String?,
    val strDescriptionEN: String?,
    val intFormedYear: String?,
    val strStadium: String?,
    val strStadiumThumb: String?,
    val strStadiumDescription: String,
    val strStadiumLocation: String?,
    val intStadiumCapacity: String?,
    val strWebsite: String?,
    val strCountry: String?,
    val strTeamBanner: String?,
    val strTeamFanart1: String?,
    val strTeamFanart2: String?,
    val strTeamFanart3: String?,
    val strTeamFanart4: String?
): Serializable

data class BasePlayerData(
    val player: List<PlayerData>?
)

data class PlayerData(
    val idPlayer: String?,
    val strPlayer: String?,
    val strNationality: String?,
    val strNumber: String?,
    val strBirthLocation: String?,
    val dateBorn: String?,
    val strWage: String?,
    val strPosition: String?,
    val strDescriptionEN: String?,
    val strWeight: String?,
    val strHeight: String?,
    val strCutout: String?
): Serializable