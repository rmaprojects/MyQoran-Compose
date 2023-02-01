package com.rmaproject.myqoran.ui.screen.search.event

sealed class SearchUiState<out T: Any?> {
    data class NotEmpty<out T: Any>(val data: T): SearchUiState<T>()
    data class Empty(val query: String): SearchUiState<Nothing>()
    object EmptyQuery: SearchUiState<Nothing>()
}