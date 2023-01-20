package com.rmaproject.myqoran.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
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

    @RawQuery(observedEntities = [Qoran::class])
    fun readQuranBySurah(query: SupportSQLiteQuery): Flow<List<Qoran>>

    @RawQuery(observedEntities = [Qoran::class])
    fun readQuranByJuz(query: SupportSQLiteQuery): Flow<List<Qoran>>

    @RawQuery(observedEntities = [Qoran::class])
    fun readQuranByPage(query: SupportSQLiteQuery): Flow<List<Qoran>>
}