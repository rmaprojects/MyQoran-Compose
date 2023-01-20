package com.rmaproject.myqoran.utils

import androidx.sqlite.db.SimpleSQLiteQuery

object Queries {
    fun getReadBySurahQuery(surahNumber: Int): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT id, sora, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, sora_name_ar, sora_name_en, footnotes_id, translation_en, footnotes_en, sora_descend_place FROM quran WHERE sora = ")
        simpleQuery.append(surahNumber)
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getReadByJuzQuery(juzNumber: Int): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT id, sora, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, sora_name_ar, sora_name_en, footnotes_id, translation_en, footnotes_en, sora_descend_place FROM quran WHERE jozz = ")
        simpleQuery.append(juzNumber)
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getReadByPageQuery(pageNumber: Int): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT id, sora, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, sora_name_ar, sora_name_en, footnotes_id, translation_en, footnotes_en, sora_descend_place FROM quran WHERE page = ")
        simpleQuery.append(pageNumber)
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}