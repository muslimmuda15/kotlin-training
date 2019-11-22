package com.rachmad.app.league.repository

import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.`object`.BaseLeagueData
import com.rachmad.app.league.`object`.BaseLeagueDetailsData
import com.rachmad.app.league.`object`.LeagueList
import com.rachmad.app.league.webservice.LeagueSite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LeagueRepository {
    var connectionLeagueList = MutableLiveData<Int>()
    var errorLeague: ErrorData? = null
    var leagueList: List<LeagueList> = ArrayList()

    fun leagueList(){
        val service = LeagueSite.connect()
        val call = service.leagueSite()

        connectionLeagueList.postValue(Connection.ACCEPTED.Status)
        call.enqueue(object: Callback<BaseLeagueData> {
            override fun onFailure(call: Call<BaseLeagueData>, t: Throwable) {
                sendError(t.message)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseLeagueData>,
                response: Response<BaseLeagueData>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        it.countrys?.let {
                            if (it.size > 0) {
                                leagueList = it
                                connectionLeagueList.postValue(Connection.OK.Status)
                            } else {
                                sendError("Data is empty")
                            }
                        } ?: run {
                            sendError("Country is empty")
                        }
                    } ?: run {
                        sendError("Body is null")
                    }
                }
                else{
                    sendError("Response not successful")
                }
            }

        })
    }

    var connectionLeagueDetails = MutableLiveData<Int>()
    var errorLeagueDetails: ErrorData? = null
    var leagueDetails:LeagueList? = null

    fun leagueDetails(id: Int){
        val service = LeagueSite.connect()
        val call = service.leagueDetails(id)

        connectionLeagueDetails.postValue(Connection.ACCEPTED.Status)
        call.enqueue(object: Callback<BaseLeagueDetailsData> {
            override fun onFailure(call: Call<BaseLeagueDetailsData>, t: Throwable) {
                sendErrorDetails(t.message)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseLeagueDetailsData>, response: Response<BaseLeagueDetailsData>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        it.leagues?.let {
                            if (it.size > 0) {
                                leagueDetails = it[0]
                                connectionLeagueDetails.postValue(Connection.OK.Status)
                            } else {
                                sendErrorDetails("Data is empty")
                            }
                        } ?: run {
                            sendErrorDetails("League is empty")
                        }
                    } ?: run {
                        sendErrorDetails("Body is null")
                    }
                }
                else{
                    sendErrorDetails("Response not successful")
                }
            }

        })
    }

    private fun sendError(message: String?){
        errorLeague = ErrorData(Connection.ERROR.Status, message)
        connectionLeagueList.postValue(Connection.ERROR.Status)
    }

    private fun sendErrorDetails(message: String?){
        errorLeague = ErrorData(Connection.ERROR.Status, message)
        connectionLeagueDetails.postValue(Connection.ERROR.Status)
    }
}