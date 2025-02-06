package com.ramki.mygallery.extention

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Long.getDay(): String {
    val timestamp = this
    val inputDate = Calendar.getInstance().apply { timeInMillis = timestamp * 1000 }

    // Compare with today's date
    val todayStart = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    val yesterdayStart = Calendar.getInstance().apply {
        add(Calendar.DATE, -1)
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    return when {
        // Check if the timestamp is today
        inputDate.after(todayStart) -> "Today"

        // Check if the timestamp is yesterday
        inputDate.after(yesterdayStart) -> "Yesterday"

        // If it's a different day, format it as "Day, Month Day, Year"
        else -> {
            val dateFormat = SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault())
            dateFormat.format(inputDate.time)
        }
    }
}
