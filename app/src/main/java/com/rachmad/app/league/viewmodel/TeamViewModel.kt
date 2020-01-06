package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.repository.TeamRepository
import com.rachmad.app.league.sqlite.TeamQuery

class TeamViewModel: ViewModel() {
    val teamRepository = TeamRepository()
    val query = TeamQuery()
    val teamLiveList = MutableLiveData<List<TeamData>>()
    val teamDetailsStorage = MutableLiveData<Boolean>()

    fun connectionTeamDataDetails(): LiveData<Int> = teamRepository.connectionTeamData
    fun errorTeamDataDetails(): ErrorData? = teamRepository.errorTeamData
    fun teamDetails(): TeamData? = teamRepository.teamData
    fun team(id: Int) = teamRepository.team(id)

    fun connectionTeamByLeague(): LiveData<Int> = teamRepository.connectionTeamByLeague
    fun errorTeamByLeague(): ErrorData? = teamRepository.errorTeamByLeague
    fun teamByLeague(): List<TeamData> = teamRepository.teamByLeague
    fun teamByLeague(leagueName: String) = teamRepository.teamByLeague(leagueName)

    fun insertTeam(data: TeamData) = query.insertTeam(data)
    private fun getTeam(id: String) = query.getTeam(id)
    fun updateTeamDetails(id: String) = teamDetailsStorage.postValue(getTeam(id))
    fun deleteTeam(id: String) = query.deleteTeam(id)
    private fun getTeamList() = query.teamList()
    fun updateDatabase() = teamLiveList.postValue(getTeamList())
}