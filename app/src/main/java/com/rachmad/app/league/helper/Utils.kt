package com.rachmad.app.league.helper

import com.rachmad.app.league.`object`.TeamData
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun dateFormat(input: String): String {
        try {
            var df = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val newDate = df.parse(input)
            df = SimpleDateFormat("dd MMM yyyy", Locale.US)
            return df.format(newDate) ?: ""
        } catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }
}