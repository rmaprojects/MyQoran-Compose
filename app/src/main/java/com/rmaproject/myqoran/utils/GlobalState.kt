package com.rmaproject.myqoran.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.rmaproject.myqoran.data.kotpref.SettingsPreferences

object GlobalState {
    var isDarkMode by mutableStateOf(SettingsPreferences.isDarkMode)
    var ayahTextSize by mutableStateOf(SettingsPreferences.ayahTextSize)
    var isFocusRead by mutableStateOf(SettingsPreferences.isFocusReadActive)
}