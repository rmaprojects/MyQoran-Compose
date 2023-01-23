package com.rmaproject.myqoran.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quran")
data class Qoran(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "jozz") val juzNumber: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0, //Surah Number
    @ColumnInfo(name = "sora_name_en") val surahNameEn: String? = "",
    @ColumnInfo(name = "sora_name_ar") val surahNameAr: String? = "",
    @ColumnInfo(name = "page") val page: Int? = 0,
    @ColumnInfo(name = "aya_no") val ayahNumber: Int? = 0,
    @ColumnInfo(name = "aya_text") val ayahText: String? = "",
    @ColumnInfo(name = "aya_text_emlaey") val textQuranSearch: String? = "",
    @ColumnInfo(name = "sora_name_id") val surahNameId: String? = "",
    @ColumnInfo(name = "sora_descend_place") val surahDescendPlace: String? = "",
    @ColumnInfo(name = "sora_name_emlaey") val textSurahSearch: String? = "",
    val translation_id: String? = "",
    val footnotes_id: String? = "",
    val translation_en: String? = "",
    val footnotes_en: String? = "",
)
