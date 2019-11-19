package com.rachmad.app.league.webservice

import com.rachmad.app.league.dataclass.BaseLeagueData
import com.rachmad.app.league.dataclass.BaseLeagueDetailsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccessSite {

    @GET("/api/v1/json/1/search_all_leagues.php?c=England&s=Soccer")
    fun leagueSite(): Call<BaseLeagueData>

    @GET("https://www.thesportsdb.com/api/v1/json/1/lookupleague.php")
    fun leagueDetails(
        @Query("id") id: Int
    ): Call<BaseLeagueDetailsData>
}