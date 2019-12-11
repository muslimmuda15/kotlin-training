package com.rachmad.app.league

import com.google.gson.Gson
import com.rachmad.app.league.`object`.BaseLeagueData
import com.rachmad.app.league.`object`.LeagueList
import com.rachmad.app.league.webservice.AccessSite
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class FootballService {
    lateinit var mockWebServer: MockWebServer
    val url = "https://www.thesportsdb.com/"
    lateinit var service: AccessSite

    @Before
    fun initial(){
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.SERVER_URL)
            .build()
        service = retrofit.create(AccessSite::class.java)
    }

    @Test
    fun leagueList() {
        val call = service.leagueSite()

        val leagueData = call.execute()
        assertNotNull(leagueData)
        assertNotNull(leagueData.body())
        assertNotNull(leagueData.body()!!.countrys)
        assertThat(leagueData.body()!!.countrys!!.size, greaterThan(0))
    }

    @Test
    fun leagueDetails(){
        val call = service.leagueDetails(4346)
        val leagueData = call.execute()

        assertNotNull(leagueData)
        assertNotNull(leagueData.body())
        assertNotNull(leagueData.body()!!.leagues)
        assertThat(leagueData.body()!!.leagues!!.size, comparesEqualTo(1))
    }

    @Test
    fun nextMatch(){
        val call = service.matchSite(4328)
        val nextMatch = call.execute()

        assertNotNull(nextMatch)
        assertNotNull(nextMatch.body())
        assertNotNull(nextMatch.body()!!.events)
        assertThat(nextMatch.body()!!.events!!.size, lessThanOrEqualTo(15))
    }

    @Test
    fun prevMatch(){
        val call = service.matchLastSite(4328)
        val nextMatch = call.execute()

        assertNotNull(nextMatch)
        assertNotNull(nextMatch.body())
        assertNotNull(nextMatch.body()!!.events)
        assertThat(nextMatch.body()!!.events!!.size, lessThanOrEqualTo(15))
    }

    @Test
    fun matchDetails(){
        val call = service.matchDetails(441613)
        val matchDetails = call.execute()

        assertNotNull(matchDetails)
        assertNotNull(matchDetails.body())
        assertNotNull(matchDetails.body()!!.events)
        assertThat(matchDetails.body()!!.events!!.size, comparesEqualTo(1))
    }

    @Test
    fun teamSite(){
        val call = service.teamSite(133604)
        val teamDetails = call.execute()

        assertNotNull(teamDetails)
        assertNotNull(teamDetails.body())
        assertNotNull(teamDetails.body()!!.teams)
        assertThat(teamDetails.body()!!.teams!!.size, greaterThan(0))
    }

    @Test
    fun searchMatch(){
        val call = service.searchAllMatch("Arsenal")
        val search = call.execute()

        assertNotNull(search)
        assertNotNull(search.body())
        assertNotNull(search.body()!!.event)
        assertThat(search.body()!!.event!!.size, greaterThan(0))
    }

    // I try using mock

    @Test
    fun leagueList2(){
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(mockWebServer.url(url))
            .build()

        val mockSite = MockSite(retrofit)
        val reqData = mockSite.leagueSite()
        val response = reqData.execute()
        assertNotNull(response)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.countrys)
        assertThat(response.body()!!.countrys!!.size, greaterThan(0))
    }
}