package com.rmaproject.myqoran.ui.screen.read.events

import android.content.Context
import com.rmaproject.myqoran.data.local.entities.Qoran

sealed class ReadQoranEvent {
    data class CopyAyah(
        val context: Context,
        val surahName: String,
        val ayahText: String,
        val translation: String
    ) : ReadQoranEvent()

    data class ShareAyah(
        val context: Context,
        val surahName: String,
        val ayahText: String,
        val translation: String
    ) : ReadQoranEvent()

    data class PlayAyah(
        val ayahNumber: Int,
        val surahNumber: Int,
        val surahName: String
    ) : ReadQoranEvent()

    data class PlayAllAyah(
        val qoranList: List<Qoran>,
        val surahName: String
    ): ReadQoranEvent()

    data class SaveBookmark(
        val surahName: String,
        val surahNumber: Int?,
        val ayahNumber: Int,
        val juzNumber: Int?,
        val pageNumber: Int?,
        val position: Int,
        val qoranTextAr: String,
        val indexType: Int
    ) : ReadQoranEvent()

    data class ChangePage(val newPage: Int) : ReadQoranEvent()
    data class GetNewAyah(val ayahList: List<Qoran>) : ReadQoranEvent()
    data class SetCurrentReading(val currentReading: String) : ReadQoranEvent()
}