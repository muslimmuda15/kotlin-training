package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.dataclass.LeagueList
import com.rachmad.app.league.repository.LeagueRepository

class ListModel: ViewModel() {
    val leagueRepository = LeagueRepository()

    fun connectionLeagueList(): LiveData<Int> = leagueRepository.connectionLeagueList
    fun errorLeagueList(): ErrorData? = leagueRepository.errorLeague
    fun leagueList(): List<LeagueList> = leagueRepository.leagueList
    fun connectLeague() = leagueRepository.leagueList()

    fun connectionLeagueDetails(): LiveData<Int> = leagueRepository.connectionLeagueDetails
    fun errorLeagueDetails(): ErrorData? = leagueRepository.errorLeagueDetails
    fun leagueDetails(): LeagueList? = leagueRepository.leagueDetails
    fun connectLeagueDetails(id: Int) = leagueRepository.leagueDetails(id)
}