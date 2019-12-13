package com.rachmad.app.league.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.`object`.BaseTeamData
import com.rachmad.app.league.`object`.TeamData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.webservice.LeagueSite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeamRepository {
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

    var connectionTeamByLeague = MutableLiveData<Int>()
    var errorTeamByLeague: ErrorData? = null
    var teamByLeague: List<TeamData> = ArrayList()

    fun teamByLeague(leagueName: String){
        val service = LeagueSite.connect()
        val call = service.teamByLeague(leagueName)

        connectionTeamByLeague.postValue(Connection.ACCEPTED.Status)
        call.enqueue(object: Callback<BaseTeamData> {
            override fun onFailure(call: Call<BaseTeamData>, t: Throwable) {
                sendErrorTeamByLeague(t.message)
                t.printStackTrace()
            }

            override fun onResponse(call: Call<BaseTeamData>, response: Response<BaseTeamData>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        it.teams?.let {
                            if (it.size > 0) {
                                teamByLeague = it
                                connectionTeamByLeague.postValue(Connection.OK.Status)
//                                    ImageData.teamMatch!!.put(id, it[0])
                            } else {
                                sendErrorTeamByLeague("We have no team")
                            }
                        } ?: run {
                            sendErrorTeamByLeague("We have no tema")
                        }
                    } ?: run {
                        sendErrorTeamByLeague("Body is null")
                    }
                } else {
                    sendErrorTeamByLeague("Response not successful")
                }
            }
        })
    }

    private fun sendErrorTeamByLeague(message: String?){
        errorTeamByLeague = ErrorData(Connection.ERROR.Status, message)
        connectionTeamByLeague.postValue(Connection.ERROR.Status)
    }
}