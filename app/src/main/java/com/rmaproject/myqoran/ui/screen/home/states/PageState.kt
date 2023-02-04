package com.rmaproject.myqoran.ui.screen.home.states

import com.rmaproject.myqoran.data.local.entities.Page

data class PageState(
    val qoranByPageList: List<Page>? = emptyList()
)