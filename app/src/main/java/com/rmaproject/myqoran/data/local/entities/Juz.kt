package com.rmaproject.myqoran.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView("SELECT MIN(id) AS id, jozz, sora, aya_text, sora_name_ar, sora_name_en, aya_no FROM quran GROUP BY jozz ORDER BY id ASC")
data class Juz(

    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = "jozz") val juzNumber: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0,
    @ColumnInfo(name = "aya_text") val TextQuran: String? = "",
    @ColumnInfo(name = "sora_name_en") val SurahName_en: String? = "",
    @ColumnInfo(name = "sora_name_ar") val SurahName_ar: String? = "",
    @ColumnInfo(name = "aya_no") val nomorAyah: Int? = 0,
    @ColumnInfo(name = "ayah_total") val numberOfAyah: Int? = 0
)