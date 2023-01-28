package com.rmaproject.myqoran.ui.screen.read

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_JUZ
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_PAGE
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH
import com.rmaproject.myqoran.ui.screen.read.events.ReadQoranEvent
import com.rmaproject.myqoran.ui.screen.read.states.QoranAyahState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
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

    val observeAbleCurrentReading = _currentPagingIndex.switchMap {
        when (indexType) {
            ORDER_BY_SURAH -> repository.getSurahList().asLiveData()
            ORDER_BY_JUZ -> repository.getJuzList().asLiveData()
            ORDER_BY_PAGE -> repository.getPageList().asLiveData()
            else -> throw Exception("Unknown Type")
        }
    }

    private val _qoranState = mutableStateOf(QoranAyahState())
    val qoranState: State<QoranAyahState> = _qoranState

    private val _currentReadingState = mutableStateOf("")
    val currentReadingState: State<String> = _currentReadingState

    val indexList = mutableListOf<Qoran>()

    fun onEvent(event: ReadQoranEvent) {
        when (event) {
            is ReadQoranEvent.ChangePage -> {
                _currentPagingIndex.value = event.newPage
            }
            is ReadQoranEvent.CopyAyah -> {
                val clipboard =
                    event.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText(
                    event.surahName,
                    "${event.ayahText}\n\n${event.translation}"
                )
                clipboard.setPrimaryClip(clip)
            }
            is ReadQoranEvent.GetNewAyah -> {
                _qoranState.value = qoranState.value.copy(
                    listAyah = event.ayahList
                )
            }
            is ReadQoranEvent.SetCurrentReading -> {
                _currentReadingState.value = event.currentReading
            }
            is ReadQoranEvent.ShareAyah -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, event.surahName)
                shareIntent.putExtra(Intent.EXTRA_TEXT, event.ayahText)
                shareIntent.putExtra(Intent.EXTRA_TEXT, event.translation)
                event.context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }
        }
    }

    init {
        when (indexType) {
            ORDER_BY_SURAH -> {
                _currentPagingIndex.value = surahNumber
                viewModelScope.launch {
                    repository.getSurahList().collectLatest {
                        indexList.addAll(it)
                    }
                }
            }
            ORDER_BY_JUZ -> {
                _currentPagingIndex.value = juzNumber
                viewModelScope.launch {
                    repository.getJuzList().collectLatest {
                        indexList.addAll(it)
                    }
                }
            }
            ORDER_BY_PAGE -> {
                _currentPagingIndex.value = pageNumber
                viewModelScope.launch {
                    repository.getPageList().collectLatest {
                        indexList.addAll(it)
                    }
                }
            }
        }
    }

}