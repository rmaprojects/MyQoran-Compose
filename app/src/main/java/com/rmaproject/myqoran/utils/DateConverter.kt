package com.rmaproject.myqoran.utils

import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun convertMillisToActualDate(
        dateAdded: Long
    ): String {
        return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dateAdded))
    }
}