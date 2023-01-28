package com.rmaproject.myqoran.data.repository

import com.rmaproject.myqoran.data.local.QoranDatabase
import com.rmaproject.myqoran.data.local.entities.Juz
import com.rmaproject.myqoran.data.local.entities.Page
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.local.entities.Surah
import com.rmaproject.myqoran.utils.Queries
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QoranRepository @Inject constructor(
    private val db: QoranDatabase
) {
    fun getQoranIndexBySurah(): Flow<List<Surah>> {
        return db.dao().showQuranIndexBySurah()
    }

    fun getQoranIndexByJuz(): Flow<List<Juz>> {
        return db.dao().showQuranIndexByJuz()
    }

    fun getQoranIndexByPage(): Flow<List<Page>> {
        return db.dao().showQuranIndexByPage()
    }

    fun getReadQoranByPage(pageNumber: Int): Flow<List<Qoran>> {
        val query = Queries.getReadByPageQuery(pageNumber)
        return db.dao().readQuranByPage(query)
    }

    fun getReadQoranByJuz(juzNumber: Int): Flow<List<Qoran>> {
        val query = Queries.getReadByJuzQuery(juzNumber)
        return db.dao().readQuranByJuz(query)
    }

    fun getReadQoranBySurah(surahNumber: Int): Flow<List<Qoran>> {
        val query = Queries.getReadBySurahQuery(surahNumber)
        return db.dao().readQuranBySurah(query)
    }

    fun getSurahList(): Flow<List<Qoran>> {
        return db.dao().getSurahList()
    }

    fun getJuzList(): Flow<List<Qoran>> {
        return db.dao().getJuzList()
    }

    fun getPageList(): Flow<List<Qoran>> {
        return db.dao().getPageList()
    }

}