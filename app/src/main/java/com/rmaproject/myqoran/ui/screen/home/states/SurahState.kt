package com.rmaproject.myqoran.ui.screen.home.states

import com.rmaproject.myqoran.data.local.entities.Surah

data class SurahState(
    val surahList: List<Surah>? = emptyList()
)