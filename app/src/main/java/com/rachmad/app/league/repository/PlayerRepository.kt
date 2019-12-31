package com.rachmad.app.league.repository

import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.`object`.BasePlayerData
import com.rachmad.app.league.`object`.PlayerData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.webservice.LeagueSite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlayerRepository {
    var connectionPalyerList = MutableLiveData<Int>()
    var errorPlayerList: ErrorData? = null
    var playerList: List<PlayerData> = ArrayList()

    fun playerList(teamName: String) {
        val service = LeagueSite.connect()
        val call = service.playerList(teamName)

        connectionPalyerList.postValue(Connection.ACCEPTED.Status)
        call.enqueue(object: Callback<BasePlayerData>{
            override fun onFailure(call: Call<BasePlayerData>, t: Throwable) {
                t.printStackTrace()
                sendErrorPlayerList(t.message)
            }

            override fun onResponse(call: Call<BasePlayerData>, response: Response<BasePlayerData>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        it.player?.let {
                            if(it.size > 0){
                                playerList = it
                                connectionPalyerList.postValue(Connection.OK.Status)
                            }
                            else{
                                sendErrorPlayerList("You have no player list")
                            }
                        } ?: run {
                            sendErrorPlayerList("Player is null")
                        }
                    } ?: run {
                        sendErrorPlayerList("Body is null")
                    }
                }
                else{
                    sendErrorPlayerList("Response not successfull")
                }
            }
        })
    }

    private fun sendErrorPlayerList(message: String?) {
        errorPlayerList = ErrorData(Connection.ERROR.Status, message)
        connectionPalyerList.postValue(Connection.ERROR.Status)
    }
}