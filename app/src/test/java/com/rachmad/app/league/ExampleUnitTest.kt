package com.rachmad.app.league

import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun dateTime(){
        var df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        var newDate = df.parse("2019-11-28")
        df = SimpleDateFormat("dd MMM yyyy", Locale.US)
        assertEquals("28 Nov 2019", df.format(newDate))

        df = SimpleDateFormat("hh:mm:ss", Locale.US)
        df.timeZone = TimeZone.getTimeZone("GMT")
        newDate = df.parse("20:00:00")
        df = SimpleDateFormat("hh:mm a", Locale.US)
        assertEquals("03:00 AM", df.format(newDate))
    }

    @Test
    fun teamList(){
        val data = "Javier Manquillo; Jordan Henderson; Lucas Leiva; Alberto Moreno;"
        val list = ArrayList(data.split(";"))
        list.forEach {
            println("TEAM : " + it)
        }

        list.removeAll { it.isNullOrBlank() }
        list.forEach {
            println("TEAM : " + it)
        }
    }

    @Test
    fun getList(){
        val data = "Javier Manquillo"
        val list = ArrayList(data.split(";"))
        list.forEach {
            println("TEAM : " + it)
        }
    }
}
