package com.rachmad.app.league

import com.google.gson.Gson
import com.rachmad.app.league.`object`.*
import retrofit2.mock.BehaviorDelegate;
import com.rachmad.app.league.webservice.AccessSite
import retrofit2.Call
import java.lang.Exception
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

class MockSite(val retrofit: Retrofit): AccessSite {
    lateinit var leagueDelegate: BehaviorDelegate<AccessSite>

    val mockRetrofit by lazy {
        MockRetrofit.Builder(retrofit)
            .networkBehavior(NetworkBehavior.create())
            .build()
    }

    override fun leagueSite(): Call<BaseLeagueData> {
        try {
            leagueDelegate = mockRetrofit.create(AccessSite::class.java)

            var leagueData: String? = null
            val inputStream = this.javaClass.classLoader!!.getResourceAsStream("league_list.json")
            leagueData = inputStream.bufferedReader().use { it.readText() }

            val data = Gson().fromJson(leagueData, BaseLeagueData::class.java)
            val baseData = BaseLeagueData(data.countrys)

            return leagueDelegate.returningResponse(baseData).leagueSite()
        }catch (e: Exception){
            throw e
        }
    }

    override fun leagueDetails(id: Int): Call<BaseLeagueDetailsData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun matchSite(id: Int): Call<BaseMatchData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun matchLastSite(id: Int): Call<BaseMatchData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun matchDetails(id: Int): Call<BaseMatchDetails> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun teamSite(id: Int): Call<BaseTeamData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchAllMatch(search: String): Call<BaseMatchSearch> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}