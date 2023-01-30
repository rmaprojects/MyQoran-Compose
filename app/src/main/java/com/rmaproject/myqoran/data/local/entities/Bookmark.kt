package com.rmaproject.myqoran.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Bookmark")
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "sora_name_en") val surahName:String? = "",
    @ColumnInfo(name = "aya_no") val ayahNumber:Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber:Int? = 0,
    @ColumnInfo(name = "position") val positionScroll:Int? = 0,
    @ColumnInfo(name = "aya_text") val textQuran:String? = "",
    val timeStamp:String,
    val timeAdded:Long
)
