package com.rmaproject.myqoran.data.kotpref

import com.chibatching.kotpref.KotprefModel

object SettingsPreferences : KotprefModel() {

    const val INDONESIAN = 0
    const val ENGLISH = 1

    var isDarkMode by booleanPref(false)
    var currentLanguage by intPref(INDONESIAN)

    override fun clear() {
        super.clear()
        isDarkMode = false
        currentLanguage = INDONESIAN
    }
}