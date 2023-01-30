package com.rmaproject.myqoran.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rmaproject.myqoran.ui.screen.home.ORDER_BY_SURAH

@Entity(tableName = "Bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "sora_name_en") val surahName: String? = "",
    @ColumnInfo(name = "aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0,
    @ColumnInfo(name = "jozz") val juzNumber: Int? = 0,
    @ColumnInfo(name = "page") val pageNumber: Int? = 0,
    @ColumnInfo(name = "position") val positionScroll: Int? = 0,
    @ColumnInfo(name = "aya_text") val textQoran: String? = "",
    val indexType: Int? = ORDER_BY_SURAH,
    val timeStamp: Long = System.currentTimeMillis(),
)
