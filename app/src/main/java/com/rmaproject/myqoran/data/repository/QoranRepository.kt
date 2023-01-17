package com.rmaproject.myqoran.data.repository

import com.rmaproject.myqoran.data.local.QoranDatabase
import com.rmaproject.myqoran.data.local.entities.Juz
import com.rmaproject.myqoran.data.local.entities.Page
import com.rmaproject.myqoran.data.local.entities.Qoran
import com.rmaproject.myqoran.data.local.entities.Surah
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
    fun getReadQoran(pageNumber: Int) : Flow<List<Qoran>> {
        return db.dao().readQuranByPage(pageNumber)
    }
}