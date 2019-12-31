package com.rachmad.app.league.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.rachmad.app.Classement.repository.ClassementRepository
import com.rachmad.app.league.`object`.ClassementData
import com.rachmad.app.league.data.ErrorData

class ClassementViewModel: ViewModel() {
    val classementRepository = ClassementRepository()

    fun connectionClassementList(): LiveData<Int> = classementRepository.connectionClassementList
    fun errorClassementList(): ErrorData? = classementRepository.errorClassement
    fun classementList(): List<ClassementData> = classementRepository.ClassementList
    fun classement(leagueId: Int) = classementRepository.ClassementList(leagueId)
}