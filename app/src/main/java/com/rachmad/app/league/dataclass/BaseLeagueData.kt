package com.rachmad.app.league.dataclass

data class BaseLeagueData(
    val countrys: List<LeagueList>
)

data class LeagueList(
    val idLeague: Int,
    val strLeagueAlternate: String,
    val strDescriptionEN: String,
    val strBadge: String,
    val strFanart1: String,
    val strFanart2: String,
    val strFanart3: String,
    val strFanart4: String
)

data class BaseLeagueDetailsData(
    val leagues: List<LeagueList>
)