package com.rmaproject.myqoran.data.kotpref

import com.chibatching.kotpref.KotprefModel
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH

object LastReadPreferences : KotprefModel() {
    var surahName by nullableStringPref(null)
    var surahNumber by intPref(1)
    var ayahNumber by intPref(1)
    var juzNumber by intPref(1)
    var pageNumber by intPref(1)
    var indexType by intPref(ORDER_BY_SURAH)
    var lastPosition by intPref(0)
}