package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.`object`.PlayerData
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.repository.PlayerRepository

class PlayerViewModel: ViewModel() {
    val playerRepository = PlayerRepository()

    fun connectionPalyerList(): LiveData<Int> = playerRepository.connectionPalyerList
    fun errorPlayerList(): ErrorData? = playerRepository.errorPlayerList
    fun playerList(): List<PlayerData> = playerRepository.playerList
    fun playerList(teamName: String) = playerRepository.playerList(teamName)
}