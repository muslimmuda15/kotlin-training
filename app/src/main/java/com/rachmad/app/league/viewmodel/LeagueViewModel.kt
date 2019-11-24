package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.`object`.LeagueList
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.repository.LeagueRepository

class LeagueViewModel: ViewModel() {
    val leagueRepository = LeagueRepository()

    fun connectionLeagueList(): LiveData<Int> = leagueRepository.connectionLeagueList
    fun errorLeagueList(): ErrorData? = leagueRepository.errorLeague
    fun leagueList(): List<LeagueList> = leagueRepository.leagueList
    fun connectLeague() = leagueRepository.leagueList()

    fun connectionLeagueDetails(): LiveData<Int> = leagueRepository.connectionLeagueDetails
    fun errorLeagueDetails(): ErrorData? = leagueRepository.errorLeagueDetails
    fun leagueDetails(): LeagueList? = leagueRepository.leagueDetails
    fun connectLeagueDetails(id: Int) = leagueRepository.leagueDetails(id)

    fun connectionTeamDataDetails(): LiveData<Int> = leagueRepository.connectionTeamData
    fun errorTeamDataDetails(): ErrorData? = leagueRepository.errorTeamData
//    fun teamDataDetails(): List<TeamData>? = leagueRepository.teamData
    fun team(id: Int) = leagueRepository.team(id)
}