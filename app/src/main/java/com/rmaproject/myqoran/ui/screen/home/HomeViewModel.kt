package com.rmaproject.myqoran.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.ui.screen.home.states.JuzState
import com.rmaproject.myqoran.ui.screen.home.states.PageState
import com.rmaproject.myqoran.ui.screen.home.states.SurahState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: QoranRepository
) : ViewModel() {

    private val _surahState = mutableStateOf(SurahState())
    val surahState: State<SurahState> = _surahState

    private val _juzState = mutableStateOf(JuzState())
    val juzState: State<JuzState> = _juzState

    private val _pageState = mutableStateOf(PageState())
    val pageState: State<PageState> = _pageState

    private var getSurahJob: Job? = null
    private var getJuzJob: Job? = null
    private var getPageJob: Job? = null

    private fun fillQoranList() {
        getSurahJob?.cancel()
        getJuzJob?.cancel()
        getPageJob?.cancel()

        getSurahJob = repository.getQoranIndexBySurah()
            .onEach { surahs ->
                _surahState.value = surahState.value.copy(
                    surahList = surahs
                )
            }.launchIn(viewModelScope)
        getJuzJob = repository.getQoranIndexByJuz()
            .onEach { juzs ->
                _juzState.value = juzState.value.copy(
                    juzList = juzs
                )
            }.launchIn(viewModelScope)
        getPageJob = repository.getQoranIndexByPage()
            .onEach { pages ->
                _pageState.value = pageState.value.copy(
                    qoranByPageList = pages
                )
            }.launchIn(viewModelScope)
    }

    init {
        fillQoranList()
    }
}