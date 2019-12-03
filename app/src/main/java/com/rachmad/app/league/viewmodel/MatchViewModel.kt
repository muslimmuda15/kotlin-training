package com.rachmad.app.league.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.league.App
import com.rachmad.app.league.`object`.MatchDetails
import com.rachmad.app.league.`object`.MatchList
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.helper.ui.DatabaseHelper
import com.rachmad.app.league.repository.MatchRepository
import com.rachmad.app.league.sqlite.MatchDB
import com.rachmad.app.league.sqlite.Query

class MatchViewModel: ViewModel() {
    val matchRepository = MatchRepository()
    val query = Query()
    val matchLiveList = MutableLiveData<List<MatchList>>()
    val matchDetailsStorage = MutableLiveData<Boolean>()

    fun insertMatch(data: MatchDetails) = query.insertMatch(data)

    private fun getMatch(id: String) = query.getMatch(id)
    fun updateMatchDetails(id: String){
        matchDetailsStorage.postValue(getMatch(id))
    }

    fun deleteMatch(id: String) = query.deleteMatch(id)

    private fun getMatchList() = query.matchList()
    fun updateDatabase(){
        matchLiveList.postValue(getMatchList())
    }

    fun connectionMatchNext(): LiveData<Int> = matchRepository.connectionMatchNextList
    fun matchNextList(): List<MatchList> = matchRepository.matchNextList
    fun errorMatchNext(): ErrorData? = matchRepository.errorMatchNext
    fun matchNext(id: Int) = matchRepository.matchNext(id)

    fun connectionMatchLast(): LiveData<Int> = matchRepository.connectionMatchLastList
    fun matchLastList(): List<MatchList> = matchRepository.matchLastList
    fun errorMatchLast(): ErrorData? = matchRepository.errorMatchLast
    fun matchLast(id: Int) = matchRepository.matchLast(id)

    fun connectionMatchDetails(): LiveData<Int> = matchRepository.connectionMatchDetails
    fun matchDetailsList(): List<MatchDetails> = matchRepository.matchDetails
    fun errorMatchDetails(): ErrorData? = matchRepository.errorMatchDetails
    fun matchDetails(id: Int) = matchRepository.matchDetails(id)

    fun connectionMatchSearch(): LiveData<Int> = matchRepository.connectionMatchSearch
    fun matchSearchList(): List<MatchDetails> = matchRepository.matchSearch
    fun errorMatchSearch(): ErrorData? = matchRepository.errorMatchSearch
    fun matchSearch(search: String) = matchRepository.matchSearch(search)
}