package com.rachmad.app.league.webservice

import com.rachmad.app.league.`object`.BaseLeagueData
import com.rachmad.app.league.`object`.BaseLeagueDetailsData
import com.rachmad.app.league.`object`.BaseMatchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccessSite {

    @GET("/api/v1/json/1/search_all_leagues.php?s=Soccer")
    fun leagueSite(): Call<BaseLeagueData>

    @GET("https://www.thesportsdb.com/api/v1/json/1/lookupleague.php")
    fun leagueDetails(
        @Query("id") id: Int
    ): Call<BaseLeagueDetailsData>

    @GET("https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php")
    fun matchSite(
        @Query("id") id: Int
    ): Call<BaseMatchData>

    @GET("https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php")
    fun matchLastSite(
        @Query("id") id: Int
    ): Call<BaseMatchData>
}