package com.rmaproject.myqoran.ui.screen.search.event

import androidx.compose.ui.focus.FocusState

sealed class SearchEvent {
    data class EnterQuery(val query: String): SearchEvent()
    data class QueryChangeFocus(val focusState: FocusState): SearchEvent()
    object FindAyah: SearchEvent()
    object FindSurah: SearchEvent()
}