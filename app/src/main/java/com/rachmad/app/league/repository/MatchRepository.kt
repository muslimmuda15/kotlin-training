package com.rachmad.app.league.repository

import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.`object`.*
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.webservice.LeagueSite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MatchRepository {
    var connectionMatchNextList = MutableLiveData<Int>()
    var errorMatchNext: ErrorData? = null
    var matchNextList: List<MatchList> = ArrayList()

    fun matchNext(id: Int) {
        val service = LeagueSite.connect()
        val call = service.matchSite(id)

        connectionMatchNextList.postValue(Connection.ACCEPTED.Status)
        call!!.enqueue(object : Callback<BaseMatchData> {
            override fun onFailure(call: Call<BaseMatchData>, t: Throwable) {
                sendErrorMatchNext(t.message)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseMatchData>,
                response: Response<BaseMatchData>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.events?.let {
                            if (it.size > 0) {
                                matchNextList = it
                                connectionMatchNextList.postValue(Connection.OK.Status)
                            } else {
                                sendErrorMatchNext("We have no data")
                            }
                        } ?: run {
                            sendErrorMatchNext("We have no data")
                        }
                    } ?: run {
                        sendErrorMatchNext("Body is null")
                    }
                } else {
                    sendErrorMatchNext("Response not successfull")
                }
            }
        })
    }

    private fun sendErrorMatchNext(message: String?) {
        errorMatchNext = ErrorData(Connection.ERROR.Status, message)
        connectionMatchNextList.postValue(Connection.ERROR.Status)
    }

    var connectionMatchLastList = MutableLiveData<Int>()
    var errorMatchLast: ErrorData? = null
    var matchLastList: List<MatchList> = ArrayList()

    fun matchLast(id: Int) {
        val service = LeagueSite.connect()
        val call = service.matchLastSite(id)

        connectionMatchLastList.postValue(Connection.ACCEPTED.Status)
        call!!.enqueue(object : Callback<BaseMatchData> {
            override fun onFailure(call: Call<BaseMatchData>, t: Throwable) {
                sendErrorMatchLast(t.message)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseMatchData>,
                response: Response<BaseMatchData>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.events?.let {
                            if (it.size > 0) {
                                matchLastList = it
                                connectionMatchLastList.postValue(Connection.OK.Status)
                            } else {
                                sendErrorMatchLast("Data is empty")
                            }
                        } ?: run {
                            sendErrorMatchLast("Event is empty")
                        }
                    } ?: run {
                        sendErrorMatchLast("Body is null")
                    }
                } else {
                    sendErrorMatchLast("Response not successfull")
                }
            }
        })
    }

    private fun sendErrorMatchLast(message: String?) {
        errorMatchLast = ErrorData(Connection.ERROR.Status, message)
        connectionMatchLastList.postValue(Connection.ERROR.Status)
    }

    var connectionMatchDetails = MutableLiveData<Int>()
    var errorMatchDetails: ErrorData? = null
    var matchDetails: List<MatchDetails> = ArrayList()

    fun matchDetails(id: Int) {
        val service = LeagueSite.connect()
        val call = service.matchDetails(id)

        connectionMatchDetails.postValue(Connection.ACCEPTED.Status)
        call!!.enqueue(object : Callback<BaseMatchDetails> {
            override fun onFailure(call: Call<BaseMatchDetails>, t: Throwable) {
                sendErrorMatchDetails(t.message)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseMatchDetails>,
                response: Response<BaseMatchDetails>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.events?.let {
                            if (it.size > 0) {
                                matchDetails = it
                                connectionMatchDetails.postValue(Connection.OK.Status)
                            } else {
                                sendErrorMatchDetails("Data is empty")
                            }
                        } ?: run {
                            sendErrorMatchDetails("Event is empty")
                        }
                    } ?: run {
                        sendErrorMatchDetails("Body is null")
                    }
                } else {
                    sendErrorMatchDetails("Response not successfull")
                }
            }
        })
    }

    private fun sendErrorMatchDetails(message: String?) {
        errorMatchDetails = ErrorData(Connection.ERROR.Status, message)
        connectionMatchDetails.postValue(Connection.ERROR.Status)
    }

    var connectionMatchSearch = MutableLiveData<Int>()
    var errorMatchSearch: ErrorData? = null
    var matchSearch: List<MatchDetails> = ArrayList()

    fun matchSearch(search: String) {
        val service = LeagueSite.connect()
        val call = service.searchAllMatch(search)

        connectionMatchSearch.postValue(Connection.ACCEPTED.Status)
        call!!.enqueue(object : Callback<BaseMatchSearch> {
            override fun onFailure(call: Call<BaseMatchSearch>, t: Throwable) {
                sendErrorMatchSearch(t.message)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseMatchSearch>, response: Response<BaseMatchSearch>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.event?.let {
                            if (it.size > 0) {
                                val data = it.filter { it.strSport == "Soccer" }
                                if(data.size > 0) {
                                    matchSearch = it
                                    connectionMatchSearch.postValue(Connection.OK.Status)
                                }
                                else{
                                    sendErrorMatchSearch("Data is empty")
                                }
                            } else {
                                sendErrorMatchSearch("Data is empty")
                            }
                        } ?: run {
                            sendErrorMatchSearch("Event is empty")
                        }
                    } ?: run {
                        sendErrorMatchSearch("Body is null")
                    }
                } else {
                    sendErrorMatchSearch("Response not successfull")
                }
            }
        })
    }

    private fun sendErrorMatchSearch(message: String?) {
        errorMatchSearch = ErrorData(Connection.ERROR.Status, message)
        connectionMatchSearch.postValue(Connection.ERROR.Status)
    }
}