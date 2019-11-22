package com.rachmad.app.league.repository

import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.`object`.BaseMatchData
import com.rachmad.app.league.`object`.MatchList
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
                                sendErrorMatchNext("Data is empty")
                            }
                        } ?: run {
                            sendErrorMatchNext("Event is empty")
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
}