package com.rmaproject.myqoran.ui.screen.home.states

import com.rmaproject.myqoran.data.local.entities.Juz

data class JuzState(
    val juzList: List<Juz>? = emptyList()
)