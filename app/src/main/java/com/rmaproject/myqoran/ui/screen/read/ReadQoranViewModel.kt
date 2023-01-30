package com.rmaproject.myqoran.ui.screen.read

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.rmaproject.myqoran.BuildConfig
import com.rmaproject.myqoran.data.kotpref.SettingsPreferences
import com.rmaproject.myqoran.data.local.entities.Bookmark
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.repository.QoranRepository
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_JUZ
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_PAGE
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH
import com.rmaproject.myqoran.ui.screen.read.events.PlayAyahEvent
import com.rmaproject.myqoran.ui.screen.read.events.ReadQoranEvent
import com.rmaproject.myqoran.ui.screen.read.states.QoranAyahState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import snow.player.PlayMode
import snow.player.PlayerClient
import snow.player.audio.MusicItem
import snow.player.playlist.Playlist
import javax.inject.Inject

@HiltViewModel
class ReadQoranViewModel @Inject constructor(
    private val repository: QoranRepository,
    savedStateHandle: SavedStateHandle,
    private val playerClient: PlayerClient
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

    private val _currentPlayedAyah = mutableStateOf("")
    val currentPlayedAyah = _currentPlayedAyah

    val playerType = mutableStateOf(PlayType.NONE)



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
            is ReadQoranEvent.PlayAyah -> {
                playerClient.stop()
                val formatSurahNumber = String.format("%03d", event.surahNumber)
                val formatAyahNumber = String.format("%03d", event.ayahNumber)
                val playList = createPlaylist(
                    title = "${event.surahName}:${event.ayahNumber}",
                    surahNumber = formatSurahNumber,
                    ayahNumber = formatAyahNumber
                )
                playerClient.connect {
                    playerClient.setPlaylist(playList, true)
                    playerClient.playMode = PlayMode.SINGLE_ONCE
                    _currentPlayedAyah.value = "${event.surahName}:${event.ayahNumber}"
                    playerType.value = PlayType.PLAY_SINGLE
                }
            }
            is ReadQoranEvent.SaveBookmark -> {
                viewModelScope.launch {
                    repository.insertBookmark(
                        Bookmark(
                            id = null,
                            surahName = event.surahName,
                            ayahNumber = event.ayahNumber,
                            surahNumber = event.surahNumber,
                            juzNumber = event.juzNumber,
                            pageNumber = event.pageNumber,
                            positionScroll = event.position,
                            textQoran = event.qoranTextAr,
                            indexType = indexType,
                        )
                    )
                }
            }
        }
    }

    fun onPlayAyahEvent(event: PlayAyahEvent) {
        when (event) {
            is PlayAyahEvent.PauseAyah -> playerClient.pause()
            is PlayAyahEvent.PlayPauseAyah -> playerClient.playPause()
            is PlayAyahEvent.SkipNext -> playerClient.skipToNext()
            is PlayAyahEvent.SkipPrevious -> playerClient.skipToPrevious()
            is PlayAyahEvent.StopAyah -> { playerClient.stop(); playerType.value = PlayType.NONE; playerClient.shutdown() }
        }
    }

    private fun createPlaylist(title: String, ayahNumber: String, surahNumber: String): Playlist {
        val musicItem = MusicItem.Builder()
            .setMusicId("$ayahNumber$surahNumber")
            .autoDuration()
            .setTitle(title)
            .setIconUri(BuildConfig.NOTIFICATION_ICON_URL)
            .setUri("${BuildConfig.AUDIO_BASE_URL}/${SettingsPreferences.currentQoriName.url}/$surahNumber$ayahNumber.mp3")
            .setArtist(SettingsPreferences.currentQoriName.qoriName)
            .build()
        return Playlist.Builder()
            .append(musicItem)
            .build()
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
        playerClient.connect {
            Log.d("CONNECTED:", it.toString())
        }
    }

    enum class PlayType {
        NONE,
        PAUSED,
        PLAY_ALL,
        PLAY_SINGLE
    }

}