package com.rmaproject.myqoran.data.local

import androidx.room.Dao
import androidx.room.Query
import com.rmaproject.myqoran.data.local.entities.Juz
import com.rmaproject.myqoran.data.local.entities.Page
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.local.entities.Surah
import kotlinx.coroutines.flow.Flow

@Dao
interface QoranDao {
    @Query("SELECT * FROM Surah")
    fun showQuranIndexBySurah(): Flow<List<Surah>>

    @Query("SELECT * FROM Juz")
    fun showQuranIndexByJuz(): Flow<List<Juz>>

    @Query("SELECT * FROM Page")
    fun showQuranIndexByPage(): Flow<List<Page>>

    @Query("SELECT sora, jozz, aya_no, aya_text, aya_text_emlaey, translation_id, sora_name_ar, sora_name_en, footnotes_id, translation_en, footnotes_en, sora_descend_place FROM quran WHERE page = :pageNumber")
    fun readQuranByPage(pageNumber: Int): Flow<List<Qoran>>
}