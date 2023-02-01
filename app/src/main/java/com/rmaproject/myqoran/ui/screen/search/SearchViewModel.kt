package com.rmaproject.myqoran.ui.screen.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.local.entities.SearchSurahResult
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.ui.screen.search.event.SearchEvent
import com.rmaproject.myqoran.ui.screen.search.event.SearchUiState
import com.rmaproject.myqoran.ui.screen.search.state.SearchFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: QoranRepository
): ViewModel() {

    private val _searchAyahState: MutableSharedFlow<SearchUiState<List<Qoran>>> = MutableStateFlow(
        SearchUiState.EmptyQuery
    )

    val searchAyahState: SharedFlow<SearchUiState<List<Qoran>>> = _searchAyahState.asSharedFlow()

    private val _searchSurahState: MutableSharedFlow<SearchUiState<List<SearchSurahResult>>> = MutableStateFlow(
        SearchUiState.EmptyQuery
    )

    val searchSurahState: SharedFlow<SearchUiState<List<SearchSurahResult>>> = _searchSurahState.asSharedFlow()

    private val _searchQuery: MutableState<SearchFieldState> = mutableStateOf(SearchFieldState())
    val searchQuery: State<SearchFieldState> = _searchQuery

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.EnterQuery -> {
                _searchQuery.value = searchQuery.value.copy(
                    text = event.query
                )
            }
            is SearchEvent.QueryChangeFocus -> {
                _searchQuery.value = searchQuery.value.copy(
                    isHintVisible = !event.focusState.isFocused && searchQuery.value.text.isBlank()
                )
            }
            is SearchEvent.FindAyah -> {
                viewModelScope.launch {
                    if (_searchQuery.value.text.isBlank() || _searchQuery.value.text.isEmpty()) {
                        _searchAyahState.emit(SearchUiState.EmptyQuery)
                        return@launch
                    }
                    repository.searchAyah(_searchQuery.value.text).collectLatest { surahList ->
                        if (surahList.isEmpty()) _searchAyahState.emit(SearchUiState.Empty(_searchQuery.value.text))
                        else _searchAyahState.emit(SearchUiState.NotEmpty(surahList))
                    }
                }
            }
            is SearchEvent.FindSurah -> {
                viewModelScope.launch {
                    if (_searchQuery.value.text.isBlank() || _searchQuery.value.text.isEmpty()) {
                        _searchSurahState.emit(SearchUiState.EmptyQuery)
                        return@launch
                    }
                    repository.searchSurah(_searchQuery.value.text).collectLatest { surahList ->
                        if (surahList.isEmpty()) _searchSurahState.emit(SearchUiState.Empty(_searchQuery.value.text))
                        else _searchSurahState.emit(SearchUiState.NotEmpty(surahList))
                    }
                }
            }
        }
    }
}