package com.rachmad.app.Classement.repository

import androidx.lifecycle.MutableLiveData
import com.rachmad.app.league.`object`.BaseClassementData
import com.rachmad.app.league.`object`.ClassementData
import com.rachmad.app.league.data.Connection
import com.rachmad.app.league.data.ErrorData
import com.rachmad.app.league.webservice.LeagueSite
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClassementRepository {
    var connectionClassementList = MutableLiveData<Int>()
    var errorClassement: ErrorData? = null
    var ClassementList: List<ClassementData> = ArrayList()

    fun ClassementList(leagueId: Int){
        val service = LeagueSite.connect()
        val call = service.classementList(leagueId)

        connectionClassementList.postValue(Connection.ACCEPTED.Status)
        call.enqueue(object: Callback<BaseClassementData> {
            override fun onFailure(call: Call<BaseClassementData>, t: Throwable) {
                sendError(t.message)
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<BaseClassementData>,
                response: Response<BaseClassementData>
            ) {
                if(response.isSuccessful){
                    response.body()?.let {
                        it.table?.let {
                            if (it.size > 0) {
                                ClassementList = it
                                connectionClassementList.postValue(Connection.OK.Status)
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

    private fun sendError(message: String?){
        errorClassement = ErrorData(Connection.ERROR.Status, message)
        connectionClassementList.postValue(Connection.ERROR.Status)
    }
}