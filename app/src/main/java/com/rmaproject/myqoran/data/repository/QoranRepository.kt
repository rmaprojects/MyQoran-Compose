package com.rmaproject.myqoran.data.repository

import com.rmaproject.myqoran.data.local.BookmarkDatabase
import com.rmaproject.myqoran.data.local.QoranDatabase
import com.rmaproject.myqoran.data.local.entities.Juz
import com.rmaproject.myqoran.data.local.entities.Page
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.local.entities.Surah
import com.rmaproject.myqoran.utils.Queries
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QoranRepository @Inject constructor(
    private val qoranDatabase: QoranDatabase,
    private val bookmarkDatabase: BookmarkDatabase
) {
    fun getQoranIndexBySurah(): Flow<List<Surah>> {
        return qoranDatabase.qoranDao().showQuranIndexBySurah()
    }

    fun getQoranIndexByJuz(): Flow<List<Juz>> {
        return qoranDatabase.qoranDao().showQuranIndexByJuz()
    }

    fun getQoranIndexByPage(): Flow<List<Page>> {
        return qoranDatabase.qoranDao().showQuranIndexByPage()
    }

    fun getReadQoranByPage(pageNumber: Int): Flow<List<Qoran>> {
        val query = Queries.getReadByPageQuery(pageNumber)
        return qoranDatabase.qoranDao().readQuranByPage(query)
    }

    fun getReadQoranByJuz(juzNumber: Int): Flow<List<Qoran>> {
        val query = Queries.getReadByJuzQuery(juzNumber)
        return qoranDatabase.qoranDao().readQuranByJuz(query)
    }

    fun getReadQoranBySurah(surahNumber: Int): Flow<List<Qoran>> {
        val query = Queries.getReadBySurahQuery(surahNumber)
        return qoranDatabase.qoranDao().readQuranBySurah(query)
    }

    fun getSurahList(): Flow<List<Qoran>> {
        return qoranDatabase.qoranDao().getSurahList()
    }

    fun getJuzList(): Flow<List<Qoran>> {
        return qoranDatabase.qoranDao().getJuzList()
    }

    fun getPageList(): Flow<List<Qoran>> {
        return qoranDatabase.qoranDao().getPageList()
    }
}