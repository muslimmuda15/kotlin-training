package com.rachmad.app.league.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.`object`.*
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.data.ErrorData
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

    var connectionTeamData = MutableLiveData<Int>()
    var errorTeamData: ErrorData? = null
    var teamData: TeamData? = null

    fun team(id: Int){
//        if(ImageData.teamMatch.get(id) == null) {
            val service = LeagueSite.connect()
            val call = service.teamSite(id)

            connectionTeamData.postValue(Connection.ACCEPTED.Status)
            call.enqueue(object : Callback<BaseTeamData> {
                override fun onFailure(call: Call<BaseTeamData>, t: Throwable) {
                    sendErrorTeam(t.message)
                    t.printStackTrace()
                }

                override fun onResponse(
                    call: Call<BaseTeamData>,
                    response: Response<BaseTeamData>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            it.teams?.let {
                                if (it.size > 0) {
                                    Log.d("Image", "IMAGE REPO : " + it[0].strTeamBadge)
                                    teamData = it[0]
                                    connectionTeamData.postValue(Connection.OK.Status)
//                                    ImageData.teamMatch!!.put(id, it[0])
                                } else {
                                    sendErrorTeam("We have no team")
                                }
                            } ?: run {
                                sendErrorTeam("We have no tema")
                            }
                        } ?: run {
                            sendErrorTeam("Body is null")
                        }
                    } else {
                        sendErrorTeam("Response not successful")
                    }
                }

            })
//        }
    }

    private fun sendErrorTeam(message: String?){
        errorTeamData = ErrorData(Connection.ERROR.Status, message)
        connectionTeamData.postValue(Connection.ERROR.Status)
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