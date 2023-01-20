package com.rmaproject.myqoran.ui.screen.read

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_JUZ
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_PAGE
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH
import com.rmaproject.myqoran.ui.screen.read.states.QoranAyahState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReadQoranViewModel @Inject constructor(
    private val repository: QoranRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val surahNumber: Int = savedStateHandle["surahNumber"] ?: 1
    val pageNumber: Int = savedStateHandle["pageNumber"] ?: 1
    val juzNumber: Int = savedStateHandle["juzNumber"] ?: 1
    val indexType: Int = savedStateHandle["indexType"] ?: ORDER_BY_SURAH

    private val _currentPagingIndex = MutableLiveData(1)
    val observeAbleAyah = _currentPagingIndex.switchMap { page ->
        when (indexType) {
            ORDER_BY_SURAH -> repository.getReadQoranBySurah(page).asLiveData()
            ORDER_BY_JUZ -> repository.getReadQoranByJuz(page).asLiveData()
            ORDER_BY_PAGE -> repository.getReadQoranByPage(page).asLiveData()
            else -> throw Exception("Unknown Type")
        }
    }
    private val _qoranState = mutableStateOf(QoranAyahState())
    val qoranState: State<QoranAyahState> = _qoranState

    fun changePage(newPage: Int) {
        _currentPagingIndex.value = newPage
    }

    fun getNewAyah(ayahList: List<Qoran>) {
        _qoranState.value = qoranState.value.copy(
            listAyah = ayahList
        )
    }

    init {
        Log.d("Juz", juzNumber.toString())
        Log.d("Page", pageNumber.toString())
        Log.d("Surah", surahNumber.toString())
        when (indexType) {
            ORDER_BY_SURAH -> {
                _currentPagingIndex.value = surahNumber
            }
            ORDER_BY_JUZ -> {
                _currentPagingIndex.value = juzNumber
            }
            ORDER_BY_PAGE -> {
                _currentPagingIndex.value = pageNumber
            }
        }
    }

}