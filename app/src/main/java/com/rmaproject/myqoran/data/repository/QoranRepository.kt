package com.rmaproject.myqoran.data.repository

import com.rmaproject.myqoran.data.local.BookmarkDatabase
import com.rmaproject.myqoran.data.local.QoranDatabase
import com.rmaproject.myqoran.data.local.entities.*
import com.rmaproject.myqoran.data.remote.model.AdzanScheduleResponse
import com.rmaproject.myqoran.data.remote.service.ApiInterface
import com.rmaproject.myqoran.utils.Queries
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QoranRepository @Inject constructor(
    private val qoranDatabase: QoranDatabase,
    private val api: ApiInterface,
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

    fun searchSurah(query: String): Flow<List<SearchSurahResult>> {
        return qoranDatabase.qoranDao().searchSurah(query)
    }

    fun searchAyah(query: String): Flow<List<Qoran>> {
        return qoranDatabase.qoranDao().searchEntireQuran(query)
    }

    fun getBookmarks(): Flow<List<Bookmark>> {
        return bookmarkDatabase.bookmarkDao().getBookmarks()
    }

    suspend fun insertBookmark(bookmark: Bookmark) {
        bookmarkDatabase.bookmarkDao().insertBookmark(bookmark)
    }

    suspend fun deleteBookmark(bookmark: Bookmark) {
        bookmarkDatabase.bookmarkDao().deleteBookmark(bookmark)
    }

    suspend fun deleteAllBookmark() {
        bookmarkDatabase.bookmarkDao().deleteAllBookmark()
    }

    suspend fun getAdzanSchedule(
        latitude: String,
        longitude: String
    ): AdzanScheduleResponse {
        return api.getAdzanSchedule(latitude, longitude)
    }
}