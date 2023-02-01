package com.rmaproject.myqoran.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.PrimaryKey

@DatabaseView(
    value = "SELECT sora, sora_name_ar, sora_name_en, sora_name_id, sora_name_emlaey, COUNT(id) as ayah_total, sora_descend_place FROM quran GROUP by sora",
    viewName = "search_surah_result"
)
data class SearchSurahResult(
    @PrimaryKey val id: Int? = 0,
    @ColumnInfo(name = "sora") val surahNumber: Int? = 0,
    @ColumnInfo(name = "sora_name_ar") val surahNameAr: String? = "",
    @ColumnInfo(name = "sora_name_en") val surahNameEn: String? = "",
    @ColumnInfo(name = "ayah_total") val numberOfAyah: Int? = 0,
    @ColumnInfo(name = "sora_name_emlaey") val surahNameEmlaey: String? = "",
    @ColumnInfo(name = "sora_descend_place") val turunSurah: String? = "",
    @ColumnInfo(name = "sora_name_id") val surahNameId: String? = ""
)