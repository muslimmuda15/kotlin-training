package com.rachmad.app.league.`object`

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
    val strThumb: String?
)