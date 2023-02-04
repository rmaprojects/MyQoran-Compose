package com.rmaproject.myqoran.utils

import java.text.SimpleDateFormat
import java.util.*

object Converters {
    fun convertMillisToActualDate(
        dateAdded: Long
    ): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateAdded))
    }

    fun convertTImeMillisToActualTime(
        timeInMillis: Long
    ): String {
        return SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date(timeInMillis))
    }

    fun convertNumberToThreeDigits(
        number: Int
    ): String {
        return String.format("%03d", number)
    }
}