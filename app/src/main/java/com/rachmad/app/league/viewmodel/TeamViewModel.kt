package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.repository.TeamRepository

class TeamViewModel: ViewModel() {
    val teamRepository = TeamRepository()

    fun connectionTeamDataDetails(): LiveData<Int> = teamRepository.connectionTeamData
    fun errorTeamDataDetails(): ErrorData? = teamRepository.errorTeamData
    fun team(id: Int) = teamRepository.team(id)

    fun connectionTeamByLeague(): LiveData<Int> = teamRepository.connectionTeamByLeague
    fun errorTeamByLeague(): ErrorData? = teamRepository.errorTeamByLeague
    fun teamByLeague(): List<TeamData> = teamRepository.teamByLeague
    fun teamByLeague(leagueName: String) = teamRepository.teamByLeague(leagueName)
}