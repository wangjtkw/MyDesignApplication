package com.example.mydesignapplication.utils

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {

    private const val YEAR_SECOND = 31_536_000L

    fun getTimestamp(parsedTime: String): Long {
        val locale = Locale("zh", "CN")
        val sdf = SimpleDateFormat("yyyy-MM-dd", locale)
        val date = sdf.parse(parsedTime)
        return (date?.time ?: 0) / 1000L
    }

    fun getAge(parsedTime: String): String {
        val oldTimestamp = getTimestamp(parsedTime)
        val newTimestamp = getCurrentTimestamp()
        val differ = newTimestamp - oldTimestamp
        val age = differ / YEAR_SECOND
        return "${age}岁"
    }

    fun getCurrentTimestamp(): Long {
        val locale = Locale("zh", "CN")
        val calendar = Calendar.getInstance(locale)
        return calendar.timeInMillis / 1000
    }
}