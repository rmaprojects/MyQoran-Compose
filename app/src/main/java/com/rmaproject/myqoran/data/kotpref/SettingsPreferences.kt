package com.rmaproject.myqoran.data.kotpref

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumOrdinalPref

object SettingsPreferences : KotprefModel() {

    const val INDONESIAN = 0
    const val ENGLISH = 1

    private const val SLIDER_DEF_VALUE = 32F

    var isDarkMode by booleanPref(false)
    var isFocusReadActive by booleanPref(false)
    var currentLanguage by intPref(INDONESIAN)
    var currentQoriOption by enumOrdinalPref(QoriOptions.ABD_SUDAIS)
    var ayahTextSize by floatPref(SLIDER_DEF_VALUE)

    override fun clear() {
        super.clear()
        isDarkMode = false
        currentLanguage = INDONESIAN
        ayahTextSize = SLIDER_DEF_VALUE
        currentQoriOption = QoriOptions.ABD_SUDAIS
        isFocusReadActive = false
    }

    enum class QoriOptions(
        val qoriName: String,
        val url: String
    ) {
        ABD_SUDAIS(
            qoriName = "Abdurrahman As-Sudais",
            url = "Abdurrahmaan_As-Sudais_64kbps",
        ),
        HUDHAIFY(
            qoriName = "Hudhaify",
            url = "Hudhaify_64kbps"
        ),
        ALAFASY(
            qoriName = "Alafasy",
            url = "Alafasy_64kbps"
        ),
        MUHD_AYYOUB(
            qoriName = "Muhammad Ayyoub",
            url = "Muhammad_Ayyoub_64kbps"
        )
    }
}