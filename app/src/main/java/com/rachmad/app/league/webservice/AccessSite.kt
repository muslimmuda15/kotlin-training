package com.rachmad.app.league.webservice

import com.rachmad.app.league.`object`.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AccessSite {

    @GET("/api/v1/json/1/search_all_leagues.php?s=Soccer")
    fun leagueSite(): Call<BaseLeagueData>

    @GET("/api/v1/json/1/lookupleague.php")
    fun leagueDetails(
        @Query("id") id: Int
    ): Call<BaseLeagueDetailsData>

    @GET("/api/v1/json/1/eventsnextleague.php")
    fun matchSite(
        @Query("id") id: Int
    ): Call<BaseMatchData>

    @GET("/api/v1/json/1/eventspastleague.php")
    fun matchLastSite(
        @Query("id") id: Int
    ): Call<BaseMatchData>

    @GET("/api/v1/json/1/lookupevent.php")
    fun matchDetails(
        @Query("id") id: Int
    ): Call<BaseMatchDetails>

    @GET("/api/v1/json/1/lookupteam.php")
    fun teamSite(
        @Query("id") id: Int
    ): Call<BaseTeamData>
}