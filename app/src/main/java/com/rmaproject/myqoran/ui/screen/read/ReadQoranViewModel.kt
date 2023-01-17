package com.rmaproject.myqoran.ui.screen.read

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rmaproject.myqoran.data.repository.QoranRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadQoranViewModel @Inject constructor(
    private val repository: QoranRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val surahNumber = savedStateHandle.get<Int>("surahNumber") ?: 1
    val pageNumber = savedStateHandle.get<Int>("pageNumber") ?: 1
    val juzNumber = savedStateHandle.get<Int>("juzNumber") ?: 1

    init {
        getQoranByPage(pageNumber)
    }

    private fun getQoranByPage(pageNumber: Int) {
        viewModelScope.launch {
            val data = repository.getReadQoran(pageNumber)
        }
    }
}