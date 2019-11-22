package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.repository.MatchRepository

class MatchViewModel: ViewModel() {
    val matchRepository = MatchRepository()

    fun connectionMatchNext(): LiveData<Int> = matchRepository.connectionMatchNextList
    fun matchNextList(): List<MatchList> = matchRepository.matchNextList
    fun errorMatchNext(): ErrorData? = matchRepository.errorMatchNext
    fun matchNext(id: Int) = matchRepository.matchNext(id)

    fun connectionMatchLast(): LiveData<Int> = matchRepository.connectionMatchLastList
    fun matchLastList(): List<MatchList> = matchRepository.matchLastList
    fun errorMatchLast(): ErrorData? = matchRepository.errorMatchLast
    fun matchLast(id: Int) = matchRepository.matchLast(id)
}