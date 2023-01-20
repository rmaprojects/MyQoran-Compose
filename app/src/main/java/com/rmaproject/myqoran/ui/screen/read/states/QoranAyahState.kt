package com.rmaproject.myqoran.ui.screen.read.states

import com.rmaproject.myqoran.data.local.entities.Qoran

data class QoranAyahState(
    val listAyah : List<Qoran> = emptyList()
)
